package br.usp.poli.pcs.lti.jmetalhhhelper.imp.algs;

import br.usp.poli.pcs.lti.jmetalhhhelper.core.DoubleTaggedSolution;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.OpManager;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.PermutationTaggedSolution;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.TaggedSolution;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.interfaces.ArchivedMetaheuristic;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2;
import org.uma.jmetal.algorithm.multiobjective.spea2.util.EnvironmentalSelection;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.DefaultDoubleSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerPermutationSolution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

/**
 * This class extends the algorithm from jMetal and implements operations
 * necessary for algorithm transitions.
 */

/**
 * The type Spea 2.
 *
 * @param <S> jMetal need.
 */
@SuppressWarnings("serial")
public class Spea2<S extends Solution<?>> extends SPEA2<S> implements
    ArchivedMetaheuristic<S> {

  /**
   * Low-Level Heuristic Selector.
   */
  protected OpManager selector;
  /**
   * Environmental Selection necessary because of constant population resize.
   */
  protected EnvironmentalSelection<S> myenvironmentalSelection;
  /**
   * Archive size.
   */
  protected int archiveSize;

  /**
   * Instantiates a new Spea 2.
   *
   * @param problem the problem
   * @param maxIterations the max iterations
   * @param populationSize the population size
   * @param crossoverOperator the crossover operator
   * @param mutationOperator the mutation operator
   * @param selectionOperator the selection operator
   * @param evaluator the evaluator
   */
  public Spea2(Problem<S> problem, int maxIterations, int populationSize,
      CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
      SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator) {
    super(problem, maxIterations, populationSize, crossoverOperator, mutationOperator,
        selectionOperator, evaluator);
    archiveSize = populationSize;
    selector = new OpManager();
    selector.setCrossoverOperator(crossoverOperator);
    selector.setMutationOperator(mutationOperator);
    this.myenvironmentalSelection = new EnvironmentalSelection<>(populationSize);
  }

  /*Necessary because of myenvironmentalSelection resize*/
  @Override
  protected List<S> selection(List<S> population) {
    List<S> union = new ArrayList<>(2 * getMaxPopulationSize());
    union.addAll(archive);
    union.addAll(population);
    strenghtRawFitness.computeDensityEstimator(union);
    archive = myenvironmentalSelection.execute(union);
    return archive;
  }

  @Override
  public List<S> updateMainPopulation(List<S> offspringPopulation) {
    population = replacement(population, offspringPopulation);
    selection(population);
    return archive;
  }

  @Override
  protected List<S> reproduction(List<S> population) {
    selector.selectOp();
    crossoverOperator = selector.getCrossoverOperator();
    mutationOperator = selector.getMutationOperator();
    List<S> offspringPopulation = new ArrayList<>(getMaxPopulationSize());

    while (offspringPopulation.size() < getMaxPopulationSize()) {
      List<S> parents = new ArrayList<>(2);
      S candidateFirstParent = selectionOperator.execute(population);
      parents.add(candidateFirstParent);
      S candidateSecondParent;
      candidateSecondParent = selectionOperator.execute(population);
      parents.add(candidateSecondParent);

      List<S> offspring = crossoverOperator.execute(parents);

      S s = offspring.get(0);
      mutationOperator.execute(s);
      TaggedSolution s2;
      if (problem instanceof AbstractDoubleProblem) {
        s2 = new DoubleTaggedSolution((DefaultDoubleSolution) s);
      } else {
        s2 = new PermutationTaggedSolution((DefaultIntegerPermutationSolution) s);
      }
      selector.assignTag(parents, s2, this);
      offspringPopulation.add((S) s2);

      selector.selectOp();
      crossoverOperator = selector.getCrossoverOperator();
      mutationOperator = selector.getMutationOperator();
    }
    return offspringPopulation;
  }

  /**
   * Generate a new Environmental Selection with a given size.
   *
   * @param size the size
   */
  public void setEnvironmentalSelectionSize(int size) {
    this.myenvironmentalSelection = new EnvironmentalSelection<>(size);
  }

  @Override
  public List<S> executeMethod() {
    List<S> offspringPopulation = reproduction(archive);
    offspringPopulation = evaluatePopulation(offspringPopulation);
    return offspringPopulation;
  }

  @Override
  public void generateNewPopulation() {
    List<S> offspringPopulation = this.executeMethod();
    population = replacement(population, offspringPopulation);
    selection(population);
    updateProgress();
  }

  @Override
  public int getIterations() {
    return this.iterations;
  }

  @Override
  public void setIterations(int iterations) {
    this.iterations = iterations;
  }

  @Override
  public void setCrossoverOperator(CrossoverOperator crossoverOperator) {
    this.crossoverOperator = crossoverOperator;
    selector.setCrossoverOperator(crossoverOperator);
  }

  @Override
  public void setMutationOperator(MutationOperator mutationOperator) {
    this.mutationOperator = mutationOperator;
    selector.setMutationOperator(mutationOperator);
  }

  @Override
  public void initMetaheuristic() {
    this.population = new ArrayList<>();
    setPopulation(createInitialPopulation());
    setPopulation(evaluatePopulation(getPopulation()));
    selection(population);
    initProgress();
  }

  @Override
  public List<S> getRealpop() {
    return getArchive();
  }

  @Override
  public int getPopulationSize() {
    return this.maxPopulationSize;
  }

  @Override
  public void setPopulationSize(int populationSize) {
    this.maxPopulationSize = populationSize;
  }

  @Override
  public int getMaxEvaluations() {
    return this.iterations * maxPopulationSize;
  }

  @Override
  public int getMaxIterations() {
    return maxIterations;
  }

  @Override
  public void run() {
    this.initMetaheuristic();
    while (!isStoppingConditionReached()) {
      this.generateNewPopulation();
    }
  }

  @Override
  protected boolean isStoppingConditionReached() {
    return iterations >= maxIterations;
  }

  @Override
  public List<S> getArchive() {
    return this.archive;
  }

  @Override
  public void setArchive(List<S> pop) {
    this.archive = pop;
  }

  @Override
  public List<S> getPopulation() {
    return population;
  }

  @Override
  public void setPopulation(List<S> pop) {
    this.population = pop;
  }

  @Override
  public int getArchiveSize() {
    return archiveSize;
  }

  @Override
  public void setArchiveSize(int archiveSize) {
    this.archiveSize = archiveSize;
  }

  @Override
  public OpManager getSelector() {
    return selector;
  }

  @Override
  public void setSelector(OpManager selector) {
    this.selector = selector;
  }
}

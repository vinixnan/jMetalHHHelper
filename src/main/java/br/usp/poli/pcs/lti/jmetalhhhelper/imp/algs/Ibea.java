package br.usp.poli.pcs.lti.jmetalhhhelper.imp.algs;

import br.usp.poli.pcs.lti.jmetalhhhelper.core.DoubleTaggedSolution;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.OpManager;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.PermutationTaggedSolution;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.TaggedSolution;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.interfaces.ArchivedMetaheuristic;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.multiobjective.ibea.IBEA;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.DefaultDoubleSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerPermutationSolution;

/**
 * This class extends the algorithm from jMetal and implements operations
 * necessary for algorithm transitions.
 */

/**
 * The type Ibea.
 *
 * @param <S> jMetal need.
 */
@SuppressWarnings("serial")
public class Ibea<S extends Solution<?>> extends IBEA<S> implements
    ArchivedMetaheuristic<S> {

  /**
   * Low-Level Heuristic Selector.
   */
  protected OpManager selector;
  /**
   * OffSpring population.
   */
  protected List<S> offSpringSolutionSet;
  /**
   * Current evaluations.
   */
  protected int evaluations;
  /**
   * Current iteration.
   */
  protected int iterations;
  /**
   * Max Number of iterations.
   */
  protected int maxIterations;

  /**
   * Instantiates a new Ibea.
   *
   * @param problem the problem
   * @param populationSize the population size
   * @param archiveSize the archive size
   * @param maxEvaluations the max evaluations
   * @param selectionOperator the selection operator
   * @param crossoverOperator the crossover operator
   * @param mutationOperator the mutation operator
   */
  public Ibea(Problem<S> problem, int populationSize, int archiveSize, int maxEvaluations,
      SelectionOperator<List<S>, S> selectionOperator, CrossoverOperator<S> crossoverOperator,
      MutationOperator<S> mutationOperator) {
    super(problem, populationSize, archiveSize, maxEvaluations, selectionOperator,
        crossoverOperator, mutationOperator);
    selector = new OpManager();
    selector.setCrossoverOperator(crossoverOperator);
    selector.setMutationOperator(mutationOperator);
    maxIterations = maxEvaluations / populationSize;
  }

  /**
   * Reproduction list.
   *
   * @param population the population
   * @return the list
   */
  protected List<S> reproduction(List<S> population) {
    offSpringSolutionSet = new ArrayList<>(getPopulationSize());
    S parent1;
    S parent2;
    while (offSpringSolutionSet.size() < getPopulationSize()) {
      selector.selectOp();
      crossoverOperator = selector.getCrossoverOperator();
      mutationOperator = selector.getMutationOperator();
      int j = 0;
      do {
        j++;
        parent1 = selectionOperator.execute(population);
      } while (j < Ibea.TOURNAMENTS_ROUNDS);
      int k = 0;
      do {
        k++;
        parent2 = selectionOperator.execute(population);
      } while (k < Ibea.TOURNAMENTS_ROUNDS);

      List<S> parents = new ArrayList<>(2);
      parents.add(parent1);
      parents.add(parent2);

      //make the crossover
      List<S> offspring = crossoverOperator.execute(parents);
      S s = offspring.get(0);
      mutationOperator.execute(s);
      TaggedSolution s2;
      if (problem instanceof AbstractDoubleProblem) {
        s2 = new DoubleTaggedSolution((DefaultDoubleSolution) s);
      } else {
        s2 = new PermutationTaggedSolution((DefaultIntegerPermutationSolution) s);
      }
      problem.evaluate((S) s2);
      selector.assignTag(parents, s2, this);
      offSpringSolutionSet.add((S) s2);
      evaluations++;
    }
    return offSpringSolutionSet;
  }

  /**
   * Selection list.
   *
   * @param population the population
   * @return the list
   */
  protected List<S> selection(List<S> population) {
    List<S> union = new ArrayList<>();
    union.addAll(population);
    union.addAll(archive);
    calculateFitness(union);
    archive = union;
    while (archive.size() > populationSize) {
      removeWorst(archive);
    }
    return archive;
  }

  @Override
  public List<S> executeMethod() {
    reproduction(archive);
    selection(offSpringSolutionSet);
    return offSpringSolutionSet;
  }

  @Override
  public void run() {
    this.initMetaheuristic();
    while (evaluations < maxEvaluations && iterations < maxIterations) {
      this.generateNewPopulation();
    }
  }

  /**
   * Evaluate population hypervolume contribution.
   *
   * @param list population.
   * @return the same population.
   */
  protected List<S> evaluatePopulation(List<S> list) {
    calculateFitness(list);
    return list;
  }

  @Override
  public void generateNewPopulation() {
    this.executeMethod();
    iterations++;
  }

  @Override
    public List<S> updateMainPopulation(List<S> matingPopulation) {
        archive=selection(matingPopulation);
        return archive;
    }

  @Override
  public int getMaxEvaluations() {
    return this.maxEvaluations;
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
    this.selector.setCrossoverOperator(crossoverOperator);
  }

  @Override
  public void setMutationOperator(MutationOperator mutationOperator) {
    this.mutationOperator = mutationOperator;
    this.selector.setMutationOperator(mutationOperator);
  }

  @Override
  public void initMetaheuristic() {
    //Initialize the variables
    offSpringSolutionSet = new ArrayList<>(getPopulationSize());
    archive = new ArrayList<>(archiveSize);
    evaluations = 0;
    iterations = 0;
    //-> Create the initial solutionSet
    S newSolution;
    for (int i = 0; i < getPopulationSize(); i++) {
      newSolution = problem.createSolution();
      problem.evaluate(newSolution);
      evaluations++;
      offSpringSolutionSet.add(newSolution);
    }
    selection(offSpringSolutionSet);
    iterations++;
  }

  @Override
  public List<S> getRealpop() {
    return getArchive();
  }

  @Override
  public int getPopulationSize() {
    return this.populationSize;
  }

  @Override
  public void setPopulationSize(int populationSize) {
    this.populationSize = populationSize;
  }

  @Override
  public int getMaxIterations() {
    return maxIterations;
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
    return offSpringSolutionSet;
  }

  @Override
  public void setPopulation(List<S> pop) {
    this.offSpringSolutionSet = pop;
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

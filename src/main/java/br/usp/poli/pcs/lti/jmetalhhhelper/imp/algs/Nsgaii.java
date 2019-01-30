package br.usp.poli.pcs.lti.jmetalhhhelper.imp.algs;

import br.usp.poli.pcs.lti.jmetalhhhelper.core.DoubleTaggedSolution;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.OpManager;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.PermutationTaggedSolution;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.TaggedSolution;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.interfaces.StandardMetaheuristic;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
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
 * The type Nsgaii.
 *
 * @param <S> jMetal need.
 */
@SuppressWarnings("serial")
public class Nsgaii<S extends Solution<?>> extends NSGAII<S> implements
    StandardMetaheuristic<S> {

  /**
   * Low-Level Heuristic Selector.
   */
  protected OpManager selector;
  /**
   * Current iteration.
   */
  protected int iterations;
  /**
   * Max Number of iterations.
   */
  protected int maxIterations;
  
  protected @Getter @Setter List<S> offspringPopulation;

  /**
   * Instantiates a new Nsgaii.
   *
   * @param problem the problem
   * @param maxEvaluations the max evaluations
   * @param populationSize the population size
   * @param crossoverOperator the crossover operator
   * @param mutationOperator the mutation operator
   * @param selectionOperator the selection operator
     * @param dominanceComparator
   * @param evaluator the evaluator
   */
  public Nsgaii(Problem<S> problem, int maxEvaluations, int populationSize,
      CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
      SelectionOperator<List<S>, S> selectionOperator, Comparator<S> dominanceComparator, SolutionListEvaluator<S> evaluator) {
    super(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator, selectionOperator, dominanceComparator, evaluator);
    selector = new OpManager();
    selector.setCrossoverOperator(crossoverOperator);
    selector.setMutationOperator(mutationOperator);
    maxIterations = maxEvaluations / populationSize;
  }

  @Override
  protected List<S> reproduction(List<S> population) {

    selector.selectOp();
    crossoverOperator = selector.getCrossoverOperator();
    mutationOperator = selector.getMutationOperator();
    int numberOfParents = crossoverOperator.getNumberOfRequiredParents();

    //checkNumberOfParents(population, numberOfParents);//generates just one, no need
    offspringPopulation = new ArrayList<>();
    for (int i = 0; i < getMaxPopulationSize(); i++) {
      List<S> parents = new ArrayList<>(numberOfParents);
      SecureRandom rdn = new SecureRandom();
      for (int j = 0; j < numberOfParents; j++) {
        parents.add(population.get(rdn.nextInt(getMaxPopulationSize())));
      }

      List<S> offspring = crossoverOperator.execute(parents);
      S s = offspring.get(0);
      mutationOperator.execute(s);
      TaggedSolution s2;
      if (problem instanceof AbstractDoubleProblem) {
        s2 = new DoubleTaggedSolution((DefaultDoubleSolution) offspring.get(0));
      } else {
        s2 = new PermutationTaggedSolution((DefaultIntegerPermutationSolution) offspring.get(0));
      }
      selector.assignTag(parents, s2, this);
      offspringPopulation.add((S) s2);

      selector.selectOp();
      crossoverOperator = selector.getCrossoverOperator();
      mutationOperator = selector.getMutationOperator();
      numberOfParents = crossoverOperator.getNumberOfRequiredParents();
      //checkNumberOfParents(population, numberOfParents);//generates just one, no need
    }
    return offspringPopulation;
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
    return evaluations >= maxEvaluations && iterations >= maxIterations;
  }

  @Override
  public void generateNewPopulation() {
    this.executeMethod();
    iterations++;
  }

  @Override
  public List<S> updateMainPopulation(List<S> matingPopulation) {
    population = replacement(population, matingPopulation);
    return population;
  }

  @Override
  public List<S> executeMethod() {
    List<S> matingPopulation = selection(population);
    offspringPopulation = reproduction(matingPopulation);
    offspringPopulation = evaluatePopulation(offspringPopulation);
    population = replacement(population, offspringPopulation);
    updateProgress();
    return population;
  }

  @Override
  public void initMetaheuristic() {
    this.population = new ArrayList<>();
    setPopulation(createInitialPopulation());
    setPopulation(evaluatePopulation(getPopulation()));
    initProgress();
    iterations = 1;
  }

  @Override
  public List<S> getRealpop() {
    return population;
  }

  @Override
  public int getPopulationSize() {
    return maxPopulationSize;
  }

  @Override
  public void setPopulationSize(int populationSize) {
    maxPopulationSize = populationSize;
  }

  @Override
  public int getMaxEvaluations() {
    return maxEvaluations;
  }

  @Override
  public void setCrossoverOperator(CrossoverOperator<S> crossoverOperator) {
    this.crossoverOperator = crossoverOperator;
    selector.setCrossoverOperator(crossoverOperator);
  }

  @Override
  public void setMutationOperator(MutationOperator<S> mutationOperator) {
    this.mutationOperator = mutationOperator;
    selector.setMutationOperator(mutationOperator);
  }

  @Override
  public int getIterations() {
    return iterations;
  }

  @Override
  public void setIterations(int iterations) {
    this.iterations = iterations;
  }

  @Override
  public int getMaxIterations() {
    return maxIterations;
  }

  @Override
  public List<S> getPopulation() {
    return population;
  }

  @Override
  public void setPopulation(List<S> population) {
    this.population = population;
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

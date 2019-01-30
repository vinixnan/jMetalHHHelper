package br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation;

import br.usp.poli.pcs.lti.jmetalhhhelper.core.interfaces.Operator;

import java.util.HashMap;
import java.util.Map;

import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.solution.util.RepairDoubleSolution;

/**
 * The type Integer polynomial muta.
 * This class extends the jMetal mutation class and implements Operator
 * interface. This was         created to use polymorphism without changes jMetal mutation
 * class.
 */
@SuppressWarnings("serial")
public class IntegerPolynomialMuta extends IntegerPolynomialMutation implements
    Operator {

  /**
   * Map which contains all operator parameters.
   */
  protected Map<String, Object> parameters;

  /**
   * Default constructor.
   */
  public IntegerPolynomialMuta() {
    parameters = new HashMap<>();
  }

  /**
   * Instantiates a new Integer polynomial muta.
   *
   * @param problem the problem
   * @param distributionIndex the distribution index
   */
  public IntegerPolynomialMuta(IntegerProblem problem, double distributionIndex) {
    super(problem, distributionIndex);
    parameters = new HashMap<>();
  }

  /**
   * Instantiates a new Integer polynomial muta.
   *
   * @param mutationProbability the mutation probability
   * @param distributionIndex the distribution index
   */
  public IntegerPolynomialMuta(double mutationProbability, double distributionIndex) {
    super(mutationProbability, distributionIndex);
    parameters = new HashMap<>();
  }

  /**
   * Instantiates a new Integer polynomial muta.
   *
   * @param mutationProbability the mutation probability
   * @param distributionIndex the distribution index
   * @param solutionRepair the solution repair
   */
  public IntegerPolynomialMuta(double mutationProbability, double distributionIndex,
      RepairDoubleSolution solutionRepair) {
    super(mutationProbability, distributionIndex, solutionRepair);
    parameters = new HashMap<>();
  }

  @Override
  public void allocateParameters() {
    this.setDistributionIndex((double) this.parameters.get("distributionIndex"));
    this.setMutationProbability((double) this.parameters.get("probability"));
  }

  @Override
  public void setParameter(String attributeName, Object value) {
    this.parameters.put(attributeName, value);
  }

  @Override
  public Object getParameter(String attributeName) {
    return this.parameters.getOrDefault(attributeName, null);
  }

  @Override
  public Map<String, Object> getParameters() {
    return parameters;
  }

  @Override
  public void setParameters(Map<String, Object> parameters) {
    this.parameters = parameters;
  }
}

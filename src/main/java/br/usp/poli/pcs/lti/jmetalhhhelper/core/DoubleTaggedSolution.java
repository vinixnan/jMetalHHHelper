package br.usp.poli.pcs.lti.jmetalhhhelper.core;

import lombok.Getter;
import lombok.Setter;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.DefaultDoubleSolution;

/**
 * This class extends the jMetal class DefaultDoubleSolution and implements TaggedSolution.
 * So, It contains methods used to associate a DefaultDoubleSolution with an algorithm and
 * LowLevelHeuristic.
 */
public class DoubleTaggedSolution extends DefaultDoubleSolution implements TaggedSolution {

  private static final long serialVersionUID = -849648866550315279L;

  /**
   * The associated algorithm.
   */
  private @Getter @Setter Algorithm algorithm;

  /**
   * The associated Low-Level Heuristic.
   */
  private @Getter @Setter LowLevelHeuristic action;
  
  private @Getter @Setter int parentNeighborType;//-1 NOTHING 0=NEIGHBOR 1=POPULATION
  
  private @Getter @Setter int subProblemId;//-1 to NOTHING
  
  private @Getter @Setter boolean newSolution;

  /**
   * @param problem a jMetal DoubleProblem.
   */
  public DoubleTaggedSolution(DoubleProblem problem) {
    super(problem);
    this.algorithm = null;
    this.action = null;
    this.parentNeighborType=-1;
    this.subProblemId=-1;
    this.newSolution=false;
  }

  /**
   * @param solution a jMetal DefaultDoubleSolution.
   */
  public DoubleTaggedSolution(DefaultDoubleSolution solution) {
    super(solution);
    this.algorithm = null;
    this.action = null;
    this.parentNeighborType=-1;
    this.subProblemId=-1;
    this.newSolution=false;
  }
  
  /**
   * @param solution a jMetal DefaultDoubleSolution.
   */
  public DoubleTaggedSolution(DoubleTaggedSolution solution) {
    super(solution);
    this.algorithm = solution.getAlgorithm();
    this.action = solution.getAction();
    this.parentNeighborType=solution.getParentNeighborType();
    this.subProblemId=solution.getParentNeighborType();
    this.newSolution=solution.isNewSolution();
  }
  
  @Override
  public Object getTheVariableValue(int i) {
    return super.getVariableValue(i);
  }
  
  @Override
  public DoubleTaggedSolution copy() {
    return new DoubleTaggedSolution(this);
  }
  
  @Override
    public String strVariables() {
        String str="";
        for (int i = 0; i < this.getNumberOfVariables(); i++) {
            str+=String.valueOf(getVariableValue(i))+".";
        }
        return str;
    }
}

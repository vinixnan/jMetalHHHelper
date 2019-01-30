package br.usp.poli.pcs.lti.jmetalhhhelper.core;

import lombok.Getter;
import lombok.Setter;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.PermutationProblem;
import org.uma.jmetal.solution.impl.DefaultIntegerPermutationSolution;

/**
 * This class extends the jMetal class DefaultIntegerPermutationSolution and implements
 * TaggedSolution. So, It contains methods used to associate a DefaultIntegerPermutationSolution
 * with an algorithm and LLH.
 */

/**
 * @param <T> necessary because of jMetal.
 */
public class PermutationTaggedSolution<T extends Object> extends
    DefaultIntegerPermutationSolution implements TaggedSolution {

  /**
   * The associated algorithm.
   */
  private Algorithm algorithm;

  /**
   * The associated Low-Level Heuristic.
   */
  private LowLevelHeuristic action;
  
  private @Getter @Setter int subProblemId;//-1 to NOTHING
  
  private @Getter @Setter boolean newSolution;

  /**
   * @param problem a Permutation Problem.
   */
  public PermutationTaggedSolution(PermutationProblem<?> problem) {
    super(problem);
    algorithm = null;
    action = null;
    this.subProblemId=-1;
    this.newSolution=false;
  }

  /**
   * @param solution a DefaultIntegerPermutationSolution solution.
   */
  public PermutationTaggedSolution(DefaultIntegerPermutationSolution solution) {
    super(solution);
    algorithm = null;
    action = null;
    this.subProblemId=-1;
    this.newSolution=false;
  }


  @Override
  public LowLevelHeuristic getAction() {
    return action;
  }


  @Override
  public void setAction(LowLevelHeuristic action) {
    this.action = action;
  }


  @Override
  public Algorithm getAlgorithm() {
    return algorithm;
  }


  @Override
  public void setAlgorithm(Algorithm algorithm) {
    this.algorithm = algorithm;
  }


  @Override
  public Object getTheVariableValue(int i) {
    return super.getVariableValue(i);
  }
  
  @Override
  public PermutationTaggedSolution copy() {
    return new PermutationTaggedSolution(this);
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

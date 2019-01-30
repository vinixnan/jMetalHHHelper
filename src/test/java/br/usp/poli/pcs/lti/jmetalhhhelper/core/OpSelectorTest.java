package br.usp.poli.pcs.lti.jmetalhhhelper.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.DefaultDoubleSolution;

/**
 *
 */
public class OpSelectorTest<S extends Solution<?>> {

  private final HashMap maxValues;
  OpSelector ops;

  public OpSelectorTest() {
    ops = Mockito.mock(OpSelector.class, Mockito.CALLS_REAL_METHODS);
    maxValues = new HashMap<>();
    maxValues.put("cross_probability", 1.0);
    maxValues.put("cross_distributionIndex", 100.0);
    maxValues.put("cross_alpha", 1.0);
    maxValues.put("cross_binaryrossoverProbability", 1.0);
    maxValues.put("muta_probability", 1.0);
    maxValues.put("muta_distributionIndex", 100.0);
    maxValues.put("muta_perturbation", 100.0);
    maxValues.put("muta_maxIterations", 100 * 750);
    maxValues.put("muta_binaryMutationProbability", 1.0);
  }

  @Before
  public void generateSomeLLH() {
    ArrayList<LowLevelHeuristic> llhs = new ArrayList<>();
    llhs.add(Mockito.mock(LowLevelHeuristic.class, Mockito.CALLS_REAL_METHODS));
    llhs.add(Mockito.mock(LowLevelHeuristic.class, Mockito.CALLS_REAL_METHODS));
    llhs.add(Mockito.mock(LowLevelHeuristic.class, Mockito.CALLS_REAL_METHODS));
    ops.setLlhs(llhs);
    for (Iterator it = ops.getLlhs().iterator(); it.hasNext(); ) {
      LowLevelHeuristic llh = (LowLevelHeuristic) it.next();
      llh.setSolutionType("Real");
      llh.setMaxValues(maxValues);
      llh.randomize();
    }

  }


  /**
   * Test of equals method, of class OpSelector.
   */
  @Test
  public void testEquals() {
    Problem problem = new WFG1(4, 20, 2);
    DoubleTaggedSolution solution = new DoubleTaggedSolution(
        (DefaultDoubleSolution) problem.createSolution());
    DoubleTaggedSolution copy = new DoubleTaggedSolution((DefaultDoubleSolution) solution);
    assertTrue(ops.equals(solution, copy));
  }

  /**
   * Test of equals method, of class OpSelector.
   */
  @Test
  public void testDifferent() {
    Problem problem = new WFG1(4, 20, 2);
    DoubleTaggedSolution solution = new DoubleTaggedSolution(
        (DefaultDoubleSolution) problem.createSolution());
    DoubleTaggedSolution copy = new DoubleTaggedSolution(
        (DefaultDoubleSolution) problem.createSolution());
    assertFalse(ops.equals(solution, copy));
  }

  /**
   * Test of equals method, of class OpSelector.
   */
  @Test
  public void testOffspringEqualsParent() {
    Problem problem = new WFG1(4, 20, 2);
    DoubleTaggedSolution solution = new DoubleTaggedSolution(
        (DefaultDoubleSolution) problem.createSolution());
    solution.setAction((LowLevelHeuristic) ops.getLlhs().get(0));
    DoubleTaggedSolution copy = new DoubleTaggedSolution((DefaultDoubleSolution) solution);
    copy.setAction((LowLevelHeuristic) ops.getLlhs().get(1));
    List<S> parents = new ArrayList<>();
    parents.add((S) solution);
    parents.add((S) copy);
    DoubleTaggedSolution offspring = new DoubleTaggedSolution(solution);
    LowLevelHeuristic found = ops
        .buildTagCrossover(parents, offspring, (LowLevelHeuristic) ops.getLlhs().get(2));
    assertTrue(found.equals(ops.getLlhs().get(0)));
  }

  @Test
  public void testOffspringDifferentParent() {
    Problem problem = new WFG1(4, 20, 2);
    DoubleTaggedSolution solution = new DoubleTaggedSolution(
        (DefaultDoubleSolution) problem.createSolution());
    solution.setAction((LowLevelHeuristic) ops.getLlhs().get(0));
    DoubleTaggedSolution copy = new DoubleTaggedSolution((DefaultDoubleSolution) solution);
    copy.setAction((LowLevelHeuristic) ops.getLlhs().get(1));
    List<S> parents = new ArrayList<>();
    parents.add((S) solution);
    parents.add((S) copy);
    DoubleTaggedSolution offspring = new DoubleTaggedSolution(
        (DefaultDoubleSolution) problem.createSolution());
    LowLevelHeuristic found = ops
        .buildTagCrossover(parents, offspring, (LowLevelHeuristic) ops.getLlhs().get(2));
    assertFalse(found.equals(ops.getLlhs().get(0)));
  }

}

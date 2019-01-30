package br.usp.poli.pcs.lti.jmetalhhhelper.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import br.usp.poli.pcs.lti.jmetalhhhelper.core.interfaces.Operator;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover.NullCross;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation.NullMuta;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.CrossoverFacade;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.MutationFacade;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 */
public class LowLevelHeuristicTest {

  private LowLevelHeuristic llh;
  private HashMap maxValues;

  @Before
  public void initLlh() {
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
    llh = Mockito.mock(LowLevelHeuristic.class, Mockito.CALLS_REAL_METHODS);
    llh.setSolutionType("Real");
    llh.setSolutionType(llh.getSolutionType());
    llh.setMaxValues(maxValues);
    llh.setMaxValues(llh.getMaxValues());
    llh.randomize();
  }

  /**
   * Test of randomize method, of class LowLevelHeuristic.
   */
  @Test
  public void testRandomize() {
    System.out.println("testRandomize");
    Operator crossover = llh.getCrossover().cloneMyOperator();
    Operator mutation = llh.getMutation().cloneMyOperator();
    llh.randomize();
    boolean v1 = llh.getCrossover().getOp().getParameter("probability") == crossover
        .getParameter("probability");
    boolean v2 = llh.getMutation().getOp().getParameter("probability") == mutation
        .getParameter("probability");
    boolean v3 = llh.getCrossover().getOp().getClass() == crossover.getClass();
    boolean v4 = llh.getMutation().getOp().getClass() == mutation.getClass();
    assertFalse(v1 && v2 && v3 && v4);
  }

  /**
   * Test of getCrossover method, of class LowLevelHeuristic.
   */
  @Test
  public void testRandomizeandSetHash() {
    System.out.println("testRandomizeandSetHash");
    Operator crossover = llh.getCrossover().cloneMyOperator();
    Operator mutation = llh.getMutation().cloneMyOperator();
    HashMap<String, Object> myparameters = (HashMap<String, Object>) llh.allParametersToHash();
    llh.randomize();
    llh.hashToParameter(myparameters);
    boolean v1 = llh.getCrossover().getOp().getParameter("probability") == crossover
        .getParameter("probability");
    boolean v2 = llh.getMutation().getOp().getParameter("probability") == mutation
        .getParameter("probability");
    boolean v3 = llh.getCrossover().getOp().getClass() == crossover.getClass();
    boolean v4 = llh.getMutation().getOp().getClass() == mutation.getClass();
    assertTrue(v1 && v2 && v3 && v4);
  }

  /**
   * Test of getCrossover method, of class LowLevelHeuristic.
   */
  @Test
  public void testFromNullToRandom() {
    System.out.println("testRandomizeandSetHash");
    CrossoverFacade cf = new CrossoverFacade(maxValues, "Real");
    cf.setOp(new NullCross());
    cf.setParams(new HashMap<>());
    MutationFacade mf = new MutationFacade(maxValues, "Real");
    mf.setOp(new NullMuta());
    mf.setParams(new HashMap<>());
    llh.setCrossover((CrossoverFacade) cf.cloneMySelf());
    llh.setMutation((MutationFacade) mf.cloneMySelf());
    HashMap<String, Object> myparameters = (HashMap<String, Object>) llh.allParametersToHash();
    llh.randomize();
    llh.hashToParameter(myparameters);
    boolean v1 = llh.getCrossover().getOp().getClass() == cf.getOp().getClass();
    boolean v2 = llh.getMutation().getOp().getClass() == mf.getOp().getClass();
    assertTrue(v1 && v2);
  }

}

package br.usp.poli.pcs.lti.jmetalhhhelper;

import static org.mockito.Mockito.when;

import br.usp.poli.pcs.lti.jmetalhhhelper.core.LowLevelHeuristic;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.OpManager;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.OpSelector;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.ParametersforAlgorithm;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.ParametersforHeuristics;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.interfaces.StandardMetaheuristic;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.AlgorithmBuilder;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.CrossoverFacade;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.MutationFacade;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.management.JMException;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.uma.jmetal.algorithm.multiobjective.moead.AbstractMOEAD;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;


public class AlgOpManagerSelectorTest {

  private final HashMap maxValues;
  private ParametersforAlgorithm algParameters;
  private ParametersforHeuristics heuristicParameter;
  private OpManager llhManager;
  private AlgorithmBuilder builder;

  public AlgOpManagerSelectorTest() {
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

  @Test
  public void runAlgTest() throws JMException, FileNotFoundException {
    System.out.println("AlgOpManagerSelectorIT");
    prepareParameters();
    prepareLLHManager();
    Problem problem = new WFG1(4, 20, 2);
    builder = new AlgorithmBuilder(problem);
    StandardMetaheuristic alg = builder.create(algParameters, heuristicParameter);
    alg.setSelector(llhManager);
    alg.run();
    Assert.assertTrue(alg.getIterations() == algParameters.getMaxIteractions());
  }


  private void prepareLLHManager() {
    llhManager = new OpManager();
    LowLevelHeuristic llh = Mockito.mock(LowLevelHeuristic.class, Mockito.CALLS_REAL_METHODS);
    CrossoverFacade cf = new CrossoverFacade(maxValues, "Real");
    MutationFacade mf = new MutationFacade(maxValues, "Real");
    cf = CrossoverFacade.randomOp(cf, maxValues, 0);
    mf = MutationFacade.randomOp(mf, maxValues, 0, 0);
    llh.setCrossover(cf);
    llh.setMutation(mf);
    OpSelector ops = Mockito.mock(OpSelector.class, Mockito.CALLS_REAL_METHODS);
    when(ops.select()).thenReturn(llh);
    llhManager = new OpManager(ops);
    llhManager.setSelector(ops);
    ops.setLlhs(new ArrayList());
    ops.getLlhs().add(llh);
    llhManager.getSelector().printSuite();
  }

  private void prepareParameters() {
    algParameters = new ParametersforAlgorithm();
    algParameters.setAlgorithmName("Nsgaii");
    algParameters.setArchiveSize(100);
    algParameters.setMaxIteractions(750);
    algParameters.setPopulationSize(100);

    //just for coverage
    algParameters.setMaximumNumberOfReplacedSolutions(3);
    algParameters.setNeighborSize(10);
    algParameters.setNeighborhoodSelectionProbability(0.9);
    algParameters.setSmsOffset(100);
    algParameters.setWeightsPath("");
    algParameters.setMoeadFunction(AbstractMOEAD.FunctionType.TCHE);

    heuristicParameter = new ParametersforHeuristics();
    heuristicParameter.setCrossoverDistribution(30);
    heuristicParameter.setCrossoverName("SbxCrossover");
    heuristicParameter.setCrossoverProbality(0.9);
    heuristicParameter.setMutationDistribution(10);
    heuristicParameter.setMutationName("PolynomialMutation");
    heuristicParameter.setMutationProbability(0.004);

    //just for coverage
    heuristicParameter.setAlpha(0.5);
    heuristicParameter.setDeCr(1);
    heuristicParameter.setDeF(0.5);
    heuristicParameter.setDeK(0.5);
    heuristicParameter.setDeVariant("");
    heuristicParameter.setMutationPertubation(0.004);
  }
}
package br.usp.poli.pcs.lti.jmetalhhhelper.imp.algs;

import br.usp.poli.pcs.lti.jmetalhhhelper.core.ParametersforAlgorithm;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.ParametersforHeuristics;
import static org.junit.Assert.assertEquals;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation.PermutationSwapMuta;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.AlgorithmBuilder;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.HypervolumeCalculator;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.JMException;
import org.apache.commons.configuration2.ex.ConfigurationException;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.operator.impl.crossover.PMXCrossover;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.MultiobjectiveTSP;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

/**
 *
 */
public class Spea2Test {

  private Spea2 alg;
  private int popSize;
  private int maxIterations;

  @Before
  public void init() {
    Problem problem;
    try {
      problem = new MultiobjectiveTSP("/tsp/DimacsEucl/euclidA100.tsp",
          "/tsp/DimacsEucl/euclidB100.tsp");
      popSize = 100;
      maxIterations = 2000;
      alg = new Spea2(problem, maxIterations, 0, null, null, new BinaryTournamentSelection(),
          new SequentialSolutionListEvaluator());
    } catch (IOException ex) {
      Logger.getLogger(IbeaTest.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Test
  public void testSetandGetIterations() {
    assertEquals(alg.getMaxEvaluations(), (alg.getMaxIterations() * alg.getPopulationSize()));
  }

  @Test
  public void testSetIterationsGetMaxEvaluations() {
    alg.setIterations(maxIterations / popSize);
    alg.setPopulationSize(popSize);
    assertEquals(maxIterations, alg.getMaxEvaluations());
  }

  @Test
  public void testOperators() {
    //minimal to work
    alg.setArchiveSize(popSize);
    alg.setPopulationSize(popSize);
    alg.setIterations(0);
    alg.setSelector(alg.getSelector());
    alg.setEnvironmentalSelectionSize(popSize);
    //set operators
    alg.setCrossoverOperator(new PMXCrossover(0.9));
    alg.setMutationOperator(new PermutationSwapMuta(0.1));

    //init
    alg.initMetaheuristic();
    alg.generateNewPopulation();

    assertTrue(2 == alg.getIterations() && alg.getArchive().size() == alg.getArchiveSize());
  }
  
  @Test
  public void ibeaInteractive() throws ConfigurationException, JMException, FileNotFoundException{
      Problem problem = new WFG1(4, 20, 2);
      AlgorithmBuilder ab=new AlgorithmBuilder(problem);
      ParametersforAlgorithm pAlg=new ParametersforAlgorithm("SPEA2.default");
      ParametersforHeuristics pHeu=  new ParametersforHeuristics("SBX.Poly.default", problem.getNumberOfVariables());
      pAlg.setMaxIteractions(1000);
      alg=(Spea2) ab.create(pAlg, pHeu);
      alg.initMetaheuristic();
      alg.generateNewPopulation();
      List population=alg.getRealpop();
      for (int i = 0; i < pAlg.getMaxIteractions(); i++) {
          alg.setArchive(population);
          alg.generateNewPopulation();
          population=alg.getRealpop();
      }
      population=alg.getRealpop();
      String pf
          =
          "pareto_fronts/" + problem.getName() + "." + problem.getNumberOfObjectives() + "D.pf";
      HypervolumeCalculator hyp=new HypervolumeCalculator(problem.getNumberOfObjectives(), pf);
      double val=hyp.execute(population);
      System.out.println(val);
      assertTrue(val> 0.2);
  }
}

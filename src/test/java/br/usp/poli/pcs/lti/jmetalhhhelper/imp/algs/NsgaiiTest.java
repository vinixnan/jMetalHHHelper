package br.usp.poli.pcs.lti.jmetalhhhelper.imp.algs;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.algs.Nsgaii;
import static org.junit.Assert.assertEquals;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation.PermutationSwapMuta;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.operator.impl.crossover.PMXCrossover;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.MultiobjectiveTSP;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;
import org.uma.jmetal.util.comparator.CrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

/**
 *
 */
public class NsgaiiTest {

  private Nsgaii alg;
  private int popSize;
  private int maxEvaluations;

  @Before
  public void init() {
    Problem problem;
    try {
      problem = new MultiobjectiveTSP("/tsp/DimacsEucl/euclidA100.tsp",
          "/tsp/DimacsEucl/euclidB100.tsp");
      popSize = 100;
      maxEvaluations = 2000;
      alg = new Nsgaii(problem, maxEvaluations, 1, null, null, new BinaryTournamentSelection(), new CrowdingDistanceComparator(),
          new SequentialSolutionListEvaluator());
    } catch (IOException ex) {
      Logger.getLogger(IbeaTest.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Test
  public void testSetandGetIterations() {
    alg = new Nsgaii(new WFG1(), maxEvaluations, popSize, null, null,
        new BinaryTournamentSelection(), new CrowdingDistanceComparator(), new SequentialSolutionListEvaluator());
    assertEquals(alg.getMaxEvaluations(), (alg.getMaxIterations() * alg.getPopulationSize()));
  }


  @Test
  public void testSetIterationsGetMaxEvaluations() {
    alg.setIterations(maxEvaluations / popSize);
    alg.setPopulationSize(popSize);
    assertEquals(maxEvaluations, alg.getMaxEvaluations());
  }

  @Test
  public void testOperators() {
    //minimal to work
    alg.setPopulationSize(popSize);
    alg.setIterations(0);
    alg.setSelector(alg.getSelector());

    //set operators
    alg.setCrossoverOperator(new PMXCrossover(0.9));
    alg.setMutationOperator(new PermutationSwapMuta(0.1));

    //init
    alg.initMetaheuristic();
    alg.generateNewPopulation();

    assertEquals(2, alg.getIterations());
  }
}

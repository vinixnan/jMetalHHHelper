package br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation;

import static org.junit.Assert.assertTrue;

import br.usp.poli.pcs.lti.jmetalhhhelper.util.ProblemFactory;

import org.junit.Test;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.solution.Solution;

/**
 *
 */
public class PermutationSwapMutaTest {

  private final Problem problem;

  public PermutationSwapMutaTest() {
    String tspfile1 = "/tsp/DimacsEucl/euclidA100.tsp";
    String tspfile2 = "/tsp/DimacsEucl/euclidB100.tsp";
    problem = ProblemFactory.getTspProblems(tspfile1, tspfile2);
  }

  @Test
  public void testConstructor1() {
    PermutationSwapMuta op = new PermutationSwapMuta(1);
    PermutationSolution solution = (PermutationSolution) problem.createSolution();
    Solution sol = op.execute(solution);
    assertTrue(sol != null);
  }

  @Test
  public void testGetParameters() {
    PermutationSwapMuta op = new PermutationSwapMuta(1);
    assertTrue(op.getParameters() != null);
  }

}

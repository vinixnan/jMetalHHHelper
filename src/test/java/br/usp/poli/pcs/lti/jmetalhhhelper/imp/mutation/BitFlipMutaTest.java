package br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation.BitFlipMuta;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.singleobjective.OneMax;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.Solution;

/**
 *
 */
public class BitFlipMutaTest {

  private final Problem problem;

  public BitFlipMutaTest() {
    problem = new OneMax(8);
  }

  @Test
  public void testConstructor1() {
    BitFlipMuta op = new BitFlipMuta(1);
    Solution solution = (Solution) problem.createSolution();
    Solution sol = op.execute((BinarySolution) solution);
    assertTrue(sol != null);
  }

  @Test
  public void testGetParameters() {
    BitFlipMuta op = new BitFlipMuta(1);
    assertTrue(op.getParameters() != null);
  }

}

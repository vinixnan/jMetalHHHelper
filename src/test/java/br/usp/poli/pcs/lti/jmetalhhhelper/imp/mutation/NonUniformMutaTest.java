package br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation.NonUniformMuta;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;

/**
 *
 */
public class NonUniformMutaTest {

  private final Problem problem;

  public NonUniformMutaTest() {
    problem = new WFG1();
  }

  @Test
  public void testConstructor1() {
    NonUniformMuta op = new NonUniformMuta(1, 10, 750);
    Solution solution = (Solution) problem.createSolution();
    Solution sol = op.execute((DoubleSolution) solution);
    assertTrue(sol != null);
  }

  @Test
  public void testGetParameters() {
    NonUniformMuta op = new NonUniformMuta(1, 10, 750);
    assertTrue(op.getParameters() != null);
  }

}

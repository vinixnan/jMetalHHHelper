package br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation.UniformMuta;
import static org.junit.Assert.assertTrue;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover.BlxAlphaCrossover;

import org.junit.Test;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;

/**
 *
 */
public class UniformMutaTest {

  private final Problem problem;

  public UniformMutaTest() {
    problem = new WFG1();
  }

  @Test
  public void testConstructor1() {
    UniformMuta op = new UniformMuta(1, 10);
    Solution solution = (Solution) problem.createSolution();
    Solution sol = op.execute((DoubleSolution) solution);
    assertTrue(sol != null);
  }

  @Test
  public void testGetParameters() {
    BlxAlphaCrossover op = new BlxAlphaCrossover(1, 0.5);
    assertTrue(op.getParameters() != null);
  }

}

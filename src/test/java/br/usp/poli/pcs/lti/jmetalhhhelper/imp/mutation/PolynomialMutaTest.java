package br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation.PolynomialMuta;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.util.RepairDoubleSolutionAtRandom;

/**
 *
 */
public class PolynomialMutaTest {

  private final Problem problem;

  public PolynomialMutaTest() {
    problem = new WFG1();
  }

  @Test
  public void testConstructor1() {
    PolynomialMuta op = new PolynomialMuta((DoubleProblem) problem, 10);
    Solution solution = (Solution) problem.createSolution();
    Solution sol = op.execute((DoubleSolution) solution);
    assertTrue(sol != null);
  }

  @Test
  public void testConstructor2() {
    PolynomialMuta op = new PolynomialMuta(1, 10);
    Solution solution = (Solution) problem.createSolution();
    Solution sol = op.execute((DoubleSolution) solution);
    assertTrue(sol != null);
  }

  @Test
  public void testConstructor3() {
    PolynomialMuta op = new PolynomialMuta(1, 10, new RepairDoubleSolutionAtRandom());
    Solution solution = (Solution) problem.createSolution();
    Solution sol = op.execute((DoubleSolution) solution);
    assertTrue(sol != null);
  }

  @Test
  public void testGetParameters() {
    PolynomialMuta op = new PolynomialMuta(1, 0.5);
    assertTrue(op.getParameters() != null);
  }

}

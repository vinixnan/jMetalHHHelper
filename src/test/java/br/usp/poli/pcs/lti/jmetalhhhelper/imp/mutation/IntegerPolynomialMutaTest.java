package br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation.IntegerPolynomialMuta;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.uma.jmetal.problem.IntegerProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.singleobjective.NIntegerMin;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.util.RepairDoubleSolutionAtRandom;

/**
 *
 */
public class IntegerPolynomialMutaTest {

  private final Problem problem;

  public IntegerPolynomialMutaTest() {
    problem = new NIntegerMin(1, 10, -1000, 1000);
  }

  @Test
  public void testConstructor1() {
    IntegerPolynomialMuta op = new IntegerPolynomialMuta((IntegerProblem) problem, 10);
    Solution solution = (Solution) problem.createSolution();
    Solution sol = op.execute((IntegerSolution) solution);
    assertTrue(sol != null);
  }

  @Test
  public void testConstructor2() {
    IntegerPolynomialMuta op = new IntegerPolynomialMuta(1, 10);
    Solution solution = (Solution) problem.createSolution();
    Solution sol = op.execute((IntegerSolution) solution);
    assertTrue(sol != null);
  }

  @Test
  public void testConstructor3() {
    IntegerPolynomialMuta op = new IntegerPolynomialMuta(1, 10, new RepairDoubleSolutionAtRandom());
    Solution solution = (Solution) problem.createSolution();
    Solution sol = op.execute((IntegerSolution) solution);
    assertTrue(sol != null);
  }

  @Test
  public void testGetParameters() {
    IntegerPolynomialMuta op = new IntegerPolynomialMuta(1, 0.5);
    assertTrue(op.getParameters() != null);
  }

}

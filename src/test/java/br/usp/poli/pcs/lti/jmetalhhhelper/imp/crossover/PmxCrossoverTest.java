package br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import br.usp.poli.pcs.lti.jmetalhhhelper.util.ProblemFactory;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.problem.Problem;

/**
 *
 */
public class PmxCrossoverTest {

  private final Problem problem;
  private List parents;

  public PmxCrossoverTest() {
    String tspfile1 = "/tsp/DimacsEucl/euclidA100.tsp";
    String tspfile2 = "/tsp/DimacsEucl/euclidB100.tsp";
    problem = ProblemFactory.getTspProblems(tspfile1, tspfile2);
  }

  @Before
  public void generateParents() {
    parents = new ArrayList();
    parents.add(problem.createSolution());
    parents.add(problem.createSolution());
  }

  @Test
  public void testConstructor1() {
    PmxCrossover op = new PmxCrossover(1);
    List offspring = op.execute(parents);
    assertThat(offspring.isEmpty(), is(false));
  }

  @Test
  public void testGetParameters() {
    PmxCrossover op = new PmxCrossover(1);
    assertTrue(op.getParameters() != null);
  }

}

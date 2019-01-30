package br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover.OnePointCrossover;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.singleobjective.OneMax;

/**
 *
 */
public class OnePointCrossoverTest {

  private final Problem problem;
  private List parents;

  public OnePointCrossoverTest() {
    problem = new OneMax(8);
  }

  @Before
  public void generateParents() {
    parents = new ArrayList();
    parents.add(problem.createSolution());
    parents.add(problem.createSolution());
  }

  @Test
  public void testConstructor1() {
    OnePointCrossover op = new OnePointCrossover(0.9);
    List offspring = op.execute(parents);
    assertThat(offspring.isEmpty(), is(false));
  }

  @Test
  public void testGetParameters() {
    OnePointCrossover op = new OnePointCrossover(0.9);
    assertTrue(op.getParameters() != null);
  }

}

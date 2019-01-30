package br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover.IntegerSbxCrossover;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.singleobjective.NIntegerMin;

/**
 *
 */
public class IntegerSbxCrossoverTest {

  private final Problem problem;
  private List parents;

  public IntegerSbxCrossoverTest() {
    problem = new NIntegerMin(1, 10, -1000, 1000);
  }

  @Before
  public void generateParents() {
    parents = new ArrayList();
    parents.add(problem.createSolution());
    parents.add(problem.createSolution());
  }

  @Test
  public void testConstructor1() {
    IntegerSbxCrossover op = new IntegerSbxCrossover(1, 0.5);
    List offspring = op.execute(parents);
    assertThat(offspring.isEmpty(), is(false));
  }

  @Test
  public void testGetParameters() {
    IntegerSbxCrossover op = new IntegerSbxCrossover(1, 0.5);
    assertTrue(op.getParameters() != null);
  }

}

package br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover.BlxAlphaCrossover;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover.SbxCrossover;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;

/**
 *
 */
public class SbxCrossoverTest {

  private final Problem problem;
  private List parents;

  public SbxCrossoverTest() {
    problem = new WFG1();
  }

  @Before
  public void generateParents() {
    parents = new ArrayList();
    parents.add(problem.createSolution());
    parents.add(problem.createSolution());
  }

  @Test
  public void testConstructor() {
    SbxCrossover op = new SbxCrossover(1, 30);
    List offspring = op.execute(parents);
    assertThat(offspring.isEmpty(), is(false));
  }

  @Test
  public void testGetParameters() {
    BlxAlphaCrossover op = new BlxAlphaCrossover(1, 0.5);
    assertTrue(op.getParameters() != null);
  }
}

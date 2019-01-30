package br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover.DifferentialEvolution;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;
import org.uma.jmetal.solution.DoubleSolution;

/**
 *
 */
public class DifferentialEvolutionTest {

  private final DoubleProblem problem;
  private List<DoubleSolution> parents;

  public DifferentialEvolutionTest() {
    problem = new WFG1();
  }

  @Before
  public void generateParents() {
    parents = new ArrayList();
    parents.add(problem.createSolution());
    parents.add(problem.createSolution());
    parents.add(problem.createSolution());
  }

  @Test
  public void testConstructor1() {
    DifferentialEvolution op = new DifferentialEvolution(1, 0.5, "rand/1/bin");
    op.setCurrentSolution(problem.createSolution());
    List offspring = op.execute(parents);
    assertThat(offspring.isEmpty(), is(false));
  }

  @Test
  public void testConstructor2() {
    DifferentialEvolution op = new DifferentialEvolution(1, 0.5, 0.5, "rand/1/bin");
    op.setCurrentSolution(problem.createSolution());
    List offspring = op.execute(parents);
    assertThat(offspring.isEmpty(), is(false));
  }

  @Test
  public void testGetParameters() {
    DifferentialEvolution op = new DifferentialEvolution(1, 0.5, "rand/1/bin");
    assertTrue(op.getParameters() != null);
  }

}

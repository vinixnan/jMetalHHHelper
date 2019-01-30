package br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover.NullCross;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 *
 */
public class NullCrossTest {

  @Test
  public void testGetParameters() {
    NullCross op = new NullCross();
    assertTrue(op.getParameters() != null);
  }

}

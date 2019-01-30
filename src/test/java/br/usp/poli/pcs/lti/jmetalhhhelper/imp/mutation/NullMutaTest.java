package br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation.NullMuta;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 *
 */
public class NullMutaTest {

  @Test
  public void testGetParameters() {
    NullMuta op = new NullMuta();
    assertTrue(op.getParameters() != null);
  }

}

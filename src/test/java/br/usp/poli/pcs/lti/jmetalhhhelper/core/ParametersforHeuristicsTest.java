/*
 * Copyright 2017 vinicius.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.usp.poli.pcs.lti.jmetalhhhelper.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import br.usp.poli.pcs.lti.jmetalhhhelper.util.AlgorithmBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.JMException;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.Test;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;

/**
 *
 */
public class ParametersforHeuristicsTest {

  public ParametersforHeuristicsTest() {
  }

  private boolean commomTest(String file) {
    try {
      AlgorithmBuilder lagb = new AlgorithmBuilder(new WFG1());
      ParametersforHeuristics param = new ParametersforHeuristics(file, 24);
      String crossNameGenerated = lagb.generateCross(param).getClass().getSimpleName()
          .toLowerCase();
      String mutaNameGenerated = lagb.generateMuta(param, 0).getClass().getSimpleName()
          .toLowerCase();
      String crossNameParam = param.getCrossoverName().toLowerCase();
      String mutaNameParam = param.getMutationName().toLowerCase();
      boolean t1 = crossNameParam.contains(crossNameGenerated);
      boolean t2 = mutaNameParam.contains(mutaNameGenerated);
      return t1 && t2;
    } catch (ConfigurationException | JMException ex) {
      Logger.getLogger(ParametersforHeuristicsTest.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  @Test
  public void SBXNonUniformTest() {
    assertTrue(commomTest("SBX.NonUniform.default"));
  }

  @Test
  public void SBXUniformTest() {
    assertTrue(commomTest("SBX.Uniform.default"));
  }

  @Test
  public void BLXPolyTest() {
    assertTrue(commomTest("BLX.Poly.default"));
  }

  @Test
  public void NullTest() {
    assertTrue(commomTest("nullCrossover.nullMutation.default"));
  }

  @Test
  public void PMXNullTest() {
    assertTrue(commomTest("PMX.nullMutation.default"));
  }

  @Test
  public void ONEPNullTest() {
    assertTrue(commomTest("ONEP.nullMutation.default"));
  }

  @Test
  public void HUXNullTest() {
    assertTrue(commomTest("HUX.nullMutation.default"));
  }

  @Test
  public void NullBitFlipTest() {
    assertTrue(commomTest("nullCrossover.BitFlip.default"));
  }

  @Test
  public void NullPermutationTest() {
    assertTrue(commomTest("nullCrossover.Permutation.default"));
  }

  @Test(expected = NullPointerException.class)
  public void errorOpeningFile() {
    assertFalse(commomTest("T.T.T"));
  }

}

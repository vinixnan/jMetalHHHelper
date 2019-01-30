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

package br.usp.poli.pcs.lti.jmetalhhhelper.util;

import br.usp.poli.pcs.lti.jmetalhhhelper.util.CrossoverFacade;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class CrossoverCloneTest {

  public static Map<String, Object> maxValues;

  /**
   * Init.
   */
  @BeforeClass
  public static void init() {
    CrossoverCloneTest.maxValues = new HashMap<>();
    maxValues.put("cross_probability", 1.0);
    maxValues.put("cross_distributionIndex", 100.0);
    maxValues.put("cross_alpha", 1.0);
    maxValues.put("cross_binaryrossoverProbability", 1.0);
  }

  protected void commonTest(String solutionType, int idOperator, String expectedOperatorName) {
    System.out
        .println("randomOp_" + solutionType + "_" + idOperator + "_to_" + expectedOperatorName);
    CrossoverFacade generated = CrossoverFacade
        .randomOp(new CrossoverFacade(maxValues, solutionType), maxValues, idOperator);
    CrossoverFacade copy = (CrossoverFacade) generated.cloneMySelf();
    assertEquals(generated.op.getClass().getSimpleName(), copy.op.getClass().getSimpleName());
  }

  /**
   * Test of randomOp method, to generate SbxCrossover.
   */
  @Test
  public void testRandomOp_CrossoverFacade_SbxCrossover() {
    commonTest("Real", 0, "SbxCrossover");

  }

  /**
   * Test of randomOp method, to generate BlxAlphaCrossover.
   */
  @Test
  public void testRandomOp_CrossoverFacade_BlxAlphaCrossover() {
    commonTest("Real", 1, "BlxAlphaCrossover");

  }

  /**
   * Test of randomOp method, to generate NullCross.
   */
  @Test
  public void testRandomOp_CrossoverFacade_NullCross() {
    commonTest("Real", 2, "NullCross");

  }


  /**
   * Test of randomOp method, to generate HuxCrossover.
   */
  @Test
  public void testRandomOp_CrossoverFacade_HuxCrossover() {
    commonTest("Binary", 0, "HuxCrossover");

  }

  /**
   * Test of randomOp method, to generate OnePointCrossover.
   */
  @Test
  public void testRandomOp_CrossoverFacade_OnePointCrossover() {
    commonTest("Binary", 1, "OnePointCrossover");

  }

  /**
   * Test of randomOp method, to generate NullCross.
   */
  @Test
  public void testRandomOp_CrossoverFacade_NullCross2() {
    commonTest("Binary", 2, "NullCross");

  }

  /**
   * Test of randomOp method, to generate PmxCrossover.
   */
  @Test
  public void testRandomOp_CrossoverFacade_PmxCrossover() {
    commonTest("Permutation", 0, "PmxCrossover");

  }

  /**
   * Test of randomOp method, to generate NullCross.
   */
  @Test
  public void testRandomOp_CrossoverFacade_NullCross3() {
    commonTest("Permutation", 1, "NullCross");
  }

  /**
   * Test of randomOp method, to generate NullCross.
   */
  @Test
  public void testRandomOp_CrossoverFacade_IntegerSbxCrossover() {
    commonTest("Integer", 0, "IntegerSbxCrossover");
  }

  /**
   * Test of randomOp method, to generate NullCross.
   */
  @Test
  public void testRandomOp_CrossoverFacade_NullCross4() {
    commonTest("Integer", 1, "NullCross");
  }


  /**
   * Test of cloneMyself method.
   */
  @Test
  public void testClone_CrossoverFacade() {
    CrossoverFacade generated = CrossoverFacade
        .randomOp(new CrossoverFacade(maxValues, "Real"), maxValues, 0);
    CrossoverFacade clone = (CrossoverFacade) generated.cloneMySelf();
    assertEquals(generated.getClass().getName(), clone.getClass().getName());
  }

  /**
   * Test of randomOp method, to generate default for Real.
   */
  @Test
  public void testRandomOp_force_default() {
    commonTest("Real", 777, "SbxCrossover");
  }

  /**
   * Test of randomOp method, to generate default for Binary.
   */
  @Test
  public void testRandomOp_force_default2() {
    commonTest("Binary", 777, "HuxCrossover");
  }

  /**
   * Test of randomOp method, to generate default for Permutation.
   */
  @Test
  public void testRandomOp_force_default3() {
    commonTest("Permutation", 777, "PmxCrossover");

  }

  /**
   * Test of randomOp method, to generate NullCross.
   */
  @Test
  public void testRandomOp_force_default4() {
    commonTest("Integer", 777, "IntegerSbxCrossover");
  }
}

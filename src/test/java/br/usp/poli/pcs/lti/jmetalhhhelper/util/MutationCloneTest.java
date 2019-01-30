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

import br.usp.poli.pcs.lti.jmetalhhhelper.util.MutationFacade;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class MutationCloneTest {

  public static Map<String, Object> maxValues;

  /**
   * Init.
   */
  @BeforeClass
  public static void init() {
    maxValues = new HashMap<>();
    maxValues.put("muta_probability", 1.0);
    maxValues.put("muta_distributionIndex", 100.0);
    maxValues.put("muta_perturbation", 100.0);
    maxValues.put("muta_maxIterations", 100 * 750);
    maxValues.put("muta_binaryMutationProbability", 1.0);
  }

  protected void commonTest(String solutionType, int idOperator, String expectedOperatorName) {
    System.out
        .println("randomOp_" + solutionType + "_" + idOperator + "_to_" + expectedOperatorName);
    MutationFacade generated = MutationFacade
        .randomOp(new MutationFacade(maxValues, solutionType), maxValues, 0, idOperator);
    MutationFacade copy = (MutationFacade) generated.cloneMySelf();
    assertEquals(generated.op.getClass().getSimpleName(), copy.op.getClass().getSimpleName());
  }

  /**
   * Test of randomOp method, to generate PolynomialMuta.
   */
  @Test
  public void testRandomOp_MutationFacade_PolynomialMuta() {
    commonTest("Real", 0, "PolynomialMuta");

  }

  /**
   * Test of randomOp method, to generate UniformMuta.
   */
  @Test
  public void testRandomOp_MutationFacade_UniformMuta() {
    commonTest("Real", 1, "UniformMuta");

  }

  /**
   * Test of randomOp method, to generate NonUniformMuta.
   */
  @Test
  public void testRandomOp_MutationFacade_NonUniformMuta() {
    commonTest("Real", 2, "NonUniformMuta");

  }

  /**
   * Test of randomOp method, to generate NullMuta.
   */
  @Test
  public void testRandomOp_MutationFacade_NullMuta() {
    commonTest("Real", 3, "NullMuta");

  }

  /**
   * Test of randomOp method, to generate NullMuta.
   */
  @Test
  public void testRandomOp_MutationFacade_SimpleRandomMuta() {
    commonTest("Real", 4, "SimpleRandomMuta");

  }

  /**
   * Test of randomOp method, to generate BitFlipMuta.
   */
  @Test
  public void testRandomOp_MutationFacade_BitFlipMuta() {
    commonTest("Binary", 0, "BitFlipMuta");

  }

  /**
   * Test of randomOp method, to generate NullMuta.
   */
  @Test
  public void testRandomOp_MutationFacade_NullMuta2() {
    commonTest("Binary", 1, "NullMuta");

  }

  /**
   * Test of randomOp method, to generate BitFlipMuta.
   */
  @Test
  public void testRandomOp_MutationFacade_IntegerPolynomialMuta() {
    commonTest("Integer", 0, "IntegerPolynomialMuta");

  }

  /**
   * Test of randomOp method, to generate NullMuta.
   */
  @Test
  public void testRandomOp_MutationFacade_NullMuta3() {
    commonTest("Integer", 1, "NullMuta");

  }

  /**
   * Test of randomOp method, to generate BitFlipMuta.
   */
  @Test
  public void testRandomOp_MutationFacade_PermutationSwapMuta() {
    commonTest("Permutation", 0, "PermutationSwapMuta");

  }

  /**
   * Test of randomOp method, to generate NullMuta.
   */
  @Test
  public void testRandomOp_MutationFacade_NullMuta4() {
    commonTest("Permutation", 1, "NullMuta");

  }

  /**
   * Test of cloneMyself method.
   */
  @Test
  public void testClone_MutationFacade() {
    MutationFacade generated = MutationFacade
        .randomOp(new MutationFacade(maxValues, "Real"), maxValues, 0, 0);
    MutationFacade clone = (MutationFacade) generated.cloneMySelf();
    assertEquals(generated.getClass().getName(), clone.getClass().getName());
  }

  /**
   * Test of randomOp method, to generate default for Real.
   */
  @Test
  public void testRandomOp_force_default() {
    commonTest("Real", 777, "PolynomialMuta");
  }

  /**
   * Test of randomOp method, to generate default for Binary.
   */
  @Test
  public void testRandomOp_force_default2() {
    commonTest("Binary", 777, "BitFlipMuta");
  }

  /**
   * Test of randomOp method, to generate default for Binary.
   */
  @Test
  public void testRandomOp_force_default3() {
    commonTest("Integer", 777, "IntegerPolynomialMuta");
  }

  /**
   * Test of randomOp method, to generate default for Binary.
   */
  @Test
  public void testRandomOp_force_default4() {
    commonTest("Permutation", 777, "PermutationSwapMuta");
  }

}

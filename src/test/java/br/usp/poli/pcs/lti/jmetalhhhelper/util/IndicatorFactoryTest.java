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

import static org.junit.Assert.assertEquals;

import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.Calculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.RCalculator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.JMetalException;

/**
 *
 */
public class IndicatorFactoryTest {

  private final Problem problem;
  private final int populationSize;

  public IndicatorFactoryTest() {
    problem = ProblemFactory.getProblem("WFG1", 4, 20, 2);
    populationSize = 100;
  }

  protected void commonTest(String qualityIndicatorName) {
    Calculator result;
    try {
      result = IndicatorFactory
          .buildCalculator(qualityIndicatorName, problem, populationSize);
      assertEquals(result.getName(), qualityIndicatorName);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(IndicatorFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(IndicatorFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Test
  public void getHypervolumeTest() {
    commonTest("Hypervolume");
  }

  @Test
  public void getIGDTest() {
    commonTest("IGD");
  }

  @Test
  public void getGDTest() {
    commonTest("GD");
  }

  @Test
  public void getRNITest() {
    commonTest("RNI");
  }

  @Test
  public void getSpreadTest() {
    commonTest("Spread");
  }

  @Test
  public void getR2Test() {
    commonTest("R");
  }

  @Test
  public void getNullTest() {
    Calculator result;
    try {
      result = IndicatorFactory.buildCalculator("", problem, populationSize);
      assertEquals(result, null);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(IndicatorFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(IndicatorFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Test(expected = JMetalException.class)
  public void forceR2PFError() {
    System.out.println("forceR2PFError");

    try {
      RCalculator r = new RCalculator(2, 100, "");
    } catch (FileNotFoundException ex) {
      Logger.getLogger(IndicatorFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(IndicatorFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}

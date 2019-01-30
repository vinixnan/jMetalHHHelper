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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import br.usp.poli.pcs.lti.jmetalhhhelper.util.ProblemFactory;

import java.util.Arrays;

import org.junit.Test;
import org.uma.jmetal.problem.Problem;

/**
 * The type Problem factory test.
 */
public class ProblemFactoryTest {

  /**
   * Test of testGetWFGProblems method, of class ProblemFactory.
   *
   * @throws Exception the exception
   */
  @Test
  public void testGetWFGProblems() throws Exception {
    System.out.println("testGetWFGProblems");
    String baseName = "WFG";
    int wfgNumberOfPositions = 4;
    int numberOfDistanceVariables = 20;
    int numberOfObjectives = 2;
    int numberOfDifferentProblems = 9;
    String[] allnames = new String[numberOfDifferentProblems];
    String[] toVerify = new String[allnames.length];
    for (int i = 0; i < allnames.length; i++) {
      String name = baseName + String.valueOf(i + 1);
      allnames[i] = name;
      Problem p = ProblemFactory.getProblem(name, wfgNumberOfPositions, numberOfDistanceVariables,
          numberOfObjectives);
      toVerify[i] = p.getName();
    }
    assertArrayEquals(allnames, toVerify);
  }

  /**
   * Test of testGetDTLZProblems method, of class ProblemFactory.
   *
   * @throws Exception the exception
   */
  @Test
  public void testGetDTLZProblems() throws Exception {
    System.out.println("testGetDTLZProblems");
    String baseName = "DTLZ";
    int numberOfDistanceVariables = 20;
    int numberOfObjectives = 2;
    int numberOfDifferentProblems = 7;
    String[] allnames = new String[numberOfDifferentProblems];
    String[] toVerify = new String[allnames.length];
    for (int i = 0; i < allnames.length; i++) {
      String name = baseName + String.valueOf(i + 1);
      allnames[i] = name;
      Problem p = ProblemFactory.getProblem(name, numberOfDistanceVariables,
          numberOfObjectives);
      toVerify[i] = p.getName();
    }
    assertArrayEquals(allnames, toVerify);
  }

  /**
   * Test of testGetDTLZProblems method, of class ProblemFactory.
   *
   * @throws Exception the exception
   */
  @Test
  public void testGetZDTProblems() throws Exception {
    System.out.println("testGetZDTProblems");
    String baseName = "ZDT";
    int numberOfDistanceVariables = 20;
    int numberOfObjectives = 2;
    int numberOfDifferentProblems = 6;
    String[] allnames = new String[numberOfDifferentProblems];
    String[] toVerify = new String[allnames.length];
    for (int i = 0; i < allnames.length; i++) {
      String name = baseName + String.valueOf(i + 1);
      allnames[i] = name;
      Problem p = ProblemFactory.getProblem(name, numberOfDistanceVariables,
          numberOfObjectives);
      toVerify[i] = p.getName();
    }
    assertArrayEquals(allnames, toVerify);
  }

  /**
   * Test of testGetDTLZProblems method, of class ProblemFactory.
   *
   * @throws Exception the exception
   */
  @Test
  public void testGetTSPProblems() throws Exception {
    System.out.println("testGetTSPProblems");
    String tspfile1 = "/tsp/DimacsEucl/euclidA100.tsp";
    String tspfile2 = "/tsp/DimacsEucl/euclidB100.tsp";
    Problem problem = ProblemFactory.getTspProblems(tspfile1, tspfile2);
    assertEquals(problem.getName(), "MultiobjectiveTSP");
  }

  /**
   * Test of testGetDTLZProblems method, of class ProblemFactory.
   *
   * @throws Exception the exception
   */
  @Test(expected = NullPointerException.class)
  public void testGetErrorTSPProblems() throws Exception {
    String tspfile1 = "/tsp/DimacsEucl/test";
    String tspfile2 = "test";
    Problem problem = ProblemFactory.getTspProblems(tspfile1, tspfile2);
  }

  /**
   * Test of getting OneZeroMax method, of class ProblemFactory.
   *
   * @throws Exception the exception
   */
  @Test
  public void testGetOneMaxProblem() throws Exception {
    System.out.println("testGetOneMaxProblem");
    Problem problem = ProblemFactory.getProblem("OneZeroMax", 2);
    assertEquals(problem.getName(), "OneZeroMax");
  }

  /**
   * Test of testGetWFGProblems method, of class ProblemFactory.
   *
   * @throws Exception the exception
   */
  @Test
  public void testGetAllWFGProblems() throws Exception {
    System.out.println("testGetAllWFGProblems");
    String baseName = "WFG";
    int wfgNumberOfPositions = 4;
    int numberOfDistanceVariables = 20;
    int numberOfObjectives = 2;
    Problem[] problems = ProblemFactory
        .getProblems(baseName, wfgNumberOfPositions, numberOfDistanceVariables,
            numberOfObjectives);
    String[] allnames = new String[problems.length];
    String[] toVerify = new String[problems.length];
    for (int i = 0; i < allnames.length; i++) {
      allnames[i] = baseName + String.valueOf(i + 1);
      toVerify[i] = problems[i].getName();
    }
    Arrays.sort(allnames);
    Arrays.sort(toVerify);
    assertArrayEquals(allnames, toVerify);
  }

  /**
   * Test of testGetWFGProblems method, of class ProblemFactory.
   *
   * @throws Exception the exception
   */
  @Test
  public void testGetAllDTLZProblems() throws Exception {
    System.out.println("testGetAllDTLZProblems");
    String baseName = "DTLZ";
    int numberOfDistanceVariables = 20;
    int numberOfObjectives = 2;
    Problem[] problems = ProblemFactory.getProblems(baseName, numberOfDistanceVariables,
        numberOfObjectives);
    String[] allnames = new String[problems.length];
    String[] toVerify = new String[problems.length];
    for (int i = 0; i < allnames.length; i++) {
      allnames[i] = baseName + String.valueOf(i + 1);
      toVerify[i] = problems[i].getName();
    }
    Arrays.sort(allnames);
    Arrays.sort(toVerify);
    assertArrayEquals(allnames, toVerify);
  }
}

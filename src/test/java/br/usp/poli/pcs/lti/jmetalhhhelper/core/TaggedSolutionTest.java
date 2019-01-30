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

import static org.junit.Assert.assertTrue;

import br.usp.poli.pcs.lti.jmetalhhhelper.util.ProblemFactory;

import org.junit.Test;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.PermutationProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.DefaultDoubleSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerPermutationSolution;

/**
 *
 */
public class TaggedSolutionTest {

  @Test
  public void generatePermutationTaggedFromSolution() {
    String tspfile1 = "/tsp/DimacsEucl/euclidA100.tsp";
    String tspfile2 = "/tsp/DimacsEucl/euclidB100.tsp";
    Problem problem = ProblemFactory.getTspProblems(tspfile1, tspfile2);
    Solution s = (Solution) problem.createSolution();
    PermutationTaggedSolution ps = new PermutationTaggedSolution(
        (DefaultIntegerPermutationSolution) s);
    ps.setAction(ps.getAction());
    ps.setAlgorithm(ps.getAlgorithm());
    String varS = "";
    String varPS = "";
    for (int i = 0; i < s.getNumberOfVariables(); i++) {
      varS += s.getVariableValue(i) + "_";
      varPS += ps.getVariableValue(i) + "_";
    }
    assertTrue(varS.equals(varPS));
  }

  @Test
  public void generateDoubleTaggedFromSolution() {
    Problem problem = new WFG1();
    Solution s = (Solution) problem.createSolution();
    DoubleTaggedSolution ps = new DoubleTaggedSolution((DefaultDoubleSolution) s);
    String varS = "";
    String varPS = "";
    for (int i = 0; i < s.getNumberOfVariables(); i++) {
      varS += s.getVariableValue(i) + "_";
      varPS += ps.getVariableValue(i) + "_";
    }
    assertTrue(varS.equals(varPS));
  }

  @Test
  public void generatePermutationTaggedFromProblem() {
    String tspfile1 = "/tsp/DimacsEucl/euclidA100.tsp";
    String tspfile2 = "/tsp/DimacsEucl/euclidB100.tsp";
    Problem problem = ProblemFactory.getTspProblems(tspfile1, tspfile2);
    PermutationTaggedSolution ps = new PermutationTaggedSolution((PermutationProblem) problem);
    int val = Integer.parseInt(ps.getVariableValueString(0));
    assertTrue(0 < val);
  }

  @Test
  public void generateDoubleTaggedFromProblem() {
    Problem problem = new WFG1();
    DoubleTaggedSolution ps = new DoubleTaggedSolution((DoubleProblem) problem);
    double val = Double.parseDouble(ps.getVariableValueString(0));
    assertTrue(0 < val);
  }

}

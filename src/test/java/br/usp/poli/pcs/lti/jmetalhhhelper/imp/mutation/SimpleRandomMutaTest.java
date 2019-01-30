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

package br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation.SimpleRandomMuta;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;

/**
 *
 */
public class SimpleRandomMutaTest {

  private final Problem problem;

  public SimpleRandomMutaTest() {
    problem = new WFG1();
  }

  @Test
  public void testConstructor1() {
    SimpleRandomMuta op = new SimpleRandomMuta(0.9);
    Solution solution = (Solution) problem.createSolution();
    Solution sol = op.execute((DoubleSolution) solution);
    assertTrue(sol != null);
  }

  @Test
  public void testGetParameters() {
    SimpleRandomMuta op = new SimpleRandomMuta(0.9);
    assertTrue(op.getParameters() != null);
  }

}

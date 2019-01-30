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

import br.usp.poli.pcs.lti.jmetalhhhelper.util.WeightArrayListsGenerator;
import java.util.ArrayList;

import org.junit.Test;

/**
 *
 */
public class WeightVectorsGeneratorTest {

  public WeightVectorsGeneratorTest() {
  }

  /**
   * Test of main method, of class WeightVectorsGeneratorTest.
   */
  @Test
  public void testMain() {
    System.out.println("main");
    ArrayList<String> params = new ArrayList<>();
    params.add("-hparam");
    params.add(String.valueOf(6));
    params.add("-mparam");
    params.add(String.valueOf(2));
    params.add("-tparam");
    params.add(String.valueOf(1));
    String[] args = params.toArray(new String[0]);
    WeightArrayListsGenerator.main(args);
  }

  /**
   * Test of generate method, of class WeightVectorsGeneratorTest.
   */
  @Test
  public void testGenerate() {
    System.out.println("generate");
    WeightArrayListsGenerator gen = new WeightArrayListsGenerator(2, 6, 1.0 / 6.0, 1);
    gen.generate();
  }


}

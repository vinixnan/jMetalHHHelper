package br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics;

import java.io.FileNotFoundException;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.archive.impl.NonDominatedSolutionListArchive;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.util.FrontUtils;

/**
 * This class extends Calculator class and perform an evaluation using the
 * RniCalculator Indicator.
 */
public class RniCalculator<S extends Solution<?>> extends Calculator {

  /**
   * The population size.
   */
  private @Getter @Setter int populationMaxSize;

  /**
   * Instantiates a new Rni.
   *
   * @param numberOfObjectives the number of objectives
   * @param populationMaxSize the population max size
   * @param path the path
   * @throws FileNotFoundException the file not found exception
   */
  public RniCalculator(int numberOfObjectives, int populationMaxSize, String path)
      throws FileNotFoundException {
    super(numberOfObjectives, path);
    this.populationMaxSize = populationMaxSize;
    this.indicatorName = "RNI";
    lowerValuesAreBetter=false;
  }

  /**
   * Instantiates a new Rni calculator.
   *
   * @param numberOfObjectives the number of objectives
   * @param populationMaxSize the population max size
   * @param referenceFront the reference front
   */
  public RniCalculator(int numberOfObjectives, int populationMaxSize, Front referenceFront) {
    super(numberOfObjectives, referenceFront);
    this.populationMaxSize = populationMaxSize;
    this.indicatorName = "RNI";
    lowerValuesAreBetter=false;
  }

  @Override
  public double calculate(Front front, double[] maximumValues, double[] minimumValues) {
    List<S> listOfFront = (List<S>) FrontUtils.convertFrontToSolutionList(front);
    NonDominatedSolutionListArchive ndominated = new NonDominatedSolutionListArchive();
    for (Solution s : listOfFront) {
      ndominated.add(s);
    }
    int listSize = ndominated.size();
    return ((double) listSize) / this.populationMaxSize;
  }
}

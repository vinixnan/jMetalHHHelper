package br.usp.poli.pcs.lti.jmetalhhhelper.core;

import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.GdCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.HypervolumeCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.IgdCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.RCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.RniCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.SpreadCalculator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uma.jmetal.qualityindicator.impl.GenerationalDistance;
import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistance;
import org.uma.jmetal.qualityindicator.impl.R2;
import org.uma.jmetal.qualityindicator.impl.Spread;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontNormalizer;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.PointSolution;
import org.uma.jmetal.util.point.impl.ArrayPoint;
import org.uma.jmetal.util.point.Point;

/**
 *
 */
public class SeveralIndicatorTest {

  public static Front frontA;
  public static Front frontB;
  public static Front normalizedFrontA;
  public static Front normalizedfrontB;
  public static Front referenceFront;
  public static Front normalizedReferenceFront;

  @BeforeClass
  public static void init() {
    int numberOfPoints = 3;
    int numberOfDimensions = 2;
    frontA = new ArrayFront(numberOfPoints, numberOfDimensions);
    frontB = new ArrayFront(numberOfPoints, numberOfDimensions);
    referenceFront = new ArrayFront(numberOfPoints * 2, numberOfDimensions);

    Point point1 = new ArrayPoint(numberOfDimensions);
    point1.setValue(0, 0.0);
    point1.setValue(1, 6.0);
    Point point2 = new ArrayPoint(numberOfDimensions);
    point2.setValue(0, 2.0);
    point2.setValue(1, 3.0);
    Point point3 = new ArrayPoint(numberOfDimensions);
    point3.setValue(0, 4.0);
    point3.setValue(1, 2.0);

    frontA.setPoint(0, point1);
    frontA.setPoint(1, point2);
    frontA.setPoint(2, point3);

    Point point4 = new ArrayPoint(numberOfDimensions);
    point4.setValue(0, 1.0);
    point4.setValue(1, 7.0);
    Point point5 = new ArrayPoint(numberOfDimensions);
    point5.setValue(0, 2.0);
    point5.setValue(1, 3.0);
    Point point6 = new ArrayPoint(numberOfDimensions);
    point6.setValue(0, 3.5);
    point6.setValue(1, 1.0);

    frontB.setPoint(0, point4);
    frontB.setPoint(1, point5);
    frontB.setPoint(2, point6);

    referenceFront.setPoint(0, point1);
    referenceFront.setPoint(1, point2);
    referenceFront.setPoint(2, point3);
    referenceFront.setPoint(3, point4);
    referenceFront.setPoint(4, point5);
    referenceFront.setPoint(5, point6);

    double[] tempMinimum = FrontUtils.getMinimumValues(referenceFront);
    double[] tempMaximum = FrontUtils.getMaximumValues(referenceFront);
    FrontNormalizer frontNormalizer = new FrontNormalizer(tempMinimum, tempMaximum);
    normalizedFrontA = frontNormalizer.normalize(frontA);
    normalizedfrontB = frontNormalizer.normalize(frontB);
    normalizedReferenceFront = frontNormalizer.normalize(referenceFront);

  }

  @Test
  public void testHypervolume() {
    System.out.println("testHypervolume");
    HypervolumeCalculator metric = new HypervolumeCalculator(2);
    metric.setParetoTrueFront(referenceFront);
    PISAHypervolume jMetalMetric = new PISAHypervolume(normalizedReferenceFront);
    double metricValueAFromCalculator = metric.execute(frontA);
    List<PointSolution> frontAToListNotNormalized = FrontUtils
        .convertFrontToSolutionList(frontA);
    double metricValueAFromListCalculator = metric.execute(frontAToListNotNormalized);
    List<PointSolution> frontAToList = FrontUtils
        .convertFrontToSolutionList(normalizedFrontA);
    double metricValueAFromjMetal = jMetalMetric.evaluate(frontAToList);
    Assert.assertTrue(metricValueAFromCalculator == metricValueAFromjMetal
        && metricValueAFromjMetal == metricValueAFromListCalculator);
  }

  @Test
  public void testIGD() {
    System.out.println("testIGD");
    IgdCalculator metric = new IgdCalculator(2, referenceFront);
    InvertedGenerationalDistance jMetalMetric = new InvertedGenerationalDistance(normalizedReferenceFront);
    double metricValueAFromCalculator = metric.execute(frontA);
    List<PointSolution> frontAToListNotNormalized = FrontUtils.convertFrontToSolutionList(normalizedFrontA);
    double metricValueAFromjMetal = jMetalMetric.evaluate(frontAToListNotNormalized);
    Assert.assertTrue(metricValueAFromCalculator == metricValueAFromjMetal);
  }

  @Test
  public void testGD() {
    System.out.println("testGD");
    GdCalculator metric = new GdCalculator(2, referenceFront);
    GenerationalDistance jMetalMetric = new GenerationalDistance(normalizedReferenceFront);
    double metricValueAFromCalculator = metric.execute(frontA);
    List<PointSolution> frontAToListNotNormalized = FrontUtils.convertFrontToSolutionList(normalizedFrontA);
    double metricValueAFromjMetal = jMetalMetric.evaluate(frontAToListNotNormalized);
    Assert.assertTrue(metricValueAFromCalculator == metricValueAFromjMetal);
  }

  @Test
  public void testR() {
    System.out.println("testR");
    RCalculator metric;
    try {
      metric = new RCalculator(2, referenceFront.getNumberOfPoints(), referenceFront);
      String weightPath = "MOEAD_Weights/W" + 2 + "D_"
          + 6 + ".dat";
      R2 jMetalMetric;
      try {
        jMetalMetric = new R2(weightPath, referenceFront);
        double metricValueAFromCalculator = metric.execute(frontA);
        List<PointSolution> frontAToListNotNormalized = FrontUtils
            .convertFrontToSolutionList(frontA);
        double metricValueAFromListCalculator = metric.execute(frontAToListNotNormalized);

        List<PointSolution> frontAToList = FrontUtils
            .convertFrontToSolutionList(normalizedFrontA);
        //our calculator class is changed
        double metricValueAFromjMetal = jMetalMetric.evaluate(frontAToList);
        Assert.assertTrue(metricValueAFromCalculator == metricValueAFromjMetal
            && metricValueAFromjMetal == metricValueAFromListCalculator);
      } catch (IOException ex) {
        Logger.getLogger(SeveralIndicatorTest.class.getName()).log(Level.SEVERE, null, ex);
      }
    } catch (IOException ex) {
      Logger.getLogger(SeveralIndicatorTest.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  @Test
  public void testSpread() {
    System.out.println("testSpread");
    SpreadCalculator metric = new SpreadCalculator(2, referenceFront);
    Spread jMetalMetric = new Spread(normalizedReferenceFront);
    double metricValueAFromCalculator = metric.execute(frontA);
    List<PointSolution> frontAToListNotNormalized = FrontUtils.convertFrontToSolutionList(normalizedFrontA);
    double metricValueAFromjMetal = jMetalMetric.evaluate(frontAToListNotNormalized);
    Assert.assertTrue(metricValueAFromCalculator == metricValueAFromjMetal);
  }

  @Test
  public void testRNI() {
    System.out.println("testRNI");
    RniCalculator metric = new RniCalculator(2, referenceFront.getNumberOfPoints(), referenceFront);
    double metricValueAFromCalculator = metric.execute(referenceFront);
    List<PointSolution> frontAToListNotNormalized = FrontUtils
        .convertFrontToSolutionList(frontA);
    double metricValueAFromListCalculator = metric.execute(frontAToListNotNormalized);

    Assert.assertTrue(metricValueAFromCalculator == 0.5 && 0.5 == metricValueAFromListCalculator);
  }

  @Test
  public void testSharingInformationAmongIndicators() {
    System.out.println("testSharingInformationAmongIndicators");
    HypervolumeCalculator hyper = new HypervolumeCalculator(2);
    IgdCalculator igd = new IgdCalculator(2, referenceFront);
    GdCalculator gd = new GdCalculator(2, referenceFront);
    igd.clear();
    gd.clear();
    hyper.clear();
    hyper.setParetoTrueFront(referenceFront);
    igd.setParetoTrueFront(referenceFront);
    igd.setParetoTrueFront(referenceFront);
    gd.setParetoTrueFront(referenceFront);
    
    double metricValueAFromCalculatorGD = gd.execute(frontA);
    double metricValueAFromCalculatorIGD = igd.execute(frontA);
    double metricValueAFromCalculatorHyp = hyper.execute(frontA);
    
    
    List<PointSolution> normalizedfrontAToList = FrontUtils
        .convertFrontToSolutionList(normalizedFrontA);
    
    GenerationalDistance jMetalMetricGD = new GenerationalDistance(normalizedReferenceFront);
    double metricValueAFromjMetalGD = jMetalMetricGD.evaluate(normalizedfrontAToList);
    
    InvertedGenerationalDistance jMetalMetricIGD = new InvertedGenerationalDistance(normalizedReferenceFront);
    double metricValueAFromjMetalIGD = jMetalMetricIGD.evaluate(normalizedfrontAToList);
    
    PISAHypervolume jMetalMetricHyp = new PISAHypervolume(normalizedReferenceFront);
    double metricValueAFromjMetalHyp = jMetalMetricHyp.evaluate(normalizedfrontAToList);
    Assert.assertTrue(metricValueAFromCalculatorGD == metricValueAFromjMetalGD
        && metricValueAFromCalculatorHyp == metricValueAFromjMetalHyp
        && metricValueAFromCalculatorIGD == metricValueAFromjMetalIGD);
  }

  @Test
  public void testSavingandReadingFromFile() {
    System.out.println("testSavingandReadingFromFile");
    List<PointSolution> frontAToList = FrontUtils
        .convertFrontToSolutionList(frontA);
    new SolutionListOutput(frontAToList)
        .setSeparator("\t")
        .setVarFileOutputContext(new DefaultFileOutputContext("frontA_VAR"))
        .setFunFileOutputContext(new DefaultFileOutputContext("frontA_FUN"))
        .print();
    List<PointSolution> referenceFrontToList = FrontUtils
        .convertFrontToSolutionList(referenceFront);
    new SolutionListOutput(referenceFrontToList)
        .setSeparator("\t")
        .setVarFileOutputContext(new DefaultFileOutputContext("reference_VAR"))
        .setFunFileOutputContext(new DefaultFileOutputContext("reference_FUN"))
        .print();

    HypervolumeCalculator hyperFromAttributtes = new HypervolumeCalculator(2);
    hyperFromAttributtes.setParetoTrueFront(referenceFront);
    double hypValueFromListAttributtes = hyperFromAttributtes.execute(frontAToList);
    double hypValueFromFrontAttributtes = hyperFromAttributtes.execute(frontA);

    HypervolumeCalculator hyperFromFile = new HypervolumeCalculator(2);
    try {
      hyperFromFile.setParetoTrueFront(new ArrayFront("reference_FUN"));
      double hypValueFromFile = hyperFromFile.execute("frontA_FUN");
      Assert.assertTrue(hypValueFromListAttributtes == hypValueFromFrontAttributtes
          && hypValueFromListAttributtes == hypValueFromFile);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(SeveralIndicatorTest.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
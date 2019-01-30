package br.usp.poli.pcs.lti.jmetalhhhelper.util;

import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.AlgorithmEffort;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.Calculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.EpsilonCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.GdCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.HypervolumeCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.HypervolumeRatioCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.IgdCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.IgdPlusCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.NRCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.RCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.RniCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.SpreadCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.UDMetricHandler;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.QuickHypervolumeCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.SpacingCalculator;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.WFGHypervolumeCalculator;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.front.imp.ArrayFront;

/**
 * The type Indicator factory.
 */
public class IndicatorFactory {

  /**
   * The constant Hypervolume.
   */
  public static final String Hypervolume = "Hypervolume";
  /**
   * The constant RniCalculator.
   */
  public static final String RNI = "RNI";
  /**
   * The constant IGD.
   */
  public static final String IGD = "IGD";
  /**
   * The constant IGDPlus.
   */
  public static final String IGDPlus = "IGDPlus";
  /**
   * The constant R.
   */
  public static final String R = "R";
  /**
   * The constant GD.
   */
  public static final String GD = "GD";
  /**
   * The constant Spread.
   */
  public static final String Spread = "Spread";
  /**
   * The constant Epsilon.
   */
  public static final String Epsilon = "Epsilon";
  
  /**
   * The constant Uniform distribution.
   */
  public static final String UD = "UD";
  
  /**
   * The constant Algorithm Effort.
   */
  public static final String AlgorithmEffort = "AE";
  
  /**
   * The constant Hypervolume.
   */
  public static final String FastHypervolume = "FastHypervolume";
  
  /**
   * The constant Hypervolume.
   */
  public static final String Spacing = "Spacing";
  
  /**
   * The constant Hypervolume.
   */
  public static final String NR = "NR";
  
  public static final String HR = "HR";

  /**
   * Build calculator calculator.
   *
   * @param qualityIndicatorName the quality indicator name
   * @param problem the problem
   * @param populationSize the population size
   * @return the calculator
   * @throws java.io.FileNotFoundException file not found.
   * @throws java.io.IOException file not found.
   */
  public static Calculator buildCalculator(String qualityIndicatorName, Problem problem,
      int populationSize) throws FileNotFoundException, IOException {
    String pf =
        "pareto_fronts/" + problem.getName() + "." + problem.getNumberOfObjectives() + "D.pf";
    if(problem.getName().equals("UF")){
        pf =
        "pareto_fronts/" + problem.getClass().getSimpleName() + "." + problem.getNumberOfObjectives() + "D.pf";
    }
    switch (qualityIndicatorName) {
      case IndicatorFactory.Hypervolume:
        HypervolumeCalculator hyp=new HypervolumeCalculator(problem.getNumberOfObjectives());
        if(problem.getName().startsWith("WFG") || problem.getName().startsWith("DTLZ") || problem.getName().startsWith("UF") || problem.getName().startsWith("ZDT")){
            hyp.setParetoTrueFront(new ArrayFront(pf));
        }
        else{
            //Real problems will not use that, keeping for safe
            hyp.addParetoFront(new ArrayFront(pf));
        }
        return hyp;
      case IndicatorFactory.FastHypervolume:
        return new QuickHypervolumeCalculator(problem.getNumberOfObjectives());
      case IndicatorFactory.Epsilon:
        return new EpsilonCalculator(problem.getNumberOfObjectives(), pf);
      case IndicatorFactory.RNI:
        return new RniCalculator(problem.getNumberOfObjectives(), populationSize, pf);
      case IndicatorFactory.IGD:
        return new IgdCalculator(problem.getNumberOfObjectives(), pf);
      case IndicatorFactory.IGDPlus:
        return new IgdPlusCalculator(problem.getNumberOfObjectives(), pf);
      case IndicatorFactory.GD:
        return new GdCalculator(problem.getNumberOfObjectives(), pf);
      case IndicatorFactory.R:
        return new RCalculator(problem.getNumberOfObjectives(), populationSize, pf);
      case IndicatorFactory.Spread:
        return new SpreadCalculator(problem.getNumberOfObjectives(), pf);
      case IndicatorFactory.AlgorithmEffort:
        AlgorithmEffort ae=new AlgorithmEffort(populationSize);
        ae.setParetoTrueFront(new ArrayFront(pf));//necessary for compatibility
        return ae;
      case IndicatorFactory.UD:
        return new UDMetricHandler(problem.getNumberOfObjectives(), pf);
      case IndicatorFactory.Spacing:
        return new SpacingCalculator(problem.getNumberOfObjectives(), pf);
      case IndicatorFactory.NR:
        return new NRCalculator(problem.getNumberOfObjectives(), populationSize, pf);
      case IndicatorFactory.HR:
        return new HypervolumeRatioCalculator(problem.getNumberOfObjectives());
      default:
        return null;
    }
  }
}
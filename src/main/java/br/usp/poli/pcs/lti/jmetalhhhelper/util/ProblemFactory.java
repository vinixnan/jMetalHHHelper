package br.usp.poli.pcs.lti.jmetalhhhelper.util;

import br.usp.poli.pcs.lti.jmetalhhhelper.problems.CarSideImpact;
import br.usp.poli.pcs.lti.jmetalhhhelper.problems.CrashWorthiness;
import br.usp.poli.pcs.lti.jmetalhhhelper.problems.Machining;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.MultiobjectiveTSP;
import org.uma.jmetal.problem.multiobjective.OneZeroMax;
import org.uma.jmetal.problem.multiobjective.UF.UF1;
import org.uma.jmetal.problem.multiobjective.UF.UF10;
import org.uma.jmetal.problem.multiobjective.UF.UF2;
import org.uma.jmetal.problem.multiobjective.UF.UF3;
import org.uma.jmetal.problem.multiobjective.UF.UF4;
import org.uma.jmetal.problem.multiobjective.UF.UF5;
import org.uma.jmetal.problem.multiobjective.UF.UF6;
import org.uma.jmetal.problem.multiobjective.UF.UF7;
import org.uma.jmetal.problem.multiobjective.UF.UF8;
import org.uma.jmetal.problem.multiobjective.UF.UF9;
import org.uma.jmetal.problem.multiobjective.Water;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ1;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ2;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ3;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ4;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ5;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ6;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ7;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;
import org.uma.jmetal.problem.multiobjective.wfg.WFG2;
import org.uma.jmetal.problem.multiobjective.wfg.WFG3;
import org.uma.jmetal.problem.multiobjective.wfg.WFG4;
import org.uma.jmetal.problem.multiobjective.wfg.WFG5;
import org.uma.jmetal.problem.multiobjective.wfg.WFG6;
import org.uma.jmetal.problem.multiobjective.wfg.WFG7;
import org.uma.jmetal.problem.multiobjective.wfg.WFG8;
import org.uma.jmetal.problem.multiobjective.wfg.WFG9;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT1;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT2;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT3;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT4;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT5;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT6;

/**
 * The type Problem factory.
 */
public class ProblemFactory {

    /**
     * Gets tsp problems.
     *
     * @param distanceFile the distance file
     * @param costFile the cost file
     * @return the tsp problems
     */
    public static Problem getTspProblems(String distanceFile, String costFile) {
        try {
            return new MultiobjectiveTSP(distanceFile, costFile);
        } catch (IOException ex) {
            Logger.getLogger(ProblemFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets problem.
     *
     * @param tipo the tipo
     * @param numberOfObjectives the number of objectives
     * @return the problem
     */
    public static Problem getProblem(String tipo, int numberOfObjectives) {
        return getProblems(tipo, 0, 0, numberOfObjectives)[0];
    }

    /**
     * Gets problem.
     *
     * @param tipo the tipo
     * @param numberOfDistanceVariables the number of distance variables
     * @param numberOfObjectives the number of objectives
     * @return the problem
     */
    public static Problem getProblem(String tipo, int numberOfDistanceVariables,
            int numberOfObjectives) {
        return getProblems(tipo, 0, numberOfDistanceVariables, numberOfObjectives)[0];
    }

    /**
     * Gets problem.
     *
     * @param tipo the tipo
     * @param numberOfPositions the number of positions
     * @param numberOfDistanceVariables the number of distance variables
     * @param numberOfObjectives the number of objectives
     * @return the problem
     */
    public static Problem getProblem(String tipo, int numberOfPositions,
            int numberOfDistanceVariables, int numberOfObjectives) {
        return getProblems(tipo, numberOfPositions, numberOfDistanceVariables, numberOfObjectives)[0];
    }

    /**
     * Get problems problem [ ].
     *
     * @param tipo the tipo
     * @param numberOfObjectives the number of objectives
     * @return the problem [ ]
     */
    public static Problem[] getProblems(String tipo, int numberOfObjectives) {
        return getProblems(tipo, 0, 0, numberOfObjectives);
    }

    /**
     * Get problems problem [ ].
     *
     * @param tipo the tipo
     * @param numberOfDistanceVariables the number of distance variables
     * @param numberOfObjectives the number of objectives
     * @return the problem [ ]
     */
    public static Problem[] getProblems(String tipo, int numberOfDistanceVariables,
            int numberOfObjectives) {
        return getProblems(tipo, 0, numberOfDistanceVariables, numberOfObjectives);
    }

    /**
     * Get continuous problems problem [ ].
     *
     * @param tipo the tipo
     * @param numberOfPositions the wfg number of positions
     * @param numberOfDistanceVariables the number of distance variables
     * @param numberOfObjectives the number of objectives
     * @return the problem [ ]
     */
    public static Problem[] getProblems(String tipo, int numberOfPositions,
            int numberOfDistanceVariables, int numberOfObjectives) {
        int N = 2;//in contest
        double epsilon = 0.1;
        if (tipo.equalsIgnoreCase("WFG2")) {
            Problem[] problems = {
                new WFG2(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),};
            return problems;
        } else if (tipo.equalsIgnoreCase("WFG3")) {
            Problem[] problems = {
                new WFG3(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),};
            return problems;
        } else if (tipo.equalsIgnoreCase("WFG4")) {
            Problem[] problems = {
                new WFG4(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),};
            return problems;
        } else if (tipo.equalsIgnoreCase("WFG5")) {
            Problem[] problems = {
                new WFG5(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),};
            return problems;
        } else if (tipo.equalsIgnoreCase("WFG6")) {
            Problem[] problems = {
                new WFG6(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),};
            return problems;
        } else if (tipo.equalsIgnoreCase("WFG7")) {
            Problem[] problems = {
                new WFG7(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),};
            return problems;
        } else if (tipo.equalsIgnoreCase("WFG8")) {
            Problem[] problems = {
                new WFG8(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),};
            return problems;
        } else if (tipo.equalsIgnoreCase("WFG9")) {
            Problem[] problems = {
                new WFG9(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),};
            return problems;
        } else if (tipo.equalsIgnoreCase("DTLZ1")) {
            Problem[] problems = {
                new DTLZ1(numberOfDistanceVariables, numberOfObjectives),};
            return problems;
        } else if (tipo.equalsIgnoreCase("DTLZ2")) {
            Problem[] problems = {
                new DTLZ2(numberOfDistanceVariables, numberOfObjectives),};
            return problems;
        } else if (tipo.equalsIgnoreCase("DTLZ3")) {
            Problem[] problems = {
                new DTLZ3(numberOfDistanceVariables, numberOfObjectives),};
            return problems;
        } else if (tipo.equalsIgnoreCase("DTLZ4")) {
            Problem[] problems = {
                new DTLZ4(numberOfDistanceVariables, numberOfObjectives),};
            return problems;
        } else if (tipo.equalsIgnoreCase("DTLZ5")) {
            Problem[] problems = {
                new DTLZ5(numberOfDistanceVariables, numberOfObjectives),};
            return problems;
        } else if (tipo.equalsIgnoreCase("DTLZ6")) {
            Problem[] problems = {
                new DTLZ6(numberOfDistanceVariables, numberOfObjectives),};
            return problems;
        } else if (tipo.equalsIgnoreCase("DTLZ7")) {
            Problem[] problems = {
                new DTLZ7(numberOfDistanceVariables, numberOfObjectives),};
            return problems;
        } else if (tipo.equalsIgnoreCase("ZDT1")) {
            Problem[] problems = {
                new ZDT1(numberOfDistanceVariables),};
            return problems;
        } else if (tipo.equalsIgnoreCase("ZDT2")) {
            Problem[] problems = {
                new ZDT2(numberOfDistanceVariables),};
            return problems;
        } else if (tipo.equalsIgnoreCase("ZDT3")) {
            Problem[] problems = {
                new ZDT3(numberOfDistanceVariables),};
            return problems;
        } else if (tipo.equalsIgnoreCase("ZDT4")) {
            Problem[] problems = {
                new ZDT4(numberOfDistanceVariables),};
            return problems;
        } else if (tipo.equalsIgnoreCase("ZDT5")) {
            Problem[] problems = {
                new ZDT5(numberOfDistanceVariables),};
            return problems;
        } else if (tipo.equalsIgnoreCase("ZDT6")) {
            Problem[] problems = {
                new ZDT6(numberOfDistanceVariables),};
            return problems;
        } else if (tipo.equalsIgnoreCase("UF1")) {
            Problem[] problems = {
                new UF1(numberOfDistanceVariables),};
            return problems;
        } else if (tipo.equalsIgnoreCase("UF2")) {
            Problem[] problems = {
                new UF2(numberOfDistanceVariables),};
            return problems;
        } else if (tipo.equalsIgnoreCase("UF3")) {
            Problem[] problems = {
                new UF3(numberOfDistanceVariables),};
            return problems;
        } else if (tipo.equalsIgnoreCase("UF4")) {
            Problem[] problems = {
                new UF4(numberOfDistanceVariables),};
            return problems;
        } else if (tipo.equalsIgnoreCase("UF5")) {
            Problem[] problems = {
                new UF5(numberOfDistanceVariables, N, epsilon),};
            return problems;
        } else if (tipo.equalsIgnoreCase("UF6")) {
            Problem[] problems = {
                new UF6(numberOfDistanceVariables, N, epsilon),};
            return problems;
        } else if (tipo.equalsIgnoreCase("UF7")) {
            Problem[] problems = {
                new UF7(numberOfDistanceVariables),};
            return problems;
        } else if (tipo.equalsIgnoreCase("UF8")) {
            Problem[] problems = {
                new UF8(numberOfDistanceVariables),};
            return problems;
        } else if (tipo.equalsIgnoreCase("UF9")) {
            Problem[] problems = {
                new UF9(numberOfDistanceVariables, epsilon),};
            return problems;
        } else if (tipo.equalsIgnoreCase("UF10")) {
            Problem[] problems = {
                new UF10(numberOfDistanceVariables),};
            return problems;
        } else if (tipo.equalsIgnoreCase("DTLZ")) {
            Problem[] problems = {
                //segundo o artigo mixture
                new DTLZ1(5, 3),
                new DTLZ2(7, 3),
                new DTLZ3(12, 3),
                new DTLZ4(10, 3),
                new DTLZ5(10, 3),
                new DTLZ6(10, 3),
                new DTLZ7(),};
            return problems;
        } else if (tipo.equalsIgnoreCase("WFG")) {
            Problem[] problems = {
                //segundo o artigo mixture
                new WFG1(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG2(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG3(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG4(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG5(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG6(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG7(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG8(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG9(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),};
            return problems;
        } else if (tipo.equalsIgnoreCase("OneZeroMax")) {
            Problem[] problems = {
                new OneZeroMax(),};
            return problems;
        } else if (tipo.equalsIgnoreCase("ENG")) {
            Problem[] problems = {
                new CrashWorthiness(), new CarSideImpact(), new Water(), new Machining()};
            return problems;
        } 
        else if (tipo.equalsIgnoreCase("CrashWorthiness")) {
            Problem[] problems = {
                new CrashWorthiness()};
            return problems;
        }
        else if (tipo.equalsIgnoreCase("CarSideImpact")) {
            Problem[] problems = {
                new CarSideImpact()};
            return problems;
        }
        else if (tipo.equalsIgnoreCase("Water")) {
            Problem[] problems = {
                new Water()};
            return problems;
        }
        else if (tipo.equalsIgnoreCase("Machining")) {
            Problem[] problems = {
                new Machining()};
            return problems;
        }
        else {
            Problem[] problems = {
                new WFG1(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG2(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG3(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG4(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG5(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG6(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG7(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG8(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG9(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new DTLZ1(5, 3),
                new DTLZ2(7, 3),
                new DTLZ3(12, 3),
                new DTLZ4(10, 3),
                new DTLZ5(10, 3),
                new DTLZ6(10, 3),
                new DTLZ7(),};
            return problems;
        }
    }
}

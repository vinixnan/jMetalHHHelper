package br.usp.poli.pcs.lti.jmetalhhhelper.util;

import br.usp.poli.pcs.lti.jmetalhhhelper.core.ParametersforAlgorithm;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.ParametersforHeuristics;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.interfaces.Operator;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.interfaces.StandardMetaheuristic;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.algs.Gde3;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.algs.Ibea;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.algs.Nsgaii;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.algs.Paes;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.algs.Spea2;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover.BlxAlphaCrossover;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover.DifferentialEvolution;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover.HuxCrossover;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover.NullCross;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover.OnePointCrossover;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover.PmxCrossover;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.crossover.SbxCrossover;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation.BitFlipMuta;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation.NonUniformMuta;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation.NullMuta;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation.PermutationSwapMuta;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation.PolynomialMuta;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.mutation.UniformMuta;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.management.JMException;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.CrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

/**
 * This class builds algorithms.
 */
/**
 * The type Algorithm builder.
 *
 * @param <S> jMetal need.
 */
public class AlgorithmBuilder<S extends Solution<?>> {

    /**
     * The Problem.
     */
    protected Problem problem;

    /**
     * Instantiates a new Algorithm builder.
     *
     * @param problem the problem
     */
    public AlgorithmBuilder(Problem problem) {
        this.problem = problem;
    }

    /**
     * Generate cross crossover operator.
     *
     * @param configParams the config params
     * @return the crossover operator
     * @throws JMException the jm exception
     */
    public CrossoverOperator generateCross(ParametersforHeuristics configParams) throws JMException {
        Operator operator = null;
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("probability", configParams.getCrossoverProbality());
        if ("sbxCrossover".equalsIgnoreCase(configParams.getCrossoverName())) {
            parameters.put("distributionIndex", configParams.getCrossoverDistribution());
            operator = new SbxCrossover();
            operator.setParameters(parameters);
            operator.allocateParameters();

        } else if ("blxAlphaCrossover".equalsIgnoreCase(configParams.getCrossoverName())) {
            parameters.put("alpha", configParams.getAlpha());
            operator = new BlxAlphaCrossover();
            operator.setParameters(parameters);
            operator.allocateParameters();

        } else if ("huxCrossover".equalsIgnoreCase(configParams.getCrossoverName())) {
            operator = new HuxCrossover();
            operator.setParameters(parameters);
            operator.allocateParameters();

        } else if ("onePointCrossover".equalsIgnoreCase(configParams.getCrossoverName())) {
            operator = new OnePointCrossover();
            operator.setParameters(parameters);
            operator.allocateParameters();

        } else if ("NullCross".equalsIgnoreCase(configParams.getCrossoverName())) {
            operator = new NullCross();
            operator.setParameters(parameters);
            operator.allocateParameters();

        } else if ("DifferentialEvolutionCrossover".equalsIgnoreCase(configParams.getCrossoverName())) {
            parameters.put("cr", configParams.getDeCr());
            parameters.put("f", configParams.getDeF());
            parameters.put("k", configParams.getDeK());
            parameters.put("variant", configParams.getDeVariant());
            operator = new DifferentialEvolution(0, 0, configParams.getDeVariant());
            operator.setParameters(parameters);
            operator.allocateParameters();

        } else if ("pmxCrossover".equalsIgnoreCase(configParams.getCrossoverName())) {
            operator = new PmxCrossover();
            operator.setParameters(parameters);
            operator.allocateParameters();

        }
        return (CrossoverOperator) operator;
    }

    /**
     * Generate muta mutation operator.
     *
     * @param configParams the config params
     * @param maxIterations the max iterations
     * @return the mutation operator
     * @throws JMException the jm exception
     */
    public MutationOperator generateMuta(ParametersforHeuristics configParams, int maxIterations)
            throws JMException {
        Operator operator = null;
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("probability", configParams.getMutationProbability());
        if ("polynomialMutation".equalsIgnoreCase(configParams.getMutationName())) {
            parameters.put("distributionIndex", configParams.getMutationDistribution());
            operator = new PolynomialMuta();
            operator.setParameters(parameters);
            operator.allocateParameters();

        } else if ("uniformMutation".equalsIgnoreCase(configParams.getMutationName())) {
            parameters.put("perturbation", configParams.getMutationPertubation());
            operator = new UniformMuta();
            operator.setParameters(parameters);
            operator.allocateParameters();

        } else if ("nonUniformMutation".equalsIgnoreCase(configParams.getMutationName())) {
            parameters.put("perturbation", configParams.getMutationPertubation());
            parameters.put("maxIterations", maxIterations);
            operator = new NonUniformMuta();
            operator.setParameters(parameters);
            operator.allocateParameters();

        } else if ("bitFlipMutation".equalsIgnoreCase(configParams.getMutationName())) {
            operator = new BitFlipMuta();
            operator.setParameters(parameters);
            operator.allocateParameters();

        } else if ("nullMutation".equalsIgnoreCase(configParams.getMutationName())) {
            operator = new NullMuta();
            operator.setParameters(parameters);
            operator.allocateParameters();

        } else if ("PermutationSwapMutation".equalsIgnoreCase(configParams.getMutationName())) {
            operator = new PermutationSwapMuta();
            operator.setParameters(parameters);
            operator.allocateParameters();

        }
        return (MutationOperator) operator;
    }

    /**
     * Generate selection selection operator.
     *
     * @return the selection operator
     * @throws JMException the jm exception
     */
    public SelectionOperator generateSelection() throws JMException {
        SelectionOperator se = new BinaryTournamentSelection();
        return se;
    }

    /**
     * Create ibea standard meta-heuristic.
     *
     * @param configAlg the config alg
     * @param configHeuristic the config heuristic
     * @return the standard meta-heuristic
     * @throws JMException the jm exception
     */
    public StandardMetaheuristic createIbea(ParametersforAlgorithm configAlg,
            ParametersforHeuristics configHeuristic) throws JMException {
        SelectionOperator selection = this.generateSelection();
        CrossoverOperator crossover = this.generateCross(configHeuristic);
        MutationOperator mutation = this.generateMuta(configHeuristic, configAlg.getMaxIteractions());
        StandardMetaheuristic algorithm = new Ibea(problem, configAlg.getPopulationSize(),
                configAlg.getArchiveSize(),
                configAlg.getMaxIteractions() * configAlg.getPopulationSize(),
                selection, crossover, mutation);
        return algorithm;
    }

    /**
     * Create nsga-ii standard meta-heuristic.
     *
     * @param configAlg the config alg
     * @param configHeuristic the config heuristic
     * @return the standard meta-heuristic
     * @throws JMException the jm exception
     */
    public StandardMetaheuristic createNsgaii(ParametersforAlgorithm configAlg,
            ParametersforHeuristics configHeuristic) throws JMException {
        SelectionOperator selection = this.generateSelection();
        CrossoverOperator crossover = this.generateCross(configHeuristic);
        MutationOperator mutation = this.generateMuta(configHeuristic, configAlg.getMaxIteractions());

        StandardMetaheuristic algorithm = new Nsgaii(problem,
                configAlg.getMaxIteractions() * configAlg.getPopulationSize(),
                configAlg.getPopulationSize(), crossover,
                mutation, selection, new CrowdingDistanceComparator<S>(), new SequentialSolutionListEvaluator());
        return algorithm;
    }

    /**
     * Create spea 2 standard meta-heuristic.
     *
     * @param configAlg the config alg
     * @param configHeuristic the config heuristic
     * @return the standard meta-heuristic
     * @throws JMException the jm exception
     */
    public StandardMetaheuristic createSpea2(ParametersforAlgorithm configAlg,
            ParametersforHeuristics configHeuristic) throws JMException {
        SelectionOperator selection = this.generateSelection();
        CrossoverOperator crossover = this.generateCross(configHeuristic);
        MutationOperator mutation = this.generateMuta(configHeuristic, configAlg.getMaxIteractions());
        StandardMetaheuristic algorithm = new Spea2(problem, configAlg.getMaxIteractions(),
                configAlg.getPopulationSize(), crossover,
                mutation, selection, new SequentialSolutionListEvaluator());
        return algorithm;
    }

    /**
     * Create spea 2 standard meta-heuristic.
     *
     * @param configAlg the config alg
     * @param configHeuristic the config heuristic
     * @return the standard meta-heuristic
     * @throws JMException the jm exception
     */
    public StandardMetaheuristic createGde3(ParametersforAlgorithm configAlg,
            ParametersforHeuristics configHeuristic) throws JMException {
        configHeuristic.setDeCr(0.2);//TEMP @TODO new parameter file
        configHeuristic.setDeF(0.2);
        CrossoverOperator crossover = this.generateCross(configHeuristic);
        StandardMetaheuristic algorithm = new Gde3((DoubleProblem) problem,
                configAlg.getPopulationSize(), configAlg.getMaxIteractions()
                * configAlg.getPopulationSize(), new DifferentialEvolutionSelection(),
                (DifferentialEvolutionCrossover) crossover,
                new SequentialSolutionListEvaluator());
        return algorithm;
    }

    /**
     * Create spea 2 standard meta-heuristic.
     *
     * @param configAlg the config alg
     * @param configHeuristic the config heuristic
     * @return the standard meta-heuristic
     * @throws JMException the jm exception
     */
    public StandardMetaheuristic createPaes(ParametersforAlgorithm configAlg,
            ParametersforHeuristics configHeuristic) throws JMException {
        int biSections = 3;
        MutationOperator mutation = this.generateMuta(configHeuristic, configAlg.getMaxIteractions());
        StandardMetaheuristic algorithm = new Paes(problem, configAlg.getPopulationSize(), configAlg.getMaxIteractions()
                * configAlg.getPopulationSize(), biSections, mutation);
        return algorithm;
    }

    /**
     * Create standard metaheuristic.
     *
     * @param configAlg the config alg
     * @param configHeuristic the config heuristic
     * @return the standard metaheuristic
     * @throws JMException the jm exception
     * @throws FileNotFoundException the file not found exception
     */
    public StandardMetaheuristic create(ParametersforAlgorithm configAlg,
            ParametersforHeuristics configHeuristic) throws JMException, FileNotFoundException {
        switch (configAlg.getAlgorithmName()) {
            case "Ibea":
                return createIbea(configAlg, configHeuristic);
            case "Nsgaii":
                return this.createNsgaii(configAlg, configHeuristic);
            case "Spea2":
                return createSpea2(configAlg, configHeuristic);
            case "Gde3":
                return createGde3(configAlg, configHeuristic);
            case "Paes":
                return createPaes(configAlg, configHeuristic);
            default:
                System.err.println("Algorithm not found");
                return null;
        }
    }

}

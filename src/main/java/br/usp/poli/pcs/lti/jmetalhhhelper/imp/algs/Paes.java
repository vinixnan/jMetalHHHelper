package br.usp.poli.pcs.lti.jmetalhhhelper.imp.algs;

import br.usp.poli.pcs.lti.jmetalhhhelper.core.OpManager;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.interfaces.StandardMetaheuristic;
import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.multiobjective.paes.PAES;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author vinicius
 */
public class Paes<S extends Solution<?>> extends PAES<S> implements StandardMetaheuristic<S> {

    /**
     * Low-Level Heuristic Selector.
     */
    protected OpManager selector;

    /**
     * Max Number of real population generation.
     */
    protected int realPopMaxGeneration;

    protected int maxIterations;
    protected int iterations;

    protected List<S> offspringPopulation;

    public Paes(Problem<S> problem, int archiveSize, int maxEvaluations, int biSections, MutationOperator<S> mutationOperator) {
        super(problem, archiveSize, maxEvaluations, biSections, mutationOperator);
        selector = new OpManager();
        selector.setCrossoverOperator(null);
        selector.setMutationOperator(mutationOperator);
        maxIterations = maxEvaluations / archiveSize;
        iterations = 0;
    }

    @Override
    public int getMaxEvaluations() {
        return this.maxEvaluations;
    }

    @Override
    public int getIterations() {
        return this.iterations;
    }

    @Override
    public OpManager getSelector() {
        return selector;
    }

    @Override
    public void setSelector(OpManager selector) {
        this.selector = selector;
    }

    public int getRealPopMaxGeneration() {
        return realPopMaxGeneration;
    }

    public void setRealPopMaxGeneration(int realPopMaxGeneration) {
        this.realPopMaxGeneration = realPopMaxGeneration;
    }

    @Override
    public int getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Override
    public int getPopulationSize() {
        return archiveSize;
    }

    @Override
    public void setPopulationSize(int populationSize) {
        this.archiveSize = populationSize;
    }

    public int getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(int evaluations) {
        this.evaluations = evaluations;
    }

    @Override
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    @Override
    protected boolean isStoppingConditionReached() {
        return evaluations >= maxEvaluations && iterations >= maxIterations;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List<S> reproduction(List<S> population) {
        S mutatedSolution = (S) population.get(0).copy();
        mutationOperator.execute(mutatedSolution);

        List<S> mutationSolutionList = new ArrayList<>(1);
        mutationSolutionList.add(mutatedSolution);
        return mutationSolutionList;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List<S> replacement(List<S> population, List<S> offspringPopulation) {
        S current = population.get(0);
        S mutatedSolution = offspringPopulation.get(0);

        int flag = comparator.compare(current, mutatedSolution);
        if (flag == 1) {
            current = (S) mutatedSolution.copy();
            archive.add(mutatedSolution);
        } else if (flag == 0) {
            if (archive.add(mutatedSolution)) {
                population.set(0, test(current, mutatedSolution, archive));
            }
        }

        population.set(0, current);
        return population;
    }

    @Override
    public void initMetaheuristic() {
        population = createInitialPopulation();
        population = evaluatePopulation(population);
        initProgress();
        iterations=1;
    }

    @Override
    public void generateNewPopulation() {
        this.executeMethod();
        population = replacement(population, offspringPopulation);
        updateProgress();
        iterations++;
    }

    @Override
    public List<?> executeMethod() {
        List<S> matingPopulation = selection(population);
        offspringPopulation = reproduction(matingPopulation);
        offspringPopulation = evaluatePopulation(offspringPopulation);
        return offspringPopulation;
    }

    @Override
    public void run() {
        this.initMetaheuristic();
        while (!isStoppingConditionReached()) {
            this.generateNewPopulation();
        }
    }

    @Override
    public List<?> updateMainPopulation(List<S> list) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<S> getRealpop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCrossoverOperator(CrossoverOperator<S> co) {

    }

    @Override
    public void setMutationOperator(MutationOperator<S> mo) {
        this.mutationOperator = mo;
    }
}

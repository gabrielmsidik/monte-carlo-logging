package com.smu.sidik.montecarlo.util;

import com.smu.sidik.montecarlo.domain.Forest;
import com.smu.sidik.montecarlo.domain.ForestSimulationInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;

public class ForestSimulationUtil {

    public static final Logger LOG = LoggerFactory.getLogger(ForestSimulationUtil.class);

    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.FLOOR;
    public static final Integer DEFAULT_SCALE = 5;
    public static final MathContext DEFAULT_MATH_CONTEXT = new MathContext(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);

    public static Forest simulate(ForestSimulationInput forestSimulationInput) {
        Forest forest = forestSimulationInput.getForest();
        Integer simulationSteps = forestSimulationInput.getSimulationSteps();
        BigDecimal loggingFactor = forestSimulationInput.getLoggingFactor();

        for (int i = 0; i < simulationSteps; i++) {
            LOG.info("Simulating Step {} of {}", i, simulationSteps);
            LOG.info("Start state: {}", forest);
            progress(forest, loggingFactor);
            LOG.info("End state:   {}", forest);
        }

        return forest;
    }

    public static Forest progress(Forest forest, BigDecimal loggingFactor) {
        int treesLogged = updateTreeCount(forest, loggingFactor);
        LOG.info("Trees logged - {}", treesLogged);
        updateAge(forest);
        return forest;
    }

    private static int updateTreeCount(Forest forest, BigDecimal loggingFactor) {
        Integer treeCount = forest.getTreeCount();
        Integer newTrees = getNewTreeCountWithUncertainty(forest);
        BigDecimal totalNewTrees = BigDecimal.valueOf(treeCount).multiply(loggingFactor).add(BigDecimal.valueOf(newTrees));
        forest.setTreeCount(totalNewTrees.intValue()) ;
        return BigDecimal.valueOf(treeCount).multiply(BigDecimal.ONE.subtract(loggingFactor)).intValue();
    }

    private static void updateAge(Forest forest) {
        forest.setAge(forest.getTreeCount() == 0 ? 0 : forest.getAge() + 1 );
    }

    public static Integer getNewTreeCountWithoutUncertainty(Forest forest) {

        BigDecimal asymptote = BigDecimal.valueOf(100);
        BigDecimal criticalPoint = asymptote.divide(BigDecimal.valueOf(5), DEFAULT_MATH_CONTEXT);
        BigDecimal inverseAsymptote = BigDecimal.ONE.divide(asymptote, DEFAULT_MATH_CONTEXT);

        BigDecimal treeCount = BigDecimal.valueOf(forest.getTreeCount());
        BigDecimal denominator = treeCount.add(BigDecimal.ONE).multiply(inverseAsymptote);
        BigDecimal fractionalTerm = BigDecimal.ONE.divide(denominator, DEFAULT_MATH_CONTEXT);

        BigDecimal growthRate = criticalPoint.subtract(fractionalTerm, DEFAULT_MATH_CONTEXT);

        LOG.debug("Calculated Growth Rate for One hectare of forest with tree count of : {} is {}", treeCount, growthRate);
        int growthCount = growthRate.intValue();
        return growthCount > 0 ? growthCount : 0;
    }

    public static Integer getNewTreeCountWithUncertainty(Forest forest) {
        BigDecimal newTreeCountWithoutUncertainty = BigDecimal.valueOf(getNewTreeCountWithoutUncertainty(forest));

        Random random = new Random();
        BigDecimal randomGaussian = new BigDecimal(String.format("%.5f", random.nextGaussian()));
        int growthCount = randomGaussian.multiply(newTreeCountWithoutUncertainty).add(newTreeCountWithoutUncertainty).intValue();

        LOG.debug("Random growthCount with newTreeCountWithoutUncertainty: {} is {}", newTreeCountWithoutUncertainty, growthCount);
        return growthCount > 0 ? growthCount: 0;
    }
}

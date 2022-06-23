package com.smu.sidik.montecarlo.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ForestSimulationInput {
    private Forest forest;
    private Integer simulationSteps;
    private int branchCount;
    private BigDecimal loggingFactor;
}

package com.smu.sidik.montecarlo;

import com.smu.sidik.montecarlo.domain.Forest;
import com.smu.sidik.montecarlo.domain.ForestSimulationInput;
import com.smu.sidik.montecarlo.util.ForestSimulationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MonteCarloController {
    public static final Logger LOG = LoggerFactory.getLogger(MonteCarloController.class);

    @GetMapping("/ping")
    public String ping() { return "MonteCarloSimulator Online!";}

    @PostMapping("/simulateForestEvolution")
    public Forest simulateForestEvolution(@RequestBody ForestSimulationInput forestSimulationInput) {
        LOG.info("INPUT: {}", forestSimulationInput);
        Forest finalForest = ForestSimulationUtil.simulate(forestSimulationInput);
        return finalForest;
    }

    @PostMapping("/getNewTreeCountWithoutUncertainty")
    public Integer getNewTreeCountWithoutUncertainty(@RequestBody Forest forest) {
        LOG.info("INPUT: {}", forest);
        return ForestSimulationUtil.getNewTreeCountWithoutUncertainty(forest);
    }

    @PostMapping("/getNewTreeCountWitUncertainty")
    public Integer getNewTreeCountWitUncertainty(@RequestBody Forest forest) {
        LOG.info("INPUT: {}", forest);
        return ForestSimulationUtil.getNewTreeCountWithUncertainty(forest);
    }

}

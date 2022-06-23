package com.smu.sidik.montecarlo.domain;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@Data
public class Forest {
    private String id;
    private Integer treeCount;
    private Integer age;
}

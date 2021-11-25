package com.epam.esm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuerySpecification {

    private List<String> tags;
    private BigDecimal price;
    private Integer duration;
    private String text;
    private List<String> order;
    private List<String> sort;
}

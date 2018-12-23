package edu.cad.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SubjectLoadDistributionDto {
    @JsonProperty("type")
    private String type;

    @JsonProperty("value")
    private double value;

    @JsonProperty("staff_id")
    private int staffId;
}

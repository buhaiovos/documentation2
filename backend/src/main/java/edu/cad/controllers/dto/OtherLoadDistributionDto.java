package edu.cad.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OtherLoadDistributionDto {
    @JsonProperty("load_id")
    private int otherLoadId;

    @JsonProperty("staff_id")
    private int staffId;

    @JsonProperty("value")
    private double value;
}

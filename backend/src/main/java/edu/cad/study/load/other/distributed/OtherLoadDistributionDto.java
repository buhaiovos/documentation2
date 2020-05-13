package edu.cad.study.load.other.distributed;

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

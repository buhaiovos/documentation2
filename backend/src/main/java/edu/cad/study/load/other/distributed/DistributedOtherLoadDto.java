package edu.cad.study.load.other.distributed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DistributedOtherLoadDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("other_load_id")
    private int otherLoadId;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("value")
    private double value;
}

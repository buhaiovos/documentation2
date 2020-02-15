package edu.cad.study.load.subject.distributed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DistributedSubjectLoadDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("load_id")
    private int subjectLoadId;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("type")
    private String type;

    @JsonProperty("amount")
    private double amount;
}

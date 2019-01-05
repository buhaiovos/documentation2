package edu.cad.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DistributedSubjectLoadDto {
    @JsonProperty("id")
    private int id;

    @JsonProperty("load_id")
    private int subjectLoadId;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("type")
    private String type;

    @JsonProperty("amount")
    private double amount;
}

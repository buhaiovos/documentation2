package edu.cad.study.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.cad.utils.gson.Option;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Options {
    @JsonProperty("Options")
    List<Option> options;
    @JsonProperty("Result")
    String result;
}

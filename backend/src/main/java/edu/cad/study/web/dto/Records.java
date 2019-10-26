package edu.cad.study.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Accessors(chain = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Records<T> {
    @JsonProperty("Records")
    List<T> records; //stupid library
    @JsonProperty("Result")
    String result;
}

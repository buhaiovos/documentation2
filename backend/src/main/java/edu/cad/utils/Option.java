package edu.cad.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Option {

    @JsonProperty("DisplayText")
    private String displayText;

    @JsonProperty("Value")
    private Object value;

    public Option(String displayText, Object value) {
        this.displayText = displayText;
        this.value = value;
    }

    public static Option empty() {
        return new Option("-", 0);
    }
}

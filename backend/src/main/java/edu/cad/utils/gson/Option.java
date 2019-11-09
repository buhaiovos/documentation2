package edu.cad.utils.gson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
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
    @Expose
    private String displayText;

    @JsonProperty("Value")
    @Expose
    private Object value;

    public Option(String displayText, Object value) {
        this.displayText = displayText;
        this.value = value;
    }

    public static Option empty() {
        return new Option("-", 0);
    }
}

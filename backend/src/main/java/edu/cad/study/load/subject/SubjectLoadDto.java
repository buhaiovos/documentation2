package edu.cad.study.load.subject;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.cad.domain.StudyLoadType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SubjectLoadDto {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("elements")
    private Map<StudyLoadType, Double> loadAmountByType;

    public SubjectLoadDto(SubjectLoadDto from) {
        this.setId(from.id);
        this.setName(from.name);
        this.setLoadAmountByType(new HashMap<>(from.loadAmountByType));
    }
}

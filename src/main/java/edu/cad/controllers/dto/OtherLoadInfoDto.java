package edu.cad.controllers.dto;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OtherLoadInfoDto {
    long id;
    String name;
    Map<String, Double> elements;
}

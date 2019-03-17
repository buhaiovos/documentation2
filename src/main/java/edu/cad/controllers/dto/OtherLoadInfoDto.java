package edu.cad.controllers.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtherLoadInfoDto {
    long id;
    String name;
    Map<String, Double> data;
}

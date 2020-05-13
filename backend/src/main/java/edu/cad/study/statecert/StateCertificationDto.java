package edu.cad.study.statecert;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StateCertificationDto {
    int id;
    int semester;
    String form;
    LocalDate start;
    LocalDate finish;
}

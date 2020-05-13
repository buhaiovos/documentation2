package edu.cad.study.practice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PracticeDto {
    int id;
    int semester;
    int weeks;
    String type;
    LocalDate start;
    LocalDate finish;
}

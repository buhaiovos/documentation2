package edu.cad.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class YearTracked {
    @Column(name = "year_of_information")
    private short yearOfInformation;
}

package edu.cad.controllers;

import edu.cad.entities.Staff;
import edu.cad.repositories.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test-repository")
@AllArgsConstructor
public class TestController {
    private final StaffRepository staffRepository;

    @GetMapping
    public List<Staff> getAll() {
        return staffRepository.findAll();
    }
}

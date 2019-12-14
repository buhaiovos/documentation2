package edu.cad.study;

import edu.cad.utils.gson.Option;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

public abstract class ActionProcessor<Response, Request, Id> {

    public abstract List<Response> list();

    public abstract Response create(Request request);

    public abstract Response update(Request request);

    public abstract void delete(Id id);

    public abstract List<Option> getOptions();

    @PostMapping
    private ResponseEntity<Response> createdRecord(@RequestBody Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(create(request));
    }

    @GetMapping
    private ResponseEntity<List<Response>> getRecords() {
        return ResponseEntity.ok(list());
    }

    @GetMapping("/enumerated")
    private ResponseEntity<List<Option>> dropdownResponse() {
        var defaultOption = Option.empty();
        var dropdownOptions = new LinkedList<Option>();
        dropdownOptions.add(defaultOption);
        dropdownOptions.addAll(getOptions());

        return ResponseEntity.ok(dropdownOptions);
    }

    @PutMapping("{id}")
    private ResponseEntity<Response> updatedRecord(@PathVariable Id id, @RequestBody Request request) {
        Response updated = update(request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("{id}")
    private ResponseEntity<?> deletedResponse(@PathVariable Id id) {
        delete(id);
        return ResponseEntity.ok().build();
    }
}

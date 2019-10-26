package edu.cad.study.web;

import edu.cad.controllers.Action;
import edu.cad.study.web.dto.Options;
import edu.cad.study.web.dto.Result;
import edu.cad.utils.gson.Option;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public abstract class ActionProcessor<Response, Request, Id> {

    public static final String OK = "OK";

    public abstract List<Response> list();

    public abstract Response create(Request request);

    public abstract Response update(Request request);

    public abstract void delete(Id id);

    public abstract List<Option> getOptions();

    public abstract List<Response> getDependent(HttpServletRequest request);

    @PostMapping(
            params = {"action"},
            consumes = {"*", "application/x-www-form-urlencoded;charset=UTF-8"}
    )
    public ResponseEntity<?> processAction(@RequestParam Action action,
                                           @RequestParam(required = false) Id id,
                                           @RequestBody(required = false) Request request) {
        switch (action) {
            case list:
                return allRecordsResponse();
            case create:
                return createdRecord(requireNonNull(request, "No create request provided"));
            case update:
                return updatedRecord(requireNonNull(request, "No update request provided"));
            case delete:
                return deletedResponse(requireNonNull(id, "No id for entity deletion"));
            case dropdown:
                return dropdownResponse();
            default:
                throw new UnsupportedOperationException("Unsupported action");
        }
    }

    @DeleteMapping("{id}")
    private ResponseEntity<?> deletedResponse(@PathVariable Id id) {
        delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    private ResponseEntity<?> updatedRecord(@RequestBody Request request) {
        Response updated = update(request);
        return ResponseEntity.ok(new Result<>(updated));
    }

    @PostMapping
    private ResponseEntity<?> createdRecord(@RequestBody Request request) {
        Response created = create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Result<>(created));
    }

    private ResponseEntity<List<Response>> allRecordsResponse() {
        return ResponseEntity.ok(list());
    }

    private ResponseEntity<Options> dropdownResponse() {
        var defaultOption = new Option("-", 0);
        var dropdownOptions = new LinkedList<Option>();
        dropdownOptions.add(defaultOption);
        dropdownOptions.addAll(getOptions());

        return ResponseEntity.ok(new Options(dropdownOptions, OK));
    }
}

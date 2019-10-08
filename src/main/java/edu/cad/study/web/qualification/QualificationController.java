package edu.cad.study.web.qualification;

import edu.cad.study.service.QualificationService;
import edu.cad.study.web.ActionProcessor;
import edu.cad.utils.gson.Option;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/v2/qualifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QualificationController extends ActionProcessor<QualificationDto, QualificationDto, Integer> {
    QualificationService service;
    QualificationMapper mapper;

    @Override
    public List<QualificationDto> list() {
        return service.getAll()
                .stream()
                .map(mapper::toResponse)
                .collect(toList());
    }

    @Override
    public QualificationDto create(QualificationDto qualificationDto) {
        return null;
    }

    @Override
    public QualificationDto update(QualificationDto qualificationDto) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public List<Option> getOptions() {
        return service.getAll()
                .stream()
                .map(mapper::toOption)
                .collect(toList());
    }

    @Override
    public QualificationDto getDependent(HttpServletRequest request) {
        return null;
    }
}

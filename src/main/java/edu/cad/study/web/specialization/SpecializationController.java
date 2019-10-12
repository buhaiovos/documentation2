package edu.cad.study.web.specialization;

import edu.cad.entities.Specialization;
import edu.cad.study.service.SpecializationService;
import edu.cad.study.web.ActionController;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v2/specializations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpecializationController extends ActionController<Specialization, Integer, SpecializationDto> {
    public SpecializationController(SpecializationService service, SpecializationMapper mapper) {
        super(service, mapper);
    }

    @Override
    public List<SpecializationDto> getDependent(HttpServletRequest request) {
        throw new UnsupportedOperationException("Unsupported operation");
    }
}

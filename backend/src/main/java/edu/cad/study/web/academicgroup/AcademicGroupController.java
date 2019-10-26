package edu.cad.study.web.academicgroup;

import edu.cad.entities.AcademicGroup;
import edu.cad.study.service.AcademicGroupService;
import edu.cad.study.web.ActionController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v2/academic-groups")
@Slf4j
public class AcademicGroupController extends ActionController<AcademicGroup, Integer, AcademicGroupDto> {
    public AcademicGroupController(AcademicGroupService service, AcademicGroupMapper mapper) {
        super(service, mapper);
    }

    @Override
    public List<AcademicGroupDto> getDependent(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported");
    }
}

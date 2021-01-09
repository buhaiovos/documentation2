package edu.cad.study.academicgroup;

import edu.cad.entities.AcademicGroup;
import edu.cad.study.ActionController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/academic-groups")
@Slf4j
class AcademicGroupController extends ActionController<AcademicGroup, Integer, AcademicGroupDto> {
    public AcademicGroupController(AcademicGroupService service, AcademicGroupMapper mapper) {
        super(service, mapper);
    }
}

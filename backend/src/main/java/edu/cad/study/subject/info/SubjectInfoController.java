package edu.cad.study.subject.info;

import edu.cad.entities.SubjectInfo;
import edu.cad.study.ActionController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v2/subject-infos")
public class SubjectInfoController extends ActionController<SubjectInfo, Integer, SubjectInfoDto> {
    public SubjectInfoController(SubjectInfoService service, SubjectInfoMapper mapper) {
        super(service, mapper);
    }
}

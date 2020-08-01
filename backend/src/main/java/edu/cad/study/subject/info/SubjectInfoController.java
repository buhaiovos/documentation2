package edu.cad.study.subject.info;

import edu.cad.entities.SubjectInfo;
import edu.cad.study.ActionController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v2/subject-infos")
public class SubjectInfoController extends ActionController<SubjectInfo, Integer, SubjectInfoDto> {
    private final SubjectInfoService service;
    private final SubjectInfoMapper mapper;

    public SubjectInfoController(SubjectInfoService service, SubjectInfoMapper mapper) {
        super(service, mapper);
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/enriched")
    public List<RichSubjectInfoDto> getAllEnriched() {
        List<SubjectInfo> all = service.getAll();
        return mapper.toEnrichedResponse(all);
    }
}

package edu.cad.study.load.other;

import edu.cad.domain.FormOfEducation;
import edu.cad.entities.OtherLoadInfo;
import edu.cad.utils.k3.SourceOfFinancing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class OtherLoadInfoService {
    private final OtherLoadInfoRepositoryWrapper otherLoadDao;

    public List<OtherLoadInfo> search(int semester,
                                      FormOfEducation formOfEducation,
                                      SourceOfFinancing sourceOfFinancing) {
        return otherLoadDao.findAll()
                .stream()
                .filter(load -> load.getSemester() == semester)
                .filter(load -> formOfEducation.getDbId() == load.getEducationForm().getId())
                .filter(load -> sourceOfFinancing.equals(load.getSourceOfFinancing()))
                .collect(toList());
    }
}

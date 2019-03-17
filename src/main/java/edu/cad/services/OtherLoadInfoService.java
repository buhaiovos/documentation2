package edu.cad.services;

import edu.cad.daos.HibernateDao;
import edu.cad.domain.FormOfEducation;
import edu.cad.entities.OtherLoadInfo;
import edu.cad.utils.k3.SourceOfFinancing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OtherLoadInfoService {
    private final HibernateDao<OtherLoadInfo> otherLoadDao;

    public List<OtherLoadInfo> search(int semester,
                                      FormOfEducation formOfEducation,
                                      SourceOfFinancing sourceOfFinancing) {
        return otherLoadDao.getAll()
                .stream()
                .filter(load -> load.getSemester() == semester)
                .filter(load -> formOfEducation.getDbId() == load.getEducationForm().getId())
                .filter(load -> sourceOfFinancing.equals(load.getSourceOfFinancing()))
                .collect(Collectors.toList());
    }
}

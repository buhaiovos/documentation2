package edu.cad.services;

import edu.cad.daos.HibernateDao;
import edu.cad.entities.Staff;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffServiceImpl implements StaffService {
    private final HibernateDao<Staff> staffDao;

    @Override
    public Staff getById(int id) {
        log.info("Getting staff by id: {}", id);
        return staffDao.getById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("No staff with id '%d'", id)));
    }
}

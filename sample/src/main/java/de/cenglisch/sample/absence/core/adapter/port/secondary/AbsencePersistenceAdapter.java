package de.cenglisch.sample.absence.core.adapter.port.secondary;

import de.cenglisch.sample.absence.core.domain.Absence;
import de.cenglisch.sample.absence.core.domain.AbsenceRepository;
import de.cenglisch.sample.employees.api.domain.EmployeeId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AbsencePersistenceAdapter implements AbsenceRepository {

    private final AbsenceJpaRepository absenceJpaRepository;
    private final AbsenceMapper absenceMapper;
    
    @Override
    public Collection<Absence> findByEmployeeId(EmployeeId employeeId) {
        return absenceJpaRepository.findByEmployeeId(employeeId.id())
                .stream()
                .map(absenceMapper::to)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Absence> findAll() {
        return absenceJpaRepository.findAll()
                .stream()
                .map(absenceMapper::to)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByEmployeeId(EmployeeId employeeId) {
        absenceJpaRepository.deleteByEmployeeId(employeeId.id());
    }

    @Override
    public void save(Absence absence) {
        absenceJpaRepository.save(absenceMapper.to(absence));
    }
}

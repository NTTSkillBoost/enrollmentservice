package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.repository;

import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.gateway.CourseClient;
import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.gateway.EmployeeClient;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Enrollment;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.EnrollmentRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EnrollmentRepositoryAdapter implements EnrollmentRepositoryPort {

    private final EnrollmentJpaRepository enrollmentRepository;
    private final EmployeeClient employeeClient;
    private final CourseClient courseClient;

    @Override
    public Enrollment save(Enrollment enrollment) {
        if (enrollment.getCourseId() != null) {
            var employee = employeeClient.existsById(enrollment.getStudentId());
            if (!employee) {
                throw new EntityNotFoundException("Funcionário com ID " + enrollment.getId() + " não encontrado");
            }

            var course = courseClient.existsById(enrollment.getCourseId());
            if (!course) {
                throw new EntityNotFoundException("Curso com ID " + enrollment.getCourseId() + " não encontrado");
            }

            return enrollmentRepository.save(enrollment);
        }

        return enrollment;
    }

    @Override
    public Enrollment findById(UUID id) {
        Optional<Enrollment> byId = enrollmentRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new EntityNotFoundException("Funcionário com ID " + id + " não encontrado");
        }
    }

    @Override
    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    @Override
    public Enrollment findByCourseId(UUID courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }
}

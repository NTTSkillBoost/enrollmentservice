package br.com.nttdata.nttskillboost.activitymanagementservice.ports.out;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Enrollment;

import java.util.List;
import java.util.UUID;

public interface EnrollmentRepositoryPort {
    Enrollment save(Enrollment enrollment);
    Enrollment findById(UUID id);
    List<Enrollment> findAll();
    Enrollment findByCourseId(UUID courseId);
    boolean isStudentEnrolled(UUID studentId, UUID courseId);
}

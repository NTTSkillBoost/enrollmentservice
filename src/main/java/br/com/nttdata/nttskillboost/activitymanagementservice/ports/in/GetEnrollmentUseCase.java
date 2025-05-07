package br.com.nttdata.nttskillboost.activitymanagementservice.ports.in;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Enrollment;

import java.util.List;
import java.util.UUID;

public interface GetEnrollmentUseCase {
    Enrollment findById(UUID id);
    List<Enrollment> findAll();
    boolean isStudentEnrolled(UUID studentId, UUID courseId);
}

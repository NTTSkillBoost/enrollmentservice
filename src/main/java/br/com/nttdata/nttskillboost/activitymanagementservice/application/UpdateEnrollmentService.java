package br.com.nttdata.nttskillboost.activitymanagementservice.application;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Enrollment;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.in.UpdateEnrollmentUseCase;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.EnrollmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateEnrollmentService implements UpdateEnrollmentUseCase {

    private final EnrollmentRepositoryPort courseRepositoryPort;

    @Override
    public Enrollment update(UUID id, Enrollment enrollment) {
        Enrollment byId = courseRepositoryPort.findById(id);
        if (byId == null) {
            return null;
        }

        Enrollment enrollmentUpdate = new Enrollment();
        enrollmentUpdate.setId(byId.getId());
        enrollmentUpdate.setCourseId(enrollment.getCourseId());
        enrollmentUpdate.setStudentId(enrollment.getStudentId());
        enrollmentUpdate.setEnrollmentStatus(enrollment.getEnrollmentStatus());
        enrollmentUpdate.setEnrollmentDate(enrollment.getEnrollmentDate());
        enrollmentUpdate.setStatus(enrollment.getStatus());

        return courseRepositoryPort.save(enrollmentUpdate);
    }
}

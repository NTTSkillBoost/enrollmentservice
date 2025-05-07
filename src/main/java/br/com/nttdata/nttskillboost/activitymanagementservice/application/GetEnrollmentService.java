package br.com.nttdata.nttskillboost.activitymanagementservice.application;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Enrollment;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.in.GetEnrollmentUseCase;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.EnrollmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetEnrollmentService implements GetEnrollmentUseCase {

    private final EnrollmentRepositoryPort enrollmentRepositoryPort;

    @Override
    public Enrollment findById(UUID id) {
        return enrollmentRepositoryPort.findById(id);
    }

    @Override
    public List<Enrollment> findAll() {
        return enrollmentRepositoryPort.findAll();
    }

    @Override
    public boolean isStudentEnrolled(UUID studentId, UUID courseId) {
        return enrollmentRepositoryPort.isStudentEnrolled(studentId, courseId);
    }
}

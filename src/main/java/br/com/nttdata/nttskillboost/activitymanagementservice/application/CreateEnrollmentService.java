package br.com.nttdata.nttskillboost.activitymanagementservice.application;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Enrollment;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.in.CreateEnrollmentUseCase;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.EnrollmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateEnrollmentService implements CreateEnrollmentUseCase {

    private final EnrollmentRepositoryPort enrollmentRepositoryPort;

    @Override
    public Enrollment create(Enrollment enrollment) {
        return enrollmentRepositoryPort.save(enrollment);
    }
}

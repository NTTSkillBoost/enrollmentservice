package br.com.nttdata.nttskillboost.activitymanagementservice.application;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Enrollment;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Status;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.in.DeleteEnrollmentUseCase;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.EnrollmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteEnrollmentService implements DeleteEnrollmentUseCase {

    private final EnrollmentRepositoryPort enrollmentRepositoryPort;

    @Override
    public boolean delete(UUID id) {
        Enrollment byId = enrollmentRepositoryPort.findById(id);
        if (byId != null) {
            byId.setStatus(Status.DELETED);
            enrollmentRepositoryPort.save(byId);
            return true;
        } else {
            return false;
        }
    }
}

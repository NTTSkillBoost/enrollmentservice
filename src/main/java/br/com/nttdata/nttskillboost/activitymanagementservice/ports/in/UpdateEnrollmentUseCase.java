package br.com.nttdata.nttskillboost.activitymanagementservice.ports.in;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Enrollment;

import java.util.UUID;

public interface UpdateEnrollmentUseCase {
    Enrollment update(UUID id, Enrollment enrollment);
}

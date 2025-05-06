package br.com.nttdata.nttskillboost.activitymanagementservice.ports.in;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Enrollment;

public interface CreateEnrollmentUseCase {
    Enrollment create(Enrollment enrollment);
}

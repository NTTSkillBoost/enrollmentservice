package br.com.nttdata.nttskillboost.activitymanagementservice.ports.in;

import java.util.UUID;

public interface DeleteEnrollmentUseCase {
    boolean delete(UUID id);
}

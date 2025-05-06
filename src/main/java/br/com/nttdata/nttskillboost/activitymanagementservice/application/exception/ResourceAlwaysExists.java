package br.com.nttdata.nttskillboost.activitymanagementservice.application.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND, reason = "Business not found.")
public class ResourceAlwaysExists extends RuntimeException {
    public ResourceAlwaysExists(String message) {
        super("Business with message " + message + " not found.");
    }
}

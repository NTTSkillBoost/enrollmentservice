package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.exception;


import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND, reason = "Business not found.")
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super("Business with email " + message + " not found.");
    }
}

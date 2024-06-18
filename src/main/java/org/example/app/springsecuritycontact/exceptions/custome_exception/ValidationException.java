package org.example.app.springsecuritycontact.exceptions.custome_exception;

import lombok.Getter;
import org.example.app.springsecuritycontact.validate.interfaces.IFormDataProcessing;


@Getter
public class ValidationException extends RuntimeException{
    private final IFormDataProcessing<?> formDataForValidate;
    private final String fragmentName;

    public ValidationException(String message, IFormDataProcessing<?> formDataForValidate, String fragmentName) {
        super(message);
        this.formDataForValidate = formDataForValidate;
        this.fragmentName = fragmentName;
    }
}

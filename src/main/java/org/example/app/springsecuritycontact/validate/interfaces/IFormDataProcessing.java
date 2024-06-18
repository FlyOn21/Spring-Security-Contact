package org.example.app.springsecuritycontact.validate.interfaces;


import java.util.HashMap;

public interface IFormDataProcessing<T> {
    HashMap<String, String> getValidationFormErrors();
    boolean getIsValidForm();
    T getData();
}

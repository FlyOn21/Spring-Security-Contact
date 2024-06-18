package org.example.app.springsecuritycontact.apps_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDataDTO<T> {
    private boolean isSuccess;
    private boolean isValid;
    private List<T> data;
    private Object formData;
    private String msg;
    private List<String> commonErrors;
    private HashMap<String, String> validationFormErrors;

}

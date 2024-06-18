package org.example.app.springsecuritycontact.validate;

import org.example.app.springsecuritycontact.validate.enums.IValidateUnit;
import org.example.app.springsecuritycontact.validate.validate_entity.ValidateResultDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator<T extends IValidateUnit> {
    public ValidateResultDTO validate(String value, T field) {
        ValidateResultDTO answer = new ValidateResultDTO();
        Pattern pattern = Pattern.compile(field.getValidationRegex());
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            answer.addError(field.getErrorMsg());
            return answer;
        }
        answer.setValid(answer.getErrorsList().isEmpty());
        return answer;
    }
}

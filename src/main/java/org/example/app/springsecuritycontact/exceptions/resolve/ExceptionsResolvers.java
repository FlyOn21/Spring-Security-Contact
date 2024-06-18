package org.example.app.springsecuritycontact.exceptions.resolve;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.app.springsecuritycontact.exceptions.custome_exception.CRUDException;
import org.example.app.springsecuritycontact.exceptions.custome_exception.ValidationException;
import org.example.app.springsecuritycontact.validate.interfaces.IFormDataProcessing;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionsResolvers {

    private static final Logger SERVICE_LOGGER =
            LogManager.getLogger(ExceptionsResolvers.class);
    private static final Logger CONSOLE_LOGGER =
            LogManager.getLogger("console_logger");

    @ExceptionHandler(ValidationException.class)
    public ModelAndView validateExceptionResolver(ValidationException ex) {
        SERVICE_LOGGER.error("ERROR Validated: {}", ex.getMessage());
        CONSOLE_LOGGER.error("ERROR Validated: {}", ex.getMessage());
        IFormDataProcessing<?> formDataForValidate = ex.getFormDataForValidate();
        String fragmentName = ex.getFragmentName();
        ModelAndView modelAndView = new ModelAndView("index");
        var formData = formDataForValidate.getData();
        modelAndView.addObject("fragmentName", fragmentName);
        modelAndView.addObject("errorMsg", ex.getMessage());
        modelAndView.addObject("formData", formData);
        modelAndView.addObject("validationFormErrors", formDataForValidate.getValidationFormErrors());
        if (fragmentName.equals("contact_update")) {
            modelAndView.addObject("contact", formData);
        }
        return modelAndView;
    }

    @ExceptionHandler(CRUDException.class)
    public ModelAndView crudExceptionResolver(CRUDException ex) {
        SERVICE_LOGGER.error("ERROR CRUD: {}", ex.getMessage());
        CONSOLE_LOGGER.error("ERROR CRUD: {}", ex.getMessage());
        String fragmentName = ex.getFragmentName();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("errorMsg", String.format("ERROR: %s", ex.getMessage()));
        modelAndView.addObject("fragmentName", fragmentName);
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView mainExceptionResolver(Exception ex) {
        SERVICE_LOGGER.error("ERROR: {}", ex.getMessage());
        CONSOLE_LOGGER.error("ERROR: {}", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("errorMsg", String.format("ERROR: %s", ex.getMessage()));
        modelAndView.addObject("fragmentName", "home");
        return modelAndView;
    }
}

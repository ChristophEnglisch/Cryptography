package de.cenglisch.sample.employees.core.adapter.port;

import de.cenglisch.sample.common.domain.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    ProblemDetail handleAbsenceEndingIsFinalizedException(ValidationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation failed");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }
}

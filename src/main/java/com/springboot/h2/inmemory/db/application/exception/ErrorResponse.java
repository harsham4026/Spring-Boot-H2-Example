package com.springboot.h2.inmemory.db.application.exception;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Class for handling the error responses.
 *
 * @author Harsha Mandadi
 */
@XmlRootElement(name = "error")
public class ErrorResponse {
    public ErrorResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

    //General error message about nature of error
    private String message;

    //Specific errors in API request processing
    private List<String> details;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "message='" + message + '\'' +
                ", details=" + details +
                '}';
    }
}
package com.ecommerce.project.exceptions;

import java.util.Date;
import java.util.Map;

public record ExceptionResponse (Date timestamp,
                                 int status,
                                 String error,
                                 String message,
                                 String path,
                                 Map<String, String> validationErrors) {

}

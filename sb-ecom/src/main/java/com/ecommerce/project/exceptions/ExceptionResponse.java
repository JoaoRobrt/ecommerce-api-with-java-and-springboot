package com.ecommerce.project.exceptions;

import java.time.Instant;
import java.util.Map;

public record ExceptionResponse (Instant timestamp,
                                 int status,
                                 String error,
                                 String message,
                                 String path,
                                 Map<String, String> errors) {

}

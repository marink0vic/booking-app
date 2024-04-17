package com.booking.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class ErrorDetails {

  private HttpStatus status;

  private String message;

}

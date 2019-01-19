package com.omnicuris.ecommerce.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ServiceResponseException extends Exception {

  private HttpStatus status;
  private String message;

  public static ServiceResponseException status(HttpStatus status) {
    ServiceResponseException exception = new ServiceResponseException();
    exception.setStatus(status);
    return exception;
  }

  public static ServiceResponseException error(String message) {
    ServiceResponseException exception = new ServiceResponseException();
    exception.setMessage(message);
    return exception;
  }

  public ServiceResponseException message(String message) {
    this.setMessage(message);
    return this;
  }


}

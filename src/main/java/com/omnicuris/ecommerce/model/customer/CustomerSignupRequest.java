package com.omnicuris.ecommerce.model.customer;

import io.micrometer.core.lang.Nullable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerSignupRequest {

  @NotNull
  @NotBlank
  private String fName;
  @Nullable
  private String lName;
  @NotNull
  @NotBlank
  private String emailId;
  @NotNull
  @NotBlank
  private String contactNo;
  private List<String> address;

}

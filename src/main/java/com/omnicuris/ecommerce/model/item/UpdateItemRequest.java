package com.omnicuris.ecommerce.model.item;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateItemRequest {

  @NotNull
  @NotBlank
  private String name;
  @NotNull
  @NotBlank
  private String description;
  private Double price;
}

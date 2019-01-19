package com.omnicuris.ecommerce.model.inventory;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateInventoryItemRequest {

  @NotNull
  private Long itemId;
  @NotNull
  private Integer qty;
}

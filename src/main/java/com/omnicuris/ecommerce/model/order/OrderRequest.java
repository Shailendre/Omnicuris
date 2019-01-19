package com.omnicuris.ecommerce.model.order;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderRequest {

  @NotNull
  private Long itemId;
  @NotNull
  private Integer qty;
  @NotNull
  private Long customerId;
  @NotNull
  private Long addressId;
  @NotNull
  private OrderStatus status;
}

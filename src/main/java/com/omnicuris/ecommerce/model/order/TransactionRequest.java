package com.omnicuris.ecommerce.model.order;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionRequest {

  @NotNull
  private List<Long> orderIds;
  @NotNull
  private TransactionStatus status;

}

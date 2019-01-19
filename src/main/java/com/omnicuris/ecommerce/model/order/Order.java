package com.omnicuris.ecommerce.model.order;

import com.omnicuris.ecommerce.model.customer.Address;
import com.omnicuris.ecommerce.model.customer.Customer;
import com.omnicuris.ecommerce.model.item.Item;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "t_order")
@Setter
@Getter
public class Order {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private Item itemId;

  @OneToOne(targetEntity = Address.class)
  private Address addrId;

  @ManyToOne(targetEntity = Customer.class)
  private Customer custId;

  @Enumerated(value = EnumType.STRING)
  private OrderStatus status;

  @ManyToOne(targetEntity = Transaction.class)
  private Transaction transId;

  private Integer itemQty;
  private Double orderTotal;


  @Column(nullable = false, updatable = false)
  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date orderedAt;
}

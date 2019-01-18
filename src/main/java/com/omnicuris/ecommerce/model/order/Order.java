package com.omnicuris.ecommerce.model.order;

import com.omnicuris.ecommerce.model.customer.Address;
import com.omnicuris.ecommerce.model.customer.Customer;
import com.omnicuris.ecommerce.model.item.Item;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
  @JoinColumn(name = "item_id")
  private Item item_id;

  @OneToOne(targetEntity = Address.class)
  @JoinColumn(name = "address_id")
  private Address address_id;

  @ManyToOne(targetEntity = Customer.class)
  @JoinColumn(name = "customer_id")
  private Customer customer_id;

  @ManyToOne(targetEntity = Transaction.class)
  @JoinColumn(name = "transaction_id")
  private Transaction transaction_id;

  @Column(nullable = false, updatable = false)
  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date orderedAt;
}

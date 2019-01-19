package com.omnicuris.ecommerce.model.order;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Setter
@Getter
@Entity
@Table(name = "t_transaction")
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

  @Id
  @GeneratedValue
  private Long id;

  @OneToMany(targetEntity = Order.class)
  private List<Order> orders;

  private String mode = "UPI";

  private TransactionStatus status;

  @Column(nullable = false, updatable = false)
  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;
}

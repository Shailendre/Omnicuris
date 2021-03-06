package com.omnicuris.ecommerce.model.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omnicuris.ecommerce.model.inventory.Inventory;
import com.omnicuris.ecommerce.model.order.Order;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@Entity
@Table(name = "t_item")
@EntityListeners(AuditingEntityListener.class)
public class Item {

  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true)
  @NotBlank
  private String name;

  private String description;

  private Double price;

  @JsonIgnore
  @OneToOne(targetEntity = Inventory.class, fetch = FetchType.LAZY)
  private Inventory inventory;

  @JsonIgnore
  @OneToMany(targetEntity = Order.class, fetch = FetchType.LAZY)
  private List<Order> orders;

  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  private Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @LastModifiedDate
  private Date updatedAt;
}

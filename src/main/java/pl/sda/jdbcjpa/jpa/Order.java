package pl.sda.jdbcjpa.jpa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.sda.jdbcjpa.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="Orders")
@ToString(exclude = "customer")
public class Order extends BaseEntity {


    private BigDecimal totalCost;
    private String customerName;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "orderHeader", cascade = CascadeType.PERSIST)
    private List<OrderLine> orderLine;

}

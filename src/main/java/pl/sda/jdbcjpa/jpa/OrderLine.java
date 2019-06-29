package pl.sda.jdbcjpa.jpa;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.sda.jdbcjpa.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@ToString(exclude = "orderHeader")
public class OrderLine extends BaseEntity {



    @ManyToOne
    private Order orderHeader;  //nie uzywamy nazwy order -> to jest słowo klucz sql

    private String productName;

    private BigDecimal price;


}

package pl.sda.jdbc.jpa;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class OrderLine extends BaseEntity{



    @ManyToOne
    private Order orderHeader;  //nie uzywamy nazwy order -> to jest słowo klucz sql

    private String productName;

    private BigDecimal price;


}

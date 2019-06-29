package pl.sda.jdbcjpa.jpa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.sda.jdbcjpa.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString(exclude = "customer")
public class Cart extends BaseEntity {



    @OneToOne
    private Customer customer;

}

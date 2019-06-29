package pl.sda.jdbc.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Cart extends BaseEntity {



    @OneToOne
    private Customer customer;

}

package pl.sda.jdbcjpa.jpaAll.jpa;

import lombok.Getter;
import lombok.Setter;
import pl.sda.jdbcjpa.jpaAll.BaseEntity;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("P")
@DiscriminatorColumn(name = "type",discriminatorType = DiscriminatorType.STRING)
public abstract class Product extends BaseEntity {

    private String productName;


}

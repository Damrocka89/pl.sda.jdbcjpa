package pl.sda.jdbcjpa.jpaAll;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@ToString
@MappedSuperclass //encje dziedziczące dostaną te pola
public abstract class BaseEntity {

    @Id //must have
    @GeneratedValue(strategy = GenerationType.AUTO) //must have
    private Integer id;
}

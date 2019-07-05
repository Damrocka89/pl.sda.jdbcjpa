package pl.sda.jdbcjpa.jpaAll.jpa2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sda.jdbcjpa.jpaAll.BaseEntity;

import javax.persistence.Entity;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends BaseEntity {

    private String tagName;
}

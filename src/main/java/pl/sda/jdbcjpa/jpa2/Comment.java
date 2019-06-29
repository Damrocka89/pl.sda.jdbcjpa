package pl.sda.jdbcjpa.jpa2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.sda.jdbcjpa.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@ToString(exclude = "post")
@Entity
public class Comment extends BaseEntity {

    @Column(length = 20)
    private String commentBody;

    private String nickname;

    @ManyToOne
    private Post post;

}

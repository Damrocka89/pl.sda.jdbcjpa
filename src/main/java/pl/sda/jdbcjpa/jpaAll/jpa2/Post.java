package pl.sda.jdbcjpa.jpaAll.jpa2;

import lombok.*;
import pl.sda.jdbcjpa.jpaAll.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@Builder
@Entity
public class Post extends BaseEntity {

    private String title;
    private String body;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> comments;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Tag> tags;


    public void addComment(Comment comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
        comment.setPost(this);
    }
}

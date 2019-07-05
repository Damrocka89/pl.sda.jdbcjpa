package pl.sda.jdbcjpa.jpaAll.jpa2;

import com.google.common.collect.Lists;
import pl.sda.jdbcjpa.jpaAll.JpaDao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class JpaMain2 {

    public static void main(String[] args) {

        addPost("title 1", "body 1");
        addPost("title 2", "body 2");
        Integer post3Id = addPost("title 3", "body 3");
        Integer post4id = addPost("title 4", "body 4");

        findPostById(post3Id);

        updatePost(post3Id,"brand new body");

        deletePostById(post4id);


        JpaDao.getEntityManager().close();
        JpaDao.getEntityManagerFactory().close();
    }

    private static void deletePostById(Integer id) {
        Post postById = findPostById(id);
        EntityManager entityManager = JpaDao.getEntityManager();
        entityManager.getTransaction().begin();


        entityManager.remove(postById);
        entityManager.getTransaction().commit();
    }

    private static void updatePost(Integer id, String newBody) {
        Post post = findPostById(id);
        post.setBody(newBody);

        JpaDao.saveEntity(post);

//        EntityManager entityManager = JpaDao.getEntityManager();

//        entityManager.getTransaction().begin();
//        entityManager.merge(post);
//        entityManager.getTransaction().commit();

    }

    private static Post findPostById(Integer id) {
        EntityManager em = JpaDao.getEntityManager();
        Post post = em.find(Post.class, id);
        System.out.println(post);
        return post;
    }

    private static Integer addPost(String title, String body) {

        Comment comment1=new Comment();
        comment1.setCommentBody("just a comment1");
        comment1.setNickname("nick");

        Tag tag = findOrCreateTag("#tag");

        Tag tag2=findOrCreateTag("#tag2");

        Post post = Post.builder()
                .title(title)
                .body(body)
                .tags(Lists.newArrayList(tag,tag2))
                .build();

//        post.getComments().add(comment1);  // ---> null pointer lista jest nullem
//        comment1.setPost(post);

        post.addComment(comment1);

        System.out.println("Przed zapisem: " + post);
        JpaDao.saveEntity(post);
        System.out.println("Po zapisie: " + post);
        return post.getId();
    }

    private static Tag findOrCreateTag(String tagName) {
        EntityManager entityManager = JpaDao.getEntityManager();
        TypedQuery<Tag> query = entityManager.createQuery("select t from Tag t where tagName = :tn", Tag.class);
        query.setParameter("tn",tagName);
        Tag singleResult = null;
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return JpaDao.saveEntity(new Tag(tagName));
        }

    }


}

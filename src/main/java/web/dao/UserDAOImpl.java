package web.dao;


import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUserById (long id) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.id = :id", User.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);

    }

    @Override
    public void delete(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public void updateUser (long id, User updatedUser) {
        User user = entityManager.find(User.class, id);
        user.setName(updatedUser.getName());
        user.setSurname(updatedUser.getSurname());
        user.setAge(updatedUser.getAge());
        entityManager.persist(user);
    }
}

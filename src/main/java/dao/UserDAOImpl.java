package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import model.User;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    //@PersistenceContext говорит о том, что наш UserDAOImpl зависит от EntityManager, который управляет Entity-сущностями.
    //Эта аннотация внедряет прокси, который выполняет открытие и закрытие EntityManager автоматически
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList(); //У EntityManager свой язык запросов - JPQL
    }

    @Override
    public User getUserById (long id) {
        TypedQuery <User> query = entityManager.createQuery("select u from User u where u.id = :id", User.class);
        query.setParameter("id",id);
        return query.getSingleResult();
//      return query.getResultList().stream().findAny().orElse(null); //Можно так написать,чтобы обрабатывалась ситуация,
//      когда нет id
//      Метод findAny() возвращает первый попавшийся элемент из Stream-a, в виде обертки Optional.
    }

    //Метод persist() - вводит новый экземпляр сущности в персистентный контекст, т.е. в контекст EntityManager'а
    //Это сохранение
    @Override
    public void addUser(User user) {
        entityManager.persist(user);

    }

    @Override
    public void removeUserById(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public void updateUser (long id, String name, String lastName, byte age) {
        User user = entityManager.find(User.class, id);
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        entityManager.persist(user);

    }

}

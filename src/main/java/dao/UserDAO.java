package dao;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    List <User> getAllUsers(); // запрос select
    User getUserById (long id);

    void addUser(User user) throws SQLException; //запрос update

    void removeUserById(long id); //запрос delete
    void updateUser (long id);
}

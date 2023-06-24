package service;

import model.User;

import java.util.List;

public interface UserService {

    List <User> getAllUsers(); // запрос select
    User getUserById (long id);
    void addUser(User user);//запрос update
    void removeUserById(long id); //запрос delete
    void updateUser (long id, User updateUser);;
}

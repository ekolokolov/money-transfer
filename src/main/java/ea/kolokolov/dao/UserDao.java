package ea.kolokolov.dao;

import ea.kolokolov.model.User;

import java.util.List;

public interface UserDao {

    User getUser(String id);

    List<User> getAllUsers();

}

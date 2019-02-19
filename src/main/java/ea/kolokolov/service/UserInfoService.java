package ea.kolokolov.service;

import ea.kolokolov.model.User;

import java.util.List;

public interface UserInfoService {

    List<User> getUsers();

    User getUser(String userId);
}

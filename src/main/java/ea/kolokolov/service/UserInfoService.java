package ea.kolokolov.service;

import ea.kolokolov.model.User;

import java.util.List;

/**
 * Service for operations with Users
 */
public interface UserInfoService {

    /**
     * Get full users list
     * Should be not allowed for public api, but open for easy presentation.
     *
     * @return List with all users
     */
    List<User> getUsers();

    /**
     * Get user with login
     *
     * @param login unique identifier
     * @return User or null
     */
    User getUser(String login);

    /**
     * Create new User
     *
     * @param user object for new user
     * @return new created User
     */
    User createUser(User user);

    /**
     * delete user with
     *
     * @param login unique identifier
     * @return deleted User
     */
    User deleteUser(String login);

    /**
     * Change User
     *
     * @param user all new info about user
     * @return updated User
     */
    User changeUser(User user);
}

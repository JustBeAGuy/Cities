package com.andriiposia.cities.dao;

import com.andriiposia.cities.entity.User;

/**
 * Created by Администратор on 9/3/15.
 */
public interface UserDAO {

    User findByEmail(String email);
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean removeUser(String userEmail);

}

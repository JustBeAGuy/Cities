package com.andriiposia.cities.service;

import com.andriiposia.cities.entity.User;

/**
 * Created by Администратор on 9/4/15.
 */
public interface UserService {

    User findByEmail(String email);
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean removeUser(String userEmail);


}

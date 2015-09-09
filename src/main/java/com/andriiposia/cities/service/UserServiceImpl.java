package com.andriiposia.cities.service;

import com.andriiposia.cities.dao.UserDAO;
import com.andriiposia.cities.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Администратор on 9/8/15.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Transactional
    public boolean addUser(User user) {
        return userDAO.addUser(user);
    }

    @Transactional
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    @Transactional
    public boolean removeUser(String userEmail) {
        return userDAO.removeUser(userEmail);
    }

}

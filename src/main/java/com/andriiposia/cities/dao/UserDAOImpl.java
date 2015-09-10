package com.andriiposia.cities.dao;

import com.andriiposia.cities.entity.Country;
import com.andriiposia.cities.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 9/4/15.
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    public User findByEmail(String email) {

        List<User> users;

        users = sessionFactory.getCurrentSession()
                .createQuery("FROM User WHERE email=?")
                .setParameter(0, email.toLowerCase())
                .list();
        Logger.getLogger("USERDAO").info("size: " + users.size());
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }

    }

    @Override
    public boolean addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        boolean result = false;
            sessionFactory.getCurrentSession().update(user);
        result = true;
        return result;
    }

    @Override
    public boolean removeUser(String userEmail) {
        User user = findByEmail(userEmail);
        if (null != user) {
            sessionFactory.getCurrentSession().delete(user);
        }
        return true;
    }
}

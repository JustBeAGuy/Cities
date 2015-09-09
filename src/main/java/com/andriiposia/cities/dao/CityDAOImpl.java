package com.andriiposia.cities.dao;

import com.andriiposia.cities.entity.City;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Администратор on 9/4/15.
 */
@Repository
public class CityDAOImpl implements CityDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Integer addCity(City city) {
        sessionFactory.getCurrentSession().save(city);
        return city.getCityId();
    }

    @Override
    public City getCityById(Integer cityId) {
        return (City) sessionFactory.getCurrentSession().get(City.class, cityId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public City getCityByName(String cityName) {
        List<City> city = sessionFactory.getCurrentSession()
                .createQuery("FROM City WHERE city=?")
                .setParameter(0, cityName)
                .list();

        if (city.size() > 0) {
            return city.get(0);
        } else {
            return null;
        }

    }

    @Override
    public void updateCity(City city) {
        sessionFactory.getCurrentSession().update(city);
    }

    @Override
    public void removeCity(Integer cityId) {
        City city = getCityById(cityId);

        if (null != city) {
            sessionFactory.getCurrentSession().delete(city);
        }
    }

}

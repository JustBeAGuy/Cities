package com.andriiposia.cities.dao;

import com.andriiposia.cities.entity.Country;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Администратор on 9/4/15.
 */
@Repository
public class CountryDAOImpl implements CountryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Integer addCountry(Country country) {
        sessionFactory.getCurrentSession().save(country);
        return country.getCountryId();
    }

    @Override
    public Country getCountryById(Integer countryId) {
        return (Country) sessionFactory.getCurrentSession().get(Country.class, countryId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Country getCountryByName(String countryName) {
        List<Country> countries = sessionFactory.getCurrentSession()
                .createQuery("FROM Country WHERE country=?")
                .setParameter(0, countryName)
                .list();

        if (countries.size() > 0) {
            return countries.get(0);
        } else {
            return null;
        }

    }

    @Override
    public void updateCountry(Country country) {
        sessionFactory.getCurrentSession().update(country);
    }

    @Override
    public void removeCountry(Integer countryId) {
        Country country = getCountryById(countryId);
        if (null != country) {
            sessionFactory.getCurrentSession().delete(country);
        }
    }

    @Override
    public List<Country> getAllCountries() {
        List<Country> countries = sessionFactory.getCurrentSession()
                .createQuery("FROM Country").list();

        if (countries.size() > 0) {
            return countries;
        } else {
            return null;
        }
    }
}

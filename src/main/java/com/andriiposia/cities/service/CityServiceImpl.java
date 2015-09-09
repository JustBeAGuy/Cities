package com.andriiposia.cities.service;

import com.andriiposia.cities.dao.CityDAO;
import com.andriiposia.cities.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Администратор on 9/4/15.
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDAO cityDAO;

    @Transactional
    public Integer addCity(City city) {
        return cityDAO.addCity(city);
    }

    @Transactional
    public City getCityById(Integer cityId) {
        return cityDAO.getCityById(cityId);
    }

    @Transactional
    public City getCityByName(String cityName) {
        return cityDAO.getCityByName(cityName);
    }

    @Transactional
    public void updateCity(City city) {
        cityDAO.updateCity(city);
    }

    @Transactional
    public void removeCity(Integer cityId) {
        cityDAO.removeCity(cityId);
    }
}

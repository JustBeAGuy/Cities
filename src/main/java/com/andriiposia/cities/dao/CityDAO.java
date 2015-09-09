package com.andriiposia.cities.dao;

import com.andriiposia.cities.entity.City;

/**
 * Created by Администратор on 9/3/15.
 */
public interface CityDAO {

    Integer addCity(City city);
    City getCityById(Integer cityId);
    City getCityByName(String cityName);
    void updateCity(City city);
    void removeCity(Integer cityId);

}

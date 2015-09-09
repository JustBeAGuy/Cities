package com.andriiposia.cities.service;

import com.andriiposia.cities.entity.City;

/**
 * Created by Администратор on 9/4/15.
 */
public interface CityService {

    Integer addCity(City city);
    City getCityById(Integer cityId);
    City getCityByName(String cityName);
    void updateCity(City city);
    void removeCity(Integer cityId);


}

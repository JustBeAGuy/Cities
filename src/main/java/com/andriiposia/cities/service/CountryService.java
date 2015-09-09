package com.andriiposia.cities.service;

import com.andriiposia.cities.entity.City;
import com.andriiposia.cities.entity.Country;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Администратор on 9/4/15.
 */
public interface CountryService {

    Integer addCountry(Country country);
    Country getCountryById(Integer countryId);
    Country getCountryByName(String countryName);
    List<String> getCitiesByCountryName(String countryName);
    Map<String, City> getCitiesByCountryId(Integer countryId);
    boolean addCityToCountry(String countryName, City city);
    boolean updateCityToCountry(String countryName, String cityName, City newCity);
    City findCityInCountry(String countryName, String cityName);
    void updateCountry(Country country);
    boolean removeCountry(Integer countryId);
    List<String> getAllCountriesNames();
    boolean removeCityFromCountry(String countryName, String cityName);
}

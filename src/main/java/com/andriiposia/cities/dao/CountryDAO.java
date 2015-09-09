package com.andriiposia.cities.dao;

import com.andriiposia.cities.entity.Country;

import java.util.List;

/**
 * Created by Администратор on 9/3/15.
 */
public interface CountryDAO {

    Integer addCountry(Country country);
    Country getCountryById(Integer countryId);
    Country getCountryByName(String countryName);
    void updateCountry(Country country);
    void removeCountry(Integer countryId);
    List<Country> getAllCountries();
}

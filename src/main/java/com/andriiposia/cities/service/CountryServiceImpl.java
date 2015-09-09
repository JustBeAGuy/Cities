package com.andriiposia.cities.service;

import com.andriiposia.cities.dao.CountryDAO;
import com.andriiposia.cities.entity.City;
import com.andriiposia.cities.entity.Country;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Администратор on 9/4/15.
 */
@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryDAO countryDAO;
    @Autowired
    private CityService cityService;

    @Transactional
    public Integer addCountry(Country country) {
        return countryDAO.addCountry(country);
    }

    @Transactional
    public Country getCountryById(Integer countryId) {
        return countryDAO.getCountryById(countryId);
    }

    @Transactional
    public Country getCountryByName(String countryName) {
        return countryDAO.getCountryByName(countryName);
    }

    @Transactional
    public Map<String, City> getCitiesByCountryId(Integer countryId) {
        Map<String, City> cities = new HashMap<String, City>();

        for(City cityTemp : countryDAO.getCountryById(countryId).getCities()) {
            cities.put(cityTemp.getCity(), cityTemp);
        }

        return cities;
    }

    @Transactional
    public boolean addCityToCountry(String countryName, City city) {
        boolean result = false;

        Country country = getCountryByName(countryName);

        if(country.getCities().add(city)) {
            countryDAO.updateCountry(country);
            result = true;
        }

        return result;
    }

    @Transactional
    public boolean updateCityToCountry(String countryName, String cityName, City newCity) {
        boolean result;

        Country country = getCountryByName(countryName);

        City city = null;
        for(City cityTemp : country.getCities()) {
            if(cityTemp.getCity().equalsIgnoreCase(cityName)) {
                city = cityTemp;
            }
        }

        city.setCity(newCity.getCity());
        //updating Country
        countryDAO.updateCountry(country);
        result = true;

        return result;
    }

    @Transactional
    public City findCityInCountry(String countryName, String cityName) {
        City city = null;

        for(City cityTemp : getCountryByName(countryName).getCities()) {
            if(cityTemp.getCity().equalsIgnoreCase(cityName)) {
                city = cityTemp;
            }
        }

        return city;
    }

    @Transactional
    public List<String> getCitiesByCountryName(String countryName) {
        List<String> cities = new ArrayList<String>();

        for(City cityTemp : countryDAO.getCountryByName(countryName).getCities()) {
            cities.add(cityTemp.getCity());
        }

        return cities;
    }

    @Transactional
    public void updateCountry(Country country) {
        countryDAO.updateCountry(country);
    }

    @Transactional
    public boolean removeCountry(Integer countryId) {
        boolean result = false;

        if(!(getCountryById(countryId).getCities().size() > 0)) {
            countryDAO.removeCountry(countryId);
            result = true;
        }

        return result;
    }

    @Transactional
    public List<String> getAllCountriesNames() {
        List<String> countriesNames = new ArrayList<String>();

        for(Country country : countryDAO.getAllCountries()) {
            countriesNames.add(country.getCountry());
        }

        return countriesNames;
    }

    @Transactional
    public boolean removeCityFromCountry(String countryName, String cityName) {
        boolean result = false;

        Country country = getCountryByName(countryName);

        City city = null;
        for(City cityTemp : country.getCities()) {
            if(cityTemp.getCity().equalsIgnoreCase(cityName)) {
                city = cityTemp;
            }
        }

        if(country.getCities().remove(city)) {
            //updating Country
            countryDAO.updateCountry(country);
            result = true;
        }

        return result;
    }
}

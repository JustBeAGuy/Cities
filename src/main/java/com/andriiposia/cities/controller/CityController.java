package com.andriiposia.cities.controller;

import com.andriiposia.cities.entity.City;
import com.andriiposia.cities.service.CityService;
import com.andriiposia.cities.service.CountryService;
import com.andriiposia.cities.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Администратор on 9/3/15.
 */
@Controller
@RequestMapping("/country/{countryName:[\\w]+}/city")
@Secured("ROLE_USER")
public class CityController {

    @Autowired
    CityService cityService;
    @Autowired
    CountryService countryService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JsonResponse getAllCities(@PathVariable("countryName") String countryName){
        JsonResponse response = new JsonResponse();

        response.setStatusSuccess(countryService.getCitiesByCountryName(countryName));

        return response;
    }

    @RequestMapping(value = "/{city:[\\w]+}" ,method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JsonResponse getCountry(@PathVariable("city") String cityName, @PathVariable("countryName") String countryName) {
        JsonResponse response = new JsonResponse();

            if(countryService.findCityInCountry(countryName, cityName) != null) {
                response.setStatusSuccess(countryService.findCityInCountry(countryName, cityName));
            } else {
                response.setStatusFail("City is absent in this country");
            }

        return response;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JsonResponse addCity(@RequestParam("city") String cityName, @PathVariable("countryName") String countryName, @Valid @ModelAttribute() City city, BindingResult bindingResult) {
        JsonResponse response = new JsonResponse();

        city.setCity(cityName);

        if (bindingResult.hasErrors()){
            response.setStatusFail();
        } else {
            if(countryService.addCityToCountry(countryName, city)){
                response.setStatusSuccess("City: "+city.getCity()+" added successfully");
            } else {
                response.setStatusFail("Duplicate City In This Country");
            }
        }
        return response;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{city:[\\w]+}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JsonResponse updateCity(@RequestParam("new_city_name") String newCityName, @PathVariable("city") String cityName, @PathVariable("countryName") String countryName, @Valid @ModelAttribute() City city, BindingResult bindingResult) {
        JsonResponse response = new JsonResponse();

        city.setCity(newCityName);
        if (bindingResult.hasErrors()) {
            response.setStatusSuccess(bindingResult.getAllErrors());
        } else{
            if(countryService.updateCityToCountry(countryName, cityName, city)){
                response.setStatusSuccess("Updated Successfully");
            } else {
                response.setStatusFail("Fail to Update");
            }
        }

        return response;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{city:[\\w]+}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JsonResponse removeCity(@PathVariable("city") String cityName, @PathVariable("countryName") String countryName) {
        JsonResponse response = new JsonResponse();
        if(countryService.removeCityFromCountry(countryName, cityName)){
            response.setStatusSuccess("Deleted");
        } else {
            response.setStatusFail("Fail to delete");
        }
        return response;
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resources Not Found")
    void handlerException(NullPointerException ex) {

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resources Not Found")
    void handlerException(Exception ex) {

    }

}

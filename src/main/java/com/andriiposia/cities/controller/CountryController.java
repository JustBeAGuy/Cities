package com.andriiposia.cities.controller;

import com.andriiposia.cities.entity.Country;
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
@RequestMapping("/country")
@Secured("ROLE_USER")
public class CountryController {

    @Autowired
    CountryService countryService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JsonResponse getAllCountries() {
        JsonResponse response = new JsonResponse();
        response.setStatusSuccess(countryService.getAllCountriesNames());
        return response;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JsonResponse addCountry(@RequestParam("country") String countryName, @Valid @ModelAttribute() Country country, BindingResult bindingResult) {
        JsonResponse response = new JsonResponse();

        country.setCountry(countryName);
        if(bindingResult.hasErrors()) {
            response.setStatusFail(bindingResult.getAllErrors());
        } else {
            countryService.addCountry(country);
            response.setStatusSuccess("Country: "+country.getCountry()+" added successfully");
        }
        return response;
    }

    @RequestMapping(value = "/{country:[\\w]+}" ,method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JsonResponse getCountry(@PathVariable("country") String countryName) {
        JsonResponse response = new JsonResponse();
            response.setStatusSuccess(countryService.getCountryByName(countryName));
        return response;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{country:[\\w]+}" ,method = RequestMethod.PUT, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JsonResponse updateCountry(@RequestParam("new_country_name") String newCountryName, @PathVariable("country") String countryName, @Valid @ModelAttribute() Country country, BindingResult bindingResult) {
        JsonResponse response = new JsonResponse();
        country = countryService.getCountryByName(countryName);
        country.setCountry(newCountryName);

        if (bindingResult.hasErrors()){
            response.setStatusFail(bindingResult.getAllErrors());
        } else {
            countryService.updateCountry(country);
            response.setStatusSuccess("Updated Successfully");
        }

        return response;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{country:[\\w]+}" ,method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JsonResponse removeCountry(@PathVariable("country") String countryName) {
        JsonResponse response = new JsonResponse();

            Country country = countryService.getCountryByName(countryName);
            if(countryService.removeCountry(country.getCountryId())) {
                response.setStatusSuccess("Deleted");
            } else {
                response.setStatusFail("Cities are still in the country");
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

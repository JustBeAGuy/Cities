package com.andriiposia.cities.controller;

import com.andriiposia.cities.entity.User;
import com.andriiposia.cities.entity.UserRole;
import com.andriiposia.cities.service.UserService;
import com.andriiposia.cities.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Andrii Posia on 9/3/15.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JsonResponse registerUser(@RequestParam("password") String password, @RequestParam("email") String email, @Valid @ModelAttribute()  User user, BindingResult bindingResult) {
        JsonResponse response = new JsonResponse();

        user.setEmail(email);
        user.setEnabled(true);
        user.setPassword(password);

        if(bindingResult.hasErrors()){
            response.setStatusFail(bindingResult.getAllErrors());
        } else {
            //Set User Roles
            Set<UserRole> userRoles = new HashSet<UserRole>();
            userRoles.add(new UserRole(user, "ROLE_USER"));

            user.setUserRole(userRoles);

            //Add User
            if(userService.addUser(user)) {
                response.setStatusSuccess();
            }
        }
        return response;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JsonResponse loginUser() {
        JsonResponse response = new JsonResponse();
        String username;
        boolean isAdmin;

        //check if User Logged in
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            //response User Email
            username = ((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            //response UserRole is Admin
            isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            //response map
            Map userRespMap = new HashMap();
            userRespMap.put("username",username);
            userRespMap.put("isAdmin", isAdmin);
            //set Success Response
            response.setStatusSuccess(userRespMap);
        } else {
            response.setStatusFail("Please, Login");
        }

        return response;
    }

    @RequestMapping(value = "/login/failure", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JsonResponse loginUserFailure() {
        JsonResponse response = new JsonResponse();

        response.setStatusFail("Incorrect Email or Password");

        return response;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JsonResponse logoutUser() {
        JsonResponse response = new JsonResponse();

        //check if User Still Logged
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            response.setStatusFail("Not success logout");
        } else {
            response.setStatusSuccess("Successfully logged out");
        }

        return response;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JsonResponse registerAdmin(@RequestParam("password") String password, @RequestParam("email") String email, @Valid @ModelAttribute()  User user, BindingResult bindingResult) {
        JsonResponse response = new JsonResponse();

        user.setEmail(email);
        user.setEnabled(true);
        user.setPassword(password);

        if(bindingResult.hasErrors()){
            response.setStatusFail(bindingResult.getAllErrors());
        } else {
            //Set User Roles
            Set<UserRole> userRoles = new HashSet<UserRole>();
            userRoles.add(new UserRole(user, "ROLE_USER"));
            userRoles.add(new UserRole(user, "ROLE_ADMIN"));

            user.setUserRole(userRoles);

            //Add User
            if(userService.addUser(user)) {
                response.setStatusSuccess();
            }
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

package com.andriiposia.cities.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.andriiposia.cities.dao.UserDAO;
import com.andriiposia.cities.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Администратор on 9/8/15.
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Transactional(readOnly=true)
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        com.andriiposia.cities.entity.User user = userDAO.findByEmail(username);
        List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

        return buildUserForAuthentication(user, authorities);


    }

    // Converts User user to userdetails.User
    private User buildUserForAuthentication(com.andriiposia.cities.entity.User user,
                                            List<GrantedAuthority> authorities) {
        return new User(user.getEmail(),
                user.getPassword(), user.isEnabled(),
                true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        for (UserRole userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }

}

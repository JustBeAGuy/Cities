package com.andriiposia.cities.entity;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Администратор on 9/3/15.
 */
@Entity
@Table(name = "USERS")
public class User implements java.io.Serializable {

    @Id
    @NotEmpty
    @Email
    @Column(name = "EMAIL", unique = true,
            nullable = false, length = 32)
    private String email;

    @NotNull
    @Size(min=4, max = 32)
    @Column(name = "PASSWORD", length = 32, nullable = false)
    private String password;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "user")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.DELETE,
            org.hibernate.annotations.CascadeType.MERGE,
            org.hibernate.annotations.CascadeType.PERSIST,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    private Set<UserRole> userRole = new HashSet<UserRole>(0);

    public User() {
    }

    public User(String email, String password, boolean enabled) {
        this.email = email;
        this.enabled = enabled;
        setPassword(password);
    }

    public User(String email, String password, boolean enabled, Set<UserRole> userRole) {
        this.email = email;
        this.enabled = enabled;
        setPassword(password);
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    //MD5 Coding
    public void setPassword(String password) {
        this.password = DigestUtils.md5Hex(password);
    }

    public Set<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}

package com.andriiposia.cities.entity;

/**
 * Created by Администратор on 9/4/15.
 */

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "USER_ROLE", uniqueConstraints = @UniqueConstraint(
        columnNames = { "ROLE", "EMAIL" }))
public class UserRole implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "USER_ROLE_ID",
            unique = true, nullable = false)
    private Integer userRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMAIL", nullable = false)
    private User user;

    @Column(name = "ROLE", nullable = false, length = 32)
    private String role;

    public UserRole() {
    }

    public UserRole(String role) {
        this.role = role;
    }

    public UserRole(User user, String role) {
        this.user = user;
        this.role = role;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (!role.equals(userRole.role)) return false;
        if (!user.equals(userRole.user)) return false;
        if (userRoleId != null ? !userRoleId.equals(userRole.userRoleId) : userRole.userRoleId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userRoleId != null ? userRoleId.hashCode() : 0;
        result = 31 * result + user.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }
}

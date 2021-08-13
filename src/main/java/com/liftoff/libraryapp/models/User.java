package com.liftoff.libraryapp.models;

import com.sun.istack.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Entity
    public class User implements UserDetails {

        // User Fields
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String userName;
        private String password;
        @Enumerated(EnumType.STRING)
        private UserRoles role;
        private String email;
        private boolean active;
        private boolean locked;

        // User Constructors


    public User(Long id, String userName, String password, UserRoles role, String email, boolean active, boolean locked) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.email = email;
        this.active = active;
        this.locked = locked;
    }

    public User(String userName, String password, UserRoles role, String email, boolean active, boolean locked) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.email = email;
        this.active = active;
        this.locked = locked;
    }

    public User() {}

    // UserDetails Parent Methods We Must Override
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
            return Collections.singletonList(authority);
        }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    // User Getters & Setters

    public Long getId() { return id; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setPassword(String password) { this.password = password; }
    public UserRoles getRole() { return role; }
    public void setRole(UserRoles role) { this.role = role; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public boolean isLocked() { return locked; }
    public void setLocked(boolean locked) { this.locked = locked; }

    // User Methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return active == user.active && locked == user.locked && id.equals(user.id) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && role == user.role && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, role, email, active, locked);
    }
}


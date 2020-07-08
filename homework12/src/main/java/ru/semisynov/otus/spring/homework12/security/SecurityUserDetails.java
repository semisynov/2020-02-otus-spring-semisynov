package ru.semisynov.otus.spring.homework12.security;

import lombok.Getter;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.semisynov.otus.spring.homework12.model.User;
import ru.semisynov.otus.spring.homework12.model.enums.UserRole;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
public class SecurityUserDetails implements UserDetails {

    private final long id;
    private final UserRole role;
    private final String login;
    private final String password;
    private final boolean isLocked;
    private boolean isAccountExpired = false;

    public SecurityUserDetails(User user) {
        this.id = user.getId();
        this.isLocked = user.isLocked();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.role = user.getRole();
        LocalDateTime passwordExpired = user.getExpired();
        if (passwordExpired != null && passwordExpired.isBefore(LocalDateTime.now())) {
            isAccountExpired = true;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (!isLocked) {
            return true;
        }
        throw new LockedException("User is locked");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isAccountExpired;
    }

    @Override
    public boolean isEnabled() {
        return !isLocked;
    }
}

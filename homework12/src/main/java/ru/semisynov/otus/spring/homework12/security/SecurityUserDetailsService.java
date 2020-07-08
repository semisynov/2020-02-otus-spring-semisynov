package ru.semisynov.otus.spring.homework12.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.semisynov.otus.spring.homework12.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework12.repositories.UserRepository;

@Slf4j
@Service("securityUserDetails")
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login)
                .map(SecurityUserDetails::new)
                .orElseThrow(() -> new ItemNotFoundException(String.format("User %s not found", login)));
    }
}

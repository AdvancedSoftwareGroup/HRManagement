package net.restapp.servise.impl;

import lombok.NonNull;
import net.restapp.model.User;
import net.restapp.repository.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * The service's layer of application for User, implements {@link org.springframework.security.core.userdetails.UserDetails}
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    /**
     * The field of User repository's layer that is called for use it's methods
     */
    @Autowired
    RepoUser repoUser;

    /**
     * The method calls a repository's method for find user by email
     * @param email - user Email
     * @return user - {@link org.springframework.security.core.userdetails.User}
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        User user = repoUser.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);

    }
}

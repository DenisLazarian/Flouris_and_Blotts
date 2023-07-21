package com.web.app.flourishandblotts.services;

import com.web.app.flourishandblotts.models.UserEntity;
import com.web.app.flourishandblotts.repositories.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    /**
     * Load the users list of the database, and take the user by email.
     * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity =  userRepository.findByMail(username)
                .orElseThrow( ()-> new UsernameNotFoundException("the user with mail "+username+" isn`t registered in data base.") );

        Collection<? extends GrantedAuthority> authorities = userEntity.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.getName().name()))
                .collect(Collectors.toSet());

        return new User(userEntity.getMail(),
                        userEntity.getPassword(),
                        true,
                        true,
                        true,
                        true,
                        authorities);
    }
}

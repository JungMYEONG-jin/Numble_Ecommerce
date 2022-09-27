package com.mj.webmarket.config;

import com.mj.webmarket.entity.type.Role;
import com.mj.webmarket.entity.user.User;
import com.mj.webmarket.exception.UserNotFoundException;
import com.mj.webmarket.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("user detail service start...");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email + " is not found..."));
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if ("admin".equals(email)){
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }else{
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }
        log.info("user detail service set role finished...");
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}

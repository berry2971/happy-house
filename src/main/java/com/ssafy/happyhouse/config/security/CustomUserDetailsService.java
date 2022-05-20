package com.ssafy.happyhouse.config.security;

import com.ssafy.happyhouse.domain.entity.User;
import com.ssafy.happyhouse.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Autowired
    public CustomUserDetailsService(
            UserMapper userMapper
    ) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        try {
            User user = userMapper.findById(id);
            CustomUserDetails userDetails = new CustomUserDetails(user);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
            userDetails.setAuthorities(authorities);
            return userDetails;
        } catch (Exception e) {
            throw new UsernameNotFoundException("없습니다...");
        }
    }

}

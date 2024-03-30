package com.airbnb.service;

import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyUserRepository;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private PropertyUserRepository propertyUserRepository;
    private JWTService jwtService;

    public UserService(PropertyUserRepository propertyUserRepository, JWTService jwtService) {
        this.propertyUserRepository = propertyUserRepository;
        this.jwtService = jwtService;
    }

    public PropertyUser addUser(PropertyUserDto dto) {
        PropertyUser user=new PropertyUser();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setUserRole(dto.getUserRole());
        user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10)));
        propertyUserRepository.save(user);
        return user;
    }

    public String verifylogin(LoginDto loginDto) {
        Optional<PropertyUser> user = propertyUserRepository.findByUsername(loginDto.getUsername());
        if (user.isPresent()) {
            PropertyUser user1 = user.get();
             if( BCrypt.checkpw(loginDto.getPassword(), user1.getPassword())) {
                 return jwtService.generateToken(user1);
             }

        }
        return null;


    }
}

package com.airbnb.controller;
import com.airbnb.dto.JWTResponse;
import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.entity.PropertyUser;
import com.airbnb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/adduser")
    public ResponseEntity<String> addUser(@RequestBody  PropertyUserDto dto) {
        PropertyUser user = userService.addUser(dto);
        if (user != null) {
            return new ResponseEntity<>("SignUp Done", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User already exists", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        String JwtToken = userService.verifylogin(loginDto);
        if (JwtToken!=null) {
            JWTResponse jwtResponse = new JWTResponse();
            jwtResponse.setToken(JwtToken);
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Credentials", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.expense.tracker.controllers;

import com.expense.tracker.helpers.Constants;
import com.expense.tracker.models.User;
import com.expense.tracker.services.user.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.expense.tracker.helpers.ResponseHandler;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  @Autowired
  UserService userService;

  private Map<String, String> generateJWTToken(User user) {

    long timeStamp = System.currentTimeMillis();

    String token = Jwts
        .builder()
        .signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)

        .setIssuedAt(new Date(timeStamp))
        .setExpiration(new Date(timeStamp + Constants.TOKEN_VALIDITY))

        .claim("user_id", user.getuser_id())
        .claim("email", user.getEmail())
        .claim("firstname", user.getfirstname())
        .claim("lastname", user.getlastname())

        .compact();

    Map<String, String> map = new HashMap<>();
    map.put("token", token);

    return map;
  }

  @PostMapping("/register")
  public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap) {

    String firstname = (String) userMap.get("firstname");

    String lastname = (String) userMap.get("lastname");

    String email = (String) userMap.get("email");

    String password = (String) userMap.get("password");

    User user = userService.registerUser(firstname, lastname, email, password);

    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {

    String email = (String) userMap.get("email");
    String password = (String) userMap.get("password");

    User user = userService.validateUser(email, password);
    return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
  }
}

/*

package com.CRUD.demo.controllers;

import com.CRUD.demo.entities.UserEntity;
import com.CRUD.demo.repositories.UserRepository;
import com.CRUD.demo.response.ResponseHandler;
import com.CRUD.demo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }

    // Add
    @PostMapping(value = "/users")
    public ResponseEntity<Object> Post(@RequestBody UserEntity params) {
        try {
            UserEntity result = userService.Post(params);
            return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    // Get
    @GetMapping(value = "/users")
    public ResponseEntity<Object> Get() {
        try {
            List<UserEntity> result = userService.Get();
            return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    // Get By ID
    @GetMapping(value = "/users/{id}")
    public ResponseEntity<Object> Get(@PathVariable int id) {
        try {
            UserEntity result = userService.Get(id);
            return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    // Update
    @PutMapping(value = "/users/{id}")
    public ResponseEntity<Object> Update(@PathVariable int id, @RequestBody UserEntity params) {
        try {
            UserEntity result = userService.Update(params, id);
            return ResponseHandler.generateResponse("Updated", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    // Delete
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<Object> Delete(@PathVariable int id) {
        try {
            String result = userService.Delete(id);
            return ResponseHandler.generateResponse("Deleted!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

}
*/
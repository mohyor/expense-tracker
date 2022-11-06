package com.expense.tracker.controllers;

import com.expense.tracker.helpers.Constants;
import com.expense.tracker.models.User;
import com.expense.tracker.services.UserService;

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

@RestController
@RequestMapping("/api/users")
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

    .claim("userID", user.getUserID())
    .claim("email", user.getEmail())
    .claim("firstName", user.getFirstName())
    .claim("lastName", user.getLastName())

    .compact();

  Map<String, String> map = new HashMap<>();
  map.put("token", token);

  return map;
 }

 @PostMapping("/register")
 public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap) {

  String firstName = (String) userMap.get("firstName");
  String lastName = (String) userMap.get("lastName");
  String email = (String) userMap.get("email");
  String password = (String) userMap.get("password");

  User user = userService.registerUser(firstName, lastName, email, password);
  return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
 }

 @PostMapping("/login")
 public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {

  String email = (String) userMap.get("email");
  String password = (String) userMap.get("password");

  User user = userService.validateUser(email, password);
  return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
 }
}

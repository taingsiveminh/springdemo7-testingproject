package com.example.demo.controller;

import com.example.demo.model.BaseResponseModel;
import com.example.demo.model.BaseResponseWithDataModel;
import com.example.demo.dto.user.UserDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//anotation
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    //use for retrieve record
    @GetMapping
    public ResponseEntity<BaseResponseWithDataModel> ListUser() {
        return userService.listUsers();
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<BaseResponseWithDataModel> getUser(@PathVariable("user_id") Long userId) {
        return userService.getUser(userId);
    }

    //use for creating /inserting record
    // request body = payload
    @PostMapping
    public ResponseEntity<BaseResponseModel> createUser(@RequestBody UserDto payload) {
        return userService.createUser(payload);
    }

    //endpoint  /api/v1/user/{id}
    @PutMapping("/{user_id}")
    public ResponseEntity<BaseResponseModel> updateUser(@PathVariable("user_id") Long userId, @RequestBody UserDto payload) {
        return userService.updateUser(payload,userId);
    }
   //Path variable
    @DeleteMapping ("/{user_id}")
    public ResponseEntity<BaseResponseModel> deleteUser (@PathVariable("user_id") Long userId) {
         return userService.deleteUser(userId);
    }
    }




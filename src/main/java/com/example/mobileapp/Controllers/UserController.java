package com.example.mobileapp.Controllers;

import com.example.mobileapp.Model.Request.UserDetailsRequestModel;
import com.example.mobileapp.Model.Response.UserRest;
import com.example.mobileapp.Services.UserService;
import com.example.mobileapp.Sharred.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path="/{id}")
    public UserRest getUser(@PathVariable String id){
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto,returnValue);
        return returnValue;
    }

    /***
     * We send a post call to create
     * a user
     * Request body takes the json body.
     * After that we create a class to convert json -> java
     *
     */
    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails)
    {
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails,userDto);
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser,returnValue);

        return returnValue;
    }

    @PutMapping
    public String updateUser(){
        return "Update user called";
    }

    @DeleteMapping
    public String deleteUser(){
        return "delete user was called";
    }
}

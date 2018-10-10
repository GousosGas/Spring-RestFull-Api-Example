package com.example.mobileapp.Controllers;

import com.example.mobileapp.Model.Request.UserDetailsRequestModel;
import com.example.mobileapp.Model.Response.*;
import com.example.mobileapp.Services.AddressesService;
import com.example.mobileapp.Services.UserService;
import com.example.mobileapp.Sharred.dto.AddressDto;
import com.example.mobileapp.Sharred.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("users/")
public class UserController {

    @Autowired
    UserService userService;



    @Autowired
    AddressesService addressesService;

    @GetMapping(path="/{id}",produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(@PathVariable String id){
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto,returnValue);
        return returnValue;
    }

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public List<UserRest> getUsers(@RequestParam(value="page",defaultValue = "0") int page,
                                   @RequestParam(value="limit",defaultValue = "25") int limit){
        List<UserRest> returnValue = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page,limit);

        for(UserDto userDto:users){
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(userDto,userModel);
            returnValue.add(userModel);
        }

        return  returnValue;
    }


    /***
     * We send a post call to create
     * a user
     * Request body takes the json body.
     * After that we create a class to convert json -> java
     *
     */
    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails)
            throws Exception
    {
        UserRest returnValue;
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails,UserDto.class);

        UserDto createdUser = userService.createUser(userDto);
        returnValue =  modelMapper.map(createdUser,UserRest.class);

        return returnValue;
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public UserRest updateUser(@RequestBody UserDetailsRequestModel userDetails,
                             @PathVariable String id){
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails,userDto);
        UserDto updatedUser = userService.updateUser(id,userDto);
        BeanUtils.copyProperties(updatedUser,returnValue);

        return returnValue;
    }

    @DeleteMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})

    public OperationStatusModel deleteUser(@PathVariable String id){
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @GetMapping(path="/{id}/addresses",produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public List<AddressRest> getUserAddresses(@PathVariable String id){
        List<AddressRest> returnvalue = new ArrayList<>();
        List<AddressDto> addressesDto = addressesService.getAddresses(id);


        if(addressesDto != null && !addressesDto.isEmpty()){
            Type listType = new TypeToken<List<AddressRest>>(){}.getType();
            ModelMapper modelMapper = new ModelMapper();
            returnvalue = modelMapper.map(addressesDto,listType);
        }

        return returnvalue;
    }

    @GetMapping(path="/{userId}/addresses/{addressId}",produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public AddressRest getUserAddress(@PathVariable String addressId,
                                      @PathVariable String userId){

      AddressDto addressDto = addressesService.getAddress(addressId);
      ModelMapper modelMapper = new ModelMapper();

      Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(userId,addressId)).withSelfRel();

        Link userLink = linkTo(UserController.class)//users
                .slash(userId)
                .withRel("user");

        Link addressesLink = linkTo(UserController.class)//users
                .slash(userId)
                .slash("addresses")
                .withRel("addresses");


      AddressRest addressRest = modelMapper.map(addressDto,AddressRest.class);
      addressRest.add(addressLink);
      addressRest.add(addressesLink);
      addressRest.add(userLink);

      return addressRest;
    }





}

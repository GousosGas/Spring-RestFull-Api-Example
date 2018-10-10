package com.example.mobileapp.Services.Impl;

import com.example.mobileapp.Entity.AddressEntity;
import com.example.mobileapp.Entity.UserEntity;
import com.example.mobileapp.Repositories.AddressRepository;
import com.example.mobileapp.Repositories.UserRepository;
import com.example.mobileapp.Services.AddressesService;
import com.example.mobileapp.Sharred.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AddressServiceImpl implements AddressesService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDto> getAddresses(String userId) {
        List<AddressDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
        if(userEntity == null) return  returnValue;

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for (AddressEntity addressEntity:addresses){
            returnValue.add(modelMapper.map(addressEntity,AddressDto.class));
        }

        return returnValue;
    }

    @Override
    public AddressDto getAddress(String addressId) {
        AddressDto returnValue = null;

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);

        if(addressEntity != null){
            returnValue = new ModelMapper().map(addressEntity,AddressDto.class);
        }

        return returnValue;
    }
}

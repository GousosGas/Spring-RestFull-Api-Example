package com.example.mobileapp.Services;

import com.example.mobileapp.Sharred.dto.AddressDto;

import java.util.List;

public interface AddressesService {
    List<AddressDto> getAddresses(String userId);
    AddressDto getAddress(String addressId);
}

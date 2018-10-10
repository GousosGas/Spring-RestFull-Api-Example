package com.example.mobileapp.Repositories;

import com.example.mobileapp.Entity.AddressEntity;
import com.example.mobileapp.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity,Long> {
    List<AddressEntity> findAllByUserDetails(UserEntity userEntity);
    AddressEntity findByAddressId(String addressId);

}

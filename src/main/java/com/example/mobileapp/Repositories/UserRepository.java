package com.example.mobileapp.Repositories;

import com.example.mobileapp.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findUserEntityByEmail(String email);
}

package com.ex.newWeb.service;

import com.ex.newWeb.Dto.RegistrationDto;
import com.ex.newWeb.models.UserEntity;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String name);
}

package com.lifescan.dummy.data.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lifescan.dummy.data.model.Login;
import com.lifescan.dummy.data.networking.service.SecurityServiceCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private SecurityServiceCore securityServiceCore;

    public String getToken(Login user) {
        Gson gson = new Gson();
        return gson.fromJson(
                gson.fromJson(securityServiceCore.login(user.getEmail(), user.getPassword()), JsonObject.class)
                        .get("result"), JsonObject.class)
                .get("token").toString();
    }

}

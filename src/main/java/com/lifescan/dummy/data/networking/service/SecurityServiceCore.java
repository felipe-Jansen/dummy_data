package com.lifescan.dummy.data.networking.service;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface SecurityServiceCore {

    @Headers({
            "Content-Type: application/json",
            "login: {login}",
            "password: {password}"
    })
    @RequestLine("POST /dms-web-services/services/rest/account/v3/authenticate")
    Object getToken(
            @Param("login") String login,
            @Param("password") String password);

}

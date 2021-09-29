package com.lifescan.dummy.data.networking.service;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="security", url="${host.domain}")
public interface SecurityServiceCore {

    @Headers({
            "Content-Type: application/json",
            "accept: aapplication/json, text/plain, */*"
    })
    @PostMapping("/dms-web-services/services/rest/account/v3/authenticate")
    String login(
            @RequestHeader("login") String login,
            @RequestHeader("password") String password);

}

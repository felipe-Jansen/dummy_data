/*
 * @author fjansen@lifescan.com
 * @version 1
 * Copyright: Copyright (c) 2021
 * Company: LifeScan IP Holdings, LLC
 * This file contains trade secrets of LifeScan IP Holdings, LLC.
 * No part may be reproduced or transmitted in any
 * form by any means or for any purpose without the express written
 * permission of LifeScan IP Holdings, LLC.
 */
package com.lifescan.dummy.data.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifescan.dummy.data.networking.service.PatientServiceCore;
import com.lifescan.dummy.data.networking.service.SecurityServiceCore;
import feign.Client;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
class FeignConfig {

    private ObjectMapper objectMapper;

    @Autowired
    private Client client;

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

    @Autowired
    public FeignConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    PatientServiceCore patientServiceCore(
            @Value("${host.domain}") String url
    ) {
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .client(client)
                .target(PatientServiceCore.class, url);
    }

    @Bean
    SecurityServiceCore securityServiceCore(
            @Value("${host.domain}") String url
    ) {
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .client(client)
                .target(SecurityServiceCore.class, url);
    }

}

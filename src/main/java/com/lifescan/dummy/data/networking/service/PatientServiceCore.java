package com.lifescan.dummy.data.networking.service;

import com.lifescan.dummy.data.model.Patient;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

public interface PatientServiceCore {

	@Headers({
			"Content-Type: application/json",
			"language: {language}",
			"country: {country}",
			"requestToken: {requestToken}"
	})
	@RequestLine("POST /dms-web-services/services/rest/account/v3/register")
	void create(
			@Param("language") String language,
			@Param("country") String country,
			@Param("requestToken") String requestToken,
			@RequestBody Patient patient);

}

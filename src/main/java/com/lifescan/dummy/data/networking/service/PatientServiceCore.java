package com.lifescan.dummy.data.networking.service;

import com.lifescan.dummy.data.model.Patient;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="patient", url="${host.domain}")
public interface PatientServiceCore {

	@Headers({"Content-Type: application/json"})
	@GetMapping("/dms-web-services/services/rest/account/v3/register")
	Object create(
			@RequestHeader("language") String language,
			@RequestHeader("country") String country,
			@RequestHeader("requestToken") String requestToken,
			@RequestBody Patient patient);

}

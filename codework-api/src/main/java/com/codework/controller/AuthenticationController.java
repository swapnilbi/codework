package com.codework.controller;

import com.codework.exception.SecurityException;
import com.codework.model.LoginInput;
import com.codework.model.LoginResponse;
import com.codework.model.Response;
import com.codework.service.IAuthenticationService;
import com.codework.utility.SecurityHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
public class AuthenticationController {

	@Autowired
	private IAuthenticationService authenticationService;

	@RequestMapping(value = "api/authenticate", method = RequestMethod.POST)
	public ResponseEntity<Response<LoginResponse>> authenticate(@RequestBody LoginInput loginInput)
			throws SecurityException {
		log.info("authenticate "+loginInput.getUsername());
		String token = authenticationService.authenticate(loginInput);
		return ResponseEntity.ok(new Response<>(new LoginResponse(token)));
	}

	@RequestMapping(value = "api/authenticate/refresh", method = RequestMethod.POST)
	public ResponseEntity<Response<LoginResponse>> refreshToken() {
		log.info("refreshToken "+SecurityHelper.getUserId());
		String token = authenticationService.refreshToken(SecurityHelper.getUserId());
		return ResponseEntity.ok(new Response<>(new LoginResponse(token)));
	}

	@RequestMapping(value = "api/logout", method = RequestMethod.GET)
	public ResponseEntity logout()
			throws SecurityException {
		log.info("logout "+SecurityHelper.getUserId());
		authenticationService.logout(SecurityHelper.getUserId());
		return  ResponseEntity.ok().build();
	}

}

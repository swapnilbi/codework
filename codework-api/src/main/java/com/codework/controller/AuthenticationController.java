package com.codework.controller;

import com.codework.exception.SecurityException;
import com.codework.model.LoginInput;
import com.codework.model.LoginResponse;
import com.codework.model.Response;
import com.codework.service.IAuthenticationService;
import com.codework.utility.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthenticationController {

	@Autowired
	private IAuthenticationService authenticationService;

	@RequestMapping(value = "api/authenticate", method = RequestMethod.POST)
	public ResponseEntity<Response<LoginResponse>> authenticate(@RequestBody LoginInput loginInput)
			throws SecurityException {
		String token = authenticationService.authenticate(loginInput);
		return ResponseEntity.ok(new Response<>(new LoginResponse(token)));
	}

	@RequestMapping(value = "api/authenticate/refresh", method = RequestMethod.POST)
	public ResponseEntity<Response<LoginResponse>> refreshToken() {
		String token = authenticationService.refreshToken(SecurityHelper.getUserId());
		return ResponseEntity.ok(new Response<>(new LoginResponse(token)));
	}

	@RequestMapping(value = "api/logout", method = RequestMethod.GET)
	public ResponseEntity logout()
			throws SecurityException {
		authenticationService.logout(SecurityHelper.getUserId());
		return  ResponseEntity.ok().build();
	}

}

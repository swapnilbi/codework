package com.codework.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "UserSession")
public class UserSession {

	@Transient
	public static final String SEQUENCE_NAME = "user_session_sequence";

	public UserSession(Long userId, String token, Date loginTime, Date tokenExpiresAt){
		this.userId = userId;
		this.token = token;
		this.loginTime = loginTime;
		this.tokenExpiresAt = tokenExpiresAt;
	}

	@Id
	private Long id;
	private Long userId;
	private String token;
	private Date loginTime;
	private Date logoutTime;
	private Date tokenExpiresAt;

}

package com.example.demo.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangePwRequest {
	private String email;
	private String userID;
}

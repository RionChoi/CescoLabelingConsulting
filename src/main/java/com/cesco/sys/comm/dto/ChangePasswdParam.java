package com.cesco.sys.comm.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswdParam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 7588027647475430107L;
	private String user_id;
	private String pwd;
	private String email;
	private String passwd1;
	private String passwd2;
}

package com.cesco.sys.comm.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NiceIdEncData implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1897947865777200312L;
	private String encData;
}

package com.cesco.sys.comm.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("nice-id")
public class NiceIdConfig {
	private String siteCode = null;
	private String sitePassword = null;
}

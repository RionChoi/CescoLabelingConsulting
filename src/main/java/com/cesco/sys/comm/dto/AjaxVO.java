package com.cesco.sys.comm.dto;

import java.io.Serializable;
import java.util.Map;

public class AjaxVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8658218190518427744L;
	
	private String path;
	private Map<String, String> param;
	private String division;
	private String result;
	private String sCtgrId;
	private String voClass;
	
	public Map<String, String> getParam() {
		return param;
	}
	public void setParam(Map<String, String> param) {
		this.param = param;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public String getsCtgrId() {
		return sCtgrId;
	}

	public void setsCtgrId(String sCtgrId) {
		this.sCtgrId = sCtgrId;
	}
	public String getVoClass() {
		return voClass;
	}
	public void setVoClass(String voClass) {
		this.voClass = voClass;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "AjaxVO [path=" + path + ", param=" + param + ", division=" + division + ", result=" + result
				+ ", sCtgrId=" + sCtgrId + ", voClass=" + voClass + "]";
	}
}

package com.bdqn.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult implements Serializable {
	
	/**
	 * 请求结果，0为失败，1为成功
	 */
	private int Code;
	
	
	/**
	 * 请求结果信息
	 */
	private String message;
	
	
	/**
	 * 请求的返回数据对象，也将被转为json格式
	 */
	private Object data;

	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	
	
}

package com.cnaidun.police.config.tip;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 返回给前台的提示（最终转化为json形式）
 *
 * @author dongyin
 * @Date 2019年5月31日
 */
@JsonPropertyOrder(value={"code","msg","success","data"})
public abstract class Tip {

	private int code;
	private String msg;
	private Object data;
	private boolean success;

	public Tip(int code, String msg, boolean success) {
		super();
		this.code = code;
		this.msg = msg;
		this.success = success;
	}

	public Tip(int code, String msg, boolean success, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}

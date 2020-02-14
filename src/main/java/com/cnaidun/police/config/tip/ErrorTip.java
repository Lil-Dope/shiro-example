package com.cnaidun.police.config.tip;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 返回给前台的错误提示
 * @author dongyin
 */
@JsonPropertyOrder(value={"code","msg","success","data"})
public class ErrorTip extends Tip {

	public ErrorTip(String msg) {
		this(0, msg);
	}

	public ErrorTip(int code, String msg) {
		super(code, msg, false);
	}

	public ErrorTip(String msg, Object data) {
		super(0, msg, false,data);
	}
	
	public ErrorTip(int code, String msg, Object data) {
		super(code, msg, false, data);
	}
}

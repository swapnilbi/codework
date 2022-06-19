package com.codework.model;

import com.codework.enums.RemarkType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Response<T> {

	T data;
	List<Remark> remarks = new ArrayList<>();

	public Response(){
	}

	public Response(T data){
		this.data = data;
	}

	public Response(List<Remark> remarks){
		this.data = data;
	}

	public Response addError(String message){
		remarks.add(new Remark(message, RemarkType.ERROR));
		return this;
	}

}

package com.codework.model;

import lombok.Data;

import java.util.List;

@Data
public class Response<T> {

	T data;
	List<Remark> remarks;

	public Response(){
	}

	public Response(T data){
		this.data = data;
	}

	public Response(List<Remark> remarks){
		this.data = data;
	}

}

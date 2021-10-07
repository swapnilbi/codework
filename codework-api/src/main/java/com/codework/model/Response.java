package com.codework.model;

import java.util.List;

public class Response<T> {

	T data;
	List<Remark> remarks;

	Response(){
	}

	Response(T data){
		this.data = data;
	}

	Response(List<Remark> remarks){
		this.data = data;
	}

}

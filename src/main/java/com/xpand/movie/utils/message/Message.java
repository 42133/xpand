package com.xpand.movie.utils.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

public class Message<T> {
    @JsonIgnore
    private HttpStatus code;
    private String message;
    private T data;

    public Message(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

    public Message(HttpStatus code, T data, String message) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

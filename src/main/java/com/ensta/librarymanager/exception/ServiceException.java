package com.ensta.librarymanager.exception;

public class ServiceException extends Exception{
    public ServiceException() {
        super();
    }
    public ServiceException(String s) {
        //super(s);
        System.out.println(s);
    }
}

package com.ensta.librarymanager.exception;

public class DaoException extends Exception{
    public DaoException() {
        super();
    }
    public DaoException(String s) {
        System.out.println(s);
        //super(s);
    }
}

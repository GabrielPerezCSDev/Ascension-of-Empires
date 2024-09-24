package com.iastate.exceptions;
/**
 *
 * @author Gabriel Perez
 *
 */
public class UserAlreadyLoggedInException extends RuntimeException {
    public UserAlreadyLoggedInException() {
        super("User is already logged in.");
    }
}
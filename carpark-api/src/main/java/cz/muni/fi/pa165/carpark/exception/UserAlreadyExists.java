/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.exception;

/**
 * Exception thrown when trying to create user with existing birth number.
 *
 * @author Tomas Vasicek
 */
public class UserAlreadyExists extends RuntimeException
{
    /**
     * Creates a new instance of <code>UserAlreadyExists</code> without detail
     * message.
     */
    public UserAlreadyExists()
    {
    }
    
    /**
     * Constructs an instance of <code>UserAlreadyExists</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UserAlreadyExists(String msg)
    {
        super(msg);
    }

    /**
     * Constructs an instance of <code>UserAlreadyExists</code> with the
     * specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public UserAlreadyExists(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    
}

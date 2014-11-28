/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.exception;

/**
 * Exception thrown when trying to create car with existing vin or licenceplate.
 *
 * @author Jiri Dockal
 */
public class CarAlreadyExists extends RuntimeException
{
    /**
     * Creates a new instance of <code>CarAlreadyExists</code> without detail
     * message.
     */
    public CarAlreadyExists()
    {
    }
    
    /**
     * Constructs an instance of <code>CarAlreadyExists</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CarAlreadyExists(String msg)
    {
        super(msg);
    }

    /**
     * Constructs an instance of <code>CarAlreadyExists</code> with the
     * specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public CarAlreadyExists(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    
}

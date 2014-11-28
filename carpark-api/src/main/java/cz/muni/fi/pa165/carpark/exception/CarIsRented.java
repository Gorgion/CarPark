/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.exception;

/**
 * Exception thrown when trying to delete rented car.
 *
 * @author Jiri Dockal
 */
public class CarIsRented extends RuntimeException
{

    /**
     * Creates a new instance of <code>CarIsRented</code> without detail
     * message.
     */
    public CarIsRented()
    {
    }

    /**
     * Constructs an instance of <code>CarIsRented</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CarIsRented(String msg)
    {
        super(msg);
    }

    /**
     * Constructs an instance of <code>CarIsRented</code> with the
     * specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public CarIsRented(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    
}

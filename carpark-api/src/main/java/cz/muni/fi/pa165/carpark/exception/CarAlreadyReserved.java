/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.exception;

/**
 * Exception thrown when trying to reserved already reserved car.
 *
 * @author Tomas Svoboda
 */
public class CarAlreadyReserved extends RuntimeException
{

    /**
     * Creates a new instance of <code>CarAlreadyReserved</code> without detail
     * message.
     */
    public CarAlreadyReserved()
    {
    }

    /**
     * Constructs an instance of <code>CarAlreadyReserved</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CarAlreadyReserved(String msg)
    {
        super(msg);
    }

    /**
     * Constructs an instance of <code>CarAlreadyReserved</code> with the
     * specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public CarAlreadyReserved(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    
}

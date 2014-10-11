/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.exception;

/**
 * Exception representing data access error.
 *
 * @author Tomas Svoboda
 */
public class DataAccessException extends RuntimeException
{

    /**
     * Creates a new instance of <code>DataAccessException</code> without detail
     * message.
     */
    public DataAccessException()
    {
    }

    /**
     * Constructs an instance of <code>DataAccessException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DataAccessException(String msg)
    {
        super(msg);
    }

    /**
     * Constructs an instance of <code>DataAccessException</code> with the
     * specified detail message.
     *
     * @param message the detail message.
     * @param cause the cause of exception
     */
    public DataAccessException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

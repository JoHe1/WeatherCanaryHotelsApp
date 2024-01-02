package org.ulpgc.dacd.exceptions;

import javax.jms.JMSException;

public class JmsExceptionConection extends RuntimeException{
    public JmsExceptionConection(String message, Throwable cause) {
        super(message, cause);
    }
}

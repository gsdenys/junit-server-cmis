package com.github.gsdenys.runner.type.parser;

/**
 * Created by gsdenys on 11/02/17.
 */
public class ParserException extends Exception {
    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }
}

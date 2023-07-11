package com.Electronics.Store.Config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GlobalResource {

    public static Logger getLogger(Class className) {

        return (Logger) LoggerFactory.getLogger(className);
    }
}

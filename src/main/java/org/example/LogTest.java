package org.example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
    private static final Logger log = LoggerFactory.getLogger(LogTest.class);

    public static void main(String[] args) {
        log.error("ERROR");
        log.warn(" WARN");
        log.info("INFO");
        log.debug("DEBUG");
    }
}
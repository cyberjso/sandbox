package io.sandbox.nebula;

import org.apache.log4j.Logger;

public class App {
    private static Logger logger =  Logger.getLogger(App.class);

    public static void main(String args[]) {
        while (true) {
            try {
                Thread.sleep(1000);
                logger.info("*** ping ***");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}

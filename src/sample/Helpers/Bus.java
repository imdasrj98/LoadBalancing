package sample.Helpers;

import java.io.Serializable;

public class Bus implements Serializable {

    private static TechDetails instance;

    private Bus() {
        instance = new TechDetails();
    }

    public static TechDetails getInstance() {
        return instance;
    }

    public static void setInstance(TechDetails instance1) {
        instance = instance1;
    }

}

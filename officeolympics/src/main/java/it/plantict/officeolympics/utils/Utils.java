package it.plantict.officeolympics.utils;


import java.sql.Timestamp;

public class Utils {

    public static Timestamp getCurrentTimestamp()
    {
        return new Timestamp(System.currentTimeMillis());
    }
}

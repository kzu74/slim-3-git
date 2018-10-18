package app.commons.util;

import java.util.Random;

public class RandomString {
    
    private static final int NUM_CHARS = 32;
    private static String chars = "abcdefghijklmonpqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static Random r = new Random();
    
    
    public static String getOne() {

        char[] buf = new char[NUM_CHARS];
        
        for (int i = 0; i < buf.length; i++) {
            buf[i] = chars.charAt(r.nextInt(chars.length()));
        }
        
        return new String(buf);
    }
    
}
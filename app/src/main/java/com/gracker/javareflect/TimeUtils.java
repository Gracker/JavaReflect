package com.gracker.javareflect;

/**
 * Project name : JavaReflect . Package name : com.gracker.javareflect . Created by gaojack . Date :
 * 15/5/15 .
 */
public class TimeUtils {

    private static double startTime = 0;

    public static void start() {
        startTime = System.nanoTime();
    }

    public static double end() {
        double mEndTime = System.nanoTime();
        return ((mEndTime - startTime) * 0.000001);
    }
}

package com.gracker.javareflect;

/**
 * Project name : JavaReflect . Package name : com.gracker.javareflect . Created by gaojack . Date :
 * 15/5/15 .
 */
public class TimeUtils {

    private static double startTime = 0;
    private static double endTime = 0;

    public static void start() {
        startTime = System.nanoTime();
    }

    public static double end() {
        endTime = System.nanoTime();
        return ((endTime - startTime) * 0.000001);
    }
}

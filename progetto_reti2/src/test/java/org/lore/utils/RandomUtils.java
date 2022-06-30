package org.lore.utils;

public class RandomUtils {
    public static int getRandomByRange(int min, int max){
        return (int) ((Math.random()*(max-min)) + min);
    }
}

package org.lore.utils;

public class RandomUtils {
    public static int getRandomByRange(int min, int max){
        //return a random value between min and max, used for generating values for the Sensors' Simulators
        return (int) ((Math.random()*(max-min)) + min);
    }
}

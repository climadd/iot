package org.lore.emu;

import java.util.Random;

public class Sensore{

        public static void main(String[]Args) throws InterruptedException {

                Random r = new Random();
                int i = r.nextInt(100);

                while(true){
                        int v = r.nextInt(10);
                        boolean c =r.nextBoolean();
                        if(c==true) i += v;
                        else if(c==false) i -= v;

                        if(i<0) i=0;
                        else if(i>100) i=100;
                        Thread.sleep(1000);
                        System.out.println("L'umidita' e' "+ i +"%");
                }


        }
        }
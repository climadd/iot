package com.random.app;

import org.junit.jupiter.api.Test;
import org.lore.tcp.TCPConfig;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
    @Test
    public void test(){
        Map<String, TCPConfig> map = new HashMap<>();
        TCPConfig conf1 = new TCPConfig(5001);
        TCPConfig conf2 = new TCPConfig(5002,"127.0.0.1");
        map.put("temperatura",conf1);
        map.put("illuminazione",conf2);

//        System.out.println(map.get("illuminazione").getIp());
//        System.out.println(map.get("illuminazione").getPort());
//        System.out.println(map.get("temperatura").getPort());

        for (String key : map.keySet()) {
            TCPConfig value = map.get(key);
            System.out.println(value.getPort());
            if(value.getIp()!=null){
                System.out.println(value.getIp());
            }
        }

    }
}

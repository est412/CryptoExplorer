package ru.est412.cryptoexplorer;

import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by Anybody on 04.11.2017.
 */
public class Test {

    public static void main(String[] args) {
        Provider provider = Security.getProvider("SUN");
        System.out.println("===========================================");
        for (Map.Entry<Object, Object> entry : provider.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        for (Provider.Service service : provider.getServices()) {
            System.out.println(service.getAlgorithm());
        }
        System.out.println("------------------------------------------");


        System.out.println(Security.getProvider("SUN").getServices());


        System.out.println(Arrays.toString(Security.getProviders()));
        System.out.println(Security.getProvider("XMLDSig").getInfo());
        //System.out.println(Security.getEntity("SUN").getServices());
        for (Provider.Service service : Security.getProvider("XMLDSig").getServices()) {
            System.out.println(service.getType() + " " +service.getAlgorithm());
        }
        System.out.println("------entrySet-------");
        System.out.println(Security.getProvider("XMLDSig").entrySet());
        for (Map.Entry<Object, Object> entry : Security.getProvider("SUN").entrySet()){
            System.out.println(entry.getKey().getClass().getName() + " : " + entry.getValue().getClass().getName());
        }
        /*
        System.out.println("-------elements------");
        Enumeration<java.lang.Object> e = Security.getEntity("SUN").elements();
        while (e.hasMoreElements()) {
            System.out.println(e.nextElement());
        }
        System.out.println(Security.getEntity("SUN").elements());
        System.out.println("------keys-------");
        Enumeration<java.lang.Object> k = Security.getEntity("SUN").keys();
        while (k.hasMoreElements()) {
            System.out.println(k.nextElement());
        }
        System.out.println("------keySet-------");
        System.out.println(Security.getEntity("SUN").keySet());
        */

    }

}

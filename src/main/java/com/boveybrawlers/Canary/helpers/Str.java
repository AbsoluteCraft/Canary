package com.boveybrawlers.Canary.helpers;

public class Str {

    public static String possessive(String name) {
        if(name.endsWith("s")) {
            return name + "'";
        }

        return name + "'s";
    }

}

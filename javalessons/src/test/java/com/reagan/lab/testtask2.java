package com.reagan.lab;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class testtask2 {
    @Test
    void standartText() {
        Map<String, Integer> dict_text = new HashMap<String, Integer>();
        dict_text.put("test", 1);
        dict_text.put("testt", 2);
        Assertions.assertEquals(dict_text, task2.numwords("test testt testt"));
    }

    @Test
    void spaceText() {
        Map<String, Integer> dict_text = new HashMap<String, Integer>();
        dict_text.put("test", 1);
        dict_text.put("testt", 2);
        Assertions.assertEquals(dict_text, task2.numwords(" test   testt  testt  "));
    }

    @Test
    void nullText() {
        Map<String, Integer> dict_text = new HashMap<String, Integer>();
        Assertions.assertEquals(dict_text, task2.numwords(""));
    }
}

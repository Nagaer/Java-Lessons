package com.reagan.lab;

import com.reagan.lab.task2.task2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class testtask2 {
    @Test
    void standartText() {
        Map<String, Integer> dict_text = new HashMap<String, Integer>();
        Map<String, Integer> dict_text0 = new HashMap<String, Integer>();
        dict_text.put("test", 1);
        dict_text.put("testt", 2);
        Assertions.assertEquals(dict_text, task2.numwords("test testt testt", dict_text0));
    }

    @Test
    void spaceText() {
        Map<String, Integer> dict_text = new HashMap<String, Integer>();
        Map<String, Integer> dict_text0 = new HashMap<String, Integer>();
        dict_text.put("test", 1);
        dict_text.put("testt", 2);
        Assertions.assertEquals(dict_text, task2.numwords(" test   testt  testt  ", dict_text0));
    }

    @Test
    void nullText() {
        Map<String, Integer> dict_text = new HashMap<String, Integer>();
        Map<String, Integer> dict_text0 = new HashMap<String, Integer>();
        Assertions.assertEquals(dict_text, task2.numwords("", dict_text0));
    }
}

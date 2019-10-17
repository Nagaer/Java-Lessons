package com.reagan.lab;

import com.reagan.lab.task3.task3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class testtask3 {
    @Test
    void twoPlustwoTest() {
        Assertions.assertEquals(4, task3.summ(2,2));
    }
    @Test
    void morePlusmoreTest() {
        Assertions.assertEquals(12000000, task3.summ(10000000,2000000));
    }
    @Test
    void plusZeroTest() {
        Assertions.assertEquals(2, task3.summ(2,0));
    }
    @Test
    void plusNegativeTest() {
        Assertions.assertEquals(-3, task3.summ(2,-5));
    }
}

package com.reagan.lab.task4.structureFaculty;

public class Educator {
    int num;
    String name;
    public Educator(int num, String name) {
        this.num = num;
        this.name = name;
    }
    public void printEducator() {
        System.out.println(this.num + " " + this.name);
    }
}

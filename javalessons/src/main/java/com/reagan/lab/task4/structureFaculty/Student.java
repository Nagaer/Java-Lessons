package com.reagan.lab.task4.structureFaculty;

public class Student {
    int num;
    String name;
    public Student(int num, String name) {
        this.num = num;
        this.name = name;
    }
    public void printStudent() {
        System.out.println(this.num + " " + this.name);
    }
}

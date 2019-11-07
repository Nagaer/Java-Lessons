package com.reagan.lab.task4.structureFaculty;

public class Lesson {
    private Subject lessSub; //сделать private
    private DateTime[] lessTime;
    private Group lessGroup;
    private Educator lessEducator;

    public Lesson(Subject lessSub, DateTime[] lessTime, Group lessGroup, Educator lessEducator) {
        this.lessSub=lessSub;
        this.lessTime=lessTime;
        this.lessGroup=lessGroup;
        this.lessEducator=lessEducator;
    }

    public static void main(String[] args) {
        DateTime D1 = new DateTime(Day.Monday, 9, 40);
        DateTime D2 = new DateTime(Day.Monday, 11, 10);
        DateTime[] D = {D1, D2};
        Student[] S = {new Student("Иванов"), new Student("Петров"), new Student("Сидоров")};
        Group G = new Group(S, "41");
        Educator E = new Educator("Малыхин");
        Lesson L = new Lesson(new Subject("Матан"), D, G, E);
    }
}

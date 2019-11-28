package com.reagan.lab.task4.structureFaculty;

public class Lesson {
    int num;
    private Subject lessSub;
    private DateTime[] lessTime;
    private Group lessGroup;
    private Educator lessEducator;

    public Lesson(int num, Subject lessSub, DateTime[] lessTime, Group lessGroup, Educator lessEducator) {
        this.num = num;
        this.lessSub=lessSub;
        this.lessTime=lessTime;
        this.lessGroup=lessGroup;
        this.lessEducator=lessEducator;
    }

    void printLesson() {
        this.lessSub.printSubject();
        System.out.println();
        this.lessTime[0].printDayTime();
        System.out.print(" ");
        this.lessTime[0].printTime();
        System.out.print(" - ");
        this.lessTime[1].printTime();
        System.out.println();
        this.lessGroup.printGroup();
        System.out.println();
        this.lessEducator.printEducator();
        System.out.println();
    }
/*
    public static void main(String[] args) {
        DateTime[] D = DateTime.CreateCouple(1, 2);
        Student[] S = {new Student("Иванов"), new Student("Петров"), new Student("Сидоров")};
        Group G = new Group(S, "41");
        Educator E = new Educator("Малыхин");
        Lesson LTesting = new Lesson(new Subject("Матан"), D, G, E);
        LTesting.printLesson();
    }
*/
    boolean hasStudent(Student student) {
        return this.lessGroup.hasStudent(student);
    }

    boolean hasEducator(Educator educator) {
        return this.lessEducator == educator;
    }
}

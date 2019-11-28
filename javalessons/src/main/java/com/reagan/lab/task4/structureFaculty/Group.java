package com.reagan.lab.task4.structureFaculty;

import java.util.List;

public class Group {
    private int num;
    private List<Student> grStudents;
    private String name;
    public Group(int num, List<Student> grStudents, String name) {
        this.num = num;
        this.grStudents = grStudents;
        this.name = name;
    }

    void printGroup() {
        System.out.print(this.num);
        System.out.print("; ");
        for (Student student : this.grStudents) {
            student.printStudent();
            System.out.print(" ");
        }
        System.out.print("; ");
        System.out.print(this.name);
    }

    boolean hasStudent(Student student) {
        for (Student st : grStudents) {
            if (st == student)
                return true;
        }
        return false;
    }
}

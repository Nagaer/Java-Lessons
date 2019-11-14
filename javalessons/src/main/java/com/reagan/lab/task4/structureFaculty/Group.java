package com.reagan.lab.task4.structureFaculty;

class Group {
    private Student[] grStudents;
    private String name;
    Group(Student[] grStudents, String name) {
        this.grStudents = grStudents;
        this.name = name;
    }

    void printGroup() {
        for (Student student : this.grStudents) {
            student.printStudent();
            System.out.print(" ");
        }
        System.out.print("; ");
        System.out.print(this.name);
    }

    boolean hasStudent(String name) {
        for (Student student : grStudents) {
            if (student.name.equals(name))
                return true;
        }
        return false;
    }
}

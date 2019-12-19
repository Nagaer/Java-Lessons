package com.reagan.ind.EkzTasks.TaskTwo;

class Student {
    String name, surname;
    int cource, id;

    Student(String name, String surname, int id, int cource) {
        this.surname = surname;
        this.name = name;
        this.id = id;
        this.cource = cource;
    }

    private void editStudent(String name, String surname, int id, int cource) {
        this.surname = surname;
        this.name = name;
        this.id = id;
        this.cource = cource;
    }

    private void print() {
        System.out.println("Id: " + this.id);
        System.out.println("Фамилия: " + this.surname);
        System.out.println("Имя: " + this.name);
        System.out.println("Курс: " + this.cource);
    }
}


package com.reagan.ind.EkzTasks.TaskThree;

public class StudentDiplom extends Student implements InterfaceStudent {
    String theme;

    private StudentDiplom(String name, String surname, int id, int cource, String theme) {
        super(name, surname, id, cource);
        this.theme = theme;
    }

    public void print() {
        System.out.println("Id: " + this.id);
        System.out.println("Фамилия: " + this.surname);
        System.out.println("Имя: " + this.name);
        System.out.println("Курс: " + this.cource);
        System.out.println("Тема диплома: " + this.theme);
    }

    private void editTheme(String newTheme) {
        this.theme = newTheme;
    }
}

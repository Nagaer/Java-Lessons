package com.reagan.lab.task4.structureFaculty;

import java.util.ArrayList;

public class Schedule {
    private ArrayList<Lesson> less = new ArrayList<>();

    private static Schedule initSchedule() {
        Educator Educ1 = new Educator("Малыхин");
        Educator Educ2 = new Educator("Гаркуша");
        Educator Educ3 = new Educator("Костенко");

        Student Stu1 = new Student("Иванов");
        Student Stu2 = new Student("Петров");
        Student Stu3 = new Student("Сидоров");
        Student Stu4 = new Student("Александров");
        Student Stu5 = new Student("Михаилов");

        Group Gr1 = new Group(new Student[]{Stu1, Stu2}, "11");
        Group Gr2 = new Group(new Student[]{Stu3, Stu4, Stu5}, "12");

        Subject Sub1 = new Subject("Математический анализ");
        Subject Sub2 = new Subject("Pascal");
        Subject Sub3 = new Subject("Дискретная математика");

        Schedule Sch = new Schedule();

        Sch.less.add(new Lesson(Sub1, DateTime.CreateCouple(1, 1), Gr1, Educ1));
        Sch.less.add(new Lesson(Sub1, DateTime.CreateCouple(1, 1), Gr2, Educ1));
        Sch.less.add(new Lesson(Sub2, DateTime.CreateCouple(1, 2), Gr1, Educ2));
        Sch.less.add(new Lesson(Sub3, DateTime.CreateCouple(1, 2), Gr2, Educ3));

        Sch.less.add(new Lesson(Sub2, DateTime.CreateCouple(2, 1), Gr1, Educ2));
        Sch.less.add(new Lesson(Sub3, DateTime.CreateCouple(2, 1), Gr2, Educ3));
        Sch.less.add(new Lesson(Sub3, DateTime.CreateCouple(2, 2), Gr1, Educ3));
        Sch.less.add(new Lesson(Sub2, DateTime.CreateCouple(2, 2), Gr2, Educ2));
        Sch.less.add(new Lesson(Sub1, DateTime.CreateCouple(2, 3), Gr1, Educ1));
        Sch.less.add(new Lesson(Sub1, DateTime.CreateCouple(2, 3), Gr2, Educ1));

        Sch.less.add(new Lesson(Sub2, DateTime.CreateCouple(3, 1), Gr1, Educ2));
        Sch.less.add(new Lesson(Sub3, DateTime.CreateCouple(3, 2), Gr1, Educ3));
        Sch.less.add(new Lesson(Sub1, DateTime.CreateCouple(3, 3), Gr1, Educ1));
        Sch.less.add(new Lesson(Sub1, DateTime.CreateCouple(3, 3), Gr2, Educ1));

        return Sch;
    }

    private void printSchedule() {
        for (Lesson l : less) {
            l.printLesson();
            System.out.println();
        }
    }

    private Schedule searchStudent(String name) {
        Schedule Sch = new Schedule();
        for (Lesson l : this.less) {
            if (l.hasStudent(name)) {
                Sch.less.add(l);
            }
        }
        return Sch;
    }

    private Schedule searchEducator(String name) {
        Schedule Sch = new Schedule();
        for (Lesson l : this.less) {
            if (l.hasEducator(name)) {
                Sch.less.add(l);
            }
        }
        return Sch;
    }

    public static void main(String[] args) {
        Schedule Sch = initSchedule();
        //Schedule S1 = Sch.searchEducator("Малыхин");
        //S1.printSchedule();
        Schedule S2 = Sch.searchStudent("Иванов");
        S2.printSchedule();
    }
}

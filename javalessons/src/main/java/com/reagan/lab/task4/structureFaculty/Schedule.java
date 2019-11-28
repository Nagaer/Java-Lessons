package com.reagan.lab.task4.structureFaculty;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Schedule {
    public List<Lesson> lessonsList;

    public Schedule(List<Lesson> lessonsList) {
        this.lessonsList = lessonsList;
    }

    public Schedule initSchedule() {
        Educator Educ1 = new Educator(1, "Малыхин");
        Educator Educ2 = new Educator(2, "Гаркуша");
        Educator Educ3 = new Educator(3, "Костенко");

        Student Stu1 = new Student(1, "Иванов");
        Student Stu2 = new Student(2, "Петров");
        Student Stu3 = new Student(3, "Сидоров");
        Student Stu4 = new Student(4, "Александров");
        Student Stu5 = new Student(5, "Михаилов");

        Group Gr1 = new Group(11, Arrays.asList(Stu1, Stu2), "11");
        Group Gr2 = new Group(12, Arrays.asList(Stu3, Stu4, Stu5), "12");

        Subject Sub1 = new Subject("Математический анализ");
        Subject Sub2 = new Subject("Pascal");
        Subject Sub3 = new Subject("Дискретная математика");

        Schedule Sch = new Schedule(Arrays.asList(
                new Lesson(1, Sub1, DateTime.CreateCouple(1, 1), Gr1, Educ1),
                new Lesson(2, Sub1, DateTime.CreateCouple(1, 1), Gr2, Educ1),
                new Lesson(3, Sub2, DateTime.CreateCouple(1, 2), Gr1, Educ2),
                new Lesson(4, Sub3, DateTime.CreateCouple(1, 2), Gr2, Educ3),
                new Lesson(5, Sub2, DateTime.CreateCouple(2, 1), Gr1, Educ2),
                new Lesson(6, Sub3, DateTime.CreateCouple(2, 1), Gr2, Educ3),
                new Lesson(7, Sub3, DateTime.CreateCouple(2, 2), Gr1, Educ3),
                new Lesson(8, Sub2, DateTime.CreateCouple(2, 2), Gr2, Educ2),
                new Lesson(9, Sub1, DateTime.CreateCouple(2, 3), Gr1, Educ1),
                new Lesson(10, Sub1, DateTime.CreateCouple(2, 3), Gr2, Educ1),
                new Lesson(11, Sub2, DateTime.CreateCouple(3, 1), Gr1, Educ2),
                new Lesson(12, Sub3, DateTime.CreateCouple(3, 2), Gr1, Educ3),
                new Lesson(13, Sub1, DateTime.CreateCouple(3, 3), Gr1, Educ1),
                new Lesson(14, Sub1, DateTime.CreateCouple(3, 3), Gr2, Educ1)
        ));

        return Sch;
    }

    public void printSchedule() {
        for (Lesson l : lessonsList) {
            l.printLesson();
            System.out.println();
        }
    }

    public Schedule searchStudent(Student student) {
        Schedule Sch = new Schedule(new ArrayList<>());
        for (Lesson l : this.lessonsList) {
            if (l.hasStudent(student)) {
                Sch.lessonsList.add(l);
            }
        }
        return Sch;
    }

    public Schedule searchEducator(Educator educator) {
        Schedule Sch = new Schedule(new ArrayList<>());
        for (Lesson l : lessonsList) {
            if (l.hasEducator(educator)) {
                Sch.lessonsList.add(l);
            }
        }
        return Sch;
    }

    public void serializationInFile(String nameFile) throws IOException {
        Gson gson = new Gson();
        String stringSchedule = gson.toJson(this);
        try (FileWriter writer = new FileWriter(nameFile, false)) {
            writer.write(stringSchedule);
            writer.flush();
        }
    }

    public Schedule deserializationFromFile(String nameFile) throws IOException {
        Gson gson = new Gson();
        String result = "";
        try (FileReader reader = new FileReader(nameFile)) {
            int c;
            while ((c=reader.read())!=-1) {
                result = result.concat(String.valueOf((char)c));
            }
        }
        return gson.fromJson(result, Schedule.class);
    }
/*
    public static void main(String[] args) {
        Student student1 = new Student(1, "Student1");
        Schedule schedule1 = Schedule.searchStudent(student1);
        schedule1.printSchedule();
    }
*/

}

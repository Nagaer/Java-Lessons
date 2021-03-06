package com.reagan.lab;

import com.reagan.lab.task4.structureFaculty.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


class scheduleTest {
    @Test
    void standardTest() {
        Student student1 = new Student(1, "Student1");
        Student student2 = new Student(2, "Student2");

        Group group1 = new Group(1, Arrays.asList(student1), "Оп1");
        Group group2 = new Group(2, Arrays.asList(student2), "Оп2");

        Educator educator1 = new Educator(1, "Educator1");
        Educator educator2 = new Educator(2, "Educator2");

        Subject english = new Subject("English");
        Subject math = new Subject("Math");

        Lesson lesson1 = new Lesson(1, english, DateTime.CreateCouple(1, 1), group1, educator1);
        Lesson lesson2 = new Lesson(2, math, DateTime.CreateCouple(1, 2), group1, educator1);
        Lesson lesson3 = new Lesson(3, math, DateTime.CreateCouple(1, 1), group2, educator2);

        Schedule schedule = new Schedule(Arrays.asList(lesson1, lesson2, lesson3));

        Assertions.assertEquals(Arrays.asList(lesson1, lesson2), schedule.searchStudent(student1).lessonsList);
    }

    @Test
    void nullTest() {
        Student student1 = new Student(1, "Student1");
        Student student2 = new Student(2, "Student2");
        Student student3 = new Student(3, "Student3");

        Group group1 = new Group(1, Arrays.asList(student1), "Оп1");
        Group group2 = new Group(2, Arrays.asList(student2), "Оп2");

        Educator educator1 = new Educator(1, "Educator1");
        Educator educator2 = new Educator(2, "Educator2");

        Subject english = new Subject("English");
        Subject math = new Subject("Math");

        Lesson lesson1 = new Lesson(1, english, DateTime.CreateCouple(1, 1), group1, educator1);
        Lesson lesson2 = new Lesson(2, math, DateTime.CreateCouple(1, 2), group1, educator1);
        Lesson lesson3 = new Lesson(3, math, DateTime.CreateCouple(1, 1), group2, educator2);

        Schedule schedule = new Schedule(Arrays.asList(lesson1, lesson2, lesson3));

        Assertions.assertEquals(Arrays.asList(), schedule.searchStudent(student3).lessonsList);
    }

    @Test
    void educatorTest() {
        Student student1 = new Student(1, "Student1");
        Student student2 = new Student(2, "Student2");

        Group group1 = new Group(1, Arrays.asList(student1), "Оп1");
        Group group2 = new Group(2, Arrays.asList(student2), "Оп2");

        Educator educator1 = new Educator(1, "Educator1");
        Educator educator2 = new Educator(2, "Educator2");

        Subject english = new Subject("English");
        Subject math = new Subject("Math");

        Lesson lesson1 = new Lesson(1, english, DateTime.CreateCouple(1, 1), group1, educator1);
        Lesson lesson2 = new Lesson(2, math, DateTime.CreateCouple(1, 2), group1, educator1);
        Lesson lesson3 = new Lesson(3, math, DateTime.CreateCouple(1, 1), group2, educator2);

        Schedule schedule = new Schedule(Arrays.asList(lesson1, lesson2, lesson3));

        Assertions.assertEquals(Arrays.asList(lesson3), schedule.searchEducator(educator2).lessonsList);
    }
}

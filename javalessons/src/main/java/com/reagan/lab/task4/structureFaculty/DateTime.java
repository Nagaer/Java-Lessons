package com.reagan.lab.task4.structureFaculty;

enum Day {
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday,
    Sunday
}

public class DateTime {
    private Day dayOfWeek;
    private int hour, minute;
    public DateTime(Day dayOfWeek, int hour, int minute) {
        this.dayOfWeek = dayOfWeek;
        this.hour = hour;
        this.minute = minute;
    }
}

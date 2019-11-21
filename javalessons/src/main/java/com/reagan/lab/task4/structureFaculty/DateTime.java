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

    private DateTime(Day dayOfWeek, int hour, int minute) {
        this.dayOfWeek = dayOfWeek;
        this.hour = hour;
        this.minute = minute;
    }

    public static DateTime[] CreateCouple(int NumOfDay, int NumOfCouple) {
        Day day = null;
        if (NumOfDay==1)
            day = Day.Monday;
        else if (NumOfDay==2)
            day = Day.Tuesday;
        else if (NumOfDay==3)
            day = Day.Wednesday;
        else if (NumOfDay==4)
            day = Day.Thursday;
        else if (NumOfDay==5)
            day = Day.Friday;
        else if (NumOfDay==6)
            day = Day.Saturday;
        else if (NumOfDay==7)
            day = Day.Sunday;

        int hour1 = 0, hour2 = 0, minute1 = 0, minute2 = 0;
        if (NumOfCouple==1) {
            hour1 = 8;
            minute1 = 0;
            hour2 = 9;
            minute2 = 30;
        }
        else if (NumOfCouple==2) {
            hour1 = 9;
            minute1 = 40;
            hour2 = 11;
            minute2 = 10;
        }
        else if (NumOfCouple==3) {
            hour1 = 11;
            minute1 = 30;
            hour2 = 13;
            minute2 = 0;
        }
        else if (NumOfCouple==4) {
            hour1 = 13;
            minute1 = 10;
            hour2 = 14;
            minute2 = 40;
        }
        else if (NumOfCouple==5) {
            hour1 = 15;
            minute1 = 0;
            hour2 = 16;
            minute2 = 30;
        }
        else if (NumOfCouple==6) {
            hour1 = 16;
            minute1 = 40;
            hour2 = 18;
            minute2 = 10;
        }
        else if (NumOfCouple==7) {
            hour1 = 18;
            minute1 = 30;
            hour2 = 19;
            minute2 = 0;
        }
        else if (NumOfCouple==8) {
            hour1 = 19;
            minute1 = 10;
            hour2 = 20;
            minute2 = 40;
        }
        DateTime[] D = new DateTime[2];
        D[0] = new DateTime(day, hour1, minute1);
        D[1] = new DateTime(day, hour2, minute2);

        return D;
    }

    void printDayTime() {
        System.out.print(this.dayOfWeek);
    }

    void printTime() {
        System.out.print(this.hour);
        System.out.print(":");
        if (this.minute == 0)
            System.out.print("00");
        else
           System.out.print(this.minute);
    }

    public static void main(String[] args) {
        DateTime D = CreateCouple(1, 1)[1];
        D.printDayTime();
        D.printTime();
    }
}

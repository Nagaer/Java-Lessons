package com.reagan.lab.task5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.time.LocalDateTime.now;

public class LogTimeInputStream extends InputStream {
    InputStream in;
    LogTimeInputStream(InputStream in) {
        this.in = in;
    }
    @Override
    public void read() {
        System.out.println(now());
        in.read();
        System.out.println(now());
    }

    public static void main(String[] args) {
        try {
            FileInputStream in = new FileInputStream("TwoFile.txt");
            while (in.available() > 0) {
                System.out.println(in.read());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            try {
                throw e;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
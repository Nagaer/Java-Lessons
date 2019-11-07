package com.reagan.lab.task2;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class task2 { //Имена класса с большой буквы и говорящие
    public static Map<String, Integer> numwords(String text, Map<String, Integer> dict_text) {
        text = text.trim(); //Удаляет лишние пробелы (Теперь почему-то не удаляет)
        String[] list_text = text.split(" +"); //Правильно так listText, final если мы не меняем потом
        if (list_text[0].equals("")) {
            String[] new_list_text = new String[list_text.length-1];
            System.arraycopy(list_text, 1, new_list_text, 0, list_text.length-1);
            list_text = new_list_text;
        }
        for (String word : list_text) {
            if (dict_text.containsKey(word)) {
                dict_text.put(word, dict_text.get(word) + 1);
            }
            else {
                dict_text.put(word, 1);
            }
        }
        return dict_text;
    }

    public static Map<String, Integer> fileNumWords(final File folder, Map<String, Integer> dict_text) throws IOException {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                fileNumWords(fileEntry, dict_text);
            }
            else {
                Scanner in = new Scanner(fileEntry);
                while (in.hasNextLine()) {
                    numwords(in.nextLine(), dict_text);
                }
            }
        }
        return dict_text;
    }

    public static void main(String[] args) throws IOException {
        final File folder = new File("D://Idea Projects//javalessons//src//main//resources");
        Map<String, Integer> dict_text = new HashMap<>();
        System.out.println(fileNumWords(folder, dict_text));
    }
}

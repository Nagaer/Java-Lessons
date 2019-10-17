package com.reagan.lab.task2;

import java.util.*;

public class task2 { //Имена класса с большой буквы и говорящие
    public static Map<String, Integer> numwords(String text) {
        text = text.trim(); //Удаляет лишние пробелы
        String[] list_text = " ".split(text); //Правильно так listText, final если мы не меняем потом
        if (list_text[0].equals("")) {
            String[] new_list_text = new String[list_text.length-1];
            System.arraycopy(list_text, 1, new_list_text, 0, list_text.length-1);
            list_text = new_list_text;
        }
        Map<String, Integer> dict_text = new HashMap<String, Integer>();
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
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите текст: ");
        String text = in.nextLine();
        System.out.println(numwords(text).toString());
    }
}

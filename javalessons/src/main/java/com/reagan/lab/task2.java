package com.reagan.lab;

import com.sun.deploy.util.ArrayUtil;

import java.util.*;

public class task2 {
    static Map<String, Integer> numwords(String text) {
        String[] list_text = text.split(" +");
        if (list_text[0].equals("")) {
            String[] new_list_text = new String[list_text.length-1];
            System.arraycopy(list_text, 1, new_list_text, 0, list_text.length-1);
            list_text = new_list_text;
        }
        Map<String, Integer> dict_text = new HashMap<String, Integer>();
        int N = list_text.length;
        for (int i = 0; i < N; i++) {
            if (dict_text.containsKey(list_text[i])) {
                dict_text.put(list_text[i], dict_text.get(list_text[i]) + 1);
            }
            else {
                dict_text.put(list_text[i], 1);
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

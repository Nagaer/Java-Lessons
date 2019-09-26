package lab;

import java.util.*;

public class task2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите текст: ");
        String text = in.nextLine();
        String[] list_text = text.split(" ");
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
        System.out.println(dict_text.toString());
    }
}

package com.reagan.ind.Zerus;

import java.util.*;


public class Creature {
    //int hits; //Здоровье существа, опустится до 0 - погибнет
    int energy; //Энергия существа, опустится до 0 - начнёт отбавлять HP
    int cur; //Текущая команда для выполнения, указатель
    int status; //Состояние существа. 0 - не существует, 1 - жив, 2 - труп
    int x, y; //Координаты существа
    List<Integer> color;
    ArrayList<ArrayList<Integer>> mind; //Мозг - однонаправленный граф состояний

    public void step() {
        Random random = new Random();
        if (status==0 || status == 1) return; //Если мы не существуем или мертвы, ничего не делаем
        cur = 0; //Установились в состояние анализа
        while (cur != -1) {
            double q = Math.random();
        }
    }

    public void photosynthesis() {
        energy += (11 - (15*y/Environment.height));
    }
}

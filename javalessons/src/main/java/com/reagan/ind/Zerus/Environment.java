package com.reagan.ind.Zerus;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Environment extends JFrame {
    static int winWidth = 1670, winHeight = 870; //Размер для окна
    static int width, height; //Кол-во клеток в среде
    static Creature[][] creatures; //Сетка существ
    int generation;

    public Environment(int width, int height) {
        Environment.width = width;
        Environment.height = height;
        this.creatures = new Creature[width][height];

        setTitle("Зерус");
        setSize(new Dimension(winWidth, winHeight));
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize(), fSize = getSize();
        if (fSize.height > sSize.height) {fSize.height = sSize.height;}
        if (fSize.width > sSize.width) {fSize.width = sSize.width;}
        setLocation((sSize.width - fSize.width)/2, (sSize.height - fSize.height)/2);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.drawRect(49, 49, width * 4 + 1, height * 4 + 1);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (creatures[x][y] == null) { //Не существует
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(50 + x * 4, 50 + y * 4, 4,4);
                } else if (creatures[x][y].status == 1) { //Живой, рисуем его цветом
                    graphics.setColor(Color.GREEN);
                    graphics.fillRect(50 + x * 4, 50 + y * 4, 4,4);
                } else if (creatures[x][y].status == 2) { //Мёртв, рисуем серым
                    graphics.setColor(Color.GRAY);
                    graphics.fillRect(50 + x * 4, 50 + y * 4, 4,4);
                }
            }
        }

        graphics.setColor(Color.WHITE);
        graphics.fillRect(50, 30, 140, 16);
        graphics.setColor(Color.BLACK);
        graphics.drawString("Поколение: " + String.valueOf(generation), 54, 44);
    }

    public void run() {
        generation = 0;
        while (true) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (creatures[x][y] != null) {
                        creatures[x][y].step();
                    }
                }
            }
            generation++;
            if (generation%10 == 0) {
                paint(getGraphics());
            }
        }
    }

    public static void main(String[] args) {
        Environment simulation = new Environment(400, 200);
        simulation.createAdam();
        simulation.run();
    }

    private void createAdam() {
        Creature creature = new Creature();

        creature.cur = 0;
        creature.x = width/2;
        creature.y = height/2;
        creature.energy = 100;
        creature.color = Arrays.asList(0, 250, 0);
        creature.status = 1;
        creature.mind = new MarkovChain(0, 1, 1.0);
        creature.mind.addEdge(1, 2, 1.0);
        creature.mind.addEdge(2, -1, 1.0);

        creatures[creature.x][creature.y] = creature;
    }
}

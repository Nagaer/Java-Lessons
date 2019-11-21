package com.reagan.ind.Zerus;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Environment extends JFrame {
    static int width, height; //Кол-во клеток в среде
    static Creature[][] creatures; //Сетка существ
    private int generation, organic, population;

    public Environment(int width, int height) {
        Environment.width = width;
        Environment.height = height;
        creatures = new Creature[width][height];

        setTitle("Зерус");
        //Размер для окна
        int winWidth = 1870; //1670
        int winHeight = 970; //870
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
        int qx = 50, qy = 120;
        graphics.drawRect(qx-1, qy-1, width * 4 + 1, height * 4 + 1);

        organic = 0;
        population = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (creatures[x][y] == null) { //Не существует, рисуем белым
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(qx + x * 4, qy + y * 4, 4,4);
                } else if (creatures[x][y].status == 1) { //Живой, рисуем его цветом (временно зелёным)
                    graphics.setColor(Color.GREEN);
                    graphics.fillRect(qx + x * 4, qy + y * 4, 4,4);
                    population++;
                } else if (creatures[x][y].status == 2) { //Мёртв, рисуем серым
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(qx + x * 4, qy + y * 4, 4,4);
                    graphics.setColor(Color.GRAY);
                    graphics.fillRect(qx + x * 4, qy + y * 4, 3,3);
                    organic++;
                }
            }
        }

        graphics.setColor(Color.WHITE);
        graphics.fillRect(10, 40, 100, 16);
        graphics.setColor(Color.BLACK);
        graphics.drawString("Поколение: " + generation, 14, 54);

        graphics.setColor(Color.WHITE);
        graphics.fillRect(10, 60, 100, 16);
        graphics.setColor(Color.BLACK);
        graphics.drawString("Органика: " + organic, 14, 74);

        graphics.setColor(Color.WHITE);
        graphics.fillRect(10, 80, 100, 16);
        graphics.setColor(Color.BLACK);
        graphics.drawString("Популяция: " + population, 14, 94);
    }

    private void run() {
        generation = 0;
        while (generation < 1000000) {
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
        Environment simulation = new Environment(445, 205);
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

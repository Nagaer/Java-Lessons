package com.reagan.ind.Zerus;

//spring-boot-starter-web

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Environment extends JFrame {
    static int width, height; //Кол-во клеток в среде
    static Creature[][] creatures; //Сетка существ
    private int generation, organic, population;
    private Creature bestCreature;

    public Environment(int width, int height) {
        Environment.width = width;
        Environment.height = height;
        creatures = new Creature[width][height];

        setTitle("Зерус");
        int winWidth = 1870;
        int winHeight = 970;
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
        int cordX = 45, cordY = 120;
        graphics.drawRect(cordX-1, cordY-1, width * 4 + 1, height * 4 + 1);

        organic = 0;
        population = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if ((creatures[x][y] == null) || (creatures[x][y].status == 0)) { //Не существует, рисуем белым
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(cordX + x * 4, cordY + y * 4, 4,4);
                } else if (creatures[x][y].status == 1) { //Живой, рисуем его цветом (временно зелёным)
                    graphics.setColor(Color.BLACK);
                    graphics.fillRect(cordX + x * 4, cordY + y * 4, 4,4);

                    int green = (int) (creatures[x][y].color_G - ((creatures[x][y].color_G*creatures[x][y].energy)/2000));
                    if (green < 0)
                        green = 0;
                    else if (green > 255)
                        green = 255;
                    int blue = (int) (creatures[x][y].color_B*0.8 - ((creatures[x][y].color_B*creatures[x][y].mineral)/2000));
                    graphics.setColor(new Color(creatures[x][y].color_R, green, blue));
                    graphics.fillRect(cordX + x * 4 + 1, cordY + y * 4 + 1, 3,3);
                    population++;
                } else if (creatures[x][y].status == 2) { //Мёртв, рисуем серым
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(cordX + x * 4, cordY + y * 4, 4,4);
                    graphics.setColor(Color.GRAY);
                    graphics.fillRect(cordX + x * 4, cordY + y * 4, 3,3);
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
        graphics.fillRect(10, 80, 120, 16);
        graphics.setColor(Color.BLACK);
        graphics.drawString("Популяция: " + population, 14, 94);
    }

    private void run() {
        generation = 0;
        while (generation < 10000) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (creatures[x][y] != null) {
                        creatures[x][y].step();
                    }
                }
            }
            generation++;
            if (generation % 10 == 0) {
                paint(getGraphics());
            }
            updateBestCreature();
        }
        //bestCreature.printCreature();
    }

    public static void main(String[] args) {
        Environment simulation = new Environment(445, 205);
        simulation.createAdam();
        simulation.run();
    }

    private void createAdam() {
        Creature creature = new Creature();

        creature.x = width/2;
        creature.y = height/2;
        creature.energy = 990;
        creature.mineral = 0;
        creature.direction = 4;
        creature.color_R = 170;
        creature.color_G = 170;
        creature.color_B = 170;
        creature.status = 1;
        List<Integer> listMind = new ArrayList<>();
        for (int i = 0; i < 64; ++i) {
            listMind.add(25);
        }
        creature.mind = new Mind(listMind);
        creature.points = 0;

        creatures[creature.x][creature.y] = creature;
        bestCreature = creature;
    }

    private void updateBestCreature() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (Environment.creatures[i][j] != null) {
                    if (Environment.creatures[i][j].points > bestCreature.points) {
                        bestCreature = Environment.creatures[i][j];
                    }
                }
            }
        }
    }
}

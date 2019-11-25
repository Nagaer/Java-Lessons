package com.reagan.ind.Zerus;

import java.util.*;


public class Creature {
    //int hits; //Здоровье существа, опустится до 0 - погибнет
    int energy; //Энергия существа, опустится до 0 - начнёт отбавлять HP
    int cur; //Текущая команда для выполнения, указатель
    int status; //Состояние существа. 0 - не существует, 1 - жив, 2 - труп
    int x, y; //Координаты существа
    List<Integer> color;
    MarkovChain mind; //Мозг - однонаправленный граф состояний

    void step() {
        if (status == 0 || status == 2) return; //Если мы не существуем или мертвы, ничего не делаем
        cur = 0;
        while (cur != -1) {
            execute(cur);
            cur = mind.branching(cur);
            //System.out.println(cur);
        }
		energy -= 3;
        if (energy > 1000) {
            mitosis();
        }
        if (energy < 0) {
            status = 2;
        }
    }

    private Creature copy() {
        Creature copyCreature = new Creature();
        copyCreature.energy = this.energy;
        copyCreature.cur = this.cur;
        copyCreature.status = this.status;
        copyCreature.x = this.x;
        copyCreature.y = this.y;
        copyCreature.color = this.color;
        copyCreature.mind = this.mind;
        return copyCreature;


    }

    private void execute(int num) {
        switch (num) {
            case 11:
                absoluteMove(this.x, this.y-1);
                System.out.println(11);
                break;
            case 12:
                absoluteMove(this.x+1, this.y-1);
                System.out.println(12);
                break;
            case 13:
                absoluteMove(this.x+1, this.y);
                System.out.println(13);
                break;
            case 14:
                absoluteMove(this.x+1, this.y+1);
                System.out.println(14);
                break;
            case 15:
                absoluteMove(this.x, this.y+1);
                System.out.println(15);
                break;
            case 16:
                absoluteMove(this.x-1, this.y+1);
                System.out.println(16);
                break;
            case 17:
                absoluteMove(this.x-1, this.y);
                System.out.println(17);
                break;
            case 18:
                absoluteMove(this.x-1, this.y-1);
                System.out.println(18);
                break;
            case 20:
                photosynthesis();
                break;
        }
    }

    private void absoluteMove(int newX, int newY) {
        //System.out.println("Time to move!");
        if ((this.x == newX) && (this.y == newY)) {
            System.out.println("Совпадают текущий и новый набор координат");
        }
        if ((newX >= 0) && (newY >= 0) && (newX <= Environment.width - 1) && (newY <= Environment.height - 1)) {
            if (Environment.creatures[newY][newY] != null) {
                return;
            } else {
                Environment.creatures[newX][newY] = this;
                Environment.creatures[this.x][this.y] = null;
                this.x = newX;
                this.y = newY;
                /*
                int oldX = this.x;
                int oldY = this.y;
                this.x = newX;
                this.y = newY;
                //Environment.creatures[newX][newY] = this.copy();
                //Environment.creatures[oldX][oldY].status = 0;
                if (Environment.creatures[oldX][oldY] != null) {
                    System.out.println("Я этого пидора в Химках видал. Движением делится! Сча мы это исправим");
                    if ((oldX == newX) && (oldY == newY)) {
                        System.out.println("Совпадают старый и новый набор координат11");
                    }
                    if ((oldX == this.x) && (oldY == this.y)) {
                        System.out.println("Совпадают старый и новый набор координат22");
                    }
                    Environment.creatures[oldX][oldY].status = 0;
                }
                */
            }
        }
    }

    private void relativeMove(int x, int y) {
        absoluteMove(this.x+x, this.y+y);
    }

    private String surrounded() {
        List<String> directions = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));
        if (x<=0) {
            directions.remove("6");
            directions.remove("7");
            directions.remove("8");
        }
        if (y<=0) {
            directions.remove("8");
            directions.remove("1");
            directions.remove("2");
        }
        if (x>=Environment.width - 1) {
            directions.remove("2");
            directions.remove("3");
            directions.remove("4");
        }
        if (y>=Environment.height - 1) {
            directions.remove("4");
            directions.remove("5");
            directions.remove("6");
        }
        if (directions.contains("1"))
            if (Environment.creatures[x][y-1] != null)
                directions.remove("1");
        if (directions.contains("2"))
            if (Environment.creatures[x+1][y-1] != null)
                directions.remove("2");
        if (directions.contains("3"))
            if (Environment.creatures[x+1][y] != null)
                directions.remove("3");
        if (directions.contains("4"))
            if (Environment.creatures[x+1][y+1] != null)
                directions.remove("4");
        if (directions.contains("5"))
            if (Environment.creatures[x][y+1] != null)
                directions.remove("5");
        if (directions.contains("6"))
            if (Environment.creatures[x-1][y+1] != null)
                directions.remove("6");
        if (directions.contains("7"))
            if (Environment.creatures[x-1][y] != null)
                directions.remove("7");
        if (directions.contains("8"))
            if (Environment.creatures[x-1][y-1] != null)
                directions.remove("8");
        if (directions.size() == 0) {
            return "0";
        }
        int rnd = new Random().nextInt(directions.size());
        return directions.get(rnd);
    }

    private void photosynthesis() {
        energy += 11 - (15*y/Environment.height) + 30; //Ускоренный рост для тестов
    }

    private void mitosis() {
        Creature newCreature = new Creature();
        int incX = 0, incY = 0;
        switch (surrounded()) {
            case "0":
                status = 2;
                return;
            case "1":
                incX = 0;
                incY = -1;
                break;
            case "2":
                incX = 1;
                incY = -1;
                break;
            case "3":
                incX = 1;
                incY = 0;
                break;
            case "4":
                incX = 1;
                incY = 1;
                break;
            case "5":
                incX = 0;
                incY = 1;
                break;
            case "6":
                incX = -1;
                incY = 1;
                break;
            case "7":
                incX = -1;
                incY = 0;
                break;
            case "8":
                incX = -1;
                incY = -1;
                break;
        }
        this.energy /= 2;
        newCreature.cur = 0;
        newCreature.x = this.x + incX;
        newCreature.y = this.y + incY;
        newCreature.energy = this.energy/2;
        newCreature.color = this.color;
        newCreature.status = 1;
        newCreature.mind = this.mind;
        newCreature.mind.mutate();
        //this.mind.mutate();

        Environment.creatures[newCreature.x][newCreature.y] = newCreature;
    }
}

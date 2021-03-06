package com.reagan.ind.Zerus;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Creature {
    int energy; //Энергия существа, опустится до 0 - смерть
    int mineral; //Минералы существа
    int status; //Состояние существа. 0 - не существует, 1 - жив, 2 - труп
    int x, y; //Координаты существа
    int direction; //Направление существа
    int color_R, color_G, color_B; //Цвета существа
    int points; //Прототип подсчёта очков выживаемости
    Mind mind; //Мозг
    private Creature prev, next; //Ссылки на предыдущего и следующего существа в цепочке

    void step() {
        if (status == 0 || status == 2) return; //Если мы не существуем или мертвы, ничего не делаем
        for (int i = 0; i < 15; i++) {
            int command = mind.genom.get(mind.cur);

            if (command == 23) { //Относительная смена направления
                int param = mind.getParam() % 8;
                int newDirection = direction + param;
                if (newDirection >= 8)
                    newDirection -= 8;
                direction = newDirection;
                mind.incCur(2);
            }
            if (command == 24) { //Абсолютная смена направления
                direction = mind.getParam() % 8;
                mind.incCur(2);
            }
            if (command == 25) { //Фотосинтез
                photosynthesis();
                mind.incCur(1);
                break;
            }
            if (command == 26) { //Относительный шаг
                if (isMulti() == 0) {
                    int direction = mind.getParam() % 8;
                    mind.indirectIncCur(creatureMove(direction, false));
                }
                break;
            }
            if (command == 27) { //Абсолютный шаг
                if (isMulti() == 0) {
                    int direction = mind.getParam() % 8;
                    mind.indirectIncCur(creatureMove(direction, true));
                }
                break;
            }
            if (command == 28) { //Относительный укус
                int direction = mind.getParam() % 8;
                mind.indirectIncCur(eat(direction, false));
                break;
            }
            if (command == 29) { //Абсолютный укус
                int direction = mind.getParam() % 8;
                mind.indirectIncCur(eat(direction, true));
                break;
            }
            if (command == 30) { //Относительный взгляд
                int direction = mind.getParam() % 8;
                mind.indirectIncCur(see(direction, false));
                break;
            }
            if (command == 31) { //Абсолютный взгляд
                int direction = mind.getParam() % 8;
                mind.indirectIncCur(see(direction, true));
                break;
            }
            if (command == 32) { //Относительный делёж
                int direction = mind.getParam() % 8;
                mind.indirectIncCur(care(direction, false));
                break;
            }
            if (command == 33) { //Абсолютный делёж
                int direction = mind.getParam() % 8;
                mind.indirectIncCur(care(direction, true));
                break;
            }
            if (command == 34) { //Относительный подарок
                int direction = mind.getParam() % 8;
                mind.indirectIncCur(give(direction, false));
                break;
            }
            if (command == 35) { //Абсолютный подарок
                int direction = mind.getParam() % 8;
                mind.indirectIncCur(give(direction, true));
                break;
            }
            if (command == 36) { //Развернуться по горизонтали
                if (Math.random() < 0.5)
                    direction = 3;
                else
                    direction = 7;
                mind.incCur(1);
            }
            if (command == 37) {
                int param = mind.getParam() * Environment.height / mind.MIND_SIZE;
                if (y < param)
                    mind.incCur(2);
                else
                    mind.incCur(3);
            }
            if (command == 38) {
                int param = mind.getParam() * 1000 / mind.MIND_SIZE;
                if (energy < param)
                    mind.incCur(2);
                else
                    mind.incCur(3);
            }
            if (command == 39) {
                int param = mind.getParam() * 1000 / mind.MIND_SIZE;
                if (mineral < param)
                    mind.incCur(2);
                else
                    mind.incCur(3);
            }
            if (command == 40) { //Деление многоклеточного
                int q = isMulti();
                if (q == 3)
                    mitosis();
                else
                    multiMitosis();
                mind.incCur(1);
                break;
            }
            if (command == 41) { //Деление
                int q = isMulti();
                if ((q == 3) || (q == 0))
                    mitosis();
                else
                    multiMitosis();
                mind.incCur(1);
                break;
            }
            if (command == 42) { //Закрыт ли путь
                if (findDirection() == 8)
                    mind.indirectIncCur(1);
                else
                    mind.indirectIncCur(2);
            }
            if (command == 43) { //Растёт ли энергия
                mind.indirectIncCur(energyGrow());
            }
            if (command == 44) { //Растут ли минералы
                if (y > Environment.height / 2)
                    mind.indirectIncCur(1);
                else
                    mind.indirectIncCur(2);
            }
            if (command == 45) { //Является ли многоклеточным
                mind.indirectIncCur(isMulti());
            }
            if (command == 46) { //Преобразование минералов
                mineralisis();
                mind.incCur(1);
                break;
            }
            if (command == 47) { //Мутировать без деления
                mind.mutate();
                mind.mutate();
                mind.incCur(1);
                break;
            }
            if (command == 48) { //Атака на геном
                genomAttack();
                mind.incCur(1);
                break;
            }
        }

        int command = mind.genom.get(mind.cur); //Безусловный переход на случай несовпадения ни с одной из команд
        if (((command >= 0) && (command <= 22)) || ((command >= 49) && (command <= 63))) {
            mind.incCur(command);
        }

        if (status == 1) { //Если бот всё ещё жив после команд
            if (isMulti() == 3) {
                int commonMinerals = mineral + prev.mineral + next.mineral;
                mineral = commonMinerals/3;
                prev.mineral = commonMinerals/3;
                next.mineral = commonMinerals/3;

                if ((prev.isMulti() == 3) && (next.isMulti()==3)) {
                    int commonEnergy = energy + prev.energy + next.energy;
                    energy = commonEnergy/3;
                    prev.energy = commonEnergy/3;
                    next.energy = commonEnergy/3;
                }
            } else if (isMulti() == 1) {
                if (prev.isMulti() == 3) {
                    int commonEnergy = energy + prev.energy;
                    energy = commonEnergy*3/4;
                    prev.energy = commonEnergy/4;
                }
            } else if (isMulti() == 2) {
                if (next.isMulti() == 3) {
                    int commonEnergy = energy + next.energy;
                    energy = commonEnergy * 3 / 4;
                    next.energy = commonEnergy / 4;
                }
            }
            if (energy >= 1000) {
                if ((isMulti() == 1) || (isMulti() == 2))
                    multiMitosis();
                else
                    mitosis();
            }

            energy -= 3;

            if (energy < 0) {
                die();
            }

            if (Environment.height > 2) {
                mineral++;
                if (y > Environment.height * 2 / 3) {
                    mineral++;
                }
                if (y > Environment.height * 5 / 6) {
                    mineral++;
                }
                if (mineral > 999) {
                    mineral = 999;
                }
            }
            points += 10;
        }
    }

    private void move(int newX, int newY) {
        Environment.creatures[newX][newY] = this;
        Environment.creatures[this.x][this.y] = null;
        this.x = newX;
        this.y = newY;
    }

    private void delete() {
        Environment.creatures[this.x][this.y] = null;
    }

    private int creatureMove(int direction, boolean flag) { //flag=true=absolute, false=relative
        int newX, newY;
        if (flag) {
            newX = xFromVectorA(direction);
            newY = yFromVectorA(direction);
        } else {
            newX = xFromVectorR(direction);
            newY = yFromVectorR(direction);
        }

        if ((newY < 0) || (newY >= Environment.height)) { //Если там стена
            return 3;
        }
        if (Environment.creatures[newX][newY] == null) { //Если там пусто и можно двигаться
            move(newX, newY);
            return 2;
        }
        if ((Environment.creatures[newX][newY].status == 0) || (Environment.creatures[newX][newY].status == 2)) {
            return 4;
        }
        return 5;
    }

    private void die() {
        status = 2;
        if (prev != null)
            prev.next = null;
        if (next != null)
            next.prev = null;
        prev = null;
        next = null;
    }

    private void photosynthesis() {
        int min;
        if (mineral < 100) {
            min = 0;
        } else if (mineral < 400) {
            min = 1;
        } else {
            min = 2;
        }
        int enr = 14 - (15 * y / Environment.height) + min;
        if (enr > 0) {
            energy += enr;
            goGreen(enr);
        }
    }

    private void mineralisis() {
        if (mineral > 100) {
            energy += 400;
            mineral -= 100;
            goBlue(100);
        } else {
            energy += 4 * mineral;
            mineral = 0;
            goBlue(4 * mineral);
        }
    }

    private void mitosis() {
        this.energy -= 150;
        if (this.energy < 0)
            return;
        int n = findDirection();
        if (n == 8) {
            status = 2;
            return;
        }
        Creature newCreature = new Creature();
        int newX = xFromVectorR(n), newY = yFromVectorR(n);
        newCreature.x = newX;
        newCreature.y = newY;
        newCreature.energy = this.energy / 2;
        this.energy /= 2;
        newCreature.mineral = this.mineral / 2;
        this.mineral /= 2;
        newCreature.direction = (int) (Math.random() * 8);
        newCreature.color_R = color_R;
        newCreature.color_G = color_G;
        newCreature.color_B = color_B;
        newCreature.status = 1;
        List<Integer> listMind = new ArrayList<>();
        listMind.addAll(this.mind.genom);
        newCreature.mind = new Mind(listMind);
        newCreature.mind.cur = 0;
        if (Math.random() < 0.25)
            newCreature.mind.mutate();

        Environment.creatures[newCreature.x][newCreature.y] = newCreature;
    }

    private void multiMitosis() {
        if ((prev != null) && (next != null))
            return;
        energy -= 150;
        if (energy < 0)
            return;
        int n = findDirection();
        if (n == 8) {
            energy = 0;
            return;
        }
        Creature newCreature = new Creature();
        int newX = xFromVectorR(n), newY = yFromVectorR(n);
        newCreature.x = newX;
        newCreature.y = newY;
        newCreature.energy = this.energy / 2;
        this.energy /= 2;
        newCreature.mineral = this.mineral / 2;
        this.mineral /= 2;
        newCreature.direction = (int) (Math.random() * 8);
        newCreature.color_R = color_R;
        newCreature.color_G = color_G;
        newCreature.color_B = color_B;
        newCreature.status = 1;
        List<Integer> listMind = new ArrayList<>();
        listMind.addAll(this.mind.genom);
        newCreature.mind = new Mind(listMind);
        newCreature.mind.cur = 0;
        if (Math.random() < 0.25)
            newCreature.mind.mutate();
        if (next == null) {
            next = newCreature;
            newCreature.prev = this;
            newCreature.next = null;
        } else {
            prev = newCreature;
            newCreature.prev = null;
            newCreature.next = this;
        }
    }

    private int isMulti() {
        int q = 0;
        if (prev != null)
            q += 1;
        if (next != null)
            q += 2;
        return q;
    }

    private int energyGrow() {
        int min;
        if (mineral < 100) {
            min = 0;
        } else if (mineral < 400) {
            min = 1;
        } else {
            min = 2;
        }
        int enr = 14 - (15 * y / Environment.height) + min;
        if (enr >= 3)
            return 1;
        else
            return 2;
    }

    private int eat(int direction, boolean flag) {
        energy -= 4;
        int eatX, eatY;
        if (flag) {
            eatX = xFromVectorA(direction);
            eatY = yFromVectorA(direction);
        } else {
            eatX = xFromVectorR(direction);
            eatY = yFromVectorR(direction);
        }
        if ((eatY < 0) || (eatY >= Environment.height))
            return 3;
        if (Environment.creatures[eatX][eatY] == null)
            return 2;
        if (Environment.creatures[eatX][eatY].status == 2) {
            Environment.creatures[eatX][eatY].delete();
            energy += 100;
            goRed(100);
            return 4;
        }
        int minDef = Environment.creatures[eatX][eatY].mineral,
                enrDef = Environment.creatures[eatX][eatY].energy;
        if (mineral >= minDef) {
            mineral -= minDef;
            int profit = 100 + enrDef / 2;
            Environment.creatures[eatX][eatY].delete();
            energy += profit;
            goRed(profit);
            return 5;
        }
        minDef -= mineral;
        Environment.creatures[eatX][eatY].mineral = minDef;
        mineral = 0;
        if (energy >= 2 * minDef) {
            Environment.creatures[eatX][eatY].delete();
            int profit = 100 + (enrDef / 2) - 2 * minDef;
            if (profit < 0)
                profit = 0;
            energy += profit;
            goRed(profit);
            return 5;
        }
        Environment.creatures[eatX][eatY].mineral = minDef - (energy / 2);
        energy = 0;
        return 5;
    }

    private int see(int direction, boolean flag) {
        int seeX, seeY;
        if (flag) {
            seeX = xFromVectorA(direction);
            seeY = yFromVectorA(direction);
        } else {
            seeX = xFromVectorR(direction);
            seeY = yFromVectorR(direction);
        }
        if ((seeY < 0) || (seeY >= Environment.height))
            return 3;
        if (Environment.creatures[seeX][seeY] == null)
            return 2;
        if (Environment.creatures[seeX][seeY].status == 2)
            return 4;
        return 5;
    }

    private int care(int direction, boolean flag) {
        int careX, careY;
        if (flag) {
            careX = xFromVectorA(direction);
            careY = yFromVectorA(direction);
        } else {
            careX = xFromVectorR(direction);
            careY = yFromVectorR(direction);
        }
        if ((careY < 0) || (careY >= Environment.height))
            return 3;
        if (Environment.creatures[careX][careY] == null)
            return 2;
        if (Environment.creatures[careX][careY].status == 2)
            return 4;

        int enr0 = energy;
        int enr1 = Environment.creatures[careX][careY].energy;
        int min0 = mineral;
        int min1 = Environment.creatures[careX][careY].mineral;
        if (enr0 > enr1) {
            int enr = (enr0 - enr1) / 2;
            energy -= enr;
            Environment.creatures[careX][careY].energy += enr;
        }
        if (min0 > min1) {
            int min = (min0 - min1) / 2;
            mineral -= min;
            Environment.creatures[careX][careY].mineral += min;
        }
        return 5;
    }

    private int give(int direction, boolean flag) {
        int giveX, giveY;
        if (flag) {
            giveX = xFromVectorA(direction);
            giveY = yFromVectorA(direction);
        } else {
            giveX = xFromVectorR(direction);
            giveY = yFromVectorR(direction);
        }
        if ((giveY < 0) || (giveY >= Environment.height))
            return 3;
        if (Environment.creatures[giveX][giveY] == null)
            return 2;
        if (Environment.creatures[giveX][giveY].status == 2)
            return 4;

        int enr0 = energy / 4;
        energy -= enr0;
        Environment.creatures[giveX][giveY].energy += enr0;

        int min0 = mineral;
        if (min0 > 3) {
            int min = min0 / 4;
            mineral -= min;
            Environment.creatures[giveX][giveY].mineral += min;
            if (Environment.creatures[giveX][giveY].mineral > 999)
                Environment.creatures[giveX][giveY].mineral = 999;
        }
        return 5;
    }

    private void genomAttack() {
        int newX = xFromVectorR(0),
                newY = yFromVectorR(0);
        if ((newY >= 0) && (newY < Environment.height) && (Environment.creatures[newX][newY] != null)) {
            if (Environment.creatures[newX][newY].status == 1) {
                energy -= 10;
                if (energy > 0) {
                    Environment.creatures[newX][newY].mind.mutate();
                }
            }
        }
    }

    private void goGreen(int num) {
        color_G += num;
        if (color_G + num > 255)
            color_G = 255;
        int nm = num / 2;
        color_R -= nm;
        if (color_R < 0)
            color_R = 0;
        color_B -= nm;
        if (color_B < 0)
            color_R += color_B;
        if (color_R < 0)
            color_R = 0;
        if (color_B < 0)
            color_B = 0;
    }

    private void goBlue(int num) {
        color_B += num;
        if (color_B + num > 255)
            color_B = 255;
        int nm = num / 2;
        color_G -= nm;
        if (color_G < 0)
            color_R += color_G;
        color_R -= nm;
        if (color_R < 0)
            color_G += color_R;
        if (color_R < 0)
            color_R = 0;
        if (color_G < 0)
            color_G = 0;
    }

    private void goRed(int num) {
        color_R += num;
        if (color_R + num > 255)
            color_R = 255;
        int nm = num / 2;
        color_G -= nm;
        if (color_G < 0)
            color_B -= color_G;
        color_B -= nm;
        if (color_B < 0)
            color_G += color_B;
        if (color_B < 0)
            color_B = 0;
        if (color_G < 0)
            color_G = 0;
    }

    private int xFromVectorR(int n) {
        int xRes = x;
        n += direction;
        if (n >= 8) {
            n -= 8;
        }
        if (n == 0 || n == 6 || n == 7) {
            xRes--;
            if (xRes == -1) {
                xRes = Environment.width - 1;
            }
        } else if (n == 2 || n == 3 || n == 4) {
            xRes++;
            if (xRes == Environment.width) {
                xRes = 0;
            }
        }
        return xRes;
    }

    private int xFromVectorA(int n) {
        int xRes = x;
        if (n == 0 || n == 6 || n == 7) {
            xRes--;
            if (xRes == -1) {
                xRes = Environment.width - 1;
            }
        } else if (n == 2 || n == 3 || n == 4) {
            xRes++;
            if (xRes == Environment.width) {
                xRes = 0;
            }
        }
        return xRes;
    }

    private int yFromVectorR(int n) {
        int yRes = y;
        n += direction;
        if (n >= 8) {
            n -= 8;
        }
        if (n == 0 || n == 1 || n == 2) {
            yRes--;
        } else if (n == 4 || n == 5 || n == 6) {
            yRes++;
        }
        return yRes;
    }

    private int yFromVectorA(int n) {
        int yRes = y;
        if (n == 0 || n == 1 || n == 2) {
            yRes--;
        } else if (n == 4 || n == 5 || n == 6) {
            yRes++;
        }
        return yRes;
    }

    private int findDirection() {
        for (int i = 0; i < 8; ++i) {
            int dirX = xFromVectorR(i);
            int dirY = yFromVectorR(i);
            if ((dirY >= 0) && (dirY < Environment.height))
                if (Environment.creatures[dirX][dirY] == null)
                    return i;
        }
        return 8;
    }

    private String serialization() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void serializationInFile(String nameFile) throws IOException {
        Gson gson = new Gson();
        String stringCreature = gson.toJson(this);
        try (FileWriter writer = new FileWriter(nameFile, false)) {
            writer.write(stringCreature);
            writer.flush();
        }
    }

    public Creature deserializationFromFile(String nameFile) throws IOException {
        Gson gson = new Gson();
        String result = "";
        try (FileReader reader = new FileReader(nameFile)) {
            int c;
            while ((c = reader.read()) != -1) {
                result = result.concat(String.valueOf((char) c));
            }
        }
        return gson.fromJson(result, Creature.class);
    }

    public void printCreature() {
        System.out.println(this.serialization());
    }

}
package com.reagan.ind.Zerus;

import java.util.*;


public class Creature {
    //int hits; //Здоровье существа, опустится до 0 - погибнет
    int energy; //Энергия существа, опустится до 0 - начнёт отбавлять HP
    int mineral;
    int status; //Состояние существа. 0 - не существует, 1 - жив, 2 - труп
    int x, y; //Координаты существа
    int direction;
    int color_R, color_G, color_B;
    List<Integer> color;
    Mind mind; //Мозг - однонаправленный граф состояний

    void step() {
        if (status == 0 || status == 2) return; //Если мы не существуем или мертвы, ничего не делаем
        for (int i = 0; i < 15; i++) {
            int command = mind.genom.get(mind.cur);

            if (command == 23) { //Относительная смена направления
                int param = mind.getParam() % 8;
                int newDirection = direction+param;
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
                int direction = mind.getParam() % 8;
                mind.indirectIncCur(creatureMove(direction, false));
                break;
            }
            if (command == 27) { //Абсолютный шаг
                int direction = mind.getParam() % 8;
                mind.indirectIncCur(creatureMove(direction, true));
                break;
            }
            if (command == 41) {
                mitosis();
                mind.incCur(1);
                break;
            }
            if (command == 47) { //Добыча минералов
                mineralisis();
                mind.incCur(1);
                break;
            }
        }

        int command = mind.genom.get(mind.cur); //Безусловный переход на случай несовпадения ни с одной из команд
        if ((command != 47) || (command != 41) || ((command>=23) && (command<=27))) {
            mind.incCur(command);
        }

        if (energy > 1000) {
            mitosis();
        }

		energy -= 3;

        if (energy < 0) {
            status = 2;
        }

        if (Environment.height > 2) {
            mineral++;
            if (y > Environment.height*2/3) {
                mineral++;
            }
            if (y > Environment.height*5/6) {
                mineral++;
            }
            if (mineral > 999) {
                mineral = 999;
            }
        }
    }

    public void move(int newX, int newY) {
        Environment.creatures[newX][newY] = this;
        Environment.creatures[this.x][this.y] = null;
        this.x = newX;
        this.y = newY;
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

        int enr = 14 - (15*y/Environment.height); //Ускоренный рост для тестов
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
            energy += 4*mineral;
            mineral = 0;
            goBlue(100);
        }
    }

    private void mitosis() {
        this.energy -= 150;
        if (this.energy<0)
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
        newCreature.energy = this.energy/2;
        this.energy /= 2;
        newCreature.mineral = this.mineral/2;
        this.mineral /= 2;
        newCreature.direction = (int) (Math.random()*8);
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

    private void goGreen(int num) {
        color_G += num;
        if (color_G + num > 255)
            color_G = 255;
        int nm = num/2;
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
        int nm = num/2;
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
        int nm = num/2;
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

    public int xFromVectorR(int n) {
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

    public int xFromVectorA(int n) {
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

    public int yFromVectorR(int n) {
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

    public int yFromVectorA(int n) {
        int yRes = y;
        if (n == 0 || n == 1 || n == 2) {
            yRes--;
        } else if (n == 4 || n == 5 || n == 6) {
            yRes++;
        }
        return yRes;
    }

    public int findDirection() {
        for (int i = 0; i < 8; ++i) {
            int dirX = xFromVectorR(i);
            int dirY = yFromVectorR(i);
            if ((dirY >= 0) && (dirY < Environment.height))
                if (Environment.creatures[dirX][dirY] == null)
                    return i;
        }
        return 8;
    }
}

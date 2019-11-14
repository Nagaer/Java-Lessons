package com.reagan.ind.SpaceTrek;

public class Hull extends Module {
    private int volume, armor;
    @Override
    public void print() {
        System.out.println(this.name);
        System.out.println(this.hits);
        System.out.println(this.volume);
        System.out.println(this.armor);
    }
}

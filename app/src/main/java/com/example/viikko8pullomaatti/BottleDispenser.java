package com.example.viikko8pullomaatti;

import android.widget.TextView;

import java.util.ArrayList;

public class BottleDispenser {

    private static String uusinnimi = null;
    private static String uusinkoko = null;
    private static float uusinhinta = 0;
    private static BottleDispenser bd = null;

    public static BottleDispenser getInstance() {
        if (bd == null) bd = new BottleDispenser();
        return bd;
    }

    ArrayList<Bottle> bottle_list = new ArrayList<Bottle>();
    private int bottles;
    private float money;

    public BottleDispenser() {
        bottles = 6;
        money = (float)0;

//		bottle_list = new Bottle[bottles];
        for (int i = 0; i<bottles;i++) {
            Bottle bottle = new Bottle();
            bottle_list.add(bottle);
        }
        bottle_list.get(1).name = "Pepsi";
        bottle_list.get(1).volume = "1.5";
        bottle_list.get(1).price = (float)2.2;
        bottle_list.get(2).name = "Coca-Cola";
        bottle_list.get(2).volume = "0.5";
        bottle_list.get(2).price = (float)2.0;
        bottle_list.get(3).name = "Coca-Cola";
        bottle_list.get(3).volume = "1.5";
        bottle_list.get(3).price = (float)2.5;
        bottle_list.get(4).name = "Fanta";
        bottle_list.get(4).volume = "0.5";
        bottle_list.get(4).price = (float)1.95;
        bottle_list.get(5).name = "Fanta";
        bottle_list.get(5).volume = "0.5";
        bottle_list.get(5).price = (float)1.95;

    }



    public float addMoney(int i) {
        money += (i);
        return money;
    }

    public float getMoney() {
        return money;
    }

    public Bottle buyBottle(String tuote, String koko) {
        for (int i = 0; i < bottle_list.size(); i++) {
            if (bottle_list.get(i).name == tuote && bottle_list.get(i).volume == koko) {
                if (money < bottle_list.get(i).price) {
                    Bottle pullo = new Bottle();
                    pullo.name = "eirahaa";
                    return pullo;
                } else {
                    money -= bottle_list.get(i).price;
                    Bottle pullo = bottle_list.get(i);
                    bottle_list.remove(i);
                    uusinnimi = pullo.name;
                    uusinkoko = pullo.volume;
                    uusinhinta = pullo.price;
                    return pullo;
                }
            }
        }
        return null;
    }
    public void returnMoney() {
        System.out.print("Klink klink. Sinne menivät rahat! Rahaa tuli ulos ");
        System.out.printf("%.2f", money);
        System.out.print("€\n");
        money = 0;
    }

    public void printArray() {
        for (int i = 0; i < bottle_list.size();i++) {
            System.out.println(i+1 + ". Nimi: " + bottle_list.get(i).name + "\n" + "	Koko: " +
                    bottle_list.get(i).volume + "	Hinta: " + bottle_list.get(i).price);
        }
    }

    public static String getUusinnimi() {
        return uusinnimi;
    }

    public static String getUusinkoko() {
        return uusinkoko;
    }
    public static float getUusinhinta() {
        return uusinhinta;
    }
}

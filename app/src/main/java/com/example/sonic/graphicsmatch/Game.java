package com.example.sonic.graphicsmatch;

import java.util.Random;

public class Game {
    private int score;
    private int shapes[];

    public Game() {
        score = 0;
        shapes = new int[3];
        shapes[0] = 0;
        shapes[1] = 2;
        shapes[2] = 1;
    }

    public int getScore(boolean viewOnly) {
        if (!viewOnly) {
            if (shapes[0] == shapes[1] && shapes[1] == shapes[2])
                score += 75;
            else if (shapes[0] == shapes[2])
                score += 10;
            else if (shapes[0] == shapes[1]) {
                if (shapes[0] == 0 || shapes[0] == 1 || shapes[0] == 2)
                    score += 40;
                else
                    score += 10;
            } else
                score -= 10;
        }
        return score;
    }

    public int[] getShapes() {
        return shapes;
    }

    public void setShapes(int []s) {
        int i;
        for (i = 0; i < 3; i++)
            shapes[i] = s[i];
    }

    public void setScore(int s) {
        score = s;
    }

    /* cherry       0
       Lemon        1
       Bar          2
       Raspberry    3
       Seven        4
       Bell         5
     */

    public int[] spin() {
        int i;
        Random r = new Random();
        for (i = 0; i < 3; i++)
            shapes[i] = Math.abs(r.nextInt()) % 6;

        return shapes;
    }
}


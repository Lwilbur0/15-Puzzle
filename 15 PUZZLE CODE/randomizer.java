import pkg.*;
import java.util.Random;
import java.awt.Color;
import java.io.IOException;
import java.util.*;
public class randomizer {
    private Tile[][] scramble;
    private static boolean started;
    public static int moveCount = 0;
// constructs already solved scramble
    public randomizer() {
        Main.timer = new Text(0, 0, Main.formatTime(0));
        Main.timer.grow(8, 7.5);
        Main.timer.draw();
        Main.timer.translate(370 - Main.timer.getWidth(), 14);
        scramble = new Tile[4][4];
        int c = 0;
        for (int j = 0; j < scramble.length; j++) {
            for (int k = 0; k < scramble[0].length; k++) {
                c++;
                scramble[j][k] = new Tile(k, j, c);
                scramble[j][k].draw();
            }
        }    
  //removes 16th tile
    r16th();
}
    // draws tiles
    public void draw() {
        for (int j = 0; j < scramble.length; j++) {
            for (int k = 0; k < scramble[0].length; k++) {
                scramble[j][k].draw();
                r16th();
            }
        }
    }

    // randomizes tile scramble
    public void scramble() {
        moveCount = 0;
        Main.resetTimer();
        boolean isSolvable = false;
    while(!isSolvable) {
        Integer[] arrNums = new Integer[scramble.length * scramble[0].length];
        for (int i = 0; i < arrNums.length; i++) {
            arrNums[i] = Integer.valueOf(i);
        }
       // shuffles above array to be random order
        Collections.shuffle(Arrays.asList(arrNums));
        
        // assigns random number to each part of array
        for (int j = 0, i = 0; j < 4 && i < 16; j++) {
            for(int k = 0; k < 4 && i < 16; k++, i++) {
            scramble[j][k].undraw();
            scramble[j][k] = new Tile(k, j, arrNums[i] + 1);
            scramble[j][k].draw();
            }
        }
        isSolvable = isSolvable(scramble);
        r16th();
        }  
     started = false;
    }

    // https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/   cool math
    public boolean isSolvable(Tile[][] arr) {
        int emptyPos = 4 - get16thTile().getY();
        int invCount = 0;
        int[] scramble1D = new int[arr.length * arr[0].length];
            for (int j = 0, i = 0; j < arr.length; j++) {
                for (int k = 0; k < arr[0].length; k++, i++) {
                scramble1D[i] = arr[j][k].getVal();
                if (arr[k][j].getVal() == 16) {
                    scramble1D[i] = 0;
                }
            }
        }
            for (int i = 0; i < scramble.length * scramble[0].length - 1; i++) {
                for (int j = i + 1; j < scramble.length * scramble[0].length; j++) {
                    if(scramble1D[i] != 0 && scramble1D[j] != 0 && scramble1D[i] > scramble1D[j]) {
                        invCount++;
                    }
                }
            }

        if ((emptyPos % 2 == 0) == (invCount % 2 == 0)) {
            return false;
            }
        else {
            return true;
        }
        
    }
    public boolean solved() {
        for (int j = 0, i = 1; i < scramble.length * scramble[0].length - 1; j++) {
            for (int k = 0; k < scramble[0].length; k++, i++) {
                if (scramble[j][k].getVal() != i && i < 16) {
                    return false;
                }
            }
        }
        return true;
    }
    public void move(Point mp) {
        if (!started) {
            Main.startTimer();
            started = true;
        }
        
        Point moveToPoint = new Point(-mp.getX(), -mp.getY());
        Point emptyTile = get16thTile();
// x and y locations instead of 2d array indexes of to-Move tile
    int n_x = moveToPoint.getX() + emptyTile.getX();
    int n_y = moveToPoint.getY() + emptyTile.getY();

    if (n_x < 0 || n_x >= scramble[0].length|| n_y < 0 || n_y >= scramble.length) {
        return;
}
    else {
        moveCount++;
        scramble[n_y][n_x].translate(mp.getX() * 90, mp.getY() * 90);
        scramble[emptyTile.getY()][emptyTile.getX()].translate(-mp.getX() * 90, -mp.getY() * 90);
        Tile p = scramble[emptyTile.getY()][emptyTile.getX()];
        scramble[emptyTile.getY()][emptyTile.getX()] = scramble[n_y][n_x];
	    scramble[n_y][n_x] = p;
}
    if (this.solved()) {
        Main.stopTimer();
        try {
            Main.saveTime();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    }
    // removes last tile
    public void r16th() {
        for (int j = 0; j < scramble.length; j++) {
            for (int k = 0; k < scramble[0].length; k++) {
                if(scramble[j][k].getVal() == 0) {
                    scramble[j][k].undraw();
                }
            }
        }
    }
    public Point get16thTile() {
        int xIndex = 0, yIndex = 0;
        for (int j = 0; j < scramble.length; j++) {
            for (int k = 0; k < scramble[0].length; k++) {
                if(scramble[j][k].getVal() == 0) {
                    Tile placeholder16 = scramble[j][k];
                    xIndex = (placeholder16.getX() - 10)/90;
                    yIndex = (placeholder16.getY() - 40)/90;
                    return new Point(xIndex, yIndex);
                }
            }
        }
        return null;
    }
}
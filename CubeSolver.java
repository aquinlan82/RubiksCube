import java.awt.*;
import java.awt.event.*;

public class CubeSolver {
   private final int LEFT=37;
   private final int RIGHT=39;
   private final int UP=38;
   private final int DOWN=40;
    
   public boolean solved;
   private int next;
   private Cube cube;
   private int step;
   
   private int[] indexes;
   private boolean[] flags;
   
   public CubeSolver(Cube cube) {
      indexes = new int[5];
      flags = new boolean[5];
      solved = false;
      next = (int)'Q';
      step = 0;
   }

   public int getNextKey() {
      return next;
   }

   public void stepForward(Cube cubeIn) {
       cube = cubeIn;
       int status = setStatus();
       switch (status) {
         case 0: moveGreenToTop();    break;
         case 1: greenCross();        break;
         case 2: greenCorners();      break;
         case 3: middleRow();         break;
         case 4: blueCross();         break;
         case 5: blueCornersPlace();  break;
         case 6: blueCornersOrient(); break;
         case 7: finish();            break;
         case 8: solved = true;       
         default:
       }
       //System.out.println(next);
   }

   public int setStatus() {
      boolean[] steps = new boolean[9];
      steps[0] = true;
      steps[1] = (cube.getPiece(1,0,1).getTop() == Color.red && cube.getPiece(0,1,1).getFront() == Color.blue);
       
      steps[2] = (cube.getPiece(0,0,1).getTop() == Color.red &&
                       cube.getPiece(1,0,0).getTop() == Color.red &&
                       cube.getPiece(1,0,2).getTop() == Color.red &&
                       cube.getPiece(2,0,1).getTop() == Color.red && steps[1]);
                       
      steps[3] = (cube.getPiece(0,0,0).getTop() == Color.red &&
                      cube.getPiece(0,0,2).getTop() == Color.red &&
                      cube.getPiece(2,0,0).getTop() == Color.red &&
                      cube.getPiece(2,0,2).getTop() == Color.red && steps[2]);
                      
      steps[4] = (cube.getPiece(0,1,0).getFront() == cube.getPiece(0,1,1).getFront() &&
                      cube.getPiece(0,1,0).getFront() == cube.getPiece(0,1,2).getFront() &&
                      cube.getPiece(2,1,0).getBack() == cube.getPiece(2,1,1).getBack() &&
                      cube.getPiece(2,1,0).getBack() == cube.getPiece(2,1,2).getBack() && steps[3]);

      steps[5] = (cube.getPiece(0,2,1).getBottom() == Color.orange &&
                       cube.getPiece(1,2,0).getBottom() == Color.orange &&
                       cube.getPiece(1,2,2).getBottom() == Color.orange &&
                       cube.getPiece(2,2,1).getBottom() == Color.orange && steps[4]);

                      
      steps[6] = (cube.getPiece(0,2,0).equals(new Piece(cube.getPiece(0,1,1).getFront(), Color.black, Color.black, cube.getPiece(1,2,1).getBottom(), cube.getPiece(1,1,0).getLeft(), Color.black)) &&
                      cube.getPiece(0,2,2).equals(new Piece(cube.getPiece(0,1,1).getFront(), Color.black, cube.getPiece(1,2,1).getRight(), cube.getPiece(1,2,1).getBottom(), Color.black, Color.black)) &&
                      cube.getPiece(2,2,0).equals(new Piece(Color.black, Color.black, Color.black, cube.getPiece(1,2,1).getBottom(), cube.getPiece(1,1,0).getLeft(), cube.getPiece(2,1,1).getBack())) && steps[5]);  
                      
      steps[7] = (cube.getPiece(0,2,0).getBottom() == Color.orange &&
                      cube.getPiece(0,2,2).getBottom() == Color.orange &&
                      cube.getPiece(2,2,0).getBottom() == Color.orange &&
                      cube.getPiece(2,2,2).getBottom() == Color.orange && steps[6]);
                      
      steps[8] = (cube.getPiece(0,2,1).getFront() == cube.getPiece(0,1,1).getFront() &&
                      cube.getPiece(1,2,2).getRight() == cube.getPiece(1,1,2).getRight() &&
                      cube.getPiece(1,1,0).getLeft() == cube.getPiece(1,2,0).getLeft() && steps[7]);       
      for (int i = steps.length - 1; i >= 0; i--) {
         if (i == step) {
            step = i;
            return i;
         }
         if (steps[i]) {
            step = i;
            indexes = new int[5];
            flags = new boolean[5];          
            return i;
         }
      }  
      return 0;                                       
   }
   
   public void moveGreenToTop() {
      if (cube.getPiece(1,1,0).getLeft() == Color.red || cube.getPiece(1,1,2).getRight() == Color.red) {
         next = RIGHT;
      } else {
         next = UP;
      }
   }
   
   public void greenCross() {
      int FLIP = 0;
      int BACK = 1;
      int LEFT = 2;
      int FRONT = 3;
     
      Color[] other = {cube.getPiece(0,1,1).getFront(), cube.getPiece(1,1,0).getLeft(), cube.getPiece(2,1,1).getBack(), cube.getPiece(1,1,2).getRight()};
      
      if (indexes[0] < 4) {
         if (cube.getPiece(1,0,2).getTop() == Color.red && cube.getPiece(1,0,2).getRight() == other[indexes[0]]) {
            next = (int)'T';
            indexes[0]++;
            indexes[1] = 0;
            indexes[2]++;
            flags[indexes[2]] = true;
         } else if ((cube.getPiece(0,0,1).getTop() == Color.red || cube.getPiece(0,0,1).getFront() == Color.red) && !flags[FRONT]) {
            next = (int)'D';
         } else if ((cube.getPiece(1,0,0).getTop() == Color.red || cube.getPiece(1,0,0).getLeft() == Color.red) && !flags[LEFT]) {
            next = (int)'A';
         } else if ((cube.getPiece(2,0,1).getTop() == Color.red || cube.getPiece(2,0,1).getBack() == Color.red) && !flags[BACK]) {
            next = (int)'E';
         } else if (cube.getPiece(1,0,2).getTop() == other[indexes[0]] && cube.getPiece(1,0,2).getRight() == Color.red) {
            flags[FLIP] = true;
            indexes[1] = 1;
            next = (int)'H';
         } else if (flags[FLIP]) {
            char[] moves = {'H','D','G','S','S'};
            next = (int)moves[indexes[1]];
            indexes[1]++;
            if (indexes[1] >= moves.length) {
               flags[FLIP] = false;
               indexes[1] = 1;
               next = (int)'H';
            }
         } else {
            char[] moves = {'H','H','H','H','G','H','H','H','H','G','H','H','H','H','G','H','H','H','H','G','A','G','Q','Q','G','A'};
            next = (int)moves[indexes[1]];
            indexes[1]++;
            indexes[1] = indexes[1] % moves.length;
         }
      } else {
         solved = true;
         next = (int)' ';
      }
   }

    public void greenCorners() {
      Piece FR = new Piece(Color.blue, Color.red, Color.white);
      Piece BR = new Piece(Color.black, Color.red, Color.white, Color.black, Color.black, Color.green);
      Piece FL = new Piece(Color.blue, Color.red, Color.black, Color.black, Color.yellow, Color.black);
      Piece BL = new Piece(Color.black, Color.red, Color.black, Color.black, Color.yellow, Color.green);
      Piece[] piece = {FR, BR, BL, FL};
      int[][] location = {{0,0,2}, {2,0,2}, {2,0,0}, {0,0,0}};
      char[][] sides = {{'H','Y'},{'Y','H'},{'Q','A'},{'A','Q'}};
      char[] moves = {sides[indexes[0]][0], 'G', sides[indexes[0]][1], 'F', 'F'};  
      //char[] flipMoves = {
      
      
      if (cube.getLocation(piece[indexes[0]])[0] == location[indexes[0]][0] &&
          cube.getLocation(piece[indexes[0]])[1] == location[indexes[0]][1] &&
          cube.getLocation(piece[indexes[0]])[2] == location[indexes[0]][2] &&
          cube.getPiece(location[indexes[0]][0],location[indexes[0]][1],location[indexes[0]][2]).getTop() == Color.red) {
          indexes[0]++;
          indexes[1] = 0;
      }
      
      if (cube.getLocation(piece[indexes[0]])[0] != location[indexes[0]][0] ||
          cube.getLocation(piece[indexes[0]])[1] != location[indexes[0]][1] ||
          cube.getLocation(piece[indexes[0]])[2] != location[indexes[0]][2]) {
             
      } else {
      
      
      }      
      
      
      next = moves[indexes[1]];
      indexes[1]++;
      indexes[1] = indexes[1] % moves.length;
    }
   
   public void middleRow() {
       solved = true;
       next = (int)' ';
   }
    public void blueCross() {
       solved = true;
       next = (int)' ';
    }

    public void blueCornersPlace() {
        solved = true;
        next = (int)' ';
    }

    public void blueCornersOrient() {
        solved = true;
        next = (int)' ';
    }

    public void finish() {
        solved = true;
        next = (int)' ';
    }

}
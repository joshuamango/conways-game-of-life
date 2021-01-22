import java.util.*;
import java.io.*;
public class GameOfLife {
  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Usage: GameOfLife filename matrixSize [generationSpeed]");
      return;
    }

    char[][] cells = getGridData(args[0], Integer.valueOf(args[1]));
    int speed = (args.length > 2) ? Integer.valueOf(args[2]) : 1000;
    
    if (cells.length == 0) {
      System.out.println("No data entered..");
      return ;
    }
    int[][] counts = new int[cells.length][cells[0].length];
    int generation = 0;
    while (cycleGeneration(cells, counts, generation++)) {
      try {
        Thread.sleep(speed);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public static boolean cycleGeneration(char[][] cells, int[][] counts, int generation) {
    try {
      printGrid(cells, generation);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    boolean alive = false;

    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells[i].length; j++) {
        counts[i][j] = countLiveCells(cells, i, j);
      }
    }
    
    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells[i].length; j++) {
        if (cells[i][j] == '#')  {
          if (!(counts[i][j] == 2 || counts[i][j] == 3)) {
            cells[i][j] = ' ';
            alive = true;
          }
        }
        else if (cells[i][j] == ' ') {
          if (counts[i][j] == 3) {
            cells[i][j] = '#';
            alive = true;
          }
        }
      }
    }

    return alive;
  }

  public static int val(char c) {
    return (c == '#') ? 1 : 0;
  }

  public static int countLiveCells(char[][] cells, int r, int c) {
    int count = 0;

    if (r > 0) {
      count += val(cells[r-1][c]);
    }

    if (r < cells.length - 1) {
      count += val(cells[r+1][c]);
    }

    if (c > 0) {
      count += val(cells[r][c-1]);
    }

    if (c < cells[r].length - 1) {
      count += val(cells[r][c+1]);
    }

    if (r > 0 && c > 0) {
      count += val(cells[r-1][c-1]);
    }

    if (r < cells.length - 1 && c > 0) {
      count += val(cells[r+1][c-1]);
    }

    if (r < cells.length - 1 && c < cells[r].length - 1) {
      count += val(cells[r+1][c+1]);
    }

    if (r > 0 && c < cells[r].length - 1) {
      count += val(cells[r-1][c+1]);
    }

    return count;
  }

  public static void printGrid(char[][] grid, int generation) throws IOException, InterruptedException {
    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    System.out.println("Generation " + generation);
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        System.out.print(grid[i][j] + " ");
      }
      System.out.println();
    }
  }

  public static void printGrid(int[][] grid, int generation) throws IOException, InterruptedException {
    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    System.out.println("Generation " + generation);
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        System.out.print(grid[i][j] + " ");
      }
      System.out.println();
    }
  } 
    
  public static char[][] getGridData(String filename, int matrixSize) {
    File input = new File(filename);
    Scanner scanner;
    char[][] matrix = new char[matrixSize][matrixSize];

    // If File does not exists
    if (!input.exists()) {
      System.out.println("ERROR: File named " + filename + " not found...");
      System.exit(1);
    }

    try {
      scanner = new Scanner(input);
      String line;
      String[] pieces;
      int row = 0;

      while ((line = scanner.nextLine()) != null) {
        if (row == matrixSize) {
          break;
        }

        pieces = line.split(" ");
        for (int i = 0; i < matrixSize; i++) {
          matrix[row][i] = (Integer.valueOf(pieces[i]) == 1) ? '#' : ' ';
        }     

        row += 1;
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    return matrix;
  }
}
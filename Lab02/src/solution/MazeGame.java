package solution;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A maze game.
 * 
 * @author Victor C Thompson
 * @version 1/24/2020
 *
 */
public class MazeGame
{
    /**
     * The size of each side of the game map.
     */
    private final static int HEIGHT = 19;
    private final static int WIDTH = 39;   
    
    /**
     * Determines whether each grid location is "blocked"  or "open".
     * True = blocked
     */
    private boolean[][] blocked;
    
    private boolean[][] breadCrumb;

    private int startRow;
    private int startCol;
    
    /**
     * The current location of the player horizontally.
     */
    private int userCol = startCol;
    
    /**
     * The current location of the player vertically.
     */
    private int userRow = startRow;
    
    private int goalRow;
    private int goalCol;
    
    /**
     * The scanner from which each move is read.
     */
    private Scanner moveScanner;
    
    /**
     * Constructor initializes the maze with the data in 'mazeFile'.
     * @param mazeFile the input file for the maze
     */
    public MazeGame(String mazeFile)
    {
        loadMaze(mazeFile);
        moveScanner = new Scanner(System.in);
    }

    /**
     * Constructor initializes the maze with the 'mazeFile' and the move 
     * scanner with 'moveScanner'.
     * @param mazeFile the input file for the maze
     * @param moveScanner the scanner object from which to read user moves
     */
    public MazeGame(String mazeFile, Scanner moveScanner)
    {
        loadMaze(mazeFile);
        this.moveScanner = moveScanner;
    }
    
    /**
     * gets start row.
     * @return startRow position
     */
    public int getStartRow()
    {
        return startRow;
    }
    
    /**
     * sets start row.
     * @param startRow 
     */
    public void setStartRow(int startRow)
    {
        this.startRow = startRow;
    }
    
    /**
     * gets start column.
     * @return startCol position
     */
    public int getStartCol()
    {
        return startCol;
    }
    
    /**
     * set start column.
     * @param startCol 
     */
    public void setStartCol(int startCol)
    {
        this.startCol = startCol;
    }
    
    /**
     * gets user column.
     * @return userCol 
     */
    public int getUserCol()
    {
        return userCol;
    }

    /**
     * sets user column.
     * @param userCol 
     */
    public void setUserCol(int userCol)
    {
        this.userCol = userCol;
    }

    /**
     * gets user row.
     * @return userRow 
     */
    public int getUserRow()
    {
        return userRow;
    }
    
    /**
     * sets start row.
     * @param userRow 
     */
    public void setUserRow(int userRow)
    {
        this.userRow = userRow;
    }
    
    /**
     * gets goalRow.
     * @return goalRow 
     */
    public int getGoalRow()
    {
        return goalRow;
    }
    
    /**
     * sets goalRow.
     * @param goalRow 
     */
    public void setGoalRow(int goalRow)
    {
        this.goalRow = goalRow;
    }
    
    /**
     * gets goalCol.
     * @return goalCol 
     */
    public int getGoalCol()
    {
        return goalCol;
    }
    
    /**
     * sets goalCol.
     * @param goalCol 
     */
    public void setGoalCol(int goalCol)
    {
        this.goalCol = goalCol;
    }
    
    /**
     * gets moveScanner.
     * @return moveScanner 
     */
    public Scanner getMoveScanner()
    {
        return moveScanner;
    }
    
    /**
     * sets moveScanner.
     * @param moveScanner 
     */
    public void setMoveScanner(Scanner moveScanner)
    {
        this.moveScanner = moveScanner;
    }

    /**
     * getMaze returns a copy of the current maze for testing purposes.
     * @return the grid
     */
    public boolean[][] getMaze()
    {
        if (blocked == null)
        {
            return null;
        }
        boolean[][] copy = new boolean[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                copy[i][j] = blocked[i][j];
            }
        }
        return copy;
    }

    /**
     * setMaze sets the current map for testing purposes.
     * 
     * @param maze another maze.
     */
    public void setMaze(boolean[][] maze)
    {
        this.blocked = maze;
    }
    
    /**
     * Function loads the data from the maze file and creates the 'blocked' 
     * 2D array.
     *  
     * @param mazeFile the input maze file.
     * @throws FileNotFoundException 
     */
    private void loadMaze(String mazeFile)
    {    
        blocked = new boolean[HEIGHT][WIDTH];
        breadCrumb = new boolean[HEIGHT][WIDTH];
        char readChar;
        try 
        {
            File newFile = new File(mazeFile);
            Scanner readFile = new Scanner(newFile);     
            
            int r = 0;
            while (readFile.hasNext())
            {
                for (int c = 0; c < WIDTH; c++)
                {
                    readChar = readFile.next().charAt(0);
                    if (readChar == 'S')
                    {
                        setStartRow(r);
                        setStartCol(c);
                    }
                    else if (readChar == 'G')
                    {
                        setGoalRow(r);
                        setGoalCol(c);
                    }
                    else if (readChar == '1')
                    {
                        blocked[r][c] = true;
                    }
                }
                r++;
            }
            
            readFile.close();
        }
        catch (FileNotFoundException e) 
        {
            System.out.println("File was not found.");
        }
    }
    
    /**
     * Actually plays the game.
     */
    public void playGame()
    {
        int numMoves = 0;
        printMaze();
        System.out.print("Next move: ");
        makeMove(moveScanner.nextLine());
        while (!playerAtGoal()) 
        {   
            printMaze();
            System.out.println(getUserRow() + " " + getUserCol());
            System.out.print("Next move: ");
            String holder = moveScanner.nextLine();
            makeMove(holder);
            numMoves++;
            
            if (playerAtGoal()) 
            {
                System.out.println("\nCongrats! You've won");
            }
            else if (holder.toLowerCase().equals("quit"))
            {
                System.out.println("\nBetter luck next time!");
                break;
            }
        }
        System.out.print("You made " + numMoves + " moves.");
    }

    /**
     * Checks to see if the player has won the game.
     * @return true if the player has won.
     */
    public boolean playerAtGoal()
    {
        return userCol == goalCol && userRow == goalRow;
    }

    /**
     * Makes a move based on the String.
     * 
     * @param move the direction to make a move in.
     * @return whether the move was valid.
     */
    public boolean makeMove(String move)
    {
        if (move.toLowerCase().equals("up"))
        {
            if (ifValidMove(blocked, getUserRow() - 1, getUserCol()) 
                && !(blocked[getUserRow() - 1][getUserCol()]))
            {
                breadCrumb[getUserRow()][getUserCol()] = true;
                setUserRow(getUserRow() - 1);
                return true;   
            }
        }
        else if (move.toLowerCase().equals("down"))
        {
            if (ifValidMove(blocked, getUserRow() + 1, getUserCol())
                && !(blocked[getUserRow() + 1][getUserCol()]))
            {
                breadCrumb[getUserRow()][getUserCol()] = true;
                setUserRow(getUserRow() + 1);
                return true;
            }
        }
        else if (move.toLowerCase().equals("left"))
        {
            if (ifValidMove(blocked, getUserRow(), getUserCol() - 1) 
                && !(blocked[getUserRow()][getUserCol() - 1]))
            {
                breadCrumb[getUserRow()][getUserCol()] = true;
                setUserCol(getUserCol() - 1);
                return true;
            }
        }
        else if (move.toLowerCase().equals("right"))
        {
            if (ifValidMove(blocked, getUserRow(), getUserCol() + 1)
                && !(blocked[getUserRow()][getUserCol() + 1]))
            {
                breadCrumb[getUserRow()][getUserCol()] = true;
                setUserCol(getUserCol() + 1);
                return true;
            }
        }
        return false;
    }
    
    /**
     * checks to make sure move does not take player out of bounds.
     * @param array 
     * @param row 
     * @param col 
     * @return whether move is valid
     */
    public boolean ifValidMove(boolean array[][], int row, int col)
    {
        return row >= 0 && col >= 0 
            && row < array.length && col < array[row].length;
    }

    /**
     * Prints the map of the maze.
     */
    public void printMaze()
    {
        System.out.println("*---------------------------------------*");
        for (int r = 0; r < HEIGHT; r++)
        {
            System.out.print("|");
            for (int c = 0; c < WIDTH; c++)
            {
                if (r == getUserRow() && c == getUserCol())
                {
                    System.out.print("@");
                }
                else if (r == getStartRow() && c == getStartCol())
                {
                    System.out.print("S");
                }
                else if (r == getGoalRow() && c == getGoalCol())
                {
                    System.out.print("G");
                }
                else if (blocked[r][c])
                {
                    System.out.print("X");
                }
                else if (breadCrumb[r][c])
                {
                    System.out.print(".");
                }
                else
                {
                    System.out.print(" ");
                }
            }
            System.out.println("|");
        }
        System.out.println("*---------------------------------------*");
    }

    /**
     * Creates a new game, using a command line argument file name, if one is
     * provided.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        String mapFile = "data\\easy.txt";
        Scanner scan = new Scanner(System.in);
        MazeGame game = new MazeGame(mapFile, scan);
        game.playGame();
    }
}

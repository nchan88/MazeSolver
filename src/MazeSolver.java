/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        // Should be from start to end cells
        ArrayList<MazeCell> solution = new ArrayList<MazeCell>();
        solution.add(maze.getEndCell());
        MazeCell currentCell = maze.getEndCell().getParent();
        while (currentCell != null)
        {
            solution.add(0, currentCell);
            currentCell = currentCell.getParent();
        }
        return solution;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> getNeighbors (MazeCell CurrentCell)
    {
        //This is a helper method that accepts a cell and adds all valid cells around it to an ArrayList.
        //This method checks the cells in North, West, South, East order.
        ArrayList<MazeCell> neighbors = new ArrayList<MazeCell>();
        int row = CurrentCell.getRow();
        int col = CurrentCell.getCol();
        //Checking North Cell
        if (maze.isValidCell(row - 1, col))
        {
            neighbors.add(maze.getCell(row - 1 , col));
        }
        //Checking East Cell
        if (maze.isValidCell(row, col + 1))
        {
            neighbors.add(maze.getCell(row, col + 1));
        }
        //Checking South Cell
        if (maze.isValidCell(row + 1, col))
        {
            neighbors.add(maze.getCell(row + 1, col));
        }
        //Checking West Cell
        if (maze.isValidCell(row, col - 1))
        {
            neighbors.add(maze.getCell(row, col - 1));
        }
        return neighbors;
    }
    public ArrayList<MazeCell> solveMazeDFS() {
        // TODO: Use DFS to solve the maze
        Stack<MazeCell> tracker = new Stack<MazeCell>();
        maze.getStartCell().setExplored(true);
        //Starting cell is added to the stack by default.
        tracker.push(maze.getStartCell());
        //If there are no more items in stack, a solution must not have been found, so it results in null being returned.
        while (!tracker.empty())
        {
            //In DFS, the cell at the top of the stack is explored first.
            MazeCell currentCell = tracker.pop();
            //Base case. If the current cell is the end cell, the solution has been found as all parent relationships have been set.
            if (currentCell.equals(maze.getEndCell()))
            {
                return getSolution();
            }
            //For each loop goes through each valid neighbor to the current cell and adds them to stack.
            for (MazeCell neighbor : getNeighbors(currentCell))
            {
                //Ensures that the cell isn't continuously added to the stack.
                neighbor.setExplored(true);
                //Ensures that getSolution can trace the parent relationship through the maze.
                neighbor.setParent(currentCell);
                tracker.push(neighbor);
            }
        }
        return null;
    }
    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        //Uses Queue instead of stack in this method, all else functions in the same way.
        Queue<MazeCell> tracker = new LinkedList<MazeCell>();
        maze.getStartCell().setExplored(true);
        tracker.add(maze.getStartCell());
        while (!tracker.isEmpty())
        {
            //Unlike a stack, this removes the element at the end of the data structure.
            //The element that has been in queue the longest will be removed.
            MazeCell currentCell = tracker.remove();
            if (currentCell.equals(maze.getEndCell()))
            {
                return getSolution();
            }
            for (MazeCell neighbor : getNeighbors(currentCell))
            {
                neighbor.setExplored(true);
                neighbor.setParent(currentCell);
                tracker.add(neighbor);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze2.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}

package maze;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Class that solves maze problems with backtracking.
 * @author Koffman and Wolfgang
 **/

// Jude Lee
// I pledge my honor that I have abided by the Stevens Honor System.

public class Maze implements GridColors {

    /** The maze */
    private TwoDimGrid maze;

    public Maze(TwoDimGrid m) {
        maze = m;
    }

    /** Wrapper method. */
    public boolean findMazePath() {
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

    /**
     * Attempts to find a path through point (x, y).
     * @pre Possible path cells are in BACKGROUND color;
     *      barrier cells are in ABNORMAL color.
     * @post If a path is found, all cells on it are set to the
     *       PATH color; all cells that were visited but are
     *       not on the path are in the TEMPORARY color.
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return If a path through (x, y) is found, true;
     *         otherwise, false
     */
    public boolean findMazePath(int x, int y) {
        if(x < 0 || y < 0) {
        	return false;
        }
        // check if outside the grid
        
        if(x >= maze.getNCols() || y >= maze.getNRows()) {
        	return false;
        }
        // check if outside the grid, maybe put in one if statement?
        
        if(maze.getColor(x, y).equals(NON_BACKGROUND) == false) {
        	return false;
        }
        // check if color is non background
        
        if(x == maze.getNCols()-1 && y == maze.getNRows()-1) {
        	maze.recolor(x, y, PATH);
        	return true;
        }
        // check if x y is exit, recolor and return true
        
        // if none of these conditions are true, cell is part of path
        maze.recolor(x, y, PATH);
        if(findMazePath(x+1, y) == true || findMazePath(x-1, y) == true|| findMazePath(x, y+1) == true|| findMazePath(x, y-1) == true) {
        	return true;
        }
        else {
        	maze.recolor(x, y, TEMPORARY);
        	return false;
        }
    }

    // ADD METHOD FOR PROBLEM 2 HERE
    public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y) {
    	ArrayList<ArrayList<PairInt>> result = new ArrayList<>();
    	Stack<PairInt> trace = new Stack<>();
    	findMazePathStackBased(0, 0, result, trace);
    	return result;
    }
    
    public void findMazePathStackBased(int x, int y, ArrayList<ArrayList<PairInt>> result, Stack<PairInt> trace) {
    	  if(x < 0 || y < 0) {
          	return;
          }
          
          if(x > maze.getNCols() || y > maze.getNRows()) {
          	return;
          }
          
          if(maze.getColor(x, y).equals(NON_BACKGROUND) == false) {
          	return;
          }
          
          if(x == maze.getNCols()-1 && y == maze.getNRows()-1) {
        	  trace.push(new PairInt(x, y));
        	  ArrayList<PairInt> condList = new ArrayList<PairInt>();
        	  condList.addAll(trace);
        	  result.add(condList);
        	  trace.pop();
        	  maze.recolor(x, y, NON_BACKGROUND); 
        	  return;
          }
          else {
        	  trace.push(new PairInt(x, y));
        	  maze.recolor(x, y, PATH);         	 
        	  findMazePathStackBased(x+1, y, result, trace);
        	  findMazePathStackBased(x-1, y, result, trace);
        	  findMazePathStackBased(x, y+1, result, trace);
        	  findMazePathStackBased(x, y-1, result, trace);
        	  maze.recolor(x, y, NON_BACKGROUND);
        	  trace.pop();
        	  return;
          }
    }
    
    // ADD METHOD FOR PROBLEM 3 HERE
    public ArrayList<PairInt> findMazePathMin(int x, int y) {
    	ArrayList<ArrayList<PairInt>> allPaths = findAllMazePaths(x, y);
    	int min = 0;
    	
    	if(allPaths.size() == 0) {
    		ArrayList<PairInt> empty = new ArrayList<PairInt>();
    		return empty;
    	}
    	
    	for(int i = 1; i < allPaths.size(); i++) {
    		if(allPaths.get(min).size() > allPaths.get(i).size()) {
    			min = i;
    		}
    	}
    	return allPaths.get(min);
    }
    

    /*<exercise chapter="5" section="6" type="programming" number="2">*/
    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }
    /*</exercise>*/

    /*<exercise chapter="5" section="6" type="programming" number="3">*/
    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
    /*</exercise>*/

   
}
/*</listing>*/


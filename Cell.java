import java.util.*;

/**
 * A cell in a 2D cellular automaton.
 * The cell has multiple possible states.
 * This is an implementation of the rules for Conways Game of Life.
 * @see https://en.wikipedia.org/wiki/Conway's_Game_of_Life
 * 
 * @author David J. Barnes and Michael Kölling
 * @author Tim Lee
 * @version  12.04.16
 */
public class Cell
{
    // The possible states.
    public static final int ALIVE = 0, DEAD = 1;
    // The number of possible states.
    public static final int NUM_STATES = 2;

    // The cell's state.
    private int state;
    // The cell's neighbors.
    private Cell[] neighbors;

    /**
     * Set the initial state to be DEAD.
     */
    public Cell()
    {
        this(DEAD);
    }
    
    /**
     * Set the initial state.
     * @param initialState The initial state
     */
    public Cell(int initialState)
    {
        state = initialState;
        neighbors = new Cell[0];
    }
    
    /**
     * Determine this cell's next state, based on the
     * state of its neighbors.
     * This is an implementation of the rules for Conway's game of life.
     * @return The next state.
     */
    public int getNextState()
    {
        // Count the number of neighbors that are alive.
        int aliveCount = 0;
        for(Cell n : neighbors) {
            if(n.getState() == ALIVE) {
                aliveCount++;
            }
        }
        if(state == DEAD) {
            return aliveCount == 3 ? ALIVE : DEAD;
        }
        else {
            if(aliveCount == 2 || aliveCount == 3)
            {
                return ALIVE;
            }
            else
            {
                return DEAD;
            }
        }
    }
    
    /**
     * Receive the list of neighboring cells and take
     * a copy.
     * @param neighborList Neighboring cells.
     */
    public void setNeighbors(ArrayList<Cell> neighborList)
    {
        neighbors = new Cell[neighborList.size()];
        neighborList.toArray(neighbors);
    }

    /**
     * Get the state of this cell.
     * @return The state.
     */
    public int getState()
    {
        return state;
    }
    
    /**
     * Set the state of this cell.
     * @param The state.
     */
    public void setState(int state)
    {
        this.state = state;
    }   
    
}

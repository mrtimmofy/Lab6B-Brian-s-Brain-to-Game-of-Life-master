import java.security.SecureRandom;
import java.util.*;

/**
 * Maintain the environment for a 2D cellular automaton according to the rules of Conway's Game of Life.
 * 
 * @author David J. Barnes
 * @author Tim Lee
 * @version  12.04.16
 */
public class Environment
{
    // Default size for the environment.
    private static final int DEFAULT_ROWS = 50;
    private static final int DEFAULT_COLS = 50;
    
    // The grid of cells.
    private Cell[][] cells;
    // Visualization of the environment.
    private final EnvironmentView view;
    // integer to hold the number of rows.
    private int numberRows;
    // integer to hold the number of columns
    private int numberColumns;

    /**
     * Create an environment with the default size.
     */
    public Environment()
    {
        this(DEFAULT_ROWS, DEFAULT_COLS);
    }

    /**
     * Create an environment with the given size.
     * @param numRows The number of rows.
     * @param numCols The number of cols;
     */
    public Environment(int numRows, int numCols)
    {
        setup(numRows, numCols);
        numberRows = numRows;
        numberColumns = numCols;
        randomize();
        setupNeighbors();
        
        view = new EnvironmentView(this, numRows, numCols);
        view.showCells();
    }
    
    /**
     * Run the automaton for one step.
     */
    public void step()
    {
        // Build a record of the next state of each cell.
        int[][] nextStates = new int[numberRows][numberColumns];
        // Ask each cell to determine its next state.
        for(int row = 0; row < numberRows; row++) {
            int[] rowOfStates = nextStates[row];
            for(int col = 0; col < numberColumns; col++) {
                rowOfStates[col] = cells[row][col].getNextState();
            }
        }
        // Update the cells' states.
        for(int row = 0; row < numberRows; row++) {
            int[] rowOfStates = nextStates[row];
            for(int col = 0; col < numberColumns; col++) {
                setCellState(row, col, rowOfStates[col]);
            }
        }
    }
    
    /**
     * Reset the state of the automaton to all DEAD.
     */
    public void reset()
    {
        for(int row = 0; row < numberRows; row++) {
            for(int col = 0; col < numberColumns; col++) {
                setCellState(row, col, Cell.DEAD);
            }
        }
    }
    
    /**
     * Generate a random setup.
     */
    public void randomize()
    {
        SecureRandom rand = new SecureRandom();
        for(int row = 0; row < numberRows; row++) {
            for(int col = 0; col < numberColumns; col++) {
                setCellState(row, col, rand.nextInt(2));
            }
        }
    }
    
    /**
     * Set the state of one cell.
     * @param row The cell's row.
     * @param col The cell's col.
     * @param state The cell's state.
     */
    public void setCellState(int row, int col, int state)
    {
        cells[row][col].setState(state);
    }
    
    /**
     * Return the grid of cells.
     * @return The grid of cells.
     */
    public Cell[][] getCells()
    {
        return cells;
    }
    
    /**
     * Setup a new environment of the given size.
     * @param numRows The number of rows.
     * @param numCols The number of cols;
     */
    private void setup(int numRows, int numCols)
    {
        cells = new Cell[numRows][numCols];
        for(int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                cells[row][col] = new Cell();
            }
        }
       // setupNeighbors();
    }
    
    /**
     * Give to a cell a list of its neighbors. Depending on loaction, neighbors do not wrap around if cell is on the edge of its environment
     */
    private void setupNeighbors()
    {
        // Allow for 8 neighbors plus the cell.
        ArrayList<Cell> neighbors = new ArrayList<>(9);
        for(int row = 0; row < numberRows; row++) {
            for(int col = 0; col < numberColumns; col++) {
                Cell cell = cells[row][col];
                // This process will also include the cell.
                if(row == 0 || row == (numberRows - 1) || col == 0 || col == (numberColumns - 1))
                {
                    if (row == 0)
                    {
                        if(col == 0)
                        {
                            neighbors.add(cells[0][1]);
                            neighbors.add(cells[1][0]);
                            neighbors.add(cells[1][1]);
                        }
                        else if(col == (numberColumns - 1))
                        {
                            neighbors.add(cells[0][numberColumns - 2]);
                            neighbors.add(cells[1][numberColumns - 2]);
                            neighbors.add(cells[1][numberColumns - 1]);
                        }
                        else
                        {
                            neighbors.add(cells[0][col - 1]);
                            neighbors.add(cells[0][col + 1]);
                            neighbors.add(cells[1][col - 1]);
                            neighbors.add(cells[1][col]);
                            neighbors.add(cells[1][col + 1]);
                        }
                    }
                   else if(row == (numberRows - 1))
                   {
                       if(col == 0)
                       {
                            neighbors.add(cells[numberRows - 2][0]);
                            neighbors.add(cells[numberRows - 2][1]);
                            neighbors.add(cells[numberRows - 1][1]);
                       }
                       else if(col == (numberColumns - 1))
                       {
                           neighbors.add(cells[numberRows - 2][numberColumns - 2]);
                           neighbors.add(cells[numberRows - 2][numberColumns - 1]);
                           neighbors.add(cells[numberRows - 1][numberColumns - 2]);
                       }
                       else
                       {
                           neighbors.add(cells[numberRows - 1][col - 1]);
                           neighbors.add(cells[numberRows - 1][col + 1]);
                           neighbors.add(cells[numberRows - 2][col - 1]);
                           neighbors.add(cells[numberRows - 2][col]);
                           neighbors.add(cells[numberRows - 2][col + 1]);
                       }
                    }
                    else if(col == (numberColumns - 1))
                    {
                        neighbors.add(cells[row - 1][numberColumns - 2]);
                        neighbors.add(cells[row - 1][numberColumns - 1]);
                        neighbors.add(cells[row][numberColumns - 2]);
                        neighbors.add(cells[row + 1][numberColumns - 2]);
                        neighbors.add(cells[row + 1][numberColumns - 1]);
                   }
                   else
                   {
                       neighbors.add(cells[row - 1][0]);
                       neighbors.add(cells[row - 1][1]);
                       neighbors.add(cells[row][1]);
                       neighbors.add(cells[row + 1][0]);
                       neighbors.add(cells[row + 1][1]);
                   }
                }
                else
                {
                    for(int dr = -1; dr <= 1; dr++) {
                        for(int dc = -1; dc <= 1; dc++) {
                            int nr = (row + dr) % numberRows;
                            int nc = (col + dc) % numberColumns;
                            neighbors.add(cells[nr][nc]);
                        }
                    }   
                    // The neighbours should not include the cell at
                    // (row,col) so remove it.
                    neighbors.remove(cell);
                }
                cell.setNeighbors(neighbors);
                neighbors.clear();
            }
        }
    }

}

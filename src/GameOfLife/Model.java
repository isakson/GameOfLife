package GameOfLife;

public class Model {
    public Cell[][] cells;
    private int currentGeneration;
    private int currentPopulation;
    private Controller controller;
    public int rowCount;
    public int columnCount;


    /**
     * Initializes the model.
     */
    public Model(int rowCount, int columnCount, Controller controller){
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        currentGeneration = 1;
        currentPopulation = 0;
        this.cells = new Cell[rowCount][columnCount];
        this.controller = controller;
        startSimulation();
    }

    /**
     * Initializes dead cells for the start of the simulation.
     */
    private void startSimulation(){
        for (int row = 0; row < rowCount; row++){
            for (int column = 0; column < columnCount; column++){
                this.cells[row][column] = new Cell(row, column, controller);
            }
        }
    }

    public int getCurrentGeneration() {
        return currentGeneration;
    }

    public void incrementCurrentGeneration() {
        this.currentGeneration++;
    }

    public void setCurrentGeneration(int generation) {this.currentGeneration = generation;}

    public int getCurrentPopulation(){
        int counter = 0;
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                if (cells[row][column].getLifeState()){
                    counter++;
                }
            }
        }
        setCurrentPopulation(counter);
        return currentPopulation;
    }

    public void setCurrentPopulation(int newPopulation){
        this.currentPopulation = newPopulation;
    }

    public boolean getCellState(int row, int column){
        assert row >= 0 && row < this.cells.length && column >= 0 && column < this.cells[0].length;
        return this.cells[row][column].getLifeState();
    }

    /**
     * Resets all cells to dead and the generation to 1.
     */
    public void reset() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                cells[row][column].setLifeState(false);
            }
        }
        setCurrentGeneration(1);
    }

    /**
     * Default for the glider setup.
     */
    public void gliderDefault() {
        reset();
        cells[17][17].setLifeState(true);
        cells[17][18].setLifeState(true);
        cells[17][19].setLifeState(true);
        cells[16][19].setLifeState(true);
        cells[15][18].setLifeState(true);
    }

    /**
     * Default for the small exploder setup.
     */
    public void smallExploderDefault() {
        reset();
        cells[17][18].setLifeState(true);
        cells[16][17].setLifeState(true);
        cells[16][19].setLifeState(true);
        cells[15][17].setLifeState(true);
        cells[15][18].setLifeState(true);
        cells[15][19].setLifeState(true);
        cells[14][18].setLifeState(true);
    }

    /**
     * Default for the exploder setup.
     */
    public void exploderDefault() {
        reset();
        //Left line
        cells[15][15].setLifeState(true);
        cells[16][15].setLifeState(true);
        cells[17][15].setLifeState(true);
        cells[18][15].setLifeState(true);
        cells[19][15].setLifeState(true);

        //Center dots
        cells[15][17].setLifeState(true);
        cells[19][17].setLifeState(true);

        //Right line
        cells[15][19].setLifeState(true);
        cells[16][19].setLifeState(true);
        cells[17][19].setLifeState(true);
        cells[18][19].setLifeState(true);
        cells[19][19].setLifeState(true);
    }

    /**
     * Default for the ten cell setup.
     */
    public void tenCellDefault() {
        reset();
        cells[17][13].setLifeState(true);
        cells[17][14].setLifeState(true);
        cells[17][15].setLifeState(true);
        cells[17][16].setLifeState(true);
        cells[17][17].setLifeState(true);
        cells[17][18].setLifeState(true);
        cells[17][19].setLifeState(true);
        cells[17][20].setLifeState(true);
        cells[17][21].setLifeState(true);
        cells[17][22].setLifeState(true);
    }

    /**
     * Default for the lightweight spaceship setup.
     */
    public void lightweightSpaceshipDefault() {
        reset();
        //Left side
        cells[16][15].setLifeState(true);
        cells[18][15].setLifeState(true);

        //Top bar
        cells[15][19].setLifeState(true);
        cells[15][18].setLifeState(true);
        cells[15][17].setLifeState(true);
        cells[15][16].setLifeState(true);

        //Right side
        cells[16][19].setLifeState(true);
        cells[17][19].setLifeState(true);
        cells[18][18].setLifeState(true);
    }
}

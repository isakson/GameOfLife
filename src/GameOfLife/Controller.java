package GameOfLife;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ComboBox;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements EventHandler<MouseEvent>{

    @FXML private Button runPauseButton;
    @FXML private Button stepButton;
    @FXML private Label currentGeneration;
    @FXML private Label currentPopulation;
    @FXML private View view;
    @FXML private ComboBox<String> defaultsComboBox;
    @FXML private Slider slider;

    private boolean paused;
    private Timer timer;
    private Model model;
    private double generationsTime = 5.0;

    /**
     * Initializes a controller with no cells.
     */
    public Controller() {
        this.paused = true;
    }

    /**
     * Initializes the model, view, grid, and slider.
     */
    public void initialize(){
        this.model = new Model(this.view.getRowCount(), this.view.getColumnCount(), this);
        this.view.initializeGrid();
        this.update();
        //Set a default value for the slider.
        this.slider.setValue(6);
    }

    /**
     * Starts or continues the timer, which is used to update the model when the user
     * runs the game (as opposed to stepping).
     */
    private void startTimer() {
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        step();
                        update();
                    }
                });
            }
        };
        long generationTimeMilliseconds = (long) (1000.0/generationsTime);
        this.timer.schedule(timerTask, 0, generationTimeMilliseconds);
    }

    /**
     * Advances the life states of the cells by one generation.
     */
    public void step() {
        int rowCount = view.getRowCount();
        int columnCount = view.getColumnCount();

        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                Cell currentCell = model.cells[row][column];
                int livingNeighbors = getLivingNeighbors(currentCell);
                //Any live cell with fewer than two live neighbors dies, as if by under population.
                if (currentCell.getLifeState() && livingNeighbors < 2){
                    currentCell.setNextLifeState(false);
                }
                //Any live cell with two or three live neighbors lives on to the next generation.
                else if (currentCell.getLifeState() && (livingNeighbors == 2 || livingNeighbors == 3)){
                    currentCell.setNextLifeState(true);
                }
                //Any live cell with more than three live neighbors dies, as if by overpopulation.
                else if (currentCell.getLifeState() && livingNeighbors > 3){
                    currentCell.setNextLifeState(false);
                }
                //Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
                else if (!currentCell.getLifeState() && livingNeighbors == 3){
                    currentCell.setNextLifeState(true);
                }
                //Any dead cell that meets none of these conditions will stay dead.
                else{
                    currentCell.setNextLifeState(false);
                }
            }
        }
        model.incrementCurrentGeneration();
        updateCellLifeStates();
        update();
    }

    /**
     * Updates the cells to their next life states.
     */
    private void updateCellLifeStates(){
        int rowCount = view.getRowCount();
        int columnCount = view.getColumnCount();

        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                model.cells[row][column].updateLifeState();
            }
        }
    }

    /**
     * @param cell The cell in question
     * @return The number of living neighbors the cell has.
     */
    private int getLivingNeighbors(Cell cell){
        int x = cell.getRow();
        int y = cell.getColumn();
        int counter = 0;

        //Top left
        if (x > 0 && y > 0){
            if (model.cells[x-1][y-1].getLifeState()) {
                counter++;
            }
        }
        //Top middle
        if (y > 0){
            if (model.cells[x][y-1].getLifeState()) {
                counter++;
            }
        }
        //Top right
        if (x < model.cells[0].length - 1 && y > 0){
            if (model.cells[x+1][y-1].getLifeState()) {
                counter++;
            }
        }
        //Middle left
        if (x > 0){
            if (model.cells[x-1][y].getLifeState()) {
                counter++;
            }
        }
        //Middle right
        if (x < model.cells[0].length - 1){
            if (model.cells[x+1][y].getLifeState()) {
                counter++;
            }
        }
        //Bottom left
        if (x > 0 && y < model.cells.length - 1){
            if (model.cells[x-1][y+1].getLifeState()) {
                counter++;
            }
        }
        //Bottom middle
        if (y < model.cells.length - 1){
            if (model.cells[x][y+1].getLifeState()) {
                counter++;
            }
        }
        //Bottom right
        if (x < model.cells[0].length - 1 && y < model.cells.length - 1){
            if (model.cells[x+1][y+1].getLifeState()) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * @param x The x coordinate of the mouse click
     * @param y The y coordinate of the mouse click
     * @return The row and column of the cell that was clicked on
     */
    private int[] whichCell(double x, double y) {
        int[] cellCoordinates = new int[2];
        int column = (int) (x - 245) / 20;
        int row = (int) (y - 50) / 20;
        cellCoordinates[0] = row;
        cellCoordinates[1] = column;
        return cellCoordinates;
    }

    /**
     * Updates the view of alive cells.
     */
    public void update() {
        this.view.update(model);
        this.currentGeneration.setText(String.format("Generation: %d", this.model.getCurrentGeneration()));
        this.currentPopulation.setText(String.format("Population: %d", this.model.getCurrentPopulation()));
    }

    /**
     * @param mouseEvent A click on a cell or button.
     * If the click was in the grid, this method interprets it as a cell click and calls the cell's mouse behavior method.
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        //Checks if the click is within the grid.
        if (x >= 225 && x <= 925 && y >= 50 && y <= 750) {
            int[] cellCoordinates = new int[2];
            cellCoordinates = whichCell(x, y);
            int row = cellCoordinates[0];
            int column = cellCoordinates[1];
            Cell clickedCell = this.model.cells[row][column];
            clickedCell.mouseEventBehavior();
            update();
        }
    }

    /**
     * Handles the behavior of the slider (changes the run speed).
     */
    public void sliderBehavior() {
        double value = slider.getValue();
        this.generationsTime = value;
        if (!paused){
            timer.cancel();
            startTimer();
        }
    }

    /**
     * Handles the selection and setting of the default formations in the combo box.
     */
    public void selectCellFormation() {
        String selection = defaultsComboBox.getValue();
        if (selection.equals("Clear")) {
            model.reset();
        }
        else if (selection.equals("Glider")) {
            model.gliderDefault();
        }
        else if (selection.equals("Small Exploder")) {
            model.smallExploderDefault();
        }
        else if (selection.equals("Exploder")) {
            model.exploderDefault();
        }
        else if (selection.equals("10 Cell Row")) {
            model.tenCellDefault();
        }
        else if (selection.equals("Lightweight Spaceship")) {
            model.lightweightSpaceshipDefault();
        }
        update();
    }

    /**
     * Handles the step button functionality.
     */
    public void onStepButton() {
        step();
    }

    /**
     * Handle pause/run functionality when button is clicked.
     */
    public void onPauseButton() {
        if (this.paused) {
            this.runPauseButton.setText("Pause");
            this.startTimer();
        } else {
            this.runPauseButton.setText("Run");
            this.timer.cancel();
        }
        this.paused = !this.paused;
    }

}
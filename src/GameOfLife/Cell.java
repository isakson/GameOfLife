package GameOfLife;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class Cell extends ImageView {
    @FXML private int row;
    @FXML private int column;
    private boolean alive;
    private boolean nextLifeState;
    private Controller controller;

    /**
     * @param row The x-value of the position of the cell.
     * @param column The y-value of the position of the cell.
     * Initializes a cell.
     */
    public Cell(int row, int column, Controller controller) {
        this.row = row;
        this.column = column;
        this.alive = false;
        this.nextLifeState = false;
        this.controller = controller;
    }

    /**
     * @param lifeState The life state that the cell should have (true = alive, false = dead).
     * Sets the life state of a cell.
     */
    public void setLifeState(boolean lifeState) {
        this.alive = lifeState;
    }

    public void setNextLifeState(boolean nextLifeState){
        this.nextLifeState = nextLifeState;
    }

    public void updateLifeState(){
        this.alive = this.nextLifeState;
    }

    public boolean getLifeState(){
        return this.alive;
    }

    public int getRow(){
        return this.row;
    }
    public int getColumn(){
        return this.column;
    }

    /**
     * Called when the cell is clicked on; changes the life state of the cell and updates the controller to reflect the change.
     */
    public void mouseEventBehavior(){
        this.setLifeState(!this.getLifeState());
        controller.update();
    }
}

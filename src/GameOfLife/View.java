package GameOfLife;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class View extends Group {
    public final static double CELL_WIDTH = 20.0;

    @FXML private int rowCount;
    @FXML private int columnCount;
    public ImageView[][] cellViews;
    private Image aliveCell;
    private Image deadCell;

    public View(){
        this.aliveCell = new Image(getClass().getResourceAsStream("/res/AliveCell.png"));
        this.deadCell = new Image(getClass().getResourceAsStream("/res/DeadCell.png"));
    }

    public int getRowCount(){
        return rowCount;
    }

    public void setRowCount(int rowCount){
        this.rowCount = rowCount;
    }

    public int getColumnCount(){
        return columnCount;
    }

    public void setColumnCount(int columnCount){
        this.columnCount = columnCount;
    }

    /**
     * Draws the grid.
     */
    public void initializeGrid() {
        if (this.rowCount > 0 && this.columnCount > 0) {
            this.cellViews = new ImageView[this.rowCount][this.columnCount];
            for (int row = 0; row < this.rowCount; row++) {
                for (int column = 0; column < this.columnCount; column++) {
                    ImageView imageView = new ImageView();
                    imageView.setX((double)column * CELL_WIDTH);
                    imageView.setY((double)row * CELL_WIDTH);
                    imageView.setFitWidth(CELL_WIDTH);
                    imageView.setFitHeight(CELL_WIDTH);
                    this.cellViews[row][column] = imageView;
                    this.getChildren().add(imageView);
                }
            }
        }
    }

    public void update(Model model){
        for (int row = 0; row < this.rowCount; row++){
            for (int column = 0; column < this.columnCount; column++){
                boolean state = model.getCellState(row, column);
                if (state) {
                    this.cellViews[row][column].setImage(this.aliveCell);
                }
                else {
                    this.cellViews[row][column].setImage(this.deadCell);
                }
            }
        }
    }
}

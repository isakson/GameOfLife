Irene Sakson and Cayden Ehrlich

We made a Game of Life simulation, with views that show the current generation and numerical population. The simulation also has a dropdown menu of default cell configurations and a slider that allows the user to control the speed of the simulation.

The Model contains the information in the game grid, including which cells are "alive," the current cell population, and the current generation.
The View contains a view of the 2D grid with the cells displayed, the label displaying the current population, and the label displaying current generation.
The Controller is the interface allowing the user to choose the starting configuration of "living" cells (from the defaults or by manual entry), as well as buttons allowing them to start and stop the simulation or step through generations. The controller also contains the slider, which changes the generation speed.

Our classes are:
    - Cell (represents sprites of live and dead cells within the game and their behavior when clicked on)
    - Controller (implements EventHandler<MouseEvent>; also contains methods to initialize the game, manage the timer, step, manage the slider and stop/start/step buttons, identify cells by mouse click location, update, and obtain information about specific cells.)
    - GamefOfLife extends Application (contains information about the stage, initializes the controller.)
    - Model (contains information about the state of the game and default cell configurations)
    - View (initializes grid, updates model, manages grid characteristics such as row and column count.)
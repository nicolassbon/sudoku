# Java Sudoku

This is a Sudoku game implemented in Java. The project features a graphical user interface (GUI) built with Swing, and has a fallback terminal mode that allows you to play it from the command line in case a graphical environment is unavailable.

## Features

- **Graphical Interface (Swing):** Main interface for comfortable gameplay.
- **Terminal Mode:** Automatic fallback if a "headless" environment is detected (no graphical interface) or if an error occurs when initializing the graphical UI.
- **Configurable:** Allows you to define game configurations through command-line arguments.

## Requirements

- Java Development Kit (JDK) 8 or higher.

## Execution

You can compile and run the project from your favorite IDE (such as IntelliJ IDEA or Eclipse) or from the command line.

### From the command line

1. Compile the source files (make sure you are in the root directory):
   ```bash
   javac -d out $(find src -name "*.java")
   ```
2. Run the main class (including the initial board configuration as arguments):
   ```bash
   java -cp out com.nicolas.Main 0,0;4,false 1,0;7,false 2,0;9,true 3,0;5,false 4,0;8,true 5,0;6,true 6,0;2,true 7,0;3,false 8,0;1,false 0,1;1,false 1,1;3,true 2,1;5,false 3,1;4,false 4,1;7,true 5,1;2,false 6,1;8,false 7,1;9,true 8,1;6,true 0,2;2,false 1,2;6,true 2,2;8,false 3,2;9,false 4,2;1,true 5,2;3,false 6,2;7,false 7,2;4,false 8,2;5,true 0,3;5,true 1,3;1,false 2,3;3,true 3,3;7,false 4,3;6,false 5,3;4,false 6,3;9,false 7,3;8,true 8,3;2,false 0,4;8,false 1,4;9,true 2,4;7,false 3,4;1,true 4,4;2,true 5,4;5,true 6,4;3,false 7,4;6,true 8,4;4,false 0,5;6,false 1,5;4,true 2,5;2,false 3,5;3,false 4,5;9,false 5,5;8,false 6,5;1,true 7,5;5,false 8,5;7,true 0,6;7,true 1,6;5,false 2,6;4,false 3,6;2,false 4,6;3,true 5,6;9,false 6,6;6,false 7,6;1,true 8,6;8,false 0,7;9,true 1,7;8,true 2,7;1,false 3,7;6,false 4,7;4,true 5,7;7,false 6,7;5,false 7,7;2,true 8,7;3,false 0,8;3,false 1,8;2,false 2,8;6,true 3,8;8,true 4,8;5,true 5,8;1,false 6,8;4,true 7,8;7,false 8,8;9,false
   ```

## Project Structure

- `src/com/nicolas/Main.java`: Application entry point. Decides whether to launch the graphical or terminal interface.
- `src/com/nicolas/app/`: Application services and configuration (`BoardService`, `GameConfigParser`).
- `src/com/nicolas/domain/`: Domain logic (Sudoku rules, board, etc).
- `src/com/nicolas/ui/`: User interface implementations (`swing` and `terminal`).

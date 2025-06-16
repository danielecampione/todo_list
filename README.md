# Todo List JavaFX

This is a simple Todo List application built with JavaFX and Maven.

## Prerequisites

- Java Development Kit (JDK) 1.8 or higher
- Apache Maven

## How to Build and Run

1.  **Clone the repository or download the source code.**
2.  **Navigate to the project directory:**
    ```bash
    cd path/to/todo_list
    ```
3.  **Build the project using Maven:**
    ```bash
    mvn clean install
    ```
4.  **Run the application using the JavaFX Maven plugin:**
    ```bash
    mvn javafx:run
    ```

    Alternatively, after building, you can run the generated JAR file (if configured in `pom.xml` for fat JAR creation, which is not by default in the provided `pom.xml`). The `javafx:run` command is the most straightforward way with the current setup.

## Features

- Add new tasks
- View all tasks
- Mark tasks as completed (checkbox will appear next to the task)
- Remove individual tasks
- Remove all completed tasks

## Screenshot

![Png](https://i.ibb.co/DjT2vdF/Immagine-2025-06-16-221645.png)

# 2D Drawing Application Using Java Swing

## Project Overview

This project involved the creation of a desktop 2D drawing application using Java Swing. The application was designed to support various functionalities related to 2D shape manipulation and drawing. It was developed as part of the course "Desing Patterns" with the addition of several advanced features for managing shapes and user interactions.

The application implements the **Model-View-Controller (MVC)** architecture, ensuring a clean separation of concerns between the application logic, user interface, and data representation.

## Key Technologies & Tools Used

- **Java Swing**: Used for building the GUI and handling graphical components in the application.
- **Design Patterns**: Applied various design patterns to enhance the functionality, scalability, and maintainability of the application:
  - **MVC Pattern**: Ensured separation between the user interface (View), data logic (Model), and user input handling (Controller).
  - **Observer Pattern**: Used for managing dynamic user interactions, such as when shapes are selected or modified.
  - **Command Pattern**: Implemented for undo/redo functionality, allowing actions to be reversed or re-executed by the user.
  - **Prototype Pattern**: Applied to facilitate the cloning of shapes and the re-execution of commands.
  - **Adapter Pattern**: Used for adding, modifying, and deleting hexagonal shapes, integrating the `hexagon.jar` library.
  - **Strategy Pattern**: Employed for serialization and saving the drawing state, as well as for handling different strategies for loading and saving the drawing data.
  
## Key Features and Functionalities

- **Shape Drawing**: Users can draw shapes (e.g., circles, hexagons) and customize their properties (edge color, fill color) using the `JColorChooser` class.
- **Transparent Shapes**: Circles with a hole in the center support transparent interior rendering.
- **Undo/Redo Operations**: Implemented using the **Command** and **Prototype** design patterns, allowing users to undo and redo actions like drawing or modifying shapes.
- **Log Management**: The application logs user actions (e.g., drawing, shape selection, undo/redo) and saves them in a text file, providing a persistent record of user actions.
- **Serialization**: Supports saving and loading the complete drawing state to and from external media, ensuring persistence across sessions.
- **Shape Manipulation**: Features for moving shapes on the Z-axis (to front, to back, bring to front, bring to back).
- **Multi-selection & Modification**: Users can select multiple shapes or a single shape, with buttons for adding, modifying, or deleting shapes only available when appropriate.

## Knowledge and Skills Acquired

- **Java Swing**: Gained proficiency in building rich desktop applications using Swing, including event handling and GUI design.
- **Design Patterns**: Applied multiple design patterns such as **MVC**, **Observer**, **Command**, **Prototype**, and **Strategy** to create a flexible, maintainable, and scalable application.
- **Object-Oriented Programming**: Deepened understanding of OOP principles through the use of inheritance, polymorphism, and encapsulation in the application’s design.
- **Data Serialization**: Implemented serialization and file handling for saving and loading application states.
- **Version Control**: Employed **Git** for version control, ensuring a transparent development process with clear commit messages and incremental progress visible on GitHub.

## Conclusion

This project provided hands-on experience with Java GUI development, design patterns, and the practical implementation of key object-oriented concepts. It significantly enhanced my problem-solving skills and deepened my understanding of software architecture principles.

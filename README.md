

# FitSpace-Studio: Interactive Room Design Platform

#### Module Name: PUSL3122 HCI, Computer Graphics, and Visualisation

## Overview:
FurnitureFit is a desktop application aimed at transforming the customer and designer collaboration experience in the furniture designing industry. This tool enables the visualization of how selected furniture pieces, like chairs and tables, fit within a customer's space, considering the room's size, color schemes, and shape. It's designed to enhance the in-store experience by offering functional and aesthetic compatibility assessments of furniture in any given space.


# Overview:
FitSpace-Studio is an interactive platform designed to help users create, manage, and visualize custom room layouts. With real-time 3D visualizations, users can select, place, and adjust furniture in a virtual room environment. It integrates computer graphics and HCI principles to provide a seamless design experience.

### Key Features

- **Room Design**: Create custom room layouts by adjusting room dimensions and placing furniture.
######
- **3D Visualization**: Real-time rendering of the room design with smooth 3D graphics.
######
- **Furniture Catalogue**: Browse and select furniture items to place in the room.
######
- **User Management**: ASecure login, sign-up, and profile management using SQLite database.
######
- **Save & Manage Designs**: Save room designs and edit them later.
######
- **Help & Tutorials**: Access useful guides for creating and managing room designs.

## Technologies Used

This project incorporates a variety of technologies to provide a robust and efficient design experience. Here's a breakdown of the key technologies used:

- **Graphics & Visualization:**: 
            • ***LWJGL***: Lightweight Java Game Library) for handling 3D rendering and user interaction.
            • ***JOGL***: (Java OpenGL) for advanced OpenGL graphics.
###
- **User Interface:**: 
            • ***FLATLAF*** : for a modern and clean Java Swing UI design. 
###
- **Database Management**: 
            • ***SQLite*** : for storing user data, saved designs, and furniture inventory.
###
- **Libraries**: 
            • ***Assimp*** : for importing and displaying 3D models (e.g., furniture items).
###
- **FBX**: A popular, adaptable file format used to store 3D animations, models, and scenes. Incorporating FBX support allows for greater flexibility in importing and exporting 3D content, enhancing the application's interoperability with various design tools and platforms.

## Process and Workflow:

• **User Profile Management**: Users can sign up, log in, and manage their profiles.

• **Room Design Creation**: Input room dimensions and select furniture from the catalogue to start designing.

• **Real-Time 3D Rendering**: Furniture and room designs are visualized in real-time allowing users to interact with the environment.

• **Save & Manage**: Users can save their designs and make future modifications, ensuring continuity across sessions.

• **Database Integration**: All user and design data are stored securely in SQLite for easy retrieval and management.



FitSpace-Studio demonstrates the integration of HCI, computer graphics, and visualization principles to deliver a practical, interactive design tool for users interested in creating personalized room layouts.

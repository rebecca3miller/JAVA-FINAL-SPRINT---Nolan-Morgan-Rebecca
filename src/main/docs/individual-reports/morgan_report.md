# Individual Contribution Report - Morgan

## Assigned Features

In this project, I mainly focased on the Workout class model, Service, and DAO, as well as the same for the Merchandise model, Service, and DAO. I was also responsible for creating a diagram showing all the interactions and relationships within the program.

### The Main classes I worked on:

- WorkoutClass.java
- WorkoutClassDAO.java
- WorkoutClassService.java

- Merchandise.java
- MerchandiseDAO.java
- MerchandiseService.java

I worked on everything needed to make them function together properly, including purchasing merchandise, restocking, revenue and stock reports for admin accounts. The Workout Class has similar features, like adding a class, editing a class, deleting a class, and looking up specific classes based on a trainer's ID number.

## WorkoutClass.java

WorkoutClass.java is the main model for the workout class. It holds the information for a class and works with WorkoutClassDAO to create and manage all classes scheduled for the gym.

The class itself is fairly simple, as it mostly holds the information needed to create, delete, edit, and find a class, along with getters and setters for all the information as well. The DAO is the main engine for the class and wouldn't work without it.

## WorkoutClassDAO

WorkoutClassDAO.java is the driving engine behind the class, as it is responsible for actually creating the classes and managing them. It uses the getters and setters inside the WorkoutClass.java file to access all the necessary information needed to create and manage the different classes.

WorkoutClassDAO.java is also responsible for connecting and communicating with the database that stores all our data and can read, write, retrieve, or update data as needed. It functions as the layer underneath the UI for users and is an important part of how the entire system works. Each different class in the system has their own DAO file to work and communicate together.

## WorkoutClassService.java

The WorkoutClassService.java is responsible for executing commands from the user without having a giant Java file that would slow down the system.

It works closely together with the DAO class to read and display data as needed by the user. If this class were combined with the DAO class instead of being separate, then the system would have to run through hundreds of lines of code in one place, making it more difficult to manage and potentially less efficient. Keeping these two classes separate allows them to communicate with each other while improving the organization and overall user experience.

## Merchandise.java

Merchandise.java is very similar to WorkoutClass.java with a few changes.

The basic idea is the same in that this class stores information and provides getters/setters for the merchandise available in the gym for purchase. Merchandise.java also has its own DAO and service like WorkoutClass.java, but instead of class information, it contains information about the different types of merchandise available for purchase.

## MerchandiseDAO.java

Like the WorkoutClass.java DAO, the MerchandiseDAO.java has the same general function, just with different information and a different table to store data in the database.

## MerchandiseService.java

Also similar to the WorkoutClassService.java file, the MerchandiseService.java has different commands that the user can use, but it still follows the same basic idea and function as the WorkoutClassService.java.

The user can browse all available merchandise and make purchases, with their total being calculated. Admins in the system can also update stock numbers, add or delete merchandise based on the ID number entered, and have a revenue report printed out as well. Regular users are restricted from accessing the manager commands.

## Github Contributions

Our group created individual branches in Github that we each uploaded our code to and could use to check how each member was progressing and see what they were working on. After completing our respective code, we merged all the branches into main and integrated all the code into one program.

### Github Commit Links

- https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/7b27bb8b69fe14949edefb15598dc3ef1d1839a2
- https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/1586d2897ec8e3e55d775564f1bc0c1b196384bd
- https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/d5b69d5020553ef312aa421a3abbf546f6ecbbc6
- https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/cde380e4076dd9067f04933ec1ca6fbaf61c11fd

## Challenges And Problem Solving

One challenge we faced was communicaton and scheduling complcations, as it was sometimes hard to get in contact with everyone. Without proper communication between group members, working can become almost impossible especially when talking about organization, code structure, and what each member is responsible for. Since schedule conflicts are most times unavoidable in life, the solution we came up with was checking our group chat at least once a day to see if anyone was confused, unsure, or had a question. That way, even if we didn't get a response right away, we could move on to other things in the meantime while we waited for an answer and didn't slow down our progress on the code by waiting for potentially hours. This ended up working well for us.

## Skills Learned

A skill I learned was how to communicate better within a group project and be more organized with my code and ongoing projects, as well as improving my time management skills according to deadlines and being ready for anything that was needed in time. for example, having a team meeting and discussing our progress so far and our plan moving forward helped us determine how to improve or fix our code.

I also got to have more practice with these skills throughout the project as well:

- The Java language and syntax
- Databases and the use of Postgres
- Mapping class diagrams
- Optimizing a program's service layer
- Getter and Setter usage

## AI Usage Log

During the project, I used ChatGPT to help me understand a lot of the code and what exactly each line did so that my understanding of the Java language could grow and I could write more types of code on my own without external help. My use of AI was more of a learning tool in that regard, but I also used it to help identify why I had error messages in my code when I wasn't sure what each one meant. AI is also useful for helping break down massive tasks like a final into smaller steps so I feel less overwhelmed and can focus on my current task better without the stress of thinking about the assignment in its entirety. When a person is overwhelmed by the thought of an assignment, they may not be able to do their best work, and the results could suffer. I also used AI as a spell check tool for this report as I struggle a lot with grammer and spelling of some words.

## Team Reflection

Our Team's performance was okay, but could definitely be improved a lot. In my opinion we need to work on our:

- Communication and Timelines
- Better Time Management
- Organizion of Responsibilities

## AI Feature Challenge Reflection

For the feature challenge, I mainly used AI (ChatGPT) to help me break the challenge down into easier-to-understand steps and explain the challenge in a way that made more sense to me.

I also used AI to explain the code because I was having a little trouble understanding everything, since handling files and reading/writing to them is still somewhat new to me. I also used it to understand the file streams and how they work, along with their purposes, better.

### A few examples of prompts I asked were:

- "Can you break this challenge down into steps so it's easier to understand."
- "Why am I getting this error message?" (Picture of error message)
- "Explain the difference of javas different file streams and how the I/O ones work specificly with an example."
- "What happens if the stream crashes or I don't close them properly?"
- "Explain the best way to structure this code and the best place to intergrate it."
- "Can you explain why I need this line of code in this exact spot and what exactly it does?" (Picture of line of code)

### Technical Reflection

I think the most important thing I learned while completing this section was just how important it is to close Java streams when you're done using them. There are several things that could happen if you don't close the streams properly. For example, the data might not be fully writen, it could leave the file locked so that other programs that access or edit the same files might not be able to access them, Java could run out of resources needed to process the code and could stop working entirely, and it could cause resource leaks, which are extremely important to prevent in larger projects and programs.

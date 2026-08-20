# Individual Contribution Report - Rebecca

## Assigned Features

In this project I mainly focased on the Workout class, Service, DAO and the same for the Merchandise class, Service and DAO. I was also resiponable for creating a diagram showing all the interactions and relationships of the program. 

### The Main classes I worked on:

* WorkoutClass.java
* WorkoutClassDAO.java
* WorkoutClassService.java

* Merchandise.java
* MerchandiseDAO.java
* MerchandiseService.java


Including everything to make them function together properly like purchasing merchandise, restocking, reveune and stock reports for admin accounts. The Workout Class has simular features like adding a class, deleting a class, edit a class and looking up specific classes based on a trainers ID number.


## WorkoutClass.java

WorkoutClass.java is the main model for the workout class, it holds the information for a class and works with WorkoutClassDAO to create and manage all classes schedulaed for the gym. 

The class it's self is fairly simple as it mostly just holds the information needed to create/delete/edit/find a class along with getters and setters for all the information as well. The DAO is the main engine for this class and wouldn't work without it.


## WorkoutClassDAO

WorkoutClassDAO.java is the driving engine behind the class as it's the program that is actully creating the classes and managing them. It uses the getters and setters inside the WorkoutClass.java file to access all the nessicary information to create and manage the different classes.

WorkoutClassDAO.java is also the program that connects and talks to the database that stores all our data and can read, write, retrive or update data as needed. It functions as the layer underneith the UI for users and is how the enitire system works, and each different class in the system has their own DAO file to work and comunicate together.


## WorkoutClassService.java

The WorkoutClassService.java is responisable for excuting commands by the user without having a giant java file that would slow down the system. 

It works tightly together with the DAO class to read and display data as needed by the user. If this class was combined with the DAO class instead of being seperate then the system would waste a lot of time running through hundreds of lines of code and be extremly slow, where as when these two classes are kept secret they can talk to eachother and work much faster improving the user expericeance and saving them time.


## Merchandise.java

Merchandise.java is very simaler to WorkoutClass.java with a few changes.

The basic idea is the same in that this class stores information and getters/setters for the merchandise in the gym for purchase. Merchandise.java also has it's own DAO file and service like WorkoutClass.java but instead of class information its replaced with information about the different types of merchandise available for purchase.


## MerchandiseDAO.java

Again like the WorkoutClass.java DAO the MerchandiseDAO has the same function just with different information and a different table to store data on the database.


## MerchandiseService.java

Also similar to the WorkoutService.java file, the MerchandiseService has different commands the user can use but still the same idea and basic function as the WorkoutService.java.

The user can browse all available merchandise and purchase with their total calculated. Admins in the system can also update stock numbers, add or delete merchandise based on ID number entered and have a revune report printed out as well. Regular users are blacklisted from the manager commands.


## Github Contributions

Our group made individual branches in github that we each uploaded our code to and could check how each member was doing and see what they were working on. After completing our respective code we merged all branches into main and intergraded all the code as one program.

### Github Commit Links

* https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/7b27bb8b69fe14949edefb15598dc3ef1d1839a2
* https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/1586d2897ec8e3e55d775564f1bc0c1b196384bd
*


## Challenges And Problem Solving

One challenge we faced was comunication and schelduling complcations, where it was some times hard to get in contact with everyone. Without proper comunication between group members working together become almost impossiable espially when talking about organization or code structure and what each member is resiponiable for. Since scheldule conflict is most times unavoidable in life the soulition we came up with was checking into our groupchat at least once a day to see if anyone was confused/unsure or had a question. That way even if we didn't get a response right away we could move on to other things in the mean time while we waited for an answer and didn't slow down our progress on the code from waiting for possiable hours, this ended up working well for us.


## Skills Learned

A skill I learned was how to comunicate better within a group project and be more organized with my code and ongoing projects, as well as better time management according to deadlines and being ready for anything needed in time. for example haveing a team meeting and discussing your progress so far and your plan moving forward on how to improve or fix your code.

I also got to have more practice with these skills throughout the project as well:

* The Java language and syntax
* Databases and use of Postgres
* Mapping class diagrams
* Optimizing a programs service layer
* Getter/Setter use


## AI Usage Log

During the length of this project I used ChatGPT to help me understand a lot of the code and what exactly each line did so my understanding of the java language would grow and I could write more types of code on my own without external help. My use of AI is more of a learning tool in that reguard but I also used it to help identify why I had error messages appear in my code when I wasn't sure what each one ment. AI is also useful to help break down massive tasks like a final into bite size steps so I feel less overwhelmed and can focas on my current task better without the stress of thinking about the assignment in it's entirity, because if a person is overwhelmed by the thought of the assignment they aren't able to do their best work and the results could suffer.


## Team Reflection

Our Team preformance was ok but could deffinatly be improved a lot, in my opion we need to work on our:

* Comunication and Timelines
* Better Time Management
* Organizion of responseablitlys


## AI Feature Challenge Reflection

For the feature challenge I mainly used AI (ChatGPT) to help me break the challenge down into easier to understand steps and to explain the challenge that made more sense to me and how I think. 

I also used AI to explain the code because I was having a little trouble understanding everything since handling files and reading/writing to them is still somewhat new to me. I also used it to understand the file streams and how they work along with their purposes better.

### A few examples of prompts I asked were:

* "Can you break this challenge down into steps so it's easier to understand."
* "Why am I getting this error message?" (Picture of error message)
* "Explain the difference of javas different file streams and how the I/O ones work specificly with an example."
* "What happens if the stream crashes or I don't close them properly?"
* "Explain the best way to structure this code and the best place to intergrate it."
* "Can you explain why I need this line of code in this exact spot and what exactly it does?" (Picture of line of code)


### Technical Reflection

I think the most important thing I learned while completing this section was just how important it is to close java streams when your done using them. Theres a few things that could happen if you don't close the streams properly like: the data might not fully get writen, it could leave the file locked so other programs that access or edit the same files might not be able to access them, java can run out of resources to process the code and could stop working entirely, and it prevents resource leaks which are extremly important in larger projects and programs.
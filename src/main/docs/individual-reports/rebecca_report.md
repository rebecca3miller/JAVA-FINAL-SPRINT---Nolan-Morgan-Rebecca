# Individual Contribution Report - Rebecca

## Assigned Features

My main part of the project was the Membership Management section. I worked on the classes needed to purchase and manage memberships and get membership expense and revenue information.

The main classes I worked on were:

* `Membership.java`
* `MembershipDAO.java`
* `MembershipService.java`

## Membership.java

I created the `Membership` class to hold the information for a gym membership. This includes information such as the membership ID, user ID, membership type, and price.

I used constructors, getters, and setters to create and access the membership information. I kept this class fairly simple because its main purpose is to represent the membership information that is being used throughout the program.

## MembershipDAO.java

I worked on the `MembershipDAO` class to handle the database side of the membership system.

This class connects the membership section to PostgreSQL and handles the SQL needed for membership information. This included adding membership purchases to the database and getting membership information back from the database when it is needed.

I also used try/catch blocks to handle SQL errors so that if something goes wrong with a database operation, the program can handle the error instead of immediately crashing.

## MembershipService.java

I created the `MembershipService` class to handle the membership functions used by the rest of the application.

The service works with the DAO instead of putting all of the database code directly into the menu or other classes. This helped me understand more about why projects separate the application into different layers instead of putting everything into one Java file.

The membership service handles functions related to purchasing memberships, viewing a user's membership expenses, and getting the total membership revenue that can be viewed by an Admin.

## GitHub Contributions

I completed my work on my own GitHub branch and made commits as I completed different parts of the membership section. After the code was completed and ready to be added to the rest of the project, the work was added to the main branch with the rest of the group's code.


## Challenges & Problem Solving

One challenge we ran into during the project was accidentally getting two of our assigned sections mixed up. I had started working on the Membership section, but that section was originally assigned to another group member. When they checked GitHub and noticed that I had already completed work on Memberships, they reached out to me to make sure we had not both started working on the same thing.

Instead of both of us continuing and ending up with duplicated Java code, they worked on the section that was originally assigned to me and we switched our responsibilities. This worked out well because neither person's work had to be deleted or redone.

This showed me how important communication and regularly checking GitHub are when working on a group project. Since everyone was working on separate branches and could see each other's progress, we were able to notice the mix-up early and solve it without losing any of the work we had already completed.

## Skills Learned

This project helped me get more comfortable with separating Java code into different classes based on what each class is responsible for.

I also got more practice with:

* Java classes and objects
* Constructors, getters, and setters
* DAO and service classes
* JDBC
* PostgreSQL
* SQL queries in Java
* Try/catch exception handling
* Git branches and commits
* Working with other people's code in a group project

One of the biggest things I learned was how the Java side and database side of an application work together. Earlier projects made more sense individually, but this project helped me see how multiple pieces can connect together into one larger program.

## Team Reflection

Splitting the project into different sections made it easier for everyone to work on their own features without all editing the same files at once. We used separate GitHub branches for our work and then brought everything together on the main branch.

One thing that was important was communication about who was responsible for each feature. There was some confusion over the assigned sections at one point, but we were able to figure it out and continue with the work without having two people complete the same section.

Overall, working as a group gave me more experience with how separate pieces of code have to eventually work together as one application.

## AI Usage Log

I used ChatGPT throughout the project mainly as a learning and troubleshooting tool. I used it when I needed a concept explained in a different way, wanted help understanding what my next step should be, or needed help figuring out why something in my code was not working.

For my Membership section, I used AI to help me understand the purpose of the `Membership`, `MembershipDAO`, and `MembershipService` classes and how they work together. I also used it to better understand JDBC, database operations, testing, Git commands, and error handling.

I tried to use AI as a way to support what we were learning in class rather than relying on it to complete the project for me. When I asked for code examples, I kept them similar to the Java style we were taught so that I could understand what each part was doing and explain it myself.


## AI Feature Challenge Reflection

### How I Used AI

For the File Export Challenge, I used ChatGPT mainly to help me understand Java File I/O and break the feature down into smaller steps before implementing it. Since file handling was a newer part of Java for me, I found it more helpful to ask questions about what each part was doing instead of just getting a completed method that I would have to try to understand afterwards.

I used AI to explain how Java checks if a folder exists, how a program can create a folder when it is missing, and how information can be written to a text file. I also used it to better understand why file streams need to be closed and what problems can happen if resources are not handled correctly.

When working on the feature, I tried to keep the implementation similar to the Java we have been using in class. I used the explanations to help me understand what I needed to do, then tested the feature in our project and made sure the generated report contained the correct information.

### Example AI Prompts

Some of the questions I used while learning and working through the feature were:

* "Can you explain Java File I/O in simple terms and how it applies to the report feature in this project?"

* "What steps would I need to follow to export membership revenue from my program into a text file?"

* "How can I check if a reports folder already exists before trying to create one in Java?"

* "Can you explain the difference between creating a folder, creating a file, and writing information to the file?"

* "How does Java take information that I retrieved from PostgreSQL and write it into a text file?"

* "Why do I need to close the file writer after I am finished using it?"

* "What could happen if the program crashes before a file stream is properly closed?"

* "How should I use try/catch when working with files, and what type of errors am I trying to handle?"

* "Can you explain the file export code line by line so I understand what each part is doing?"

* "What should I test after I finish my report export method to make sure it is actually working correctly?"

### Technical Reflection

One thing I learned from this feature was that opening and writing to a file also means the program is using a resource that needs to be properly managed. Closing the file stream is important because it makes sure the program is finished with the resource and that the information being written has been properly saved.

If the program crashes or stops before the stream is properly closed, there is a chance that some of the information may not finish writing to the file. It can also leave resources open longer than they should be. This helped me understand why file handling needs error handling and why resources should always be properly closed when the program is finished with them.

I also learned that exporting a report involves more than just creating a text file. The program first needs to get the correct information, make sure the report folder exists, create or open the correct file, format the information so that it is readable, write the information, and then properly close the resources.

### Code Showcase




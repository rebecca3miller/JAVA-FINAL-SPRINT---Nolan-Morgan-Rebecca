# Individual Contribution Report - Nolan

## Assigned Features
- User and Authentication
    - registration
    - bcrypt
    - role handling
    - Files:
        - Admin.java
        - Authorization.java
        - Member.java
        - Trainer.java
        - User.java
        - UserDAO.java
        - UserService.java

- Database Connection
    - Files:
        - DatabaseConnection.java

- Report export
    - Merchandise report
    - membership report
    - Files:
        - reportExport.java

- Logger
    - Built custom logger with java.util.logger
    - File:
        - CustomLogger.java

- User entry file
    - Built the UserMain RBAC UI and connected the role menus to the services.
    - File:
        - UserMain.java


## GitHub Contributions

Repository: [Gym Management System](https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca)

My branch: [Nolan](https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/tree/Nolan)

Pull request: [Pull Request #4 - Nolan](https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/pull/4)

Relevant commits in Nolan Branch:

- [Completed User logic](https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/ae2a1223198d62a9054c43dcae6855c7baa86604)
- [Updated password protection and registration/update validation](https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/10eab014689ce336b316c63951cf5177bb83789e)

Relevant commits in Main branch

- [Updated folder structure and added required blank files for final integration](https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/28f670bc9fd50da470cf1a8df44ab09bd7da4b8d)
- [Added pom, logger, database connection and small user changes](https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/3704f8bdc436c080faac016e0db04ba74189050d)
- [Refactored RBAC across system and removed duplicate logic](https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/9764cd19e35a9e5b9a65373382dae06f12e77965)
- [Main method and app entry point](https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/27f9fe734ab9261cdeb6d3dd18051513595604f7)
- [Fixed package imports across system](https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/c8ad90fdde3890b7f124a372d22c472e70d57e2f)
- [Added file export action](https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/08811c65a6afbbd15a50adfebc8af67c57e26ee3)
- [Changed custom logger to use java.util.logging](https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/a12f5421c378bfa41f678fc6304b45a95f5b7c96)
- [Added thorough logger integration and Admin report export action](https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/d6d894f135468694cbf4a7c1ef15486efbc3fdb0)
- [Fixed dependencies and gitignore](https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/e85d4b505f899e35480ae561b15d4bf43bc97839)
- [Small package name fixes](https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/c5e6c0dc3def09cc20ad71d3fc3d480123c8b349)
- [Small clarity changes and missing function](https://github.com/rebecca3miller/JAVA-FINAL-SPRINT---Nolan-Morgan-Rebecca/commit/b4bab9aa241165161599a93a84cd3f31d27f9155)


## Challenges & Problem Solving

One technical challenge was integrating the user, authentication, and RBAC code with the rest of the team's packages. Some classes initially had package or import mismatches, and authorization logic was duplicated in different parts of the application. I solved this by correcting the package references, centralizing the permission checks in `Authorization`, and applying the checks in the service methods as well as the menu. I then used Maven compilation and tests to confirm that the changes worked with the rest of the project. This taught me that consistent package organization and service-layer authorization are important when combining work from multiple branches.

## Skills Learned
I learned the differences between sets and lists. I used them in UserService[11]. I became more familiar with the logging utility and creating a custom logger. I also worked on implementing authentication at both the service and menu levels. Jordan's example used menu-layer authentication for simplicity, but service-layer authentication felt safer in case someone were to call the public methods directly outside just the UI. Only server-side security will truly be safe in a modern app. Map.of allows an unmodifiable string list of actions, compared to a mutable HashMap.

## AI Usage Log
When creating this project, I used AI to complete monotonous getters and setters. This allowed me to build basic object classes faster and focus on the logic. I occasionally asked for utility or optimization recommendations. AI also allowed me to generate quick test files for a multitude of features.

The AI tool I used was GitHub Copilot. I reviewed its suggestions and adapted them to fit the project's existing code.

## Team Reflection
Team performance was okay. We needed more consistent communication and work to prevent rushing and waiting on others.

The team could improve by agreeing on feature ownership earlier, communicating more consistently about progress, and allowing more time for integration testing before the deadline.

## Feature Challenge Reflection

### AI Dialogue
I mainly used AI in figuring out how to check for folder existence and create one if necessary. Some of the prompts I used included:

- How do I check whether a reports folder exists in Java?
- How do I create the reports folder if it does not exist?
- Why should a BufferedWriter be closed after writing a file?
- How does try-with-resources close Java file and database resources?

### Technical Reflection

Closing file streams is important because it flushes buffered content to the file and releases the system resources used by the stream. In the report export feature, try-with-resources closes the `BufferedWriter`, `PreparedStatement`, and `ResultSet` automatically after the report is generated, including when an exception occurs. If the program crashes before a stream is closed, the report may be incomplete, buffered data may not be written, and the file or database resources may remain open. Using try-with-resources makes the export process more reliable and prevents these resources from being left open.

### Code Showcase

```java
public void getMerchandiseReport(User requestingUser) {
    try {
        Authorization.requireRole(requestingUser, "Export merchandise report", "Admin");
    } catch (IOException e) {
        System.err.println("Access denied. Admin role required.");
        return;
    }

    String sql = "SELECT * FROM merchandise";
    Path reportFile;
    try {
        Files.createDirectories(REPORTS_DIR);
        reportFile = REPORTS_DIR.resolve(MERCHANDISE_REPORT_FILE);
    } catch (IOException e) {
        CustomLogger.logError("Failed to create /reports/ directory.", e);
        return;
    }

    try (BufferedWriter writer = Files.newBufferedWriter(reportFile);
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {
        double totalInventoryValue = 0;
        writer.write("Merchandise Inventory Report");
        writer.newLine();
        writer.write("========================================");
        writer.newLine();

        while (resultSet.next()) {
            String itemName = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            int stock = resultSet.getInt("stock");
            double itemValue = price * stock;
            totalInventoryValue += itemValue;
            writer.write(String.format("Item: %s, Price: $%.2f, Stock: %d, Value: $%.2f%n",
                    itemName, price, stock, itemValue));
        }

        writer.write("========================================");
        writer.newLine();
        writer.write(String.format("Total inventory valuation: $%.2f%n", totalInventoryValue));
    } catch (IOException | SQLException e) {
        CustomLogger.logError("Failed to export merchandise report.", e);
    }
}
```
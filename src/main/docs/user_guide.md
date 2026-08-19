# Gym Management System User Guide

## System Overview

The Gym Management System is a Java console application made to help manage different parts of a gym. The system allows users to create an account and log in as either an Admin, Trainer, or Member. Depending on the user's role, they will have different options available to them.

The system can be used to manage memberships, workout classes, merchandise, users, and other gym information. All of the information is stored in a PostgreSQL database.

## User Roles

There are three different user roles in the system.

### Admin

The Admin has the most control over the system. An Admin can view users and their contact information, delete users, view total membership revenue, manage merchandise, and create, update, or delete workout classes.

### Trainer

A Trainer can create and manage workout classes and view the classes they are assigned to. Trainers are also able to purchase a gym membership and view merchandise that is available.

### Member

A Member can purchase a gym membership, browse available workout classes, view merchandise, and see how much they have spent on memberships.

## Logging In

When the program starts, the user can log into their account using their username and password.

After successfully logging in, the menu displayed will depend on the user's role. For example, a Member will not see options that are only available to an Admin.

This helps make sure users only have access to the parts of the system that they are supposed to use.

## Purchasing a Membership

Members and Trainers are able to purchase gym memberships.

To purchase a membership:

1. Log into the system.
2. Select the option to purchase a membership.
3. Choose the membership that you would like to purchase.
4. Follow the instructions displayed in the console.
5. The membership purchase will then be saved to the database.

Members can also view their total membership expenses after purchasing memberships.

## Workout Classes

Workout classes can be managed by Admins and Trainers.

Trainers can create and manage their workout class schedules and view the classes they are assigned to.

Members can browse the workout classes that are currently available so they can see what classes the gym offers.

## Merchandise

The system also keeps track of gym merchandise such as food, drinks, and workout gear.

Admins can add merchandise, change prices, restock items, and view the current inventory.

Members and Trainers can view the merchandise that is available.

## System Limitations

This project is a console-based application, so it does not have a graphical user interface or website.

The system also does not process real payments. Membership and merchandise purchases are recorded by the application, but the actual payment would need to be handled separately.

The application also requires a connection to the PostgreSQL database for database features to work.

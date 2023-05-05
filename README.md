## EVENT_PLANNING
###### The Event Planning System allows users to plan and manage their events efficiently. The system includes user authentication and registration, event details input, checking for available dates, vendor requirements input and filtering, payment updates and status tracking. Users can create an account, input event details such as the name, date range, budget, and location preference, and the system checks for available dates and locations. Users can also input their vendor requirements, including the vendor type, budget allocation, and specific requirements, and the system generates a list of suitable vendors based on user criteria and budget. The system also includes payment processing and status tracking to provide users with an overview of their event planning progress. Overall, the Event Planning System helps users plan and organize their events more efficiently by automating the planning and management process.


#### Main Method
###### The main method  allows a user to register or log in, and then plan an event. The program prompts the user to enter the type of event, the number of guests, and then presents a menu of options to choose from.The options include checking date availability, booking a location, booking a caterer, booking a decoration team, checking the status of the event, and canceling the event.The program maintains a file called "Event details.txt" that keeps track of the user's event information, including the event date, location ID, caterer ID, decoration ID, and any reference numbers for payments.

#### Userregisteration function
######  The code defines a method named userregistration() that handles user registration in an event planning system. The method prompts the user to enter their details, including username, password, name, address, city, email, phone number, Adhaar number, and account number.

#### UserLogin function
###### This Java code defines a static method named "userLogin", which is responsible for authenticating a user by verifying their username and password against records stored in a databaseThe method prompts the user to enter their username and password using the standard input stream. It then establishes a connection to the MySQL database and queries the "user" table to check if the entered username exists. If it does, the method retrieves the corresponding password and verifies it against the entered password using the "passwordverification" method. If the password is correct, the method returns the username. If the entered username does not exist in the database, the method prompts the user to enter their correct username and password again. If the entered username is still incorrect, the method calls the "userregistration" method, which allows the user to register as a new user.

#### Date Availabilty function
###### The dateavailability() function checks whether a particular date is available for booking an event and returns the date if it is available, or null if it is not available. 

#### Location Booking function
###### The locationbooking() function then takes the username and the event date as input, and retrieves a list of available locations in the user's city on the specified date. It prompts the user to select a preferred location and makes an advance payment for booking the location. The function returns the ID of the selected location. 

#### Caterer function
###### The method "caterer" that takes a String parameter "username" and returns an integer value "caterer_id". The method interacts with a MySQL database to retrieve and display caterer information based on the user's food preference (vegetarian, non-vegetarian, or both) and city. The user is prompted to select their preferred caterer, and the method returns the corresponding caterer ID from the database. If an exception occurs during the database interaction, the method prints the exception message.

#### Decoration function
###### This is a method that takes in a username as input and returns an integer value representing the decoration team selected by the user. The method connects to a MySQL database, retrieves the user's city from the database, and then displays the available decoration teams for that city. The user is prompted to select a preferred decoration team, and the method returns the decoration ID of the selected team from the database.

#### Event Status Function
###### The eventStatus method takes three arguments: the username of the user who created the event, the filename of a file to write the output to, and a boolean value called cancellation.

#### Cancellation function
###### This is a Java method named cancellation that takes a string parameter username. The purpose of this method is to allow a user to cancel an event they have registered for. If the user chooses to cancel, the method will delete the corresponding event record from a MySQL database, and initiate a refund of 75% of the payment to the user's account number. The method returns a boolean value of true if the cancellation is successful, and false if the user chooses not to cancel. 



#### SQL QUERY USEWD FOR CREATING DATABASE:
CREATE DATABASE event_planning;
USE event_planning;



User table
CREATE TABLE user (
  user_id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  name VARCHAR(100) NOT NULL,
  address VARCHAR(200),
  city VARCHAR(50),
  email VARCHAR(100),
  phone_number VARCHAR(20),
  adhaar_number VARCHAR(20),
  account_number VARCHAR(20),
  PRIMARY KEY (user_id)
);



 desc user;
+----------------+--------------+------+-----+---------+----------------+
| Field          | Type         | Null | Key | Default | Extra          |
+----------------+--------------+------+-----+---------+----------------+
| user_id        | int          | NO   | PRI | NULL    | auto_increment |
| username       | varchar(50)  | NO   |     | NULL    |                |
| password       | varchar(50)  | NO   |     | NULL    |                |
| name           | varchar(100) | NO   |     | NULL    |                |
| address        | varchar(200) | YES  |     | NULL    |                |
| city           | varchar(50)  | YES  |     | NULL    |                |
| email          | varchar(100) | YES  |     | NULL    |                |
| phone_number   | varchar(20)  | YES  |     | NULL    |                |
| adhaar_number  | varchar(20)  | YES  |     | NULL    |                |
| account_number | varchar(20)  | YES  |     | NULL    |                |
+----------------+--------------+------+-----+---------+----------------+


Event table
CREATE TABLE event (
  event_id int NOT NULL AUTO_INCREMENT,
  event_name varchar(255),
  event_date date,
  guest_number int,
  user_id int,
  location_id int,
  caterer_id int,
  decoration_id int,
  adv_ref varchar(50),
  final_ref varchar(50),
  PRIMARY KEY (event_id),
  FOREIGN KEY (user_id) REFERENCES user(user_id),
  FOREIGN KEY (location_id) REFERENCES location(location_id),
  FOREIGN KEY (caterer_id) REFERENCES caterer(caterer_id),
  FOREIGN KEY (decoration_id) REFERENCES decoration(decoration_id)
);


 desc event;
+---------------+--------------+------+-----+---------+----------------+
| Field         | Type         | Null | Key | Default | Extra          |
+---------------+--------------+------+-----+---------+----------------+
| event_id      | int          | NO   | PRI | NULL    | auto_increment |
| event_name    | varchar(255) | YES  |     | NULL    |                |
| event_date    | date         | YES  |     | NULL    |                |
| guest_number  | int          | YES  |     | NULL    |                |
| user_id       | int          | YES  | MUL | NULL    |                |
| location_id   | int          | YES  | MUL | NULL    |                |
| caterer_id    | int          | YES  | MUL | NULL    |                |
| decoration_id | int          | YES  | MUL | NULL    |                |
| adv_ref       | varchar(50)  | YES  |     | NULL    |                |
| final_ref     | varchar(50)  | YES  |     | NULL    |                |
+---------------+--------------+------+-----+---------+----------------+


Location table
CREATE TABLE location (
  location_id int NOT NULL PRIMARY KEY,
  location_name varchar(50) NOT NULL,
  address varchar(100) NOT NULL,
  city varchar(50) NOT NULL,
  rate int NOT NULL,
  phone_number varchar(20) NOT NULL,
  guest_availability int NOT NULL,
  license_number varchar(20) NOT NULL,
  email varchar(50) NOT NULL
);

 desc location;
+--------------------+--------------+------+-----+---------+-------+
| Field              | Type         | Null | Key | Default | Extra |
+--------------------+--------------+------+-----+---------+-------+
| location_id        | int          | NO   | PRI | NULL    |       |
| location_name      | varchar(50)  | NO   |     | NULL    |       |
| address            | varchar(100) | NO   |     | NULL    |       |
| city               | varchar(50)  | NO   |     | NULL    |       |
| rate               | int          | NO   |     | NULL    |       |
| phone_number       | varchar(20)  | NO   |     | NULL    |       |
| guest_availability | int          | NO   |     | NULL    |       |
| license_number     | varchar(20)  | NO   |     | NULL    |       |
| email              | varchar(50)  | NO   |     | NULL    |       |
+--------------------+--------------+------+-----+---------+-------+




Decoration table:
CREATE TABLE decoration (
  decoration_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  enterprise_name VARCHAR(255),
  location VARCHAR(255),
  rate INT,
  phone_number VARCHAR(20),
  license_number VARCHAR(20),
  email VARCHAR(255)
);



 desc decoration;
+-----------------+--------------+------+-----+---------+-------+
| Field           | Type         | Null | Key | Default | Extra |
+-----------------+--------------+------+-----+---------+-------+
| decoration_id   | int          | NO   | PRI | NULL    |       |
| enterprise_name | varchar(255) | YES  |     | NULL    |       |
| location        | varchar(255) | YES  |     | NULL    |       |
| rate            | int          | YES  |     | NULL    |       |
| phone_number    | varchar(20)  | YES  |     | NULL    |       |
| license_number  | varchar(20)  | YES  |     | NULL    |       |
| email           | varchar(255) | YES  |     | NULL    |       |
+-----------------+--------------+------+-----+---------+-------+


Caterer table
CREATE TABLE caterer (
  caterer_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  enterprise_name varchar(255),
  location varchar(255),
  rate_per_head int,
  phone_number varchar(20),
  license_number varchar(255),
  veg tinyint(1),
  non_veg tinyint(1),
  email varchar(255)
);

 desc caterer;
+-----------------+--------------+------+-----+---------+-------+
| Field           | Type         | Null | Key | Default | Extra |
+-----------------+--------------+------+-----+---------+-------+
| caterer_id      | int          | NO   | PRI | NULL    |       |
| enterprise_name | varchar(255) | YES  |     | NULL    |       |
| location        | varchar(255) | YES  |     | NULL    |       |
| rate_per_head   | int          | YES  |     | NULL    |       |
| phone_number    | varchar(20)  | YES  |     | NULL    |       |
| license_number  | varchar(255) | YES  |     | NULL    |       |
| veg             | tinyint(1)   | YES  |     | NULL    |       |
| non_veg         | tinyint(1)   | YES  |     | NULL    |       |
| email           | varchar(255) | YES  |     | NULL    |       |
+-----------------+--------------+------+-----+---------+-------+



















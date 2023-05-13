## EVENT_PLANNING
###### The Event Planning System allows users to plan and manage their events efficiently. The system includes user authentication and registration, event details input, checking for available dates, vendor requirements input and filtering, payment updates and status tracking. Users can create an account, input event details such as the name, date range, budget, and location preference, and the system checks for available dates and locations. Users can also input their vendor requirements, including the vendor type, budget allocation, and specific requirements, and the system generates a list of suitable vendors based on user criteria and budget. The system also includes payment processing and status tracking to provide users with an overview of their event planning progress. Overall, the Event Planning System helps users plan and organize their events more efficiently by automating the planning and management process.

### Methods Explanation : 
#### Main Method :
###### The main method  allows a user to register or log in, and then plan an event. The program prompts the user to enter the type of event, the number of guests, and then presents a menu of options to choose from.The options include checking date availability, booking a location, booking a caterer, booking a decoration team, checking the status of the event, and canceling the event.The program maintains a file called "Event details.txt" that keeps track of the user's event information, including the event date, location ID, caterer ID, decoration ID, and any reference numbers for payments.

#### Userregisteration function :
######  The code defines a method named userregistration() that handles user registration in an event planning system. The method prompts the user to enter their details, including username, password, name, address, city, email, phone number, Adhaar number, and account number.

#### UserLogin function :
###### This Java code defines a static method named "userLogin", which is responsible for authenticating a user by verifying their username and password against records stored in a databaseThe method prompts the user to enter their username and password using the standard input stream. It then establishes a connection to the MySQL database and queries the "user" table to check if the entered username exists. If it does, the method retrieves the corresponding password and verifies it against the entered password using the "passwordverification" method. If the password is correct, the method returns the username. If the entered username does not exist in the database, the method prompts the user to enter their correct username and password again. If the entered username is still incorrect, the method calls the "userregistration" method, which allows the user to register as a new user.

#### Date Availabilty function :
###### The dateavailability() function checks whether a particular date is available for booking an event and returns the date if it is available, or null if it is not available. 

#### Location Booking function :
###### The locationbooking() function then takes the username and the event date as input, and retrieves a list of available locations in the user's city on the specified date. It prompts the user to select a preferred location and makes an advance payment for booking the location. The function returns the ID of the selected location. 

#### Caterer function :
###### The method "caterer" that takes a String parameter "username" and returns an integer value "caterer_id". The method interacts with a MySQL database to retrieve and display caterer information based on the user's food preference (vegetarian, non-vegetarian, or both) and city. The user is prompted to select their preferred caterer, and the method returns the corresponding caterer ID from the database. If an exception occurs during the database interaction, the method prints the exception message.

#### Decoration function :
###### This is a method that takes in a username as input and returns an integer value representing the decoration team selected by the user. The method connects to a MySQL database, retrieves the user's city from the database, and then displays the available decoration teams for that city. The user is prompted to select a preferred decoration team, and the method returns the decoration ID of the selected team from the database.

#### Event Status Function :
###### The eventStatus method takes three arguments: the username of the user who created the event, the filename of a file to write the output to, and a boolean value called cancellation.

#### Cancellation function :
###### This is a Java method named cancellation that takes a string parameter username. The purpose of this method is to allow a user to cancel an event they have registered for. If the user chooses to cancel, the method will delete the corresponding event record from a MySQL database, and initiate a refund of 75% of the payment to the user's account number. The method returns a boolean value of true if the cancellation is successful, and false if the user chooses not to cancel. 



#### SQL QUERY USED FOR CREATING DATABASE:
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

### Output Documentation : 
#### This is the outlook of our program : 
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/8c643b3a-74a0-477c-a5e3-37c87eb1b212)" width="200" height="50">

#### When user(case insensitive) is typed in and the option to register is opted :
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/5923be8c-ba60-45ca-b0d3-856c8a92ba04" width="400" height="500">

##### If the option to login is taken : 
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/2eb55eed-f037-4a58-b1ca-c8ce4ce68b40" width="400" height="200">

##### After login or registration is completed, the details about the event is asked from the user : 
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/af60080b-f87f-4cf9-84c0-51f6741c96e5" width="350" height="150">

##### Main menu is displayed and the user is asked to choose : 
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/0b03d78c-6b75-4b8a-b976-6fe75db4e5b3" width="170" height="230">

##### On choosing the first option “Date Availability”, the user will be able to check whether the date of the event is booked or not :
###### If the date is available : 
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/e85b0694-997b-4b6e-932a-611b2ba4c426" width="480" height="120">

###### If the date is not available : 
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/a197586d-f3ca-4c21-b284-c48864b62341" width="400" height="100">

##### On choosing the second option “ Location Booking” A list of available locations is given and the user is asked to select. After the location is selected the user is asked for the advance payment reference number : 
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/177d2052-af94-48f1-b413-ce7b53334a46" width="650" height="300">

##### On choosing the third option “Caterer Booking”, list of caterers available is given and the user is asked to select :
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/89c2e132-212f-4c5e-b8f5-ad97f7153b4c" width="450" height="250">

##### On choosing the fourth option “Decoration Team booking”, the user is given a list of available décor teams and they are asked to choose. Upon choosing the décor team, the user is asked to give the reference number of the final payment : 
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/8b1bd036-b2ec-4bab-99bc-b37604979552" width="480" height="280">

##### On choosing the fifth option “Event Status”, the event status is displayed :
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/4f680c3f-2738-4f50-affd-562950d6b2b7" width="410" height="200">

##### On choosing the sixth option “Cancellation”,the user is asked to confirm cancellation by pressing 5 :
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/8551ab12-ba68-44a0-b123-44c32f7d4c85" width="950" height="100">

#### After the initial outlook if the user selects admin(case insensitive) user type : 
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/9e151ca0-d491-407b-a7d9-a2ae0b74f8e7" width="280" height="100">

##### Main menu is displayed and the admin is asked to choose : 
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/f81fed11-7e62-4bdd-9670-3fce7beecd13" width="160" height="70">

##### If the admin chooses 1 user information is displayed :
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/9f751519-2851-4427-8977-d21df6dd2a60" width="750" height="540">

##### If the admin chooses option 2, the analysis is displayed. The most popular locations,prefered caterers and decoration team is displayed and also the most popular event is displayed : 
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/6306e34b-d38f-49ce-8fee-fd10619fde9f" width="300" height="700">

##### If the admin chooses option 3, analysis of the data in the form of histogram is displayed :
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/0d4247d8-4dde-49a3-bda0-4aa2a9e8eb6c" width="340" height="600">

##### If the admin chooses option 4 event status is displayed :
<img src="https://github.com/Niranjana2001/EVENT_PLANNING/assets/118504444/d71236fa-8e46-48b4-b373-919dae37f10b" width="850" height="350">
















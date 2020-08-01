# ShopManagementSystem 2.6.3

A simple Java application for the management of a clothing store with the functionality of:
* Cash management and receipt issue.
* Profile management with the possibility of uploading photos and a report of the total sales.
* Warehouse management with addition, removal of garments and related photos.
* Supplier management.
* Employee Management

#### Update Versione 2.6.3:

* Correction about DAO classes:
	* Add an abstract superclass with CRUD (**C**reate **R**ead **U**pdate **D**elete) functions and create a singleton class for the database.

********
## Index
[Getting Started](#getting-started)

[Installing](#installing)

[Running Program](#running-the-program)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites
- [IDE Eclipse](https://www.eclipse.org/downloads/packages/installer)
- WindowBuilder for Swing
  1. In the top bar of Eclipse click on Help
  2. Click on Eclipse Marketplace
  3. In the search bar (Find) search for WindowBuilder
  4. Install
- [PostgreSQL ](http://databasemaster.it/installare-postgresql-su-windows)
 

### Installing

* Download the repository and extract the folder / zip file containing the code on the desktop

![Aprire Progetto](http://g.recordit.co/m7xGiMQU73.gif)

* Now that we have our code, let's put it aside for a moment and take care of the database.
 * Open pgAdmin (graphical tool for managing postgresql)
 
![Aprire Database](http://g.recordit.co/nOwYLWzhl6.gif)

* Last step before final use.
 * Let's go to the Database class in the package model and change the access credentials to our database.
 
 ```java
try {
			
			Class.forName("org.postgresql.Driver");                                        
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "<DBUsername>", "<DBPassword>");
			return con;
			
		} catch (SQLException | ClassNotFoundException e){e.printStackTrace();
		 JOptionPane.showMessageDialog(null, "Impossibile accedere al database","Errore", JOptionPane.ERROR_MESSAGE);
         return null;}		
```
Between the two quotes where postgres is written, enter the database username which, if not set, is by default postgres.
Between the last two quotes there is the password, it must be replaced with your password to access the database.


## Running the program

![](http://g.recordit.co/hBDw3wc3db.gif)



## Built With

**Eclipse**

**PostgreSQL**

## Authors

* [Instagram](https://www.instagram.com/_n_tony/)
 


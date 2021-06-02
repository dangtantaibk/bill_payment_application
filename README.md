# Parking Lot

This is a project to manage a parking lot. It has an automated ticketing system that facilitates the interactions between any user and the parking lot.

## Project Detailing & Use Cases

- A parking lot can hold up to 'n' cars at any given point in time
- Each slot is given a number starting at 1 increasing with increasing distance from the entry point in steps of one
- Create an automated ticketing system that allows the use of parking lot
- When a car enters the parking lot, a ticket issued is issued to the driver
- The ticket issuing process includes documenting the registration number and the color of the car and allocating an available parking slot to the car before actually handing over a ticket to the driver
- The customer should be allocated a parking slot which is nearest to the entry
- At the exit the customer returns the ticket which then marks the slot they were using as being available
- At any time, the  ticketing system should be able to provide
	- Registration numbers of all cars of a particular colour
	- Slot number in which a car with a given registration number is parked
	- Slot numbers of all slots where a car of a particular colour is parked
- This project supports 2 modes of input
	- An interactive command prompt based shell where commands can be typed in
	- It accepts a filename as a parameter at the command prompt and reads the commands from that file
- exit command will exit the interactive command prompt
- The interactive command prompt will also exit if any incorrect command is provided
- Sample Test Cases & outputs are provied at the end of this file

## Getting Started

Here are the instructions for you to set the project up and run in your local machine for testing purposes.

### System Requirements

You will probably need to run these scripts on a Linux box and require:

			
	- Java
	- Maven
	- Git (for version control)



### Installing

Run the command bin/setup to install the build the project (Java, Maven assumed to be pre-installed on the system)

			
		bin/setup


### Running the test cases

Run the command bin/run_functional_tests to run the test cases

		
		bin/run_functional_tests


### Running from command prompt

bin/setup
java -jar target/parkinglot-0.0.1-SNAPSHOT.jar (Will open an interactive command prompt)
java -jar target/parkinglot-0.0.1-SNAPSHOT.jar <filename> (Will read the commands from the file)


## Built With

- [Maven](https://maven.apache.org/) - Build/Dependency Management


## Versioning

The folder also contains a .git file. Please check the version history using "git log" & "git diff" commands


## Sample Test Cases & Outputs for Reference

			$ create_parking_lot 6
			Created a parking lot with 6 slots

			$ park KA-01-HH-1234 White
			Allocated slot number: 1

			$ park KA-01-HH-9999 White
			Allocated slot number: 2

			$ park KA-01-BB-0001 Black
			Allocated slot number: 3

			$ park KA-01-HH-7777 Red
			Allocated slot number: 4

			$ park KA-01-HH-2701 Blue
			Allocated slot number: 5

			$ park KA-01-HH-3141 Black
			Allocated slot number: 6

			$ leave 4
			Slot number 4 is free

			$ status
			Slot No.    Registration No    Colour
			1           KA-01-HH-1234      White
			2           KA-01-HH-9999      White
			3           KA-01-BB-0001      Black
			5           KA-01-HH-2701      Blue
			6           KA-01-HH-3141      Black

			$ park KA-01-P-333 White
			Allocated slot number: 4

			$ park DL-12-AA-9999 White
			Sorry, parking lot is full

			$ registration_numbers_for_cars_with_colour White
			KA-01-HH-1234, KA-01-HH-9999, KA-01-P-333

			$ slot_numbers_for_cars_with_colour White
			1, 2, 4

			$ slot_number_for_registration_number KA-01-HH-3141
			6

			$ slot_number_for_registration_number MH-04-AY-1111
			Not found

			$ exit


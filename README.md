🏧 ATM Mini Project (Java + Twilio OTP)

📌 Overview

This is a simple ATM Simulation Project written in Java.
It demonstrates basic ATM functionalities like:

➤Withdrawal (with OTP verification for large amounts 💳)

➤Deposit

➤Balance Check

➤PIN Verification

➤Additionally, it integrates with the Twilio API to send OTPs (One Time Passwords) via SMS for withdrawals above ₹8000.

🚀 Features

➤Secure PIN verification with limited attempts.

➤Custom exceptions for invalid/incorrect amounts.

➤OTP verification using Twilio SMS API.

➤Console-based interactive menu.

➤Basic banking operations: Deposit, Withdraw, Balance Inquiry.

⚙️ Tech Stack

➤Language: Java (JDK 8+ recommended)

➤IDE/Editor: Notepad / VS Code / IntelliJ (any text editor works)

➤Dependencies:

➤Twilio Java SDK(twilio-x.x.x.jar)

🛠️ Setup & Run

➤Clone this repository:

git clone https://github.com/kavyasrik7/ATM-Mini-Project.git

cd ATM-Mini-Project

➤Compile the Java file:

javac -cp "twilio-x.x.x.jar;." ATM.java

➤Run the program:

java -cp "twilio-x.x.x.jar;." ATM

(On Linux/Mac, replace ; with : in the classpath.)

📱 Twilio OTP Integration

➤This project uses Twilio to send OTP messages to the registered phone number.

➤You need to create a free Twilio account and get:

➤Account SID

➤Auth Token

➤Twilio Phone Number

📂 Project Structure
ATM-Mini-Project/
│── ATM.java        # Main source code
│── README.md      
│── twilio-x.x.x.jar 
 

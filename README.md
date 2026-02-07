# Midas
Project repo for the JPMC Advanced Software Engineering Forage program
This repository contains my work for the JPMorgan Chase & Co. Software Engineering Job Simulation on Forage. The project simulates a real-world backend financial system built with Spring Boot.

🚀 What I Built

🔹 Kafka Integration – Consumed and deserialized transaction messages using Spring Kafka

🔹 Transaction Validation & Persistence – Validated users and balances, stored data using Spring Data JPA with H2

🔹 Incentive API Integration – Called an external REST API and applied incentives to recipient balances

🔹 Balance REST API – Exposed a /balance endpoint to query user balances in JSON format

🔹 Testing & Debugging – Verified behavior using Maven tests and debugger inspection

🛠️ Tech Stack

Java

Spring Boot

Apache Kafka

Spring Data JPA

H2 Database

REST APIs

Maven

▶️ How It Works (High Level)

Transactions are published to Kafka

Midas Core consumes and validates them

Valid transactions are saved to the database

Incentives are fetched from an external API

User balances are updated accordingly

Users can query balances via REST API

📜 Certificate

This project was completed as part of the JPMorgan Chase & Co. Software Engineering Job Simulation on Forage.

🔗 Simulation link:
https://www.theforage.com/simulations/jpmorgan/advanced-software-engineering-r0fm

🙌 Acknowledgements

Thanks to JPMorgan Chase & Co. and Forage for providing a realistic, hands-on backend engineering experience.

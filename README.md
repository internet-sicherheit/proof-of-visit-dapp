# Proof of Visit Dapp

## Introduction

The goal of this project is to create an example for a decentralized application (DApp), showcasing a proof of visit concept.
This means, that the DApp can be used to verify, that one person was at one certain location at a specific point in time.
Once can think about it as prooving that you visisted the Institute for Internet Security in Gelsenkirchen.


## Project concept

### Concept explenation

To achieve this goal, the project will contain two android applications and a smart contract deployed on the [bloxberg](https://bloxberg.org/) Ethereum network. 
The first application represents a user application. 
With this application, a user can view a list of all locations where he has been. 
Furtheremore he can create a wallet and generate a QR-code that the second application can scan. 
The second application represents an admin application. 
This application is handled by a person who manages a location. 
He can create a location and a token that is connected with his own location. 
By scanning the QR-code from the user application, the admin application will first generate a token of a certain location and then transfer this token to the user application's wallet to validate the visit of a user. 
A visitor that handles the user application can now view his received token on a list in the application.

By transferring a [ERC721](http://erc721.org/) token, the admin DApps calls a function of the smart contract, where all data is stored. 
In contrast to the user DApp, if the admin DApp is calling a function, it alters the contract's state. 
The user DApp can now get the stored data by calling only reading functions of the contract.


### C4-Concept-Model

This image reperesents the project concept as a c4 model:  
![alt text](https://raw.githubusercontent.com/internet-sicherheit/proof-of-visit-dapp/master/documentation/c4model/client_based/c4mode_client_based/Proof%20of%20Visit%20Dapp.png)


## Used programming languages

### Smart Contract
In order to develop the smart contract, the language [Solidity](https://solidity.readthedocs.io) will be used. 
This decision was made with regards to Solidity being the most documented and used language, having extensive tooling support. 
By using Solidity we are also able to make this project accessible for more people, because of the higher familarity in the community.

### Application
The application is well suited for mobile use, and we therefore decided to focus on Android. 
Android provides an open operating system and supports developers to create thier own applications. 
Furthermore, Android is mostly widely used mobile OS in the world.

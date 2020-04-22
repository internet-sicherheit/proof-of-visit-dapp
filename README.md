# Proof of Visit Dapp

## Introduction

The goal of this project is to create an example for a decentralized application (DAPP), that implements a proof of visit concept. This means, that the DAPP has to verify, that one person was at one certain location.


## Project concept

### Concept explenation

To achieve this goal, the project will contain two android applications and a smart contract that is deployed on the Bloxberg Ethereum network. The first application represents a user application. With this application, a user can view a list of all locations where he has been. Furtheremore he can create a wallet and generate a QR-code that the second application can scan. The second application represents an admin application. This application is handled by a person who manages a location. He can create a location and a token that is connected with his own location. By scanning the QR-code from the user application, the admin application will first generate a token of a certain location and then transfer this token to the user application's wallet to validate the visit of a user. A visitor that handles the user application can now view his received token on a list in the application.

By transferring a token, the admin DAPP calls a function of the smart contract, where all data is stored. In contrast of the user DAPP, if the admin DAPP is calling a function, it alters the contract's state. The user DAPP can now get the stored data by calling only reading functions of the contract.


### C4-Concept-Model

This image reperesents the project concept as a c4 model:
![alt text](https://raw.githubusercontent.com/internet-sicherheit/proof-of-visit-dapp/master/documentation/c4model/client_based/c4mode_client_based/Proof%20of%20Visit%20Dapp.png)


## Used programming languages

### Smart Contract

To create the smart contract, the language solidity will be used. This decision was made because solidity is the most documented language and has the most tooling/support for samrt contracts. Also by using solidity much more people can understand this project, because by the common usage of this language.

### Application

To use the applications it is nessescary to be mobile. Therefore we decided to use android. Android delivers a open operating system and supports developers to create thier own applicaitons. Furthermore android is used on the most mobile devices arround the world.

# A demo that integrates (Estimote beacons - smartwatch - FireBase - smartphone) with a table-GUI
## Coded in Kotlin in Android Studio

The idea is to localize a user (wearing a smart-watch) who is moving in EmaroLab, based on proximity to Estimote beacons. **AND**. Display the users approximate location within EmaroLab.

This repository holds the source code that has two applications, one is for the smart-watch and another for the smart-phone.
The smart-watch application does the localization and the data is transferred to a database in the cloud (using **FireBase**).
While, the smart-phone application reads the data from the cloud and shows the user's approximate location with a simple GUI.

Architecture:

Estimote Beacons <--> Smart-Watch <--> FireBase <--> Smart-Phone <--> GUI

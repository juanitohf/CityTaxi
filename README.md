
Overview:

This user manual explains how to use CityTaxi and how it was developed. 


Abstract:

    The main goal was to design an Android Mobile Application that will be used to match prospective passengers with nearest taxi driver. 
    The app will be designed with THREE actors in mind: Prospective Passenger, Taxi driver, and Administrator. 

    The GPS sensor in the phone will be used to determine Prospective Passenger's location, 
    and an algorithm will be used to determine which Taxi is closest, and match them up.

Start Guide:

    1. Create an account on CityTaxi.
    2. Login into CityTaxi app.
    3. Press Request Taxi button if you need a Taxi, and wait for the taxi driver respond. 

Installation:

    CityTaxi will be hosted on github: https://github.com/juanitohf/CityTaxi.git. 
    Open it on eclipse and download Google play services and attach it into CityTaxi app.
    Go to https://code.google.com/apis/console and enable the API Google Maps Android API v2. 

Dependencies:

    1. Git repository: The distribution revision control and source code management system.
                        If cloned from Github, that will be 'git cloneÿhttps://github.com/juanitohf/CityTaxi.git <option>', 
                        <option> could be the repository name that you want to name it on your local machine; or without <option>, 
                        will be the name of CityTaxi. In addition, user can go to the above url, and download zip file.
    2. Apache Web Server: 
    3. PHP 5.5: The PHP Hypertext Processor must be installed in order for any of the php backend to work.
    4. MySQL: Is the only database in CityTaxi.

Installation server steps:

    1. Get the copy from Git virtual repository: 
        Command:  $ git clone git://github.com/juanitohf/CityTaxi.git.
        Unzip the file called CityTaxiServer.zip and upload the files into the server. 

    2. Enable the correct permission: Apache server needs to read all files and folder. 
        a. The apache user must be able to read all the files on the Wizard system.
            Command: chmod 644 <files name>
        b. The apache user must be able to read all folders on the CityTaxi system.
            Command: chmod 755 <folders name>
    3. Prepare the database.
        Access to mysql from the terminal:
        Command: mysql ?u (user) ?h(host) ?p(password).
        Create a new database and give full permission. 

	      Commands:

        a. CREATE DATABASE ?CityTaxi
        b. CREATE USER ?CityTaxi?@?localhost? IDENTIFIED BY ?NameUser?
        c. GRAND SELECT, CREATE, INSERT, DELETE, ALTER ON ?CityTaxi? TO ?CityTaxi? @?localhost?;
        d. Flush privileges. 

        Download CityTaxi.sql  http://web-huertas.com/public_html/work/programs/CityTaxi_Server/db/ City_Taxi.sql
        Copy City_Taxi.sql content and paste on the mysql prompt terminal to create all necessary tables necessaries to control CityTaxi backend.

    4. Disk Space
        CityTaxi requires 5MB.





<?php

/*
Group 7: John Balbuza, Fernando Bilbao, Arisa Kitagishi, Imahd Khan, Susan Long, Sarah Thompson, Jonathan White
DnD Helper App - Tavern's Table
COP 4331C, Summer 2018
Professor Richard Leinecker
*/

// We set these variables to the correct information in order to access the database.
$hostname = 'localhost';
$username = 'root';
$password = 'contactmanager7';
$databaseName = 'dndApp';

// Establish a connection to the database.
$connection = new mysqli($hostname, $username, $password, $databaseName);

// If we have a connection error, we send the connection error message to returnWithError, this method will send a JSON where one of its fields indicates the connection error.
if($connection->connect_error) {
  returnWithError($connection->connect_error);
} else {

  // We take the passed in username.
  $username = $_POST['username'];

  // We take passed in password.
  $password = $_POST['password'];

  // We take passed in email.
  $email = $_POST['email'];

  // Create a hash value with our passed in password.
  $hash = hash('sha256', $password);

  // We return everything from our database that has the same passed in username, hashed password, and email.
  $query = "SELECT * FROM Users WHERE username = '$username' AND password = '$hash' AND email = '$email'";

  // We perform our query.
  $result = $connection->query($query);

  // We check whether there are any rows that have the same passed in username, password, and email.
  if ($result->num_rows > 0) {

    // If the same information has already been used to create a user, we send an error indicating that this user already exists.
    returnWithError("This user already exists");
  } else {

    // Since we have verified that a user with the same passed in information doesn't exist, we insert this new user into our Users table with the passed in username, created hash password, and passed in email.
    $query = "INSERT INTO Users (username, password, email) VALUES ('$username', '$hash', '$email')";

    // We perform our query.
    $result = $connection->query($query);

    // We check whether the query was performed, if not, we return a connection error.
    if (!$result) {
      returnWithError($connection->connect_error);
    } else {

        // Since we have successfully inserted our new user into our table, we want to also retrieve the userID associated with it.
        $query = "SELECT userID FROM Users WHERE username = '$username' AND password = '$hash' AND email = '$email'";

        // We perform the query.
        $result = $connection->query($query);

        // We check whether the query was performed, if not, we return a connection error.
        if (!$result) {
          returnWithError($connection->connect_error);
        } else {

          // We get the specfic row we are looking for from our Users table that matches the passed in username, password, and email.
          $row = $result->fetch_assoc();

          // We assign the userID which we recieved from the Users table and assign it to the variable userID.
          $userID = $row['userID'];

          // We return a JSON with our userID.
          returnWithInfo($userID);
        }

        // We are no longer using our database so we are able close it.
        $connection->close();

    }

  }

}

// We set the correct header to accomodate our JSON and echo the passed in obj variable.
function sendResultInfoAsJson($obj) {
  header('Content-type: application/json');
  echo $obj;
}

// If we have an error we return a JSON indicating our userID with 0 since this is not a valid index, and we also return the error we recieved in this JSON.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function returnWithError($error) {
  $returnValue = '{"userID": 0, "error": "' . $error . '"}';
  sendResultInfoAsJson($returnValue);
}

// If we haven't recieved an error, we use our passed in userID for our JSON and leave the error field blank as there is no error involved.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function returnWithInfo($userID) {
  $returnValue = '{"userID": "' . $userID . '", "error": ""}';
  sendResultInfoAsJson($returnValue);
}

 ?>

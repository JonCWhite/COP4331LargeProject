<?php

/*
Group 7: John Balbuza, Fernando Bilbao, Arisa Kitagishi, Imahd Khan, Susan Long, Sarah Thompson, Jonathan White
DnD Helper App - Tavern's Table
COP 4331C, Summer 2018
Professor Richard Leinecker
*/

// We retrieve the passed in JSON string.
$inData = getRequestInfo();

// We set these variables to the correct information in order to access the database.
$hostname = 'localhost';
$username = 'root';
$password = 'contactmanager7';
$databaseName = 'dndApp';

// We establish a connection to the database.
$connection = new mysqli($hostname, $username, $password, $databaseName);

// If we have a connection error, we send the connection error message to returnWithError, this method will send a JSON where one of its fields indicates the connection error.
if ($connection->connect_error) {
  returnWithError($connection->connect_error);
} else {

  // We take the username from the passed in JSON.
  $username = $inData['username'];

  // We take the password from the passed in JSON.
  $password = $inData['password'];

  // We take the email from the passed in JSON.
  $email = $inData['email'];

  // We return the userID that has the same username, password, and email associated with it.
  $query = "SELECT userID FROM Users WHERE username = '$username' AND password = '$password' AND email = '$email'";

  // We perform our query.
  $result = $connection->query($query);

 // We check whether the query was performed, if not, we return a connection error.
  if (!$result) {
    returnWithError($connection->connect_error);
  } else {

    // We check whether there are any rows that have the same passed in username, password, and email.
    if ($result->num_rows > 0) {

      // We get the specfic row we are looking for from our Users table that matches the passed in username, password, and email.
      $row = $result->fetch_assoc();

      // We assign the userID which we recieved from the Users table and assign it to the variable userID.
      $userID = $row['userID'];

      // We return a JSON with our userID.
      sendResultInfo($userID);
    } else {

      // If there are no rows that have the same passed in username, password, and email, we send an error to the returnWithError method indicating that this user does not exist in our Users table.
      returnWithError("This user does not exist");
    }
  }

  // Now that we have used our databse we can safely close it.
  $connection->close();

}

// We retrieve the passed in JSON, decode it, and return it afterwards.
function getRequestInfo() {
  return json_decode(file_get_contents('php://input'), true);
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
function sendResultInfo($userID) {
  $returnValue = '{"userID": "' . $userID . '", "error": ""}';
  sendResultInfoAsJson($returnValue);
}

 ?>

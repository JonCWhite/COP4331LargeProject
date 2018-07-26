<?php

/*
Group 7: John Balbuza, Fernando Bilbao, Arisa Kitagishi, Imahd Khan, Susan Long, Sarah Thompson, Jonathan White
DnD Helper App - Tavern's Table
COP 4331C, Summer 2018
Professor Richard Leinecker
*/

// We set these variables to the correct information in order to access the database.
$hostName = 'localhost';
$username = 'root';
$password = 'contactmanager7';
$databaseName = 'dndApp';

// We establish a connection to our database.
$connection = new mysqli($hostName, $username, $password, $databaseName);

// If we have a connection error, we send the connection error message to returnWithError, this method will send a JSON where one of its fields indicates the connection error.
if ($connection->connect_error) {
  returnWithError($connection->connect_error);
} else {

  // We retrieve the passed in party key.
  $partyKey = $_POST['partyKey'];

  // We return the party size of the party which has the passed in party key.
  $query = "SELECT partySize FROM Campaign WHERE partyKey = '$partyKey'";

  // We perorm our query.
  $result = $connection->query($query);

  // If we have a connection error, we send the connection error message to returnWithError, this method will send a JSON where one of its fields indicates the connection error.
  if (!$result) {
    returnWithError($connection->connect_error);
  } else {

    // We retrive a row from our campaign table.
    $row = $result->fetch_assoc();

    // We retieve the info stored in our party size column from the row we retrieved.
    $partySize = $row['partySize'];

    // Since an additional member has been added to the party we increment the party size by one.
    $partySize++;

    // We update the party size by one since we have an additional member.
    $query = "UPDATE Campaign SET partySize = '$partySize' WHERE partyKey = '$partyKey'";

    // We perform our query.
    $result = $connection->query($query);

    // If we have a connection error, we send the connection error message to returnWithError, this method will send a JSON where one of its fields indicates the connection error.
    if (!$result) {
      returnWithError($connection->connect_error);
    } else {

      // Since we recieved no error we pass an empty string for our error.
      returnWithError("");
    }

    // We close the connection to our databse.
    $connection->close();
  }
}


// We set the correct header to accomodate our JSON and echo the passed in obj variable.
function sendResultInfoAsJson($obj) {
  header('Content-type: application/json');
  echo $obj;
}

// If we have an error we return a JSON indicating the our error which will be a connection error. 
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function returnWithError($error) {
  $returnValue = '{"error": "' . $error . '"}';
  sendResultInfoAsJson($returnValue);
}


 ?>

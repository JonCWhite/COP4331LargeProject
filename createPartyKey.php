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

// We establish a connection to the database.
$connection = new mysqli($hostname, $username, $password, $databaseName);

// If we have a connection error, we send the connection error message to returnWithError, this method will send a JSON where one of its fields indicates the connection error.
if ($connection->connect_error) {
  returnWithError($connection->connect_error);
} else {

  // We initialize our partyKey variable.
  $partyKey = "";

  // This string holds the lower-case alphabet and all 10 digits which will be used to compose our party key.
  $numbersAndLowerCaseLetters = "abcdefghijklmnopqrstuvwxyz123456789";

  // Our party key will be 5 characters in length so we loop until then.
  for ($i = 0; $i < 5; $i++) {

    // We generate a random number in the range of 0 to 34 inclusive. This random number will be used as an index to access a value from $numbersAndLowerCaseLetters.
    $randomNumber = rand(0, (strlen($numbersAndLowerCaseLetters) - 1));

    // We concatenate each lower-case letter or digit to $partyKey.
    $partyKey .= $numbersAndLowerCaseLetters[$randomNumber];
  }

  // We intend to insert our party key into our campaign table.
  $query = "INSERT INTO Campaign (partyKey) VALUES ('$partyKey')";

  // We perform our query.
  $result = $connection->query($query);

  // If we have a connection error, we send the connection error message to returnWithError, this method will send a JSON where one of its fields indicates the connection error.
  if (!$result) {
    returnWithError($connection->connect_error);
  } else {

    // We send a JSON object back with our party key.
    returnWithInfo($partyKey);

    // Connection to our datbase is closed.
    $connection->close();
  }
}

// We set the correct header to accomodate our JSON and echo the passed in obj variable.
function sendResultInfoAsJson($obj) {
  header('Content-type: application/json');
  echo $obj;
}

// If we have an error we return a JSON indicating our partyKey with an empty string since we were not able to either connect to the database or perform a successfully query with our Campaign table,
// we also return the error we recieved in this JSON.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function returnWithError($error) {
  $returnValue = '{"partyKey": "", "error": "' . $error . '"}';
  sendResultInfoAsJson($returnValue);
}

// If we haven't recieved an error, we use our passed in partyKey for our JSON and leave the error field blank as there is no error involved.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function returnWithInfo($partyKey) {
  $returnValue = '{"partyKey": "' . $partyKey . '", "error": ""}';
  sendResultInfoAsJson($returnValue);
}

 ?>

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

// We retrieve our passed in party key through POST.
$partyKey = $_POST['partyKey'];

// We check whether our party key has any upper-case letter, if so we return an error indicating that our party key must only be in lower-case.
if (checkIfContainsUpperCaseLetter($partyKey)) {
  returnWithError("All letters must be in lower case");
} else {

  // We intend to see whether party key that was passed in is an existing party key in our Campaign table.
  $query = "SELECT campaignID FROM Campaign WHERE partyKey = '$partyKey'";

  // We perorm our query.
  $result = $connection->query($query);

  // If we have a connection error, we send the connection error message to returnWithError, this method will send a JSON where one of its fields indicates the connection error.
  if (!$result) {
    returnWithError($connection->connect_error);
  } else {

    // If the number of rows is less than one than it means that no row exists with our passed in party key.
    // We return an error indicating that the user passed in an invalid party key.
    if ($result->num_rows < 1) {
      returnWithError("Invalid party key");
    } else {

      // We entered a valid party key and we notify the user.
      returnWithInfo("Party key accepted");
    }

    // We close the connection to our database.
    $connection->close();
  }
}
}

function checkIfContainsUpperCaseLetter($partyKey) {

  // We go through each character in our passed in party key.
  for($i = 0; $i < strlen($partyKey); $i++) {

    // If the current character we are at has an upper case letter we return a 1.
    if (ctype_upper($partyKey[$i])) {
      return 1;
    }
  }

  // We return a zero indicating that an uppercase letter was not found.
  return 0;
}

// We set the correct header to accomodate our JSON and echo the passed in obj variable.
function sendResultInfoAsJson($obj) {
  header('Content-type: application/json');
  echo $obj;
}

// If we have an error we return a JSON indicating info with an empty string since we were not able to either connect to the database, perform a successfully query with our Campaign table,
//  or had an uppercase letter in our party key. The specifc error we recieved is sent back.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function returnWithError($error) {
  $returnValue = '{"info": "", "error": "' . $error . '"}';
  sendResultInfoAsJson($returnValue);
}

// If we haven't recieved an error, we use our passed in info variable indicating that a correct party key was passed in leave the error field blank as there is no error involved.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function returnWithInfo($info) {
  $returnValue = '{"info": "' . $info . '", "error": "' . $error . '"}';
  sendResultInfoAsJson($returnValue);
}


 ?>

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
	// We take the userID from the passed in form data. Note that friendID and userID are swapped, since this file will be accessed fromf the recipients end.
	$friendID = intval($_POST['userID']);

	// We take the friendID from the passed in form data. Note that friendID and userID are swapped, since this file will be accessed fromf the recipients end.
	$userID = intval($_POST['friendID']);
	
	// We take the user's confirmation response from the passed in form data.
	$bAccepted = filter_var($_POST['bAccepted'], FILTER_VALIDATE_BOOLEAN);
	
	// We create a query to confirm the request exists. 
	$query = "SELECT * FROM Friends WHERE friendID = '$friendID' AND userID = '$userID'";

	// We perform the query.
	$result = $connection->query($query);

	// We confirm that the request could be found.
	if ($result->num_rows == 0) {
		// If we couldn't find the request we send back an error.
		returnWithError("Sorry, something went wrong and we couldn't find your request.");
	} else {
		// Update the database according to the user's response.
		if ($bAccepted) {
			// If the user accepted the request, then update the database to reflect that.
			$query = "UPDATE Friends SET isConfirmed = true WHERE (userID = '$userID' AND friendID = '$friendID')";
			// We create a message to inform to user their response was successful.
			$response = "Friendship confirmed!";
			// We perform our query.
			$result = $connection->query($query);
		} else {
			// Otherwise the user denied the request so we delete the entry from the friends table.
			$query = "DELETE FROM Friends WHERE (userID = '$userID' AND friendID = '$friendID')";
			// We create a message to inform to user their response was successful.
			$response = "Friendship denied!";
			// We perform our query.
			$result = $connection->query($query);
		}
		if (!$result) {
			// If the query failed, send back an error.
			returnWithError($connection->connect_error);
		}
	}
	// We are no longer using our database so we are able close it.
	$connection->close();
	
	// We return a JSON with our success message.
	returnWithInfo($response);
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
  $returnValue = '{"error": "' . $error . '"}';
  sendResultInfoAsJson($returnValue);
}

// If we haven't recieved an error, we use our passed in userID for our JSON and leave the error field blank as there is no error involved.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function returnWithInfo($response) {
  $returnValue = '{"responseMessage": "' . $response . '"}';
  sendResultInfoAsJson($returnValue);
}

 ?>

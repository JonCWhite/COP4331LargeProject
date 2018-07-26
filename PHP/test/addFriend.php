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
	// We take in the password from the passed in form data.
	$friendName = $_POST['friendName'];

	// We take the userID from the passed in form data.
	$userID = intval($_POST['userID']);

	// We create a query to find our friends user ID.
	$query = "SELECT userID FROM Users WHERE username = '$friendName'";

	// We perform the query.
	$result = $connection->query($query);

	// We check whether there are any rows match friendName.
	if ($result->num_rows == 0) {
		// If no user matches the passed in friendName, return with an error.
		returnWithError("We couldn't find anyone who goes by that name! Are you sure you typed everything in correctly?");
	}

	// If user 'friendName' was found, we record their user ID.
	$row = $result->fetch_assoc();
	$friendID = $row['userID'];

	// If the friend was found, we create a new query to ensure that the two users are not already friends.
	$query = "SELECT * FROM Friends WHERE (userID = '$userID' AND friendID = '$friendID') OR (userID = '$friendID' AND friendID = '$userID')";

	// We perform our query.
	$result = $connection->query($query);

	// We check whether there are any rows that have the same passed in username, password, and email.
	if ($result->num_rows > 0) {
		// If the same information has already been used to create a user, we send an error indicating that these users are already friends.
		returnWithError("It looks like you're already friends with that user.");
	} else {
		// Since we have verified that a friend with the same passed in information doesn't exist, we insert this new user into our Friends table.
		$query = "INSERT INTO Friends (userID, friendID) VALUES ('$userID', '$friendID')";

		// We perform our query.
		$result = $connection->query($query);

		// We check whether the query was performed, if not, we return a connection error.
		if (!$result) {
		  returnWithError($connection->connect_error);
		} else {
			// We create a message to inform to user their friend request was successfully sent.
			$response = "Request successfully sent!";

			// We are no longer using our database so we are able close it.
			$connection->close();
			  
			// We return a JSON with our userID.
			returnWithInfo($response);
		}
    }
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

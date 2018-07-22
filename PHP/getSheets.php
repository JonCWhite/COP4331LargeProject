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
	// We take the posted in campaignID
	$campaignID = $_POST['campaignID'];
	// We return the userID that has the same username and password hash value associated with it.
	$query = "SELECT characterID FROM CharactersCampaign WHERE campaignID = '$campaignID'";
	// We perform our query.
	$result = $connection->query($query);
	// We check whether the query was performed, if not, we return a connection error.
	if (!$result) {
		returnWithError($connection->connect_error);
	}
	// Initialize an array to hold our row data.
	$rows = array();
	// Iterate through the query results and add the data to the array we created.
	while ($row = mysqli_fetch_assoc($sth)) {
		$rows[] = $row;
	}
	// Now that we have used our database we can safely close it.
	$connection->close();
	sendResultInfo($rows);
}

// If we have an error we return a JSON indicating our userID with 0 since this is not a valid index, and we also return the error we recieved in this JSON.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function returnWithError($error) {
	$returnValue = '{"error": "' . $error . '"}';
	sendResultInfoAsJson($returnValue);
}
// If we haven't recieved an error, we use our passed in userID for our JSON and leave the error field blank as there is no error involved.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function sendResultInfo($rows) {
  $returnValue = json_encode($rows);
  sendResultInfoAsJson($returnValue);
}

// We set the correct header to accomodate our JSON and echo the passed in obj variable.
function sendResultInfoAsJson($obj) {
  header('Content-type: application/json');
  echo $obj;
}
 ?>
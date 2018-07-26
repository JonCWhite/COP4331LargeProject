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

$response = array();
$response2 = array();
$response["raceResult"] = array();
$response2["classResult"] = array();


// If we have a connection error, we send the connection error message to returnWithError, this method will send a JSON where one of its fields indicates the connection error.
if ($connection->connect_error)
{
  returnWithError($connection->connect_error);
}
else
{
    // Get row of Races
    $query = "SELECT raceName FROM Races";

    // Get query
    $result = $connection->query($query);


    // Save into array
    while($row = $result->fetch_assoc())
    {
        $tmp = array();
        $tmp["raceName"] = $row["raceName"];

        // Push the array
        array_push($response["raceResult"], $tmp);
    }


    $query = "SELECT name FROM Classes";

    $result = $connection->query($query);

    while($row = $result->fetch_assoc())
    {
      $tmp2 = array();
      $tmp2["name"] = $row["name"];

      array_push($response2["classResult"],$tmp2);
    }

    //returnWithInfo($response, $response2);
    //echo json_encode(array($response, $response2));
    //sendResultInfoAsJson($response, $response2)

    $newArray = array($response, $response2);

    echo json_encode($newArray);

    //returnWithInfo($newArray);


    // Return the response
    //returnWithInfo($response); 

    $connection->close();


    
}

// We set the correct header to accomodate our JSON and echo the passed in obj variable.
function sendResultInfoAsJson($ob) {
  header('Content-type: application/json');
  echo json_encode($obj);
  //die();
}


// If we have an error we return a JSON indicating our partyKey with an empty string since we were not able to either connect to the database or perform a successfully query with our Campaign table,
// we also return the error we recieved in this JSON.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
/*function returnWithError($error) {
  $returnValue = '{"Race Name": "", "error": "' . $error . '"}';
  sendResultInfoAsJson($returnValue);
}*/

// If we haven't recieved an error, we use our passed in partyKey for our JSON and leave the error field blank as there is no error involved.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function returnWithInfo($result) {
  //$returnValue = '{"Race Name": "' . $result . '", "error": ""}';
  sendResultInfoAsJson($result);
}


?>

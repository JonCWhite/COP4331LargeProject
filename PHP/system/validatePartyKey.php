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
if ($connection->connect_error) 
{
  returnWithError($connection->connect_error);
} 
else 
{

  // We retrieve our passed in party key through POST.
  $partyKey = $_POST['partyKey'];
  // Obtain the passed in userID.
  $userID = $_POST['userID'];

  // We check whether our party key has any upper-case letter, if so we return an error indicating that our party key must only be in lower-case.
  if (checkIfContainsUpperCaseLetter($partyKey)) 
  {
    returnWithError("All letters must be in lower case");
  } 
  else 
  {
    // We intend to see whether party key that was passed in is an existing party key in our Campaign table.
    $query = "SELECT campaignID FROM Campaign WHERE partyKey = '$partyKey'";

    // We perorm our query.
    $result = $connection->query($query);

    // If we have a connection error, we send the connection error message to returnWithError, this method will send a JSON where one of its fields indicates the connection error.
    if (!$result) 
    {
      returnWithError($connection->connect_error);
    } 
    else 
    {
      // If the number of rows is less than one than it means that no row exists with our passed in party key.
      // We return an error indicating that the user passed in an invalid party key.
      if ($result->num_rows > 0) 
      {
        $row = $result->fetch_assoc();
        $campaignID = $row['campaignID'];
        
        //check if user already is a DM for that campaign before sending campaignID
        // open Campaign Table to check if userID is used as DM
        $queryCampaign = "SELECT name FROM Campaign WHERE userID = $userID && campaignID = $campaignID";

        $resultCampaign = $connection->query($queryCampaign);

        // check error
        if (!$resultCampaign) {
          returnWithError("You are a DM in this campaign");
         } 
         // If the number of rows is greater than 1, then user is a DM
         else if($resultCampaign->num_rows > 0){
             returnWithError("You are a DM in this campaign");
             // We close the connection to our database.
             $connection->close();
         }
         
         //after checking if user is a DM in that campaign, check if a user is a player
         // by finding charID using userID in characters Table and then go throgh CharactersCampaign Table
	      // We obtain the characterIDs and character names that match the passed in userID.
        $queryID = "SELECT characterID, name FROM Characters WHERE userID = $userID";

        $resultID = $connection->query($queryID);

        // If our above query doesn't execute we return an error indicating that the passed in userID was invalid.
        if (!$resultID) {
          returnWithError("Invalid userID");
        } 
        else {
        // Go through Characters table
          while ($rowID = $resultID->fetch_assoc()) {
    		  // store current characterID
    		  $characterID = $rowID['characterID'];

	      	// Open another query from CharactersCampaign Table
	      	$queryCampaignChar = "SELECT campaignID FROM CharactersCampaign WHERE characterID = $characterID && campaignID = $campaignID";
	      	$resultCampaignChar = $connection->query($queryCampaignChar);
	      	// check error
      		if (!$resultCampaignChar) {
              returnWithError("error in queryCmapChar");
	      	exit();
	      	}
          // if row > 0, that means at least one of your character is already in the campaign
          else if($resultCampaignChar->num_rows > 0){
             returnWithError("You already joined this campaign");
             // We close the connection to our database.
             $connection->close();
           }
          }
        }
        returnWithInfo($campaignID);
      } 
      else 
      {
        returnWithError("Invalid party key");
      }
    }
  // increment party size using party key 
  $queryUpdate = "SELECT partySize FROM Campaign WHERE partyKey = '$partyKey'";

  // We perorm our query.
  $resultUpdate = $connection->query($queryUpdate);

  // If we have a connection error, we send the connection error message to returnWithError, this method will send a JSON where one of its fields indicates the connection error.
  if (!$resultUpdate) {
    returnWithError($connection->connect_error);
  } 
  else {

    // We retrive a row from our campaign table.
    $rowUpdate = $resultUpdate->fetch_assoc();

    // We retieve the info stored in our party size column from the row we retrieved.
    $partySize = $rowUpdate['partySize'];

    // Since an additional member has been added to the party we increment the party size by one.
    $partySize++;

    // We update the party size by one since we have an additional member.
    $queryUpdated = "UPDATE Campaign SET partySize = '$partySize' WHERE partyKey = '$partyKey'";

    // We perform our query.
    $resultUpdated = $connection->query($queryUpdated);

    // If we have a connection error, we send the connection error message to returnWithError, this method will send a JSON where one of its fields indicates the connection error.
    if (!$resultUpdated) {
      returnWithError($connection->connect_error);
    } 
    else {

      // Since we recieved no error we pass an empty string for our error.
      returnWithError("");
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
  $returnValue = '{"campaignID": "", "error": "' . $error . '"}';
  sendResultInfoAsJson($returnValue);
}

// If we haven't recieved an error, we use our passed in info variable indicating that a correct party key was passed in leave the error field blank as there is no error involved.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function returnWithInfo($campaignID) {
  $returnValue = '{"campaignID": "' . $campaignID . '", "error": "' . $error . '"}';
  sendResultInfoAsJson($returnValue);
}


 ?>

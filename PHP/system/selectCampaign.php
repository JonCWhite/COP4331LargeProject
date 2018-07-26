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

//Arrays to store and send the info back
$characterNames = array();
$campaignNames = array();
$campaignIDs = array();
$DMs = array();
$tempDMs = array();
$partySizes = array();
$DMNames = array();
$characterID = array();

// We establish a connection to the database.
$connection = new mysqli($hostname, $username, $password, $databaseName);

// If we have a connection error, we send the connection error message to returnWithError, this method will send a JSON where one of its fields indicates the connection error.
if ($connection->connect_error) {
  returnWithError($connection->connect_error);
}

else {

  // Obtain the passed in userID.
  $userID = $_POST['userID'];

  // We obtain the characterIDs and character names that match the passed in userID.
  $query = "SELECT characterID, name FROM Characters WHERE userID = $userID";

  $result = $connection->query($query);

  // If our above query doesn't execute we return an error indicating that the passed in userID was invalid.
  if (!$result) {
    returnWithError("Invalid userID");
  } else {

    // Go through Characters table
    while ($row = $result->fetch_assoc()) {
		  // store current characterID
		  $characterIDs = $row['characterID'];

		// Open another query from CharactersCampaign Table
		$queryCampaignChar = "SELECT campaignID FROM CharactersCampaign WHERE characterID = $characterIDs";
		$resultCampaignChar = $connection->query($queryCampaignChar);
		// check error
		if (!$resultCampaignChar) {
        returnWithError("error in queryCmapChar");
        exit();
		}
		  // go through ChactersCampaign Table
		  while ($rowCampaignChar = $resultCampaignChar->fetch_assoc()) {
			// store current Campaign ID as temp
			$temp = $rowCampaignChar['campaignID'];

					// open Campaign Table
					$queryCampaign = "SELECT name, userID, partySize FROM Campaign WHERE campaignID = $temp";
					$resultCampaign = $connection->query($queryCampaign);
					// check error
					if (!$result) {
					returnWithError("Invalid campaignID");
					exit();
					}

					// go through the Campaign Table
					while($rowCampaign = $resultCampaign->fetch_assoc())
					{
						//check if the character is a DM by comparing userID and the userID in the Campaign Table
						// if the userID does not match , that means the character is participating in the campaign as a player
						if($rowCampaign['userID'] != $userID)
						{
						// push the values
						array_push($campaignIDs, $rowCampaignChar['campaignID']);
						array_push($campaignNames, $rowCampaign['name']);
						array_push($DMs, $rowCampaign['userID']);
						array_push($partySizes, $rowCampaign['partySize']);
						array_push($characterNames, $row['name']);
            array_push($characterID, $row['characterID']);
						}
					}
		  }
    }

    // find DM names
	for($i = 0; $i < sizeof($DMs); $i++) {

          $query = "SELECT username FROM Users WHERE userID = $DMs[$i]";

          $result = $connection->query($query);

          while ($row = $result->fetch_assoc()) {
            array_push($DMNames, $row['username']);
          }
	}


        // We go through each of character names.
      for($i = 0; $i < sizeof($characterNames); $i++) {

        // If we have more than one character name we want to put an comma between them each time another character name appears.
          if ($commaCount > 0) {
            $characterNamesJSON .= ",";
          }

          // We assign our character name to $characterNamesJSON.
          $characterNamesJSON .= '"' . $characterNames[$i] . '"';

          // We increment $commaCount by one each time we come across another character name.
          $commaCount++;
      }

      // Since we are starting at a new value we start $commaCount at zero.
      $commaCount = 0;

      // We go through each of campaign names.
      for($i = 0; $i < sizeof($campaignNames); $i++) {

        // If we have more than one campaign name we want to put an comma between them each time another campaign name appears.
          if ($commaCount > 0) {
            $campaignNamesJSON .= ",";
          }

          // We assign a campaign name to $campaignNamesJSON.
          $campaignNamesJSON .= '"' . $campaignNames[$i] . '"';

          // We increment $commaCount by one each time we come across another campaign name.
          $commaCount++;
      }

      // Since we are starting at a new value we start $commaCount at zero.
      $commaCount = 0;

      // We go through each campaign ID.
      for($i = 0; $i < sizeof($campaignIDs); $i++) {

        // If we have more than one campaign ID we want to put an comma between them each time another campaign ID appears.
          if ($commaCount > 0) {
            $campaignIDsJSON .= ",";
          }

          // We assign a campaign ID to $campaignIDsJSON.
          $campaignIDsJSON .= '' . $campaignIDs[$i] . '';

          // We increment $commaCount by one each time we come across another campaign ID.
          $commaCount++;
      }
      
       // Since we are starting at a new value we start $commaCount at zero.
      $commaCount = 0;
            // We go through each character ID.
      for($i = 0; $i < sizeof($characterID); $i++) {

        // If we have more than one campaign ID we want to put an comma between them each time another campaign ID appears.
          if ($commaCount > 0) {
            $characterIDJSON .= ",";
          }

          // We assign a campaign ID to $campaignIDsJSON.
          $characterIDJSON .= '' . $characterID[$i] . '';

          // We increment $commaCount by one each time we come across another campaign ID.
          $commaCount++;
      }

      // Since we are starting at a new value we start $commaCount at zero.
      $commaCount = 0;

      // We go through each of our DMs.
      for($i = 0; $i < sizeof($DMNames); $i++) {

        // If we have more than one DM we want to put an comma between them each time another DM appears.
          if ($commaCount > 0) {
            $DMsJSON .= ",";
          }

          // We assign DM to $DMsJSON.
          $DMsJSON .= '"' . $DMNames[$i] . '"';

          // We increment $commaCount by one each time we come across another campaign ID.
          $commaCount++;
      }

      // Since we are starting at a new value we start $commaCount at zero.
      $commaCount = 0;

      // We go through all of our party sizes.
      for($i = 0; $i < sizeof($partySizes); $i++) {

        // If we have more than one party size we want to put an comma between them each time another party size appears.
          if ($commaCount > 0) {
            $partySizesJSON .= ",";
          }

          // We assign a party size to $partySizesJSON.
          $partySizesJSON .= '' . $partySizes[$i] . '';

          // We increment $commaCount by one each time we come across another party size.
          $commaCount++;
      }

      // We send our character names, campaign names, campaign IDs, DMs, and party sizes to the returnWithInfo method.
      returnWithInfo($characterNamesJSON, $campaignNamesJSON, $campaignIDsJSON, $DMsJSON, $partySizesJSON, $characterIDJSON);

      // We are no longer using our database so we close the connection.
      $connection->close();
    }

  }

// We set t he correct header to accomodate our JSON and echo the passed in obj variable.
function sendResultInfoAsJson($obj) {
  header('Content-type: application/json');
  echo $obj;
}

// If we have an error we return a JSON we return the error we recieved in this JSON.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function returnWithError($error) {
  $returnValue = '{"characterNames": "", "campaignNames": "", "campaignIDs": "", "characterID": "","DMs": "", "partySizes": "", "error": "' . $error . '"}';
  sendResultInfoAsJson($returnValue);
}

// We send back an array of character names, campaign names, campaign IDs, DMs, and party sizes.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function returnWithInfo($characterNames, $campaignNames, $campaignIDs, $DMNames, $partySizes, $characterID) {
  $returnValue = '{"characterNames": [' . $characterNames . '], "campaignNames": [' . $campaignNames . '], "campaignIDs": [' . $campaignIDs . '], "characterID": [' . $characterID . '], "DMNames": [' . $DMNames . '], "partySizes": [' . $partySizes . '], "error": ""}';
  sendResultInfoAsJson($returnValue);
}


 ?>

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

// We establish a connection to the database.
$connection = new mysqli($hostname, $username, $password, $databaseName);

// If we have a connection error, we send the connection error message to returnWithError, this method will send a JSON where one of its fields indicates the connection error.
if ($connection->connect_error) {
  returnWithError($connection->connect_error);
}

else {

  // Obtain the passed in userID.
  $userID = $_POST['userID'];

  // open Campaign Table to check if userID is used as DM
  $query = "SELECT campaignID, name, userID, partySize FROM Campaign WHERE userID = $userID";

  $result = $connection->query($query);

  // check error
  if (!$result) {
    returnWithError("Invalid userID");
  } else {

    // go through Campaign Table
    while ($row = $result->fetch_assoc()) {
		  // store current campaignID for comparison later
		  $CampaignIDs = $row['campaignID'];

		// open CharactersCampaign Table
		$queryCampaignChar = "SELECT characterID FROM CharactersCampaign WHERE campaignID = $CampaignIDs";
		$resultCampaignChar = $connection->query($queryCampaignChar);
   // check error
		if (!$resultCampaignChar) {
        returnWithError("error in queryCmapChar");
        exit();
		}
		// go through CharactersCampaign Table
		  while ($rowCampaignChar = $resultCampaignChar->fetch_assoc()) {
      // store current CharacterID with matched campaign ID as temp
			$temp = $rowCampaignChar['characterID'];

          // open Characters Table to compare the character ID obtained earlier
					$queryCampaign = "SELECT characterID, name FROM Characters WHERE characterID = $temp";
					$resultCampaign = $connection->query($queryCampaign);

					// check error
					if (!$result) {
					returnWithError("Invalid campaignID");
					exit();
					}

           // go through the Characters Table
					while($rowCampaign = $resultCampaign->fetch_assoc())
					{
           //checks if DM by comparing the userID from Java and the current userID in Campaign
           // if matched, that means the user is DM, so push values
           if($row['userID'] == $userID)
           {
           // push values to send to front-end
						array_push($campaignIDs, $row['campaignID']);
						array_push($campaignNames, $row['name']);
						array_push($DMs, $row['userID']);
						array_push($partySizes, $row['partySize']);
						array_push($characterNames, $rowCampaign['name']);
          }
					}
		  }
    }

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
      returnWithInfo($characterNamesJSON, $campaignNamesJSON, $campaignIDsJSON, $DMsJSON, $partySizesJSON);

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
  $returnValue = '{"characterNames": "", "campaignNames": "", "campaignIDs": "", "DMs": "", "partySizes": "", "error": "' . $error . '"}';
  sendResultInfoAsJson($returnValue);
}

// We send back an array of character names, campaign names, campaign IDs, DMs, and party sizes.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function returnWithInfo($characterNames, $campaignNames, $campaignIDs, $DMNames, $partySizes) {
  $returnValue = '{"characterNames": [' . $characterNames . '], "campaignNames": [' . $campaignNames . '], "campaignIDs": [' . $campaignIDs . '], "DMNames": [' . $DMNames . '], "partySizes": [' . $partySizes . '], "error": ""}';
  sendResultInfoAsJson($returnValue);
}


 ?>

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
$password = 'Imkh1997';
$databaseName = 'dndApp';

// We establish a connection to the database.
$connection = new mysqli($hostname, $username, $password, $databaseName);

// If we have a connection error, we send the connection error message to returnWithError, this method will send a JSON where one of its fields indicates the connection error.
if ($connection->connect_error) {
  returnWithError($connection->connect_error);
} else {

  // Obtain the passed in userID.
  $userID = $_POST['userID'];

  // $i will be an index in our arrays so we begin at 0.
  $i = 0;

  // $commaCount keeps track of when we should put commas between values when constructing a JSON array.
  $commaCount = 0;

  // $indexCounter keeps track of which index we are at.
  $indexCounter = 0;

  // We obtain the characterIDs and character names that match the passed in userID.
  $query = "SELECT characterID, name FROM Characters WHERE userID = $userID";

  // We execute our query.
  $result = $connection->query($query);

  // If our above query doesn't execute we return an error indicating that the passed in userID was invalid.
  if (!$result) {
    returnWithError("Invalid userID");
  } else {

    // We go through each row that we have retrieved.
    while ($row = $result->fetch_assoc()) {

      // We retrieve the characterID from the current row we are at.
      $characterIDs[$indexCounter] = $row['characterID'];

      // We retrieve the character name from the current row we are at.
      $characterNames[$indexCounter] = $row['name'];

      // We increment $indexCounter by one to go to the next index in our arrays.
      $indexCounter++;
    }

    // We set $indexCounter to zero since we will deal with a different set of arrays.
    $indexCounter = 0;

    // We go through all of the characterIDs we obtained.
    for($i = 0; $i < sizeof($characterIDs); $i++) {

      // We retrieve the campaignIDs from our charactersCampaign table with the same characterIDs.
      $query = "SELECT campaignID FROM charactersCampaign WHERE characterID = $characterIDs[$i]";

      // We execute our query.
      $result = $connection->query($query);

      // If our query is not executed it means we have an invalid campaignID and we return an error indicating that and exit from the program.
      if (!$result) {
        returnWithError("Invalid campaignID");
        exit();
      }

      // We go through each row we have retrieved.
      while ($row = $result->fetch_assoc()) {

        // We retrieve the campaignID from the row we just retrieved and assign it to $campaignIDs.
        $campaignIDs[$indexCounter] = $row['campaignID'];

        // We increment $indexCounter by one to get to the next index.
        $indexCounter++;
      }

    }

    // We set $indexCounter to zero since we will deal with a different set of arrays.
    $indexCounter = 0;

    // We go through each of our campaign IDs.
    for($i = 0; $i < sizeof($campaignIDs); $i++) {

      // We retrieve the name of the campaign, userID, and partySize from our campaign table with the matching campaignID.
      $query = "SELECT name, userID, partySize FROM campaign WHERE campaignID = $campaignIDs[$i]";

      // We execute our query.
      $result = $connection->query($query);

      // If our query wasn't executed it means that we have an invalid campaignID and we return an error indicating that and exit from the program.
      if (!$result) {
        returnWithError("Invalid campaignID");
        exit();
      }

      // We go through each row we have obtained.
      while ($row = $result->fetch_assoc()) {

        // We obtain the campaign name and assign it to $campaignNames
        $campaignNames[$indexCounter] = $row['name'];

        // We obtain the DM ID and assign it to $DMs.
        $DMs[$indexCounter] = $row['userID'];

        // We obtain the party size and assign it to $partySizes.
        $partySizes[$indexCounter]= $row['partySize'];

        // We increment $indexCounter by one to get to the next index in our arrays.
        $indexCounter++;
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
      for($i = 0; $i < sizeof($DMs); $i++) {

        // If we have more than one DM we want to put an comma between them each time another DM appears.
          if ($commaCount > 0) {
            $DMsJSON .= ",";
          }

          // We assign DM to $DMsJSON.
          $DMsJSON .= '' . $DMs[$i] . '';

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
  $returnValue = '{"characterNames": "", "campaignNames": "", "campaignIDs":, "", "DMs": "", "partySizes": "", "error": "' . $error . '"}';
  sendResultInfoAsJson($returnValue);
}

// We send back an array of character names, campaign names, campaign IDs, DMs, and party sizes.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function returnWithInfo($characterNames, $campaignNames, $campaignIDs, $DMs, $partySizes) {
  $returnValue = '{"characterNames": [' . $characterNames . '], "campaignNames": [' . $campaignNames . '], "campaignIDs": [' . $campaignIDs . '], "DMs": [' . $DMs . '], "partySizes": [' . $partySizes . '], "error": ""}';
  sendResultInfoAsJson($returnValue);
}


 ?>

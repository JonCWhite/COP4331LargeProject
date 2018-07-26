
<?php


//session_start();
//$inData = getRequestInfo();
$inData = getRequestInfo();

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

  // We take the possed in username.
  $userID = $inData['userID'];

  // We return the userID that has the same username and password hash value associated with it.
  $query = "SELECT * FROM Characters WHERE userID = '$userID'";

  // We perform our query.
  $result = $connection->query($query);

 // We check whether the query was performed, if not, we return a connection error.
  if (!$result) 
  {
    returnWithError($connection->connect_error);
  } 
  else 
  {
    


    // We check whether there are any rows that have the same passed in username, password hash value, and email.
    if ($result->num_rows > 0) 
    {

      $i = 0;
      while($row = $result->fetch_assoc())
      {
          $_SESSION['name'. '$i'] = $row['name'];
          $_SESSION['personality'. '$i'] = $row['personality'];
          $_SESSION['ideals'. '$i'] = $row['ideals']; 
          $_SESSION['bonds'. '$i'] = $row['bonds'];
          $_SESSION['flaws'. '$i'] = $row['flaws'];
          $_SESSION['languages'. '$i'] = $row['languages'];
          $_SESSION['passiveWisdom'. '$i'] = $row['passiveWisdom'];
          $_SESSION['proficiencyBonus'. '$i'] = $row['proficiencyBonus'];
          $_SESSION['featuresAndTraits'. '$i'] = $row['featuresAndTraits'];
          $_SESSION['speed'. '$i'] = $row['speed'];
          $_SESSION['gold'. '$i'] = $row['gold'];
          $_SESSION['silver'. '$i'] = $row['silver'];
          $_SESSION['copper'. '$i'] = $row['copper'];
          $_SESSION['spellCastingAbility'. '$i'] = $row['spellCastingAbility']; 
          $_SESSION['background'. '$i'] = $row['background'];
          $_SESSION['level'. '$i'] = $row['level'];
          $_SESSION['expPoints'. '$i'] = $row['expPoints'];   
          $_SESSION['armorClass'. '$i'] = $row['armorClass'];
          $_SESSION['maxHP'. '$i'] = $row['maxHP'];  
          $_SESSION['currentHP'. '$i'] = $row['currentHP'];
          $_SESSION['inspiration'. '$i'] = $row['inspiration'];
          $_SESSION['alignment'. '$i'] = $row['alignment'];  
          $_SESSION['strength'. '$i'] = $row['strength'];
          $_SESSION['dexterity'. '$i'] = $row['dexterity'];
          $_SESSION['intelligence'. '$i'] = $row['intelligence'];
          $_SESSION['wisdom'. '$i'] = $row['wisdom'];
          $_SESSION['constitution'. '$i'] = $row['constitution'];
          $_SESSION['charisma'. '$i'] = $row['charisma'];
          $_SESSION['notes'. '$i'] = $row['notes'];  
          $_SESSION['initiative'. '$i'] = $row['initiative'];
          $_SESSION['skillProf'. '$i'] = $row['skillProf'];
          $_SESSION['itemProf'. '$i'] = $row['itemProf']; 
          $_SESSION['abilities'. '$i'] = $row['abilities'];
          $_SESSION['inventory'. '$i'] = $row['inventory'];  
          $_SESSION['weapons'. '$i'] = $row['weapons'];
          $_SESSION['tempHP'. '$i'] = $row['tempHP'];
          $_SESSION['spells'. '$i'] = $row['spells'];  
          $_SESSION['savingThrows'. '$i'] = $row['savingThrows'];
          $_SESSION['hitDie'. '$i'] = $row['hitDie'];
          
          $i++;
      }


    
    } 
    else 
    {

      // If there are no rows that have the same passed in username and password hash value, we send an error to the returnWithError method indicating that this user does not exist in our Users table.
      returnWithError("No character exists.");
    }
  }

  // Now that we have used our database we can safely close it.
  $connection->close();

}


function getRequestInfo()
{
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
  $returnValue = '{"userID": 0, "error": "' . $error . '"}';
  sendResultInfoAsJson($returnValue);
}

// If we haven't recieved an error, we use our passed in userID for our JSON and leave the error field blank as there is no error involved.
// The returnValue variable is passed to the sendResultInfoAsJson method afterwards.
function sendResultInfo($userID, $username) {
  $returnValue = '{"id":' . $userID . ',"username":"' . $username . '","error":""}';
  sendResultInfoAsJson($returnValue);
}

 ?>








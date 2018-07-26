<?php

$hostname = 'localhost';
$username = 'root';
$password = 'contactmanager7';
$databaseName = 'dndApp';

$inData = getRequestInfo();

$connection = new mysqli($hostname, $username, $password, $databaseName);

if ($connection->connect_error) {
  returnWithError($connection->connect_error);
} else {

    $raceName = $inData['raceName'];
    $className = $inData['className'];
    $userID = $inData['userID'];
    $name = $inData['name'];
    $personality = $inData['personality'];
    $ideals = $inData['ideals'];
    $bonds = $inData['bonds'];
    $flaws = $inData['flaws'];
    $languages = $inData['languages'];
    $passiveWisdom = $inData['passiveWisdom'];
    $proficiencyBonus = $inData['proficiencyBonus'];
    $featuresAndTraits = $inData['featuresAndTraits'];
    $speed = $inData['speed'];
    $gold = $inData['gold'];
    $silver = $inData['silver'];
    $copper = $inData['copper'];
    $background = $inData['background'];
    $level = $inData['level'];
    $expPoints = $inData['expPoints'];
    $armorClass= $inData['armorClass'];
    $maxHP = $inData['maxHP'];
    $currentHP = $inData['currentHP'];
    $inspiration = $inData['inspiration'];
    $alignment = $inData['alignment'];
    $strength = $inData['strength'];
    $dexterity = $inData['dexterity'];
    $intelligence = $inData['intelligence'];
    $wisdom = $inData['wisdom'];
    $constitution = $inData['constitution'];
    $charisma = $inData['charisma'];
    $initiative = $inData['initiative'];
    $skillProf = $inData['skillProf'];
    $itemProf = $inData['itemProf'];
    $abilities = $inData['abilities'];
    $inventory = $inData['inventory'];
    $weapons = $inData['weapons'];
    $tempHP = $inData['tempHP'];
    $spells = $inData['spells'];
    $savingThrows = $inData['savingThrows'];
    $hitDie = $inData['hitDie'];

    $query = "SELECT classID FROM Classes WHERE name = '$className'";

    $result = $connection->query($query);

    if (!$result) {
      returnWithError($connection->connect_error);
    } else {

      $row = $result->fetch_assoc();

      $classID = $row['classID'];

      $query = "SELECT raceID FROM Races WHERE raceName = '$raceName'";

      $result = $connection->query($query);

      if (!$result) {
        returnWithError($connection->connect_error);
      } else {

        $row = $result->fetch_assoc();

        $raceID = $row['raceID'];

        $query = "INSERT INTO Characters (raceID, userID, classID, name, personality, ideals, bonds, flaws, languages, passiveWisdom, proficiencyBonus, featuresAndTraits, speed, gold, silver, copper, background, level, expPoints, armorClass, maxHP, currentHP, inspiration, alignment, strength, dexterity, intelligence, wisdom, constitution, charisma, initiative, skillProf, itemProf, abilities, inventory, weapons, tempHP, spells, savingThrows, hitDie) VALUES ($raceID, $userID, $classID, '$name', '$personality', '$ideals', '$bonds', '$flaws', '$languages', $passiveWisdom, $proficiencyBonus, '$featuresAndTraits', $speed, $gold, $silver, $copper, '$background', $level, $expPoints, $armorClass, $maxHP, $currentHP, $inspiration, '$alignment', $strength, $dexterity, $intelligence, $wisdom, $constitution, $charisma, $initiative, '$skillProf', '$itemProf', '$abilities', '$inventory', '$weapons', $tempHP, '$spells', '$savingThrows', $hitDie)";

        $result = $connection->query($query);

        if (!$result) {
          returnWithError($connection->connect_error);
        } else {
          returnWithInfo("Character creation successful!");
        }

      }


    }

    $connection->close();

    }



function getRequestInfo() {
  return json_decode(file_get_contents('php://input'), true);
}

function sendResultInfoAsJson($obj) {
  header('Content-type: appplication/json');
  echo $obj;
}

function returnWithError($error) {
  $returnValue = '{"info": "", "error": "' . $error . '"}';
  sendResultInfoAsJson($returnValue);
}

function returnWithInfo($info) {
  $returnValue = '{"info": "' . $info . '", "error": ""}';
  sendResultInfoAsJson($returnValue);
}

 ?>

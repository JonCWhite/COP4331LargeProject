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


    $characterID = $inData['characterID'];

    $query = "SELECT * FROM Characters WHERE characterID = $characterID";

    $result = $connection->query($query);

    if (!$result) {
      returnWithError($connection->connect_error);
    } else {

      $row = $result->fetch_assoc();

      $raceID = $row['raceID'];
      $classID = $row['classID'];
      $name = $row['name'];
      $personality = $row['personality'];
      $ideals = $row['ideals'];
      $bonds = $row['bonds'];
      $flaws = $row['flaws'];
      $languages = $row['languages'];
      $passiveWisdom = $row['passiveWisdom'];
      $proficiencyBonus = $row['proficiencyBonus'];
      $featuresAndTraits = $row['featuresAndTraits'];
      $speed = $row['speed'];
      $gold = $row['gold'];
      $silver = $row['silver'];
      $copper = $row['copper'];
      $spellCastingAbility = $row['spellCastingAbility'];
      $background = $row['background'];
      $level = $row['level'];
      $expPoints = $row['expPoints'];
      $armorClass= $row['armorClass'];
      $maxHP = $row['maxHP'];
      $currentHP = $row['currentHP'];
      $inspiration = $row['inspiration'];
      $alignment = $row['alignment'];
      $strength = $row['strength'];
      $dexterity = $row['dexterity'];
      $intelligence = $row['intelligence'];
      $wisdom = $row['wisdom'];
      $constitution = $row['constitution'];
      $charisma = $row['charisma'];
      $initiative = $row['initiative'];
      $skillProf = $row['skillProf'];
      $itemProf = $row['itemProf'];
      $abilities = $row['abilities'];
      $inventory = $row['inventory'];
      $weapons = $row['weapons'];
      $tempHP = $row['tempHP'];
      $spells = $row['spells'];
      $savingThrows = $row['savingThrows'];
      $hitDie = $row['hitDie'];

      $query = "SELECT raceName FROM Races WHERE raceID = $raceID";

      $result = $connection->query($query);

      if (!$result) {
        returnWithError($connection->connect_error);
      } else {

        $row = $result->fetch_assoc();

        $race = $row['raceName'];

        $query = "SELECT name FROM Classes WHERE classID = $classID";

        $result = $connection->query($query);

        if (!$result) {
          returnWithError($connection->connect_error);
        } else {

          $row = $result->fetch_assoc();

          $class = $row['name'];

          returnWithInfo($class, $race, $name, $personality, $ideals, $bonds, $flaws, $languages, $passiveWisdom, $proficiencyBonus, $featuresAndTraits, $speed, $gold, $silver, $copper, $spellCastingAbility, $background, $level, $expPoints, $armorClass, $maxHP, $currentHP, $inspiration, $alignment, $strength, $dexterity, $intelligence, $wisdom, $constitution, $charisma, $initiative, $skillProf, $itemProf, $abilities, $inventory, $weapons, $tempHP, $spells, $savingThrows, $hitDie);

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
  $returnValue = '{"class": "", "race": "", "name": "", "personality": "", "ideals": "", "bonds": "", "flaws": "", "languages": "", "passiveWisdom": 0, "proficiencyBonus": 0, "featuresAndTraits": "", "speed": 0, "gold": 0, "silver": 0, "copper": 0, "spellCastingAbility": "", "background": "", "level": 0, "expPoints": 0, "armorClass": 0, "maxHP": 0, "currentHP": 0, "inspiration": 0, "alignment": "", "strength": 0, "dexterity": 0, "intelligence": 0, "wisdom": 0, "constitution": 0, "charisma": 0, "initiative": 0, "skillProf": "", "itemProf": "", "abilities": "", "inventory": "", "weapons": "", "tempHP": 0, "spells": "", "savingThrows": "", "hitDie": 0}';
  sendResultInfoAsJson($returnValue);
}

function returnWithInfo($class, $race, $name, $personality, $ideals, $bonds, $flaws, $languages, $passiveWisdom, $proficiencyBonus, $featuresAndTraits, $speed, $gold, $silver, $copper, $spellCastingAbility, $background, $level, $expPoints, $armorClass, $maxHP, $currentHP, $inspiration, $alignment, $strength, $dexterity, $intelligence, $wisdom, $constitution, $charisma, $initiative, $skillProf, $itemProf, $abilities, $inventory, $weapons, $tempHP, $spells, $savingThrows, $hitDie) {
  $returnValue = '{"class": "' . $class . '", "race": "' . $race . '", "name": "' . $name . '", "personality": "' . $personality . '", "ideals": "' . $ideals . '", "bonds": "' . $bonds . '", "flaws": "' . $flaws . '", "languages": "' . $languages . '", "passiveWisdom": ' . $passiveWisdom . ', "proficiencyBonus": ' . $proficiencyBonus . ', "featuresAndTraits": "' . $featuresAndTraits . '", "speed": ' . $speed . ', "gold": ' . $gold . ', "silver": ' . $silver . ', "copper": ' . $copper . ', "spellCastingAbility": "' . $spellCastingAbility . '", "background": "' . $background . '", "level": ' . $level . ', "expPoints": ' . $expPoints . ', "armorClass": ' . $armorClass . ', "maxHP": ' . $maxHP . ', "currentHP": ' . $currentHP . ', "inspiration": ' . $inspiration . ', "alignment": "' . $alignment . '", "strength": ' . $strength . ', "dexterity": ' . $dexterity . ', "intelligence": ' . $intelligence . ', "wisdom": ' . $wisdom . ', "constitution": ' . $constitution . ', "charisma": ' . $charisma . ', "initiative": ' . $initiative . ', "skillProf": "' . $skillProf . '", "itemProf": "' . $itemProf . '", "abilities": "' . $abilities . '", "inventory": "' . $inventory . '", "weapons": "' . $weapons . '", "tempHP": ' . $tempHP . ', "spells": "' . $spells . '", "savingThrows": "' . $savingThrows . '", "hitDie": ' . $hitDie. '}';
  sendResultInfoAsJson($returnValue);
}

 ?>

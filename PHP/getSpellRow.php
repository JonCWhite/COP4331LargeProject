<?php


    // Edit character sheet
    $characterID = $_POST["characterID"];

	// Set up connection
    $hostname = 'localhost';
    $databaseName = 'dndApp';
    $username = 'root';
    $password = 'contactmanager7';
	
    $conn = new mysqli($hostname, $username, $password, $databaseName);

    // Check for connection error
    if ($conn->connect_error)
    {
        returnWithError($conn->connect_error);
    }
    // Interact with database
    else
    {
        //////////////////////////////////////////

        $sql = $conn->query("SELECT * FROM Characters WHERE characterID = $characterID");
        $get = $sql->fetch_assoc();
        $spellCastingAbility = $get["spellCastingAbility"];
        $proficiency = $get["proficiencyBonus"];

        //////////////////////////////////////////

        if($spellCastingAbility == "Cha" || $spellCastingAbility == "Cha")
        {
            $sql = $conn->query("SELECT charisma FROM Characters WHERE characterID = $characterID");
            $getMod = $sql->fetch_assoc();
            $attribute = $getMod["charisma"];
        }
        if($spellCastingAbility == "Wis" || $spellCastingAbility == "wis")
        {
            $sql = $conn->query("SELECT intelligence FROM Characters WHERE characterID = $characterID");
            $getMod = $sql->fetch_assoc();
            $attribute = $getMod["intelligence"];
        }
        if($spellCastingAbility == "Cha" || $spellCastingAbility == "Cha")
        {
            $sql = $conn->query("SELECT wisdom FROM Characters WHERE characterID = $characterID");
            $getMod = $sql->fetch_assoc();
            $attribute = $getMod["wisdom"];    
        }

        $spellMod = floor(($attribute - 10)/2);

        $spellSaveDC = 8 + $proficiency + $spellMod;

        $spellAttackBonus = $proficiency + $spellMod;

        $return = array(
            'spellCastingAbility' => $spellCastingAbility,
            'spellSaveDC' => $spellSaveDC,
            'spellAttackBonus' => $spellAttackBonus
        );

        $conn->close();

        // Return the info found.
        sendResultInfo($return);

        $conn->close();
    }

    function returnWithError($error)
    {
        $returnValue = '{"error": "' . $error . '"}';
        sendResultInfoAsJson($returnValue);
    }

    function sendResultInfo($rows)
    {
        $returnValue = json_encode($rows);
        sendResultInfoAsJson($returnValue);
    }

    function sendResultInfoAsJson($obj)
    {
        header('Content-type: application/json');
        echo $obj;
    }
?>

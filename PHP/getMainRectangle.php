<?php
    // Edit character sheet

    // Take data from front-end
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
        $sql = $conn->query("SELECT * FROM Characters WHERE characterID = $characterID");
        $row = $sql->fetch_assoc();

        $rows[] = array(
            'strength' => $row["strength"],
            'dexterity' => $row["dexterity"],
            'constitution' => $row["constitution"],
            'intelligence' => $row["strength"],
            'wisdom' => $row["dexterity"],
            'charisma' => $row["constitution"],
            'savingThrows' => $row["savingThrows"],
            'skillProf' => $row["skillProf"],
            'passiveWisdom' => $row["passiveWisdom"],
            'inspiration' => $row["inspiration"],
            'proficiencyBonus' => $row["proficiencyBonus"],
			'userID' => $row["userID"]
        );

        $conn->close();

        // Return the info found.
        sendResultInfo($rows);
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

<?php
    // Edit character sheet

    // Take data from front-end
    $characterID = $_POST["characterID"];
    $dexterity = $_POST["dexterity"];
    $constitution = $_POST["constitution"];
    $intelligence = $_POST["intelligence"];
    $wisdom = $_POST["wisdom"];
    $charisma = $_POST["charisma"];
    $strength = $_POST["strength"];
    $savingThrows = $_POST["savingThrows"];
    $skillProf = $_POST["skillProf"];
    $passiveWisdom = $_POST["passiveWisdom"];
    $inspiration = $_POST["inspiration"];
    $proficiencyBonus = $_POST["proficiencyBonus"];


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
        $conn->query("UPDATE Characters SET dexterity = $dexterity WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET constitution = $constitution WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET intelligence = $intelligence WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET wisdom = $wisdom WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET charisma = $charisma WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET strength = $strength WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET savingThrows = '$savingThrows' WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET skillProf = '$skillProf' WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET passiveWisdom = $passiveWisdom WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET inspiration = $inspiration WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET proficiencyBonus = $proficiencyBonus WHERE characterID = $characterID");

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

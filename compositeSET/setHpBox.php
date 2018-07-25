<?php
    // Edit character sheet

    // Take data from front-end
    $characterID = $_POST["characterID"];
    $armorClass = $_POST["armorClass"];
    $initiative = $_POST["initiative"];
    $speed = $_POST["speed"];
    $currentHP = $_POST["currentHP"];
    $maxHP = $_POST["maxHP"];
    $tempHP = $_POST["tempHP"];
    $hitDie = $_POST["hitDie"];

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
        $conn->query("UPDATE Characters SET armorClass = $armorClass WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET initiative = $initiative WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET speed = $speed WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET currentHP = $currentHP WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET maxHP = $maxHP WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET tempHP = $tempHP WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET hitDie = $hitDie WHERE characterID = $characterID");

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

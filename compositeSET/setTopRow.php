<?php
    // Edit character sheet
    $characterID = $_POST["characterID"];
    // Take data from front-end
    $raceName = $_POST["raceName"];
    $className = $_POST["className"];
    $characterName = $_POST["characterName"];
    $level = $_POST["level"];
    $background = $_POST["background"];
    $alignment = $_POST["alignment"];
    $expPoints = $_POST["expPoints"];

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

        $sql = $conn->query("SELECT raceID FROM Races WHERE raceName = '$raceName'");
        $getRaceID = $sql->fetch_assoc();
        $raceID = $getRaceID["raceID"];

        $sql = $conn->query("SELECT classID FROM Classes WHERE name = '$className'");
        $getClassID = $sql->fetch_assoc();
        $classID = $getClassID["classID"];

        //////////////////////////////////////////


        $conn->query("UPDATE Characters SET name = '$characterName' WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET classID = $classID WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET level = $level WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET raceID = $raceID WHERE characterID = $characterID");  
        $conn->query("UPDATE Characters SET background = '$background' WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET alignment = '$alignment' WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET expPoints = $expPoints WHERE characterID = $characterID");  

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

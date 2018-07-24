<?php
    // Edit character sheet

    // Take data from front-end
    $characterID = $_POST["characterID"];
    $personality = $_POST["personality"];
    $ideals = $_POST["ideals"];
    $bonds = $_POST["bonds"];
    $flaws = $_POST["flaws"];

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
        $conn->query("UPDATE Characters SET personality = '$personality' WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET ideals = '$ideals' WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET bonds = '$bonds' WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET flaws = '$flaws' WHERE characterID = $characterID");

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

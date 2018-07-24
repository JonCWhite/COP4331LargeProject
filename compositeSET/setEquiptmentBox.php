<?php
    // Edit character sheet

    // Take data from front-end
    $characterID = $_POST["characterID"];
    $inventory = $_POST["inventory"];
    $gold = $_POST["gold"];
    $silver = $_POST["silver"];
    $copper = $_POST["copper"];


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
        $conn->query("UPDATE Characters SET inventory = '$inventory' WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET gold = $gold WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET silver = $silver WHERE characterID = $characterID");
        $conn->query("UPDATE Characters SET copper = $copper WHERE characterID = $characterID");   

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

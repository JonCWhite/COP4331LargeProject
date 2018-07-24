<?php
    // Edit character sheet

    // Take data from front-end
    $characterID = $_POST["characterID"];
    $featuresAndTraits = $_POST["featuresAndTraits"];

    // Set up connection
    $hostname = 'localhost';
    $databaseName = 'dndApp';
    $username = 'root';
    $password = 'contactmanager7';

    $conn = new mysqli($hostname, $username, $password, $databaseName);

    // Check for connection error
    if ($conn->connect_error)
    {
        error($conn->connect_error);
    }
    // Interact with database
    else
    {
        $conn->query("UPDATE Characters SET featuresAndTraits = '$featuresAndTraits' WHERE characterID = $characterID");

        $conn->close();
    }

    function error( $err )
    {
        $retValue = '{"featuresAndTraits": 0, "error": "' . $err . '"}';
        sendResultInfoAsJson( $retValue );
    }

    function sendResultInfoAsJson( $obj )
    {
        header('Content-type: application/json');
        echo $obj;
    }
?>

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
            'abilities' => $row["abilities"],
            'weapons' => $row["weapons"],
            'spells' => $row["spells"]
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

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

        //////////////////////////////////////////

        $race = $row["raceID"];
        $class = $row["classID"];
		$userID = $row["userID"];

        $sql = $conn->query("SELECT raceName FROM Races WHERE raceID = $race");
        $getRaceName = $sql->fetch_assoc();
        $raceName = $getRaceName["raceName"];

        $sql = $conn->query("SELECT name FROM Classes WHERE classID = $class");
        $getClassName = $sql->fetch_assoc();
        $className = $getClassName["name"];
		
		$sql = $conn->query("SELECT username FROM Users WHERE userID = $userID");
		$getUsername = $sql->fetch_assoc();
		$username = $getUsername["username"];

        //////////////////////////////////////////

        $rows[] = array(
            'characterName' => $row["name"],
            'class' => $className,
            'level' => $row["level"],
            'race' => $raceName,
            'background' => $row["background"],
            'playerName' => $username,
            'alignment' => $row["alignment"],
            'experiencePoints' => $row["expPoints"],
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

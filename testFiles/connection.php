<?php


	// Declare connection information as constants
	define('hostname', 'localhost');
	define('databaseUsername', 'root');
	define('databasePassword', 'contactmanager7');
	define('databaseName', 'dndApp');
	
	$connect = msqli_connect(hostname, databaseUsername, databasePassword, databaseName);

?>
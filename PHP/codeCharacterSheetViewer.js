// COP 4331 Group 7
// John Balbuza, Fernando Bilbao, Arisa Kitagishi, Imahd Khan, Susan Long,
// Sarah Thompson, Jonathan White
// Our website name and file locations
var urlBase = 'http://cop4331-7.xyz/LAMP';
var extension = "php";



// This function registers the users
// The user inputs their first name, last name, username and password of choice


// This function logs the user in with their username and password
function doLogin()
{
	// Variables
	userId = 0;
	username = "";

	// Get username and password values to log in
	var login = document.getElementById("username").value;
	var password = document.getElementById("password").value;

	document.getElementById("loginResult").innerHTML = "";

	// Get Json
	var jsonPayload =  '{"username" : "' + login + '", "password" : "' + password + '"}';
	var url = urlBase + '/websiteLogin.' + extension;
	var xhr = new XMLHttpRequest();
	xhr.open("POST", url, false);
	xhr.setRequestHeader("Content-type", "application/json; charset=UTF-8");

	try
	{
		// Send Json
		xhr.send(jsonPayload);

		var jsonObject = JSON.parse( xhr.responseText );

		userId = jsonObject.id;

		// Show the first name and last name in the top right
		username = jsonObject.username;
		document.getElementById("username").value = "";
		document.getElementById("password").value = "";
		location.reload();
	}

	// Unable to login in. Check your fields of entry.
	catch(err)
	{
		document.getElementById("loginResult").innerHTML = "Incorrect Username/Password";
	}

}

// This function logs the user out
function doLogout()
{
	userId = 0;
	firstName = "";
	lastName = "";

	// Add extension
	var url = urlBase + '/Logout.' + extension;
	var xhr = new XMLHttpRequest();
	xhr.open("POST", url, false);
	try
	{
		// Send the request and refresh
		xhr.send();
		location.reload();
	}
	catch(err)
	{
		// document.getElementById("loginResult").innerHTML = "Logout";
	}
}

// This functions hides or shows the elements.
// Function given by the professor.
function hideOrShow( elementId, showState )
{
	var vis = "visible";
	var dis = "block";
	if( !showState )
	{
		vis = "hidden";
		dis = "none";
	}

	document.getElementById( elementId ).style.visibility = vis;
	document.getElementById( elementId ).style.display = dis;
}

function getPlayerCharacters() {
	var characterSelect = document.getElementById("characterSelect");
	
	// Get Json
	var jsonPayload = '{"userID" : ' + userID + '}';
	var url = urlBase+'/webGetCharacterList.' + extension;
	var xhr = new XMLHttpRequest();
	xhr.open("POST",url,true);
	xhr.setRequestHeader("Content-type", "application/json; charset=UTF-8");
	try
	{
		xhr.onreadystatechange=function()
		{
			// Sucess! Showing contacts
			if(this.readyState==4 && this.status ==200)
			{
				var jsonObject=JSON.parse(xhr.responseText);
				var i, character, characterID;
				
				console.log(jsonPayload);
				console.log(jsonObject);
				
				// Create the table for the corresponding search
				for(i=0;i<jsonObject.length;i++)
				{
					character = document.createElement("option");
					character.text = jsonObject[i]['name'];
					character.setAttribute("data-characterid", jsonObject[i]['name']);
					characterSelect.add(character);
				}
			}
		};
		// Send the Json
		xhr.send(jsonPayload);
	}
	// Couldn't show contacts, so catch the error
	catch(err)
	{
		console.log("Error occured when making XHR request.");
	}
}

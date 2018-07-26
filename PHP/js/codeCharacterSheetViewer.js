// COP 4331 Group 7
// John Balbuza, Fernando Bilbao, Arisa Kitagishi, Imahd Khan, Susan Long,
// Sarah Thompson, Jonathan White
// Our website name and file locations
var urlBase = 'http://cop4331-7.xyz/system';
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
/*
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
}*/

function addPlayerCharacters() {
var characterNameField= document.getElementById("characterNameField").value;
	var classLevel= document.getElementById("classLevel").value;
	var background = document.getElementById("background").value;
//	var playerName= document.getElementById("playerName").value;
	var race= document.getElementById("race").value;
	var alignment= document.getElementById("alignment").value;
	var experience= document.getElementById("experience").value;
	var str= document.getElementById("str").value;
	var dex= document.getElementById("dex").value;
 var con= document.getElementById("con").value;
var intel= document.getElementById("int").value;
var wis= document.getElementById("wis").value;
var cha= document.getElementById("cha").value;
var inspiration= document.getElementById("inspiration").value;
var proficiencyBonus= document.getElementById("proficiencyBonus").value;
var savingThrows= document.getElementById("savingThrows").value;
var skillProf= document.getElementById("skillProf").value;
var passiveWisdom= document.getElementById("passiveWisdom").value;
var languages= document.getElementById("languages").value;
var itemProf= document.getElementById("itemProf").value;
var ac= document.getElementById("ac").value;
var initiative= document.getElementById("initiative").value;
var speed= document.getElementById("speed").value;
var maxHP= document.getElementById("maxHP").value;
var currentHP= document.getElementById("currentHP").value;
var tempHP= document.getElementById("tempHP").value;
var hitDie= document.getElementById("hitDie").value;
var abilities= document.getElementById("abilities").value;
var weapons= document.getElementById("weapons").value;
var spells= document.getElementById("spells").value;
var gold= document.getElementById("gold").value;
var silver= document.getElementById("silver").value;
var copper= document.getElementById("copper").value;
var inventory= document.getElementById("inventory").value;
var personality= document.getElementById("personality").value;
var ideals= document.getElementById("ideals").value;
var bonds= document.getElementById("bonds").value;
var flaws= document.getElementById("flaws").value;
var featuresAndTraits= document.getElementById("featuresAndTraits").value;

	//document.getElementById("contactAddResul").innerHTML = "";

	// Get Json
	var jsonPayload = '{"userID": ' + userId + ', "name": "' + characterNameField + '", "level": ' + classLevel + ', "background":"'+background+ '", "raceName":"'+race+'","alignment" : "' + alignment + '", "expPoints": '+ experience + ',"strength":' + str + ',"dexterity":' + dex + ',"constitution": ' + con + ',"intelligence": ' + intel + ', "wisdom": ' + wis + ', "charisma":' + cha + ',"inspiration" : ' + inspiration + ', "proficiencyBonus": ' + proficiencyBonus + ',"savingThrows" : "' + savingThrows + '", "skillProf":"'+ skillProf+ '","passiveWisdom": ' + passiveWisdom + ',"languages":"'+languages+'","itemProf":"'+itemProf+'","armorClass": ' + ac + ', "initiative":'+ initiative+', "speed":'+speed+',"maxHP" : ' + maxHP + ', "currentHP":'+currentHP+',"tempHP" : ' + tempHP + ', "hitDie":'+ hitDie+ ',"abilities":"'+abilities+'","weapons":"'+weapons+'","spells":"'+spells+'","gold": ' + gold + ', "silver":'+ silver+', "copper":'+copper+',"personality" : "' + personality + '", "ideals":"'+ideals+'","bonds" : "' + bonds + '", "flaws":"'+ flaws+ '","featuresAndTraits":"'+featuresAndTraits+'"}';

	var url = urlBase + '/addCharacter.' + extension;

		// Request
	    var xhr = new XMLHttpRequest();
	  	xhr.open("POST", url, true);
	  	xhr.setRequestHeader("Content-type", "application/json; charset=UTF-8");
		try
		{
			xhr.onreadystatechange = function()
			{
				// Sucess! Contacts have been added
				if (this.readyState == 4 && this.status == 200)
				{
					// Show new form
	          		document.getElementById("nameForm").reset();
				}
			};
		xhr.send(jsonPayload);
		}
		// Error with adding contact
		catch(err)
		{
			//document.getElementById("contactAddResult").innerHTML = err.message;
		}
}

<?php
  session_start();
?>

<!DOCTYPE html>
<!-- COP 4331 Group 7 -->
<!-- John Balbuza, Fernando Bilbao, Arisa Kitagishi, Imahd Khan, Susan Long,
Sarah Thompson, Jonathan White -->
<html>
<head>
	<!-- External Libraries -->
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<script type="text/javascript" src="js/codeCharacterSheetViewer.js"></script>

	<style>
    .btn-format {
      margin-top: 10px;
    }

    .signup-tooltip {
      margin-top: 10px;
    }

    li {
      display: inline;
    }
	
		.well-panels {
		background-color: AAAAAA !important;
	}

	</style>
</head>
<body>
<header>
<!-- Navigation bar -->
<nav class="navbar navbar-default" id="loggedInNavBar">
  	<div class="navbar-header">
  		<a class="navbar-brand" href="#">Character Viewer</a>
  	</div>
  	<ul class="nav navbar-nav pull-right">	
		<!-- Get session variables (these will be used in the javascript) -->
		<?php if (isset($_SESSION['u_id']))
		{?>
			<script type="text/javascript">
				var userID = '<?php echo $_SESSION['u_id'];?>';
				var username = '<?php echo $_SESSION['u_username'];?>';
			</script>
			<span id="loggedInDiv">
    		<li>Logged in as
        <?php echo $_SESSION['u_username'];?> 
        </li>
    	<li>
    		<span>
    			<button type="button" id="logoutButton" class="btn navbar-btn nav-btn-format" onclick="doLogout();"> Log Out </button>
    		</span>
    	</li>
       </span>
			 <?php
		} ?>
  	</ul>
  </nav>
</header>

<!-- Login page (only displayed if the user is not logged in -->
<?php if (!isset($_SESSION['u_id']))
{?>
<div class="container-fluid" id="loginDiv">
	<h1 class="text-center">Character Viewer</h1>
	<div class="row">
		<div class="col-sm-4"></div>
		<div class="col-sm-4">
			<div class="well">
				<form method="post" action="websiteLogin.php">
				<div class="form-group" id="loginForm">
					<label for="username">Username:</label>
					<input type="text" class="form-control" id="username"></input>
					<label for="password">Password:</label>
					<input type="password" class="form-control" id="password"></input>
					<button type="button" class="btn btn-format" id="loginButton" onclick="doLogin();" >Log In</button>
          <span id="loginResult"> </span>
      			</form>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-4"></div>
	</div>
</div>
<?php
} ?>

<?php if (isset($_SESSION['u_id'])) { ?>

<script type="text/javascript">
	var userId = '<?php echo $_SESSION['u_id'];?>';
</script>

<div id="accessUIDiv">	 
	<div class="row">
	<div class="col-sm-1"></div>
		<div class="col-sm-10">
			<div class="well row">
				<!-- Displays user's contacts. This section also contains
				search and delete -->
				<h2>Select Sheet</h2>
				<div class="dropdown" id="characterSelect">
					<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Select Character
					<span class="caret"></span></button>
					<ul class="dropdown-menu" id="menuList">
					</ul>
				  </div>
				
				<!-- Form for adding a contact. First and last name fields are
				 required -->
				<h2>Character Sheet</h2>
				<div class="col-sm-12">
					<div class="well row">
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group">
									<label for="characterNameField">Character Name:</label>
									<input type="text" class="form-control" placeholder="Character Name" id="characterNameField">
								</div>
							</div>
							<label class="sr-only" for="nameForm">Name:</label>
							<form class="form-inline" id="nameForm">
								<div class="col-sm-2">
									<div class="form-group">
										<label for="addFirstName">Class & Level:</label>
										<input type="text" class="form-control" placeholder="First name" id="classLevel">
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label for="addFirstName">Background:</label>
										<input type="text" class="form-control" placeholder="First name" id="background">
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label for="addLastName">Player Name:</label>
										<input type="text" class="form-control" placeholder="Last name" id="playerName">
									</div>
								</div>
							</form>
						</div>
						<div class="row">
							<div class="col-sm-6"></div>
							<form class="form-inline" id="nameForm">
								<div class="col-sm-2">
									<div class="form-group">
										<label for="addFirstName">Race:</label>
										<input type="text" class="form-control" placeholder="First name" id="race">
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label for="addFirstName">Alignment:</label>
										<input type="text" class="form-control" placeholder="First name" id="alignment">
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label for="addLastName">Experience Points:</label>
										<input type="text" class="form-control" placeholder="Last name" id="experience">
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				
				
				
					<div class="col-sm-4">
						<div class="well row" id="statsWell">
							<div class="col-sm-2">
								<div class="form-group">
									<label for="addLastName">Str:</label>
									<input type="text" class="form-control" placeholder="Str" id="str">
								</div>
								<div class="form-group">
									<label for="addLastName">Dex:</label>
									<input type="text" class="form-control" placeholder="Dex" id="dex">
								</div>
								<div class="form-group">
									<label for="addLastName">Con:</label>
									<input type="text" class="form-control" placeholder="Con" id="con">
								</div>
								<div class="form-group">
									<label for="addLastName">Int:</label>
									<input type="text" class="form-control" placeholder="Int" id="int">
								</div>
								<div class="form-group">
									<label for="addLastName">Wis:</label>
									<input type="text" class="form-control" placeholder="Wis" id="wis">
								</div>
								<div class="form-group">
									<label for="addLastName">Cha:</label>
									<input type="text" class="form-control" placeholder="Cha" id="cha">
								</div>
							</div>
							<div class="col-sm-10">
								<div class="form-group">
									<label for="addLastName">Inspiriation:</label>
									<input type="text" class="form-control" placeholder="Inspiration" id="inspiration">
								</div>
								<div class="form-group">
									<label for="addLastName">Proficiency Bonus:</label>
									<input type="text" class="form-control" placeholder="Proficiency Bonus" id="proficiencyBonus">
								</div>
								<div class="well row">
									<div class="form-group">
										<label for="savingThrows">Saving Throws:</label>
										<textarea class="form-control" rows="5" id="savingThrows"></textarea>
									</div>
								</div>
								<div class="well row">
									<div class="form-group">
										<label for="skillProf">Skill Proficiencies:</label>
										<textarea class="form-control" rows="5" id="skillProf"></textarea>
									</div>
								</div>
								<label for="passiveWisdom">Passive Wisdom:</label>
								<input type="text" class="form-control" placeholder="Passive Wisdom" id="passiveWisdom">
							</div>
						</div>
						
						<div class="well row" id="otherWell">
							<div class="form-group">
								<label for="languages">Languages:</label>
								<textarea class="form-control" rows="5" id="languages"></textarea>
							</div>
							<div class="form-group">
								<label for="itemProf">Item Proficiencies:</label>
								<textarea class="form-control" rows="5" id="itemProf"></textarea>
							</div>
						</div>
					</div>
					
					
					
					
					<div class="col-sm-4">
						<div class="well row">
							<div class="row">
								<div class="col-sm-4">
									<div class="form-group">
										<label for="ac">Armor Class:</label>
										<input type="text" class="form-control" placeholder="AC" id="ac">
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label for="initiative">Initiative:</label>
										<input type="text" class="form-control" placeholder="Initiative" id="initiative">
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label for="speed">Speed:</label>
										<input type="text" class="form-control" placeholder="Speed" id="speed">
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="comment">Max Hit Points:</label>
								<input type="text" class="form-control" placeholder="Max HP" id="maxHP">
							</div>
							<div class="form-group">
								<label for="comment">Current Hit Points:</label>
								<input type="text" class="form-control" placeholder="Current HP" id="currentHP">
							</div>
							<div class="form-group">
								<label for="comment">Temporary Hit Points:</label>
								<input type="text" class="form-control" placeholder="Temporary HP" id="tempHP">
							</div>
							<div class="form-group">
								<label for="hitDie">Hit Dice:</label>
								<input type="text" class="form-control" placeholder="Hit Die" id="hitDie">
							</div>
						</div>
						
						<div class="well row">
							<div class="form-group">
								<label for="abilities">Abilities:</label>
								<textarea class="form-control" rows="5" id="abilities"></textarea>
							</div>
							<div class="form-group">
								<label for="weapons">Weapons:</label>
								<textarea class="form-control" rows="5" id="weapons"></textarea>
							</div>
							<div class="form-group">
								<label for="spells">Spells:</label>
								<textarea class="form-control" rows="5" id="spells"></textarea>
							</div>
						</div>
						
						<div class="well row">
							<div class="row">
								<div class="col-sm-4">
									<div class="form-group">
										<label for="gold">Gold Pieces:</label>
										<input type="text" class="form-control" placeholder="Gold" id="gold">
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label for="silver">Silver Pieces:</label>
										<input type="text" class="form-control" placeholder="Silver" id="silver">
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label for="copper">Copper Pieces:</label>
										<input type="text" class="form-control" placeholder="Copper" id="copper">
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="inventory">Equipment:</label>
								<textarea class="form-control" rows="5" id="inventory"></textarea>
							</div>
						</div>
					</div>
					
					
					
					<div style="margin-xs" class="col-sm-4">
						<div class="well row">
							<div class="form-group">
								<label for="personality">Personality Traits:</label>
								<textarea class="form-control" rows="5" id="personality"></textarea>
							</div>
							<div class="form-group">
								<label for="ideals">Ideals:</label>
								<textarea class="form-control" rows="5" id="ideals"></textarea>
							</div>
							<div class="form-group">
								<label for="bonds">Bonds:</label>
								<textarea class="form-control" rows="5" id="bonds"></textarea>
							</div>
							<div class="form-group">
								<label for="flaws">Flaws:</label>
								<textarea class="form-control" rows="5" id="flaws"></textarea>
							</div>
						</div>
						
						<div class="well row">
							<div class="form-group">
								<label for="featuresAndTraits">Features & Traits:</label>
								<textarea class="form-control" rows="5" id="featuresAndTraits"></textarea>
							</div>
						</div>
					</div>
			

				
				
				
			</div>
		</div>
	</div>
</div>
<script src="js/jquery-1.12.4.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script language="javascript">
$(document).ready(function(){
			// Populate drop-down with this user's characters
			getPlayerCharacters();
		});
</script>

</body>
</html>
<?php
} ?>

	
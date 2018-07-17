<?php
    // Initilize Character
    // Get file contents
    

    // Take data from front-end
    $race = $_POST["raceID"];
    $class = $_POST["classID"];
    $rollDex = $_POST["rollDex"];
    $rollCha = $_POST["rollCha"];
    $rollStr = $_POST["rollStr"];
    $rollCon = $_POST["rollCon"];
    $rollInt = $_POST["rollInt"];
    $rollWis = $_POST["rollWis"];

    $user = $_POST["userID"];

    $strength = $rollStr;
    $charisma = $rollCha;
    $constitution = $rollCon;
    $intelligence = $rollInt;
    $wisdom = $rollWis;
    $dexterity = $rollDex;
    $speed = 0;
    $hitDie = 0;
    $HPAddition = 0;
    $languages = "Common, ";
    $spellCastingAbility = "";
    $itemProf = [];
    $skillProf = [];
    $savingThrows = [];
    $abilities = [];
    $inventory = [];
    $quantity = [];

    // Set up connection
    $hostname = 'localhost';
    $databaseName = 'tavernTable';
    $username = 'root';
    $password = 'tavernTable7';

    $conn = new mysqli($hostname, $username, $password, $databaseName);

    // Check for connection error
    if ($conn->connect_error) 
    {
        error($conn->connect_error);
    } 
    // Interact with database
    else
    {
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// Races ////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

        if($race == "DragonBorn")
        {
            // Stength +2
            $strength += 2; 
            // Charisma +1
            $charisma += 1;
            // Speed base: 30 ft
            $speed = 30;
            // Damage resistance to the damage type associated with your ancestry
            // Languages: read, speak, write Common and Draconic
            $languages .= "Draconic";

            // Breath Weapon -- Ability
                // Black: Acid (5' by 30' line (Dex save))
                // Blue: Lightining (5' by 30' line (Dex save))
                // Brass: Fire (5' by 30' line (Dex save))
                // Bronze: Lightning (5' by 30' line (Dex save))
                // Copper: Acid (5' by 30' line (Dex save))
                // Gold: Fire (15'cone (Dex save))
                // Green: Poison (15'cone (Con save))
                // Red: Fire (15'cone (Dex save))
                // Silver: Cold (15'cone (Con save))
                // White: Cold (15'cone (Con save))
            // DC 8 + Constitution Mod + Proficiency Bonus
            // 2d6 damage on a filed save, half on a successful one.
            // Lvl 6: 3d6, Lvl 11: 4d6, Lvl 16: 5d6
            // Must complete a short or long rest before next use.
            $abilities[] = ["Draconic Ancestry"];

            // $additionalProf = "Draconic Ancestry: Breath Weapon: DC = 8 + Con Mod + Proficiency Bonus, Damage: 2d6 on a failed save, half as much on a successful one. At level 6: 3d6, at level 11: 4d6, at level 16: 5d6. Usable once each rest."; 
        }









        else if($race == "Dwarf" || $race == "HillDwarf" || $race == "MountainDwarf")
        {
            // Constitution: +2
            $constitution += 2;
            // Speed base: 25 ft
            $speed = 25;
            // Proficiency: battleaxe, handaxe, throwing hammar, AND warhammar
            $itemProf[] = ["Battleaxe", "Handaxe", "Throwing Hammar", "War Hammar", "Smith's Tools", "Brewer's Tools", "Mason's Tools"];
            // Languages: speak, read, write Common and Dwarvish.
            $languages .= "Dwarvish";
            // Dark Vision -- Ability
                // See in dim light within 60 feet of you as if bright
                // See in darkness within 60 feet of you as if dim
            // Dwarven Resilience -- Ability
                // Advantage on saving throws against poison
            // Stonecunning --  Ability
                // Intelligence (History) check related to the origin of stonework, you are considered proficient in the history skill, and add double proficiency bonus to the check

            $abilities[] = ["Darkvision", "Dwarven Resilience", "Stonecunning"];
            // $additionalProf = "Dark Vision: you can see in dim light 60 feet ahead of you as if it were bright, and darkness as if it were dim. Dwarven Resilience: advantage on saving throws against poison, and resistance to poison damage. Stonecunning: double proficiency bonus added to history checks related to the origin of stonework.";


            if($race == "HillDwarf")
            {
                // Wisdom: +1
                $wisdom += 1;
                // HP Max: +1, and +1 each level
                $HPAddition += 1;
                $abilities[] = ["Dwarven Toughness"];
            }
            if($race == "MountainDwarf")
            {
                // Strength: +2
                $strength += 2;
                $abilities[] = ["Dwarven Armor Training"];
                $itemProf[] = ["Light Armor", "Medium Armor"];
            }
        }









        else if($race == "Elf" || $race == "HighElf" || $race == "WoodElf")
        {
            // Dexterity: +2
            $dexterity += 2;
            // Base speed: 30 ft
            $speed = 30;
            // Proficiency: Perception
            $skillProf[] = ["Perception"];
            // Languages: speak, read, write Common and Elven
            $languages .= "Elvish";

            // Fey Ancestry -- Ability
                // Advantage on Saving throws against being charmed
                // Magic cannot put you to sleep
            // Trance -- Ability
                // Semi-conscious for 4 hours a day, same benefit as 8 hrs. sleep.  
            // Dark Vision -- Ability
                // See in dim light within 60 feet of you as if bright
                // See in darkness within 60 feet of you as if dim
            $abilities[] = ["Darkvision", "Fey Ancestry", "Trance", "Keen Senses"];
            // $additionalProf = "Dark Vision: you can see in dim light 60 feet ahead of you as if it were bright, and darkness as if it were dim. Fey Ancestry: magic cannot put you to sleep, and you have advantage on saving throws against being charmed. Trance: you can be semi-conscious for 4 hours a day and receive the same benefit as 8 hours of sleep."
            if($race == "HighElf")
            {
                // Intelligence: +1
                $intelligence += 1;
                // Proficiency: Longsword, shortsword, shortbow, longbow
                $itemProf[] = ["Longsword", "Shortsword", "Shortbow", "Longbow"];
                // Cantrip: One cantrip from the wizard list, intelligence is the spell casting ability mod
                $abilities[] = ["Cantrip", "Extra Language"];
                // $additionalProf += " Cantrip: you can choose one cantrip from the wizard list, intelligence is the spell casting ability modifier."
                // Read, speak, write one additional language of choice.
            }
            if($race == "WoodElf")
            {
                // Wisdom: +1
                $wisdom += 1;
                // Proficiency: Longsword, shortsword, shortbow, longbow
                $itemProf[] = ["Longsword", "Shortsword", "Shortbow", "Longbow"];
                // Base speed: 35 ft.
                $speed = 35;
                // Mask of Wild -- Ability
                    // You can attempt to hide even when you are only lightly obscured by foliage, heavy rain, falling snow, mist, and other natural phenomena.
                $abilities[] = ["Mask of Wild", "Fleet of Foot"];
                // $additionalProf += " Mask of the Wild: you can attempt to hide wven when you are only lightly obscured by foliage, heavy raiin, falling snow, mist, and other natural phenomena."
            }
        }









        else if($race == "Gnome" || $race == "ForestGnome" || $race == "RockGnome" || $race == "DeepGnome")
        {
            // Intelligence: +2
            $intelligence += 2;
            // Base speed: 25 ft
            $speed = 25;
            // Languages: speak, read, write Common and Gnomish
            $languages .= "Gnomish";
            // Gnome Cunning -- Ability
                // Advantage on Intelligence, Wisdom, and Charisma saves against magic  
            // Dark Vision -- Ability
                // See in dim light within 60 feet of you as if bright
                // See in darkness within 60 feet of you as if dim
            $abilities[] = ["Darkvision", "Gnome Cunning"];
            // $additionalProf = "Dark Vision: you can see in dim light 60 feet ahead of you as if it were bright, and darkness as if it were dim. Gnome Cunning: You have advantage on all intelligence, wisdom, and charisma saving throws against magic."

            if($race == "ForestGnome")
            {
                // Dexterity: +1
                $dexterity += 1;
                // Natural Illusionist -- Spell
                    // Minor Illusion cantrip -- intelligence is the spellcasting mod
                // Speak with Small Beasts -- Ability
                    // Through sound and gestures, you may communicate simple ideas with small or smaller beasts
                $abilities[] = ["Natural Illusionist", "Speak with Small Beasts"];
                // $additionalProf += " Natural Illusionist: You know the Minor Illusion cantrip-- intelligence is your spellcasting ability. Speak with Small Beasts: Through sound and gestures, you may communicate simple ideas with small or smaller beasts."
            }
            if($race == "RockGnome")
            {
                // Constitution: +1
                $constitution += 1;
                // Proficiency: Artificer's tools
                $itemProf[] = ["Artificer's tools"];
                // $additionalProf += " You have proficiency with Artificer tools. Artificer Lore: whenever you make a intelligence check related ti magical, alchemical, or technological items, you can add twice your proficiency bonus. Ability: Tinker.";
                $abilities[] = ["Artificer's Lore", "Tinker"];
                // Artificer's Lore -- Ability
                    // Whenever you make an Intelligence (History) check related to magical, alchemical, or technological items, you can add twice your proficiency bonus.
                // Tinker -- Ability
                    // Using those tools, you can spend 1 hour and 10 gp worth of materials to construct a Tiny clockwork device (AC 5, 1 hp). The device ceases to function after 24 hours (unless you spend 1 hour repairing it to keep the device functioning), or when you use your action to dismantle it; at that time, you can reclaim the materials used to create it. You can have up to three such devices active at a time. When you create a device, choose one of the following options:
                        // Clockwork Toy: This toy is a c1ockwork animal, monster, or person, such as a frog, mouse, bird, dragon, or soldier. When placed on the ground, the toy moves 5 feet across the ground on each of your turns in a random direction. It makes noises as appropriate to the creature it represents.
                        // Fire Starter: The device produces a miniature flame, which you can use to light a candle, torch, or campfire. Using the device requires your action.
                        // Music Box: When opened, this music box plays a single song at a moderate volume. The box stops playing when it reaches the song's end or when it is closed.
                        // At your DM's discretion, you may make other objects with effects similar in power to these. The prestidigitation cantrip is a good baseline for such effects.
            }
            if($race == "DeepGnome")
            {
                // Intelligence: +2
                $intelligence += 2;
                // Languages: speak, read, write undercommon.
                $languages .= "Undercommon";
                // Superior Darkvision -- Ability
                    // Darkvision radius of 120 feet
                // Stone Camouflage -- Ability
                    // Advantage on Dexterity (Stealth) Checks to hide in rocky terrain.
                unset($abilities[array_search('Darkvision', $abilities)]);
                $abilities[] = ["Superior Darkvision", "Stone Camouflage"];
                // $additionalProf = "Superior Dark Vision: you can see in dim light 120 feet ahead of you as if it were bright, and darkness as if it were dim. Gnome Cunning: You have advantage on all intelligence, wisdom, and charisma saving throws against magic. Stone Camouflage: you have advantage on dexterity checks to hide in rocky terrain."
            }
        }









        else if($race == "Half Elf")
        {
            // Charisma: +2
            $charisma += 2;
            // Base speed: 30 ft
            $speed = 30;
            // Proficiency: Two skills of choice
            // Two ability scores of choice: +1
            // Fey Ancestry -- Ability
                // Advantage on Saving throws against being charmed
                // Magic cannot put you to sleep
            // Dark Vision -- Ability
                // See in dim light within 60 feet of you as if bright
                // See in darkness within 60 feet of you as if dim
            $abilities[] = ["Darkvision", "Fey Ancestry", "Skill Versatility", "Extra Language"];
            // $additionalProf = "Add 1 to two different ability scores. You are proficient in two skills of your choice. Dark Vision: you can see in dim light 60 feet ahead of you as if it were bright, and darkness as if it were dim. Fey Ancestry: You have advantage on saving throws against being charmed and magic cannot put you to sleep."
            // Languages: speak, read, write Common, Elven, and one language of choice
            $languages .= "Elvish";
        }









        
        else if($race == "Half Orc")
        {
            // Strength: +2
            $strength += 2;
            // Constitution: +1
            $constitution += 1;
            // Base speed: 30 ft
            $speed = 30;
            // Proficiency: Intimidation
            $skillProf[] = ["Intimidation"];
            // Languages: speak, read, write Common and Ore.
            $languages .= "Ore";

            // Relentless Endurance -- Ability
                // When you are reduced to 0 hit points but not killed outright, you can go to 1 hit point instead. This can occur once every long rest.
            // Savage Attacks -- Ability
                // When you score a critical hit with a melee weapon attack, you can roll one of the weapon's damage dice one additional time and add it to the extra damage of the ctitical hit (x2 damage + one more die) 
            // Dark Vision -- Ability
                // See in dim light within 60 feet of you as if bright
                // See in darkness within 60 feet of you as if dim
            $abilities[] = ["Darkvision", "Menacing", "Relentless Endurance", "Savage Attacks"];
            // $additionalProf = "Dark Vision: you can see in dim light 60 feet ahead of you as if it were bright, and darkness as if it were dim. Relentless Endurance: once every long rest, when you are reduced to 0 hit points but not killed outright, you can go to 1 hit point instead. Savage Attacks: when you score a critical hit with a melee weapon attack, you can roll one of the weapon's damage dice one additional time and add it to the extra damage of the critical hit (x2 damage + one more dice roll)."
        }









        else if($race == "Halfling" || $race == "LightfootHalfling" || $race == "StoutHalfling")
        {
            // Dexterity: +2
            $dexterity += 2;
            // Speed: 25 ft.
            $speed = 25;
            // Languages: speak, read, write common and halfling
            $languages .= "Halfling";

            // Lucky -- Ability
                // When you roll a 1 on an attack roll, ability check, or saving throw, you can reroll the die, and you must use the new result (even if you roll a 1)
            // Brave -- Ability
                // Advantage on saving throws against being frightened.
            // Nimble -- Ability
                // You can move through the space of any creature that is of a size larger than yours.
            $abilities[] = ["Lucky", "Brave", "Halfling Nimbleness"];
            // $additionalProf = "Lucky: when you roll a 1 on an attack roll, ability check, or saving throw, you can reroll the die, and you must use the new result. Brave: you have advantage on saving throws against being frightened. Nimble: you can move through the space of any creature that is of a size larger than yours."

            if($race == "LightfootHalfling")
            {
                // Charisma: +1
                $charisma += 1;
                // Naturally Stealthy -- Ability
                    // You can attempt to hide even when you are only obstured by a creature that is at least one size larger than you.
                $abilities[] = ["Naturally Stealthy"];
                // $additionalProf += " Naturally Stealthy: you can attempt to hide even when you are only obstructed by a creature that is at least one size larger than you."
            }

            if($race == "StoutHalfling")
            {
                // Constitution: +1
                $constitution += 1;
                // Stout Resilience -- Ability
                    // You have advantage on saving throws against poison
                    // Resistance to poison damage.
                $abilities[] = ["Stout Resilience"];
                // $additionalProf += " Stout Resilience: you have advantage on saving throws against poison, and you have resistance to poison damage."
            }
        }









        else if($race == "Human")
        {
            // All ability scores: +1
                // OR two ability scores +1
                // Proficiency in one skill of choice
                // One feat of choice
            $constitution += 1;
            $dexterity += 1;
            $wisdom += 1;
            $intelligence += 1;
            $charisma += 1;
            $strength += 1;
            // $additionalProf = "All ability scores increase by 1 OR you can increase two ability scores by 1, choose one skill proficiency, and choose one feat."
            // Base speed: 30 ft
            $speed = 30;
            // Languages: speak, read, write Common and some other language of choice.
            $abilities[] = ["Extra Language"];
        }









        else if($race == "Tiefling")
        {
            // Intelligence: +1
            // Charisma: +2
            $intelligence += 1;
            $charisma += 2;

            // Base speed: 30 ft
            $speed = 30;
            // Languages: speak, read, write Common and Infernal
            $languages .= "Infernal";

            // Resistance: Fire damage
            // Dark Vision -- Ability
                // See in dim light within 60 feet of you as if bright
                // See in darkness within 60 feet of you as if dim
            // Hellish Resistance -- Ability
                // Resistance to fire damage
            // Infernal Legacy -- Spell, Charisma is the spellcasting ability mod
                // Thaumaturgy cantrip
                // 3rd level: Hellish Rebuke once per day as a 2nd level spell.
                // 5th level: Darkness once per day 
            //////////// OR ////////////////
            //Devil's tongue -- Spell, Charisma is the spellcasting ability mod
                // Vicious Mockery cantrip
                // 3rd level: Charm person once per day as a 2nd level spell.
                // 5th level: Enthrall once per day.
            //////////// OR ////////////////
            // Hellfire --Infernal Legacy, but with Burning Hands instead of Hellish Rebuke
            //////////// OR ////////////////
            // Winged -- Bat-like wings sprouting from shoulders, fly speed of 30 ft.
            $abilities[] = ["Darkvision", "Hellish Resistance", "Infernal Legacy"];
            // $additionalProf = "Hellish Resistance: You have resistance to fire damage. Dark Vision: you can see in dim light 60 feet ahead of you as if it were bright, and darkness as if it were dim. Choose from the following abilities: Infernal Legacy (You know the Thaumaturgy cantrip. At 3rd level, you can use Hellish Rebuke once per long rest as a second level spell. At 5th level, you can cast Darkness once per long rest. Charisma is your spellcasting ability), Hellfire (You have Infernal legacy, but with Burning Hands rather than Hellish Rebuke), or Winged (There are bat-like wings sprouting from your shoulders, your flying speed is 30 ft)"
        }









        



////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////// Classes ///////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
        $strengthMod = floor(($strength - 10)/2);
        $constitutionMod = floor(($constitution - 10)/2);
        $dexterityMod = floor(($dexterity - 10)/2);
        $intelligenceMod = floor(($intelligence - 10)/2);
        $wisdomMod = floor(($wisdom - 10)/2);
        $charismaMod = floor(($charisma - 10)/2);


        if($class == "Barbarian")
        {
            // Hit Die: 1d12 per level
            $hitDie = 12;
            // Hit Points at 1st level: 12 + Con. Mod
            $HPAddition += 12 + $constitutionMod;
            // Hit Points at higher levels: 1d12 + Con mod per level above 1st
            // Proficiencies:
                // Light armor, medium armor, shields
                // Weapons: Simple weapons, martial weapons
                // Tools: None
                // Saving Throws: Strength, Constitution
                // Skills: Choose two: Animal handling, athletics, intimidation, nature, perception, survival
            $itemProf[] = ["Simple Weapon", "Martial Weapon", "Light Armor", "Medium Armor", "Shield"];
            // Equiptment
                // Choose: greataxe OR any martial melee weapon
                // Choose: two handaxes OR any simple weapon
                // An explorer's pack
                // Four javelins
            $inventory[] = ["Explorer's Pack", "Javelin"];
            $quantity[] = [1, 4];

            $savingThrows[] = ["Strength", "Constitution"];

            $skillProf[] = ["Athletics", "Survival"];

            $abilities[] = ["Rage"];
        }









        else if($class == "Bard")
        {
            // Hit Die: 1d8 per level
            $hitDie = 8;
            // Hit Points at 1st level: 8 + Con. Mod
            $HPAddition += 8 + $constitutionMod;
            // Hit Points at higher levels: 1d8 + Con mod per level above 1st
            // Proficiencies:
                // Light armor
                // Weapons: Simple weapons, hand crossbows, longswords, rapiers, shortswords
            $itemProf[] = ["Light Armor", "Simple Weapon", "Hand Crossbow", "Longsword", "Rapier", "Shortsword", "Lute", "Flute", "Viol"];

                // Tools: Three musical instruments of choice
// TODO: prompt user for musical instrument, they enter it, it goes into database.
                // Saving Throws: Dexterity, Charisma
            $savingThrows[] = ["Dexterity", "Charisma"];
            $skillProf[] = ["Sleight Of Hand", "Acrobatics", "Performance"];
                // Skills: Any three
            $inventory[] = ["Rapier", "Entertainer's Pack", "Lute", "Leather Armor", "Dagger"];
            $quantity[] = [1, 1, 1, 1, 1];
            // Equiptment
                // Choose: rapier, longsword, or any simple weapon
                // Choose: diplomat's pack or entertainer's pack
                // Choose: a lute or any other musical instrument
                // Leather armor and a dagger
                // Alternatively, buy gear using gold (5d4x10 gp; avg. 125 gp)
            // Spell Casting Ability: Charisma
            $spellCastingAbility = "Cha";
            // Spell save DC: 8 + proficiency bonus + Charisma modifier
            // Spell attack modifier: proficiency bonus + charisma modifier
            // Spellcasting focus: any musical instrument
            $abilities[] = ["Bardic Inspiration", "Spellcasting"];
        }









        else if($class == "Cleric")
        {
            // Hit Die: 1d8 per level
            $hitDie = 8;
            // Hit Points at 1st level: 8 + Con. Mod
            $HPAddition += 8 + $constitutionMod;
            // Hit Points at higher levels: 1d8 + Con mod per level above 1st
            // Proficiencies:
                // Light armor, medium armor, shields
                // Weapons: All simple weapons
                // Tools: None
            $itemProf[] = ["Light Armor", "Medium Armor", "Shield", "Simple Weapon"];
                // Saving Throws: Wisdom, Charisma
            $savingThrows[] = ["Wisdom", "Charisma"];
                // Skills: Choose two: History, insight, medicine, persuasion, religion
            $skillProf[] = ["Medicine", "Religion"];
            // Equiptment
                // Choose: mace or warhammar (if proficient)
                // Choose: scale mail, leather armor, or chain mail (if proficient in its use)
                // Choose: light crossbow and 20 bolts, or any simple weapon
                // Choose: A piest's pack or an explorer's pack
                // A shield and a holy symbol
            $inventory[] = ["Mace", "Leather Armor", "Quarterstaff", "Priest's Pack", "Shield"];
            $quantity[] = [1, 1, 1, 1, 1];
            // Spell Casting Ability: Wisdom
            $spellCastingAbility = "Wis";
            $abilities[] = ["Spellcasting"];
            // Spell save DC: 8 + proficiency bonus + Wisdom modifier
            // Spell attack modifier: proficiency bonus + Wisdom modifier
            // Spellcasting focus: a holy symbol   
        }









        else if($class == "Druid")
        {
            // Hit Die: 1d8 per level
            $hitDie = 8;
            // Hit Points at 1st level: 8 + Con. Mod
            $HPAddition += 8 + $constitutionMod;
            // Hit Points at higher levels: 1d8 + Con mod per level above 1st
            // Proficiencies:
                // Light armor, medium armor, shields (except those made of metal)
                // Weapons: Clubs, daggers, darts, javelins, maces, quarterstaves, scimitars, sickles, slings, spears
                // Tools: Herbalism kit
            $itemProf[] = ["Light Armor", "Medium Armor", "Shield", "Club", "Dagger", "Dart", "Javelin", "Mace", "Quarterstaff", "Scimitar", "Sickle", "Sling", "Spear", "Herbalism Kit"];
                // Saving Throws: Intelligence, Wisdom
            $savingThrows[] = ["Intelligence", "Wisdom"];
                // Skills: Choose two: Arcana, Animal Handling, Insight, Nature, Survival
            $skillProf[] = ["Nature", "Survival"];
            // Equiptment
                // Choose: a wooden shield, or any simple weapon
                // Choose: a scimitar, or any simple melee weapon
                // Leather armor, an explorer's pack, and a druidic focus
                // Alternatively, buy gear using gold (2d4X10 gp, avg. 50 gp)
            $inventory[] = ["Wooden Shield", "Scimitar", "Leather Armor", "Explorer's Pack"];
            $quantity[] = [1, 1, 1, 1];
            // Spell Casting Ability: Wisdom
            $spellCastingAbility = "Wis";
            // Spell save DC: 8 + proficiency bonus + Wisdom modifier
            // Spell attack modifier: proficiency bonus + Wisdom modifier
            // Spellcasting focus: Druidic focus: mistletoe, totem carvings, yew wands, or oaken staves.

            // Language: Druidic
            $languages  .= "Druidic";
            $abilities[] = ["Spellcasting"];
        }









        else if($class == "Fighter")
        {
            // Hit Die: 1d10 per level
            $hitDie = 10;
            // Hit Points at 1st level: 10 + Con. Mod
            $HPAddition += 10 + $constitutionMod;
            // Hit Points at higher levels: 1d10 + Con mod per level above 1st
            // Proficiencies:
                // All armor, shields
                // Weapons: Simple weapons, martial weapons
                // Tools: None
            $itemProf[] = ["Armor", "Shield", "Simple Weapon", "Martial Weapon"];
                // Saving Throws: Strength, Constitution
            $savingThrows[] = ["Strength", "Constitution"];
                // Skills: Choose two: Acrobatics, Animal Handling, Athletics, History, Insight, Intimidation, Perception, Survival
            $skillProf[] = ["Athletics", "Acrobatics"];
            // Equiptment
                // Choose: Chainmail, OR leather armor, longbow, and 20 arrows
                // Choose: martial weapon and shield, OR two martial weapons
                // Choose: A light crossbow and 20 bolts, OR two handaxes
                // Choose: A dungeoneer's pack, or an explorer's pack
                // Alternatively, buy gear using gold (5d4X10 gp, avg. 125 gp)
            $inventory[] = ["Chainmail", "Battleaxe", "Rapier", "Handaxe", "Explorer's Pack"];
            $quantity[] = [1, 1, 1, 2, 1];

            $abilities[] = ["Second Wind"];
        }









        else if($class == "Monk")
        {
            // Hit Die: 1d8 per level
            $hitDie = 8;
            // Hit Points at 1st level: 8 + Con. Mod
            $HPAddition += 8 + $constitutionMod;
            // Hit Points at higher levels: 1d8 + Con mod per level above 1st
            // Proficiencies:
                // Armor: None
                // Weapons: Simple weapons, shortswords
                // Tools: One type of artisan's tools, or one musical instrument
                // Saving Throws: Strength, Dexterity
                // Skills: Choose two: Acrobatics, Athletics, History, Insight, Religion, Stealth
            $itemProf[] = ["Simple Weapon", "Shortsword", "Tinker's Tools"];
            $savingThrows[] = ["Strength", "Dexterity"];
            // Equiptment
                // Choose: a shortsword or a simple weapon
                // Choose: a dungeoneer's pack or an explorer's pack
                // 10 darts
                // Alternatively, buy gear using gold (5d4 gp, avg. 12.5 gp)
            $inventory[] = ["Shortsword", "Explorer's Pack", "Dart"];
            $quantity[] = [1, 1, 10];
            $abilities[] = ["Unarmored Defense", "Martial Arts"];
            // Ki save DC = 8 + proficiency bonus + wisdom modifier
            // Number of Ki equal to level
            // Flurry of Blows -- Ability
                // Immediately after taking the Attack action on your turn, you may spend 1 ki point to make two unarmed strikes as a bonus action
            // Patient Defence -- Ability
                // You can spend 1 ki point to take the Dodge action as a bonus action on your turn.
            // Step of the Wind -- Ability
                // You can spend 1 ki point to take the Disengage or Dash action as a bonus action on your turn. When you do so, your jump distance is doubled for the turn
        }









        else if($class == "Paladin")
        {
            // Hit Die: 1d10 per level
            $hitDie = 10;
            // Hit Points at 1st level: 10 + Con. Mod
            $HPAddition += 10 + $constitutionMod;
            // Hit Points at higher levels: 1d10 + Con mod per level above 1st
            // Proficiencies:
                // Armor: All armor, shields
                // Weapons: Simple weapons, martial weapons
                // Tools: None
                // Saving Throws: Wisdom, Charisma
                // Skills: Choose two: Athletics, Insight, Intimidation, Medicine, Persuasion, Religion
            $itemProf[] = ["Armor", "Shield", "Simple Weapon", "Martial Weapon"];
            $savingThrows[] = ["Wisdom", "Charisma"];
            $skillProf[] = ["Religion", "Athletics"];
            // Equiptment
                // Choose: a martial weapon and a shield, or two martial weapons
                // Choose: five javelins, or any simple melee weapon
                // Choose: a priest's pack, or an explorer's pack
                // Chain mail and a holy symbol
            $inventory[] = ["Lance", "Longsword", "Javelin", "Explorer's Pack", "Chainmail"];
            $quantity[] = [1, 1, 5, 1, 1];
            // Spellcasting Ability: Charisma
            $spellCastingAbility = "Cha";
                // Spell save DC = 8 + proficiency + Charisma modifier
                // Spell attack modifier = proficiency + Charisma modifier.
                // Spellcasting focus: holy symbol
            $abilities[] = ["Divine Sense", "Lay on Hands"];
        }









        else if($class == "Ranger")
        {
            // Hit Die: 1d10 per level
            $hitDie = 10;
            // Hit Points at 1st level: 10 + Con. Mod
            $HPAddition += 10 + $constitutionMod;
            // Hit Points at higher levels: 1d10 (or 6) + Con mod per level above 1st
            // Proficiencies:
                // Armor: Light armor, medium armor, shields
                // Weapons: Simple weapons, martial weapons
                // Tools: None
            $itemProf[] = ["Light Armor", "Medium Armor", "Shield", "Simple Weapon", "Martial Weapon"];
                // Saving Throws: Strength, Dexterity
            $savingThrows[] = ["Strength", "Dexterity"];
                // Skills: Choose three: Animal Handling, Athletics, Insight, Investigation, Nature, Perception, Stealth, Survival
            $skillProf[] = ["Athletics", "Insight", "Stealth"];
            // Equiptment
                // Choose: scalemail or leather armor
                // Choose: two shortswords or two simple melee weapons
                // Choose: a dungeoneer's pack, or an explorer's pack
                // A longbow and a quiver of 20 arrows.
            $inventory[] = ["Scale Mail", "Shortsword", "Explorer's Pack", "Longbow", "Arrows"];
            $quantity[] = [1, 2, 1, 1, 20];
            // Spellcasting Ability: Wisdom
            $spellCastingAbility = "Wis";
                // Spell save DC = 8 + proficiency + Wisdom modifier
                // Spell attack modifier = proficiency + Wisdom modifier
            $abilities[] = ["Favored Enemy", "Natural Explorer"];
        }









        else if($class == "Rogue")
        {
            // Hit Die: 1d8 per level
            $hitDie = 8;
            // Hit Points at 1st level: 8 + Con. Mod
            $HPAddition += 8 + $constitutionMod;
            // Hit Points at higher levels: 1d8 + Con mod per level above 1st
            // Proficiencies:
                // Armor: Light armor
                // Weapons: Simple weapons, hand crossbows, longswords, rapiers, shortswords.
            $itemProf[] = ["Light Armor", "Simple Weapon", "Hand Crossbow", "Longsword", "Rapier", "Shortsword", "Thieves' Tools"];
            $savingThrows[] = ["Dexterity", "Intelligence"];
                // Tools: Theives' tools
                // Saving Throws: Dexterity, Intelligence
                // Skills: Choose four: Actobatics, Athletics, Deception, Insight, Intimidation, Investigation, Perception, Performance, Persuasion, Sleight of Hand, Stealth
            $skillProf[] = ["Acrobatics", "Deception", "Insight", "Stealth"];
            // Equiptment
                // Choose: rapier or shortsword
                // Choose: shortbow and a quiver of 20 arrows or a shortsword
                // Choose: a burglur's pack, a dungeoneer's pack, or an explorer's pack
                // Leather armor, two daggers, and thieves' tools
            $inventory[] = ["Rapier", "Shortsword", "Burgler's Pack", "Leather Armor", "Dagger", "Theives' Tools"];
            $quantity[] = [1, 1, 1, 1, 2, 1];

            $abilities[] = ["Expertise", "Sneak Attack", "Thieves' Cant"];
        }









        else if($class == "Sorcerer")
        {
            // Hit Die: 1d6 per level
            $hitDie = 6;
            // Hit Points at 1st level: 6 + Con. Mod
            $HPAddition += 6 + $constitutionMod;
            // Hit Points at higher levels: 1d6 + Con mod per level above 1st
            // Proficiencies:
                // Armor: None
                // Weapons: Daggers, darts, slings, quarterstaves, light crossbows
                // Tools: None
            $itemProf[] = ["Dagger", "Dart", "Sling", "Quarterstaff", "Light Crossbow"];
                // Saving Throws: Constitution, Charisma
            $savingThrows[] = ["Constitution", "Charisma"];
                // Skills: Choose two: Arcana, Deception, Insight, Intimidation, Persuasion, Religion
            $skillProf[] = ["Arcana", "Insight"];
            // Equiptment
                // Choose: a light crossbow and 20 bolts, or any simple weapon
                // Choose: a component pouch, or an arcane focus
                // Choose: a dungeoneer's pack, or an explorer's pack
                // Two daggers
            $inventory[] = ["Light Crossbow", "Bolts", "Component Pouch", "Explorer's Pack", "Dagger"];
            $quantity[] = [1, 20, 1, 1, 2];
            // Spellcasting Ability: Charisma
            $spellCastingAbility = "Cha";
                // Spell save DC = 8 + proficiency + Charisma modifier
                // Spell attack modifier = proficiency + Charisma modifier
            // Spellcasting focus: you can use an arcane focus
            $abilities[] = ["Spellcasting"];
        }









        else if($class == "Warlock")
        {
            // Hit Die: 1d8 per level
            $hitDie = 8;
            // Hit Points at 1st level: 8 + Con. Mod
            $HPAddition += 8 + $constitutionMod;
            // Hit Points at higher levels: 1d8 (or 5) + Con mod per level above 1st
            // Proficiencies:
                // Armor: Light armor
                // Weapons: Simple weapons
                // Tools: None
            $itemProf[] = ["Light Armor", "Simple Weapon"];
                // Saving Throws: Wisdom, Charisma
            $savingThrows[] = ["Wisdom", "Charisma"];
                // Skills: Choose two: Arcana, Deception, History, Intimidation, Investigation, Nature, and Religion
            $skillProf[] = ["Arcana", "Investigation"];
            // Equiptment
                // Choose: a light crossbow and 20 bolts, or any simple weapon
                // Choose: a component pouch, or an arcane focus
                // Choose: a dungeoneer's pack, or a scholar's pack
                // Leather armor, any simple weapon, and two daggers
                // Plus whatever is granted by your background
            $inventory[] = ["Light Crossbow", "Bolts", "Component Pouch", "Scholar's Pack", "Leather Armor", "Dart", "Dagger"];
            $quantity[] = [1, 20, 1, 1, 1, 1, 2];
            // Spellcasting Ability: Charisma
            $spellCastingAbility = "Cha";
                // Spell save DC = 8 + proficiency + Charisma modifier
                // Spell attack modifier = proficiency + Charisma modifier
            // Spellcasting focus: you can use an arcane focus 
            $abilities[] = ["Otherwordly Patron", "Spellcasting"];   
        }









        else if($class == "Wizard")
        {
            // Hit Die: 1d6 per level
            $hitDie = 6;
            // Hit Points at 1st level: 6 + Con. Mod
            $HPAddition += 6 + $constitutionMod;
            // Hit Points at higher levels: 1d6 (or 4) + Con mod per level above 1st
            // Proficiencies:
                // Armor: None
                // Weapons: Daggers, darts, slings, quarterstaffs, light crossbows
                // Tools: None
            $itemProf[] = ["Dagger", "Dart", "Sling", "Quarterstaff", "Light Crossbow"];
                // Saving Throws: Intelligence, Wisdom
            $savingThrows[] = ["Intelligence", "Wisdom"];
                // Skills: Choose two: Arcana, History, Insight, Investigation, Medicine, and Religion
            $skillProf[] = ["Arcana", "Insight"];
            // Equiptment
                // Choose: a quarterstaff, a dagger
                // Choose: a component pouch, or an arcane focus
                // Choose: a explorer's pack, or a scholar's pack
                // Spellbook
                // In addition to the equiptment granted by your background
            $inventory[] = ["Quarterstaff", "Component Pouch", "Scholar's Pack", "Spellbook"];
            // Spellcasting Ability: Intelligence
            $spellCastingAbility = "Int";
                // Spell save DC = 8 + proficiency + Intelligence modifier
                // Spell attack modifier = proficiency + Intelligence modifier
            $abilities[] = ["Spellcasting", "Arcane Recovery"];
        }
    }

    $armorClass = 10 + $dexterity;
    $inventorySize = count($inventory);
    $itemProfSize = count($itemProf);
    $abilitiesSize = count($abilities);
    $passivePerception = 10 + $wisdomMod;

    ////////////////////////////////////////
    //////////// Characters Table //////////
    ////////////////////////////////////////

    $sql = "INSERT INTO Characters (raceID, classID, userID, languages, passivePerception, proficiencyBonus, speed, spellCastingAbility, level, expPoints, armorClass, maxHP, hitDie, inspiration, strength, dexterity, intelligence, wisdom, constitution, charisma, inventorySize) VALUES ($race, $class, $user, $languages, $passivePerception, 2, $speed, $spellCastingAbility, 1, 0, $armorClass, HPAddition, $hitDie, 0, $strength, $dexterity, $intelligence, $wisdom, $constitution, $charisma, $inventorySize)";

    $conn->query($sql);

    // Front End Notes: Modifiers are calculated as such: floor((ability score - 10)/2)


    ////////////////////////////////////////
    ////////// Proficiencies Table/////////
    ////////////////////////////////////////

    for($i = 0; $i < $itemProfSize; i++)
    {
        $itemName = $itemProf[i];

        $sql = "INSERT INTO Proficiencies (characterID, skill) VALUES ($characterID, $itemName)";

        $conn->query($sql);
    }


    ////////////////////////////////////////
    //////// CharactersItems Table /////////
    ////////////////////////////////////////

    for($i = 0; $i < $inventorySize; i++)
    {
        $thing = $inventory[i];

        $getID = "SELECT itemID FROM Items WHERE name = $thing";

        $ID = $conn->query($getID);

        $getProficiency = "SELECT proficiency FROM Proficiencies WHERE characterID = $characterID AND skill = $thing";

        $Proficiency = $conn->query($getProficiency);

        $uses = ("SELECT usesAvailable FROM Items WHERE itemID = $ID")*$quantity[i];

        $usesLeft = $conn->query($uses);

        $sql = "INSERT INTO CharactersItems (characterID, itemID, proficiency, usesRemaining) VALUES ($characterID, $ID, $Proficiency, $usesLeft)";

        $conn->query($sql);
    }

    ////////////////////////////////////////
    /////// CharactersAbilities Table //////
    ////////////////////////////////////////

    for($i = 0; $i < $abilitiesSize; $i++)
    {
        $ability = $abilities[i];

        $getID = "SELECT abilitiesID FROM Abilities WHERE name = $ability";
        $ID = $conn->query($getID);

        $sql = "INSERT INTO CharactersAbilities (characterID, abilitiesID) VALUES ($characterID, $ID)";

        $conn->query($sql);
    }
?>
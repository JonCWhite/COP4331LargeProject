<?php
    // Initilize Character

    // Get file contents
    $inData = json_decode(file_get_contents('php://input'), true);
    

    // Take data from front-end
    $race = $inData["race"];
    $class = $inData["class"];
    $characterID = $inData["characterID"];

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
        if($race == "DragonBorn")
        {
            // Stength +2
            // Charisma +1
            // Speed base: 30 ft
            // Damage resistance to the damage type associated with your ancestry
            // Languages: read, speak, write Common and Draconic

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
            // 2d6 damage on a filed save, 1d6 on a successful one.
            // Lvl 6: 3d6, Lvl 11: 4d6, Lvl 16: 5d6
            // Must complete a short or long rest before next use.
        }

        else if($race == "Dwarf" || $race == "HillDwarf" || $race == "MountainDwarf")
        {
            // Constitution: +2
            // Speed base: 25 ft
            // Damage resistance to poiton
            // Proficiency: battleaxe, handaxe, throwing hammar, AND warhammar
            // Proficiency in smith's tools, brewer's supplies, OR mason's tools
            // Languages: speak, read, write Common and Dwarvish.

            // Dark Vision -- Ability
                // See in dim light within 60 feet of you as if bright
                // See in darkness within 60 feet of you as if dim
            // Dwarven Resilience -- Ability
                // Advantage on saving throws against poison
            // Stonecunning --  Ability
                // Intelligence (History) check related to the origin of stonework, you are considered proficient in the history skill, and add double proficiency bonus to the check
            if($race == "HillDwarf")
            {
                // Wisdom: +1
                // HP Max: +1, and +1 each level
            }
            if($race == "MountainDwarf")
            {
                // Strength: +2
                // Proficiency: light and medium armor.
            }
        }

        else if($race == "Elf" || $race == "HighElf" || $race == "WoodElf" || $race == "Drow")
        {
            // Dexterity: +2
            // Base speed: 30 ft
            // Proficiency: Perception
            // Languages: speak, read, write Common and Elven

            // Fey Ancestry -- Ability
                // Advantage on Saving throws against being charmed
                // Magic cannot put you to sleep
            // Trance -- Ability
                // Semi-conscious for 4 hours a day, same benefit as 8 hrs. sleep.  
            // Dark Vision -- Ability
                // See in dim light within 60 feet of you as if bright
                // See in darkness within 60 feet of you as if dim
            if($race == "HighElf")
            {
                // Intelligence: +1
                // Proficiency: Longsword, shortsword, shortbow, longbow
                // Cantrip: One cantrip from the wizard list, intelligence is the spell casting ability mod
                // Read, speak, write one additional language of choice.
            }
            if($race == "WoodElf")
            {
                // Wisdom: +1
                // Proficiency: Longsword, shortsword, shortbow, longbow
                // Base speed: 35 ft.
                // Read, speak, write one additional language of choice.
                // Mask of Wild -- Ability
                    // You can attempt to hide even when you are only lightly obscured by foliage, heavy rain, falling snow, mist, and other natural phenomena. 
            }
            if($race == "Drow")
            {
                // Charisma: +1
                // Proficiency: Longsword, shortsword, shortbow, longbow
                // Base speed: 35 ft.
                // Read, speak, write one additional language of choice.
                // Superior Darkvision -- Ability
                    // Darkvision radius 120 ft.
                // Sunlight Sensisitivy -- Ability
                    // Disadvantage on Attack rolls and Wisdom (Perception) checks that rely on sight when you, the target of the attack, or whatever you are trying to perceive is in direct sunlight
                // Drow Magic -- Spells, Charisma is the spell casting ability mod
                    // Dancing Lights cantrip
                    // 3rd level -- faerie fire once every long rest
                    // 5th level -- darkness once every long rest 
                // Proficiency with rapiers, shortswords, hand crossbows
            }
        }

        else if($race == "Gnome" || $race == "ForestGnome" || $race == "RockGnome" || $race == "DeepGnome")
        {
            // Intelligence: +2
            // Base speed: 25 ft
            // Languages: speak, read, write Common and Gnomish

            // Fey Ancestry -- Ability
                // Advantage on Saving throws against being charmed
                // Magic cannot put you to sleep
            // Gnome Cunning -- Ability
                // Advantage on Intelligence, Wisdom, and Charisma saves against magic  
            // Dark Vision -- Ability
                // See in dim light within 60 feet of you as if bright
                // See in darkness within 60 feet of you as if dim

            if($race == "ForestGnome")
            {
                // Dexterity: +1
                // Natural Illusionist -- Spell
                    // Minor Illusion cantrip -- intelligence is the spellcasting mod
                // Speak with Small Beasts -- Ability
                    // Through sound and gestures, you may communicate simple ideas with small or smaller beasts
            }
            if($race == "RockGnome")
            {
                // Constitution: +1
                // Proficiency: Artificer's tools
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
                // Languages: speak, read, write undercommon.
                // Superior Darkvision -- Ability
                    // Darkvision radius of 120 feet
                // Stone Camouflage -- Ability
                    // Advantage on Dexterity (Stealth) Checks to hide in rocky terrain.
            }
        }

        else if($race == "Half Elf")
        {
            // Charisma: +2
            // Two ability scores of choice: +1
            // Base speed: 30 ft
            // Proficiency: Two skills of choice
                // OR Elf Weapon Training, Cantrip, Fleet of Foot, Mask of Wild, or Drow Magic.
            // Languages: speak, read, write Common, Elven, and one language of choice

            // Fey Ancestry -- Ability
                // Advantage on Saving throws against being charmed
                // Magic cannot put you to sleep
            // Dark Vision -- Ability
                // See in dim light within 60 feet of you as if bright
                // See in darkness within 60 feet of you as if dim
        }

        else if($race == "Half Orc")
        {
            // Strength: +2
            // Constitution: +1
            // Base speed: 30 ft
            // Proficiency: Intimidation
            // Languages: speak, read, write Common and Ore.

            // Relentless Endurance -- Ability
                // When you are reduced to 0 hit points but not killed outright, you can go to 1 hit point instead. This can occur once every long rest.
            // Savage Attacks -- Ability
                // When you score a critical hit with a melee weapon attack, you can roll one of the weapon's damage dice one additional time and add it to the extra damage of the ctitical hit (x2 damage + one more die) 
            // Dark Vision -- Ability
                // See in dim light within 60 feet of you as if bright
                // See in darkness within 60 feet of you as if dim
        }

        else if($race == "Halfling" || $race == "LightfootHalfling" || $race == "StoutHalfling")
        {
            // Dexterity: +2
            // Speed: 25 ft.
            // Languages: speak, read, write common and halfling

            // Lucky -- Ability
                // When you roll a 1 on an attack roll, ability check, or saving throw, you can reroll the die, and you must use the new result (even if you roll a 1)
            // Brave -- Ability
                // Advantage on saving throws against being frightened.
            // Nimble -- Ability
                // You can move through the space of any creature that is of a size larger than yours.

            if($race == "LightfootHalfling")
            {
                // Charisma: +1
                // Naturally Stealthy -- Ability
                    // You can attempt to hide even when you are only obstured by a creature that is at least one size larget than you.
            }

            if($race == "StoutHalfling")
            {
                // Constitution: +1
                // Stout Resilience -- Ability
                    // You have advantage on saving throws against poison
                    // Resistance to poison damage.
            }
        }

        else if($race == "Human")
        {
            // All ability scores: +1
                // OR two ability scores +1
                // Proficiency in one skill of choice
                // One feat of choice
            // Base speed: 30 ft
            // Languages: speak, read, write Common and some other language of choice.
        }

        else if($race == "Tiefling")
        {
            // Intelligence: +1
            // Charisma or Dexterity: +2
            // Base speed: 30 ft
            // Languages: speak, read, write Common and Infernal
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
        }




        if($class == "Barbarian")
        {
            // Hit Die: 1d12 per level
            // Hit Points at 1st level: 12 + Con. Mod
            // Hit Points at higher levels: 1d12 + Con mod per level above 1st
            // Proficiencies:
                // Light armor, medium armor, shields
                // Weapons: Simple weapons, martial weapons
                // Tools: None
                // Saving Throws: Strength, Constitution
                // Skills: Choose two: Animal handling, athletics, intimidation, nature, perception, survival
            // Equiptment
                // Choose: greataxe OR any martial melee weapon
                // Choose: two handaxes OR any simple weapon
                // An explorer's pack
                // Four javelins
        }

        else if($class == "Bard")
        {
            // Hit Die: 1d8 per level
            // Hit Points at 1st level: 8 + Con. Mod
            // Hit Points at higher levels: 1d8 + Con mod per level above 1st
            // Proficiencies:
                // Light armor
                // Weapons: Simple weapons, hand crossbows, longswords, rapiers, shortswords
                // Tools: Three musical instruments of choice
                // Saving Throws: Dexterity, Charisma
                // Skills: Any three
            // Equiptment
                // Choose: rapier, longsword, or any simple weapon
                // Choose: diplomat's pack or entertainer's pack
                // Choose: a lute or any other musical instrument
                // Leather armor and a dagger
                // Alternatively, buy gear using gold (5d4x10 gp; avg. 125 gp)
            // Spell Casting Ability: Charisma
            // Spell save DC: 8 + proficiency bonus + Charisma modifier
            // Spell attack modifier: proficiency bonus + charisma modifier
            // Spellcasting focus: any musical instrument
        }

        else if($class == "Cleric")
        {
            // Hit Die: 1d8 per level
            // Hit Points at 1st level: 8 + Con. Mod
            // Hit Points at higher levels: 1d8 + Con mod per level above 1st
            // Proficiencies:
                // Light armor, medium armor, shields
                // Weapons: All simple weapons
                // Tools: None
                // Saving Throws: Wisdom, Charisma
                // Skills: Choose two: History, insight, medicine, persuasion, religion
            // Equiptment
                // Choose: mace or warhammar (if proficient)
                // Choose: scale mail, leather armor, or chain mail (if proficient in its use)
                // Choose: light crossbow and 20 bolts, or any simple weapon
                // Choose: A piest's pack or an explorer's pack
                // A shield and a holy symbol
            // Spell Casting Ability: Wisdom
            // Spell save DC: 8 + proficiency bonus + Wisdom modifier
            // Spell attack modifier: proficiency bonus + Wisdom modifier
            // Spellcasting focus: a holy symbol   
        }

        else if($class == "Druid")
        {
            // Hit Die: 1d8 per level
            // Hit Points at 1st level: 8 + Con. Mod
            // Hit Points at higher levels: 1d8 + Con mod per level above 1st
            // Proficiencies:
                // Light armor, medium armor, shields (except those made of metal)
                // Weapons: Clubs, daggers, darts, javelins, maces, quarterstaves, scimitars, sickles, slings, spears
                // Tools: Herbalism kit
                // Saving Throws: Intelligence, Wisdom
                // Skills: Choose two: Arcana, Animal Handling, Insight, Medicine, Nature, Perception, Religion, Survival
            // Equiptment
                // Choose: a wooden shield, or any simple weapon
                // Choose: a scimitar, or any simple melee weapon
                // Leather armor, an explorer's pack, and a druidic focus
                // Alternatively, buy gear using gold (2d4X10 gp, avg. 50 gp)
            // Spell Casting Ability: Wisdom
            // Spell save DC: 8 + proficiency bonus + Wisdom modifier
            // Spell attack modifier: proficiency bonus + Wisdom modifier
            // Spellcasting focus: Druidic focus: mistletoe, totem carvings, yew wands, or oaken staves.

            // Language: Druidic
        }

        else if($class == "Fighter")
        {
            // Hit Die: 1d10 per level
            // Hit Points at 1st level: 10 + Con. Mod
            // Hit Points at higher levels: 1d10 + Con mod per level above 1st
            // Proficiencies:
                // All armor, shields
                // Weapons: Simple weapons, martial weapons
                // Tools: None
                // Saving Throws: Strength, Constitution
                // Skills: Choose two: Acrobatics, Animal Handling, Athletics, History, Insight, Intimidation, Perception, Survival
            // Equiptment
                // Choose: Chainmail, OR leather armor, longbow, and 20 arrows
                // Choose: martial weapon and shield, OR two martial weapons
                // Choose: A light crossbow and 20 bolts, OR two handaxes
                // Choose: A dungeoneer's pack, or an explorer's pack
                // Alternatively, buy gear using gold (5d4X10 gp, avg. 125 gp)
        }
        else if($class == "Monk")
        {
            // Hit Die: 1d8 per level
            // Hit Points at 1st level: 8 + Con. Mod
            // Hit Points at higher levels: 1d8 + Con mod per level above 1st
            // Proficiencies:
                // Armor: None
                // Weapons: Simple weapons, shortswords
                // Tools: One type of artisan's tools, or one musical instrument
                // Saving Throws: Strength, Dexterity
                // Skills: Choose two: Acrobatics, Athletics, History, Insight, Religion, Stealth
            // Equiptment
                // Choose: a shortsword or a simple weapon
                // Choose: a dungeoneer's pack or an explorer's pack
                // 10 darts
                // Alternatively, buy gear using gold (5d4 gp, avg. 12.5 gp)

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
            // Hit Points at 1st level: 10 + Con. Mod
            // Hit Points at higher levels: 1d10 + Con mod per level above 1st
            // Proficiencies:
                // Armor: All armor, shields
                // Weapons: Simple weapons, martial weapons
                // Tools: None
                // Saving Throws: Wisdom, Charisma
                // Skills: Choose two: Athletics, Insight, Intimidation, Medicine, Persuasion, Religion
            // Equiptment
                // Choose: a martial weapon and a shield, or two martial weapons
                // Choose: five javelins, or any simple melee weapon
                // Choose: a priest's pack, or an explorer's pack
                // Chain mail and a holy symbol
            // Spellcasting Ability: Charisma
                // Spell save DC = 8 + proficiency + Charisma modifier
                // Spell attack modifier = proficiency + Charisma modifier.
                // Spellcasting focus: holy symbol
        }

        else if($class == "Ranger")
        {
            // Hit Die: 1d10 per level
            // Hit Points at 1st level: 10 + Con. Mod
            // Hit Points at higher levels: 1d10 (or 6) + Con mod per level above 1st
            // Proficiencies:
                // Armor: Light armor, medium armor, shields
                // Weapons: Simple weapons, martial weapons
                // Tools: None
                // Saving Throws: Strength, Dexterity
                // Skills: Choose three: Animal Handling, Athletics, Insight, Investigation, Nature, Perception, Stealth, Survival
            // Equiptment
                // Choose: scalemail or leather armor
                // Choose: two shortswords or two simple melee weapons
                // Choose: a dungeoneer's pack, or an explorer's pack
                // A longbow and a quiver of 20 arrows. 
            // Spellcasting Ability: Wisdom
                // Spell save DC = 8 + proficiency + Wisdom modifier
                // Spell attack modifier = proficiency + Wisdom modifier
        }

        else if($class == "Rogue")
        {
            // Hit Die: 1d8 per level
            // Hit Points at 1st level: 8 + Con. Mod
            // Hit Points at higher levels: 1d8 + Con mod per level above 1st
            // Proficiencies:
                // Armor: Light armor
                // Weapons: Simple weapons, hand crossbows, longswords, rapiers, shortswords.
                // Tools: Theives' tools
                // Saving Throws: Dexterity, Intelligence
                // Skills: Choose four: Actobatics, Athletics, Deception, Insight, Intimidation, Investigation, Perception, Performance, Persuasion, Sleight of Hand, Stealth
            // Equiptment
                // Choose: rapier or shortsword
                // Choose: shortbow and a quiver of 20 arrows or a shortsword
                // Choose: a burglur's pack, a dungeoneer's pack, or an explorer's pack
                // Leather armor, two daggers, and thieves' tools
        }

        else if($class == "Sorcerer")
        {
            // Hit Die: 1d6 per level
            // Hit Points at 1st level: 6 + Con. Mod
            // Hit Points at higher levels: 1d6 + Con mod per level above 1st
            // Proficiencies:
                // Armor: None
                // Weapons: Daggers, darts, slings, quarterstaves, light crossbows
                // Tools: None
                // Saving Throws: Constitution, Charisma
                // Skills: Choose two: Arcana, Deception, Insight, Intimidation, Persuasion, Religion
            // Equiptment
                // Choose: a light crossbow and 20 bolts, or any simple weapon
                // Choose: a component pouch, or an arcane focus
                // Choose: a dungeoneer's pack, or an explorer's pack
                // Two daggers
            // Spellcasting Ability: Charisma
                // Spell save DC = 8 + proficiency + Charisma modifier
                // Spell attack modifier = proficiency + Charisma modifier
            // Spellcasting focus: you can use an arcane focus   
        }

        else if($class == "Warlock")
        {
            // Hit Die: 1d8 per level
            // Hit Points at 1st level: 8 + Con. Mod
            // Hit Points at higher levels: 1d8 (or 5) + Con mod per level above 1st
            // Proficiencies:
                // Armor: Light armor
                // Weapons: Simple weapons
                // Tools: None
                // Saving Throws: Wisdom, Charisma
                // Skills: Choose two: Arcana, Deception, History, Intimidation, Investigation, Nature, and Religion
            // Equiptment
                // Choose: a light crossbow and 20 bolts, or any simple weapon
                // Choose: a component pouch, or an arcane focus
                // Choose: a dungeoneer's pack, or a scholar's pack
                // Leather armor, any simple weapon, and two daggers
                // Plus whatever is granted by your background
            // Spellcasting Ability: Charisma
                // Spell save DC = 8 + proficiency + Charisma modifier
                // Spell attack modifier = proficiency + Charisma modifier
            // Spellcasting focus: you can use an arcane focus    
        }

        else if($class == "Wizard")
        {
            // Hit Die: 1d6 per level
            // Hit Points at 1st level: 6 + Con. Mod
            // Hit Points at higher levels: 1d6 (or 4) + Con mod per level above 1st
            // Proficiencies:
                // Armor: None
                // Weapons: Daggers, darts, slings, quarterstaffs, light crossbows
                // Tools: None
                // Saving Throws: Intelligence, Wisdom
                // Skills: Choose two: Arcana, History, Insight, Investigation, Medicine, and Religion
            // Equiptment
                // Choose: a quarterstaff, a dagger
                // Choose: a component pouch, or an arcane focus
                // Choose: a explorer's pack, or a scholar's pack
                // Spellbook
                // In addition to the equiptment granted by your background
            // Spellcasting Ability: Intelligence
                // Spell save DC = 8 + proficiency + Intelligence modifier
                // Spell attack modifier = proficiency + Intelligence modifier
        }
    }
?>
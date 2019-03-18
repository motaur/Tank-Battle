
"Tank Battle" 1.0 (16.06.2018 release)

Lev Berlinkov 					
e-mail: berlinkovlev@gmail.com	
	
Package: 
	default: package with all classes
	res: package with resources files
	
Classes:

	General classes:
		Main - main class the creates frame and run main menu.
		Title - main menu class, from here you can start readme info and start new game.
		Readme - info about the game.
		Game - main loop of game, that print graphics and change levels etc.
		Level - level class with properties of current level.
		ImageManager - stores all images and levels structures.
		SoundEffect - Enum with sound effects.
		Midi - midi player for game music.
		
	
	Items classes:
		FieldItem - an abstract class of game object that contents graphics, printable on screen.
		Tank - Tank object, able to move left, right, up and down, to shoot, to be banged. Can be controlled by player or computer.
		Bullet - Bullet object. Fly in current direction, and bang when intersect with another object.
		Wall - wall object. Stay on the place, have armor and bangs when armor is finished.	
		Tile - single grass image square. Order of squares is randomly generated.
		Bang - bang object, appears when another object is exploded.
		
	Controllers:
		KeyController - allow to user control the tank.
		BotController - The computer control enemy tanks.
		
Requirements
	Windows XP or later, Linux
	Java SE Runtime Environment 8 or later
	Screen resolution 800 x 600 pixels or more
	
Running the program:
		•Run the program by from "run.bat" (only for Windows) or compile the program from command line by typing: “javac Main.java” in "bin" folder.
	
All pictures, sounds etc. used in educational proposes.
	
	
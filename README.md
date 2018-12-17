voogasalad by Team YEET
===
### names of all people who worked on the project:
* Austin Kao (ak457)
* Benjamin Xu (bpx)
* José Luis San Martin (js665)
* Joshua France (jrf36)
* Orgil Batzaya (ob29)
* Rahul Ramesh (rr202)
* Scott McConnell (skm44)
* Xi Pu (xp19)

### date you started, date you finished, and an estimate of the number of hours worked on the project
* date started: October 31, 2018
* date finished: December 9, 2018
* hours worked: a lot (if you combine everyone, maybe 500+)
### each person's role in developing the project
* Austin Kao (ak457)
    * Data
        * Created xml parser and saver for other classes to use
        * Created data system that handles changes to the game resources
    * Editor
        * Implemented load and save functionality into editors
        * Assisted others on the editor team with generating GUI elements and implementing functionality
        * Improved functionality of map home and character home classes
    * Renderer
        * Added makeDirectoryFileList() and createErrorAlert() methods
* Benjamin Xu (bpx)
    * Console
        * Display events passing through event bus in user friendly format
        * Allow user to input console commands to generate system events
    * Game player
        * User interface for playing games created in game authoring environment
    * Render system
        * Factory for generating aesthetically appealing and consistently styled UI elements for use by front-end programmers on the team
    * Replay system
        * Captures events going through event bus and serializes the sequence for replay later
    * Event bus setup
        * Just did some research about Google Guava library and its event bus implementation, set it up in the project and helped others understand how it works
* José Luis San Martin (js665)
    * InputSystem
        * Accepting user key inputs and parsing them into Events that the program understands
    * AudioSystem
        * A utility system for playing music or sound effects whenever it recieves the appropriate event
* Joshua France (jrf36)
* Orgil Batzaya (ob29)
    * Editor system
        * Laid out infrastructure for class hierarchy
        * Created general editor super class
        * created map editor (with rahul)
        * created input handler
        * created game settings maker
    * Render system
        * created Scrollable item interface 
        * Created ScrollablePaneNew, a dynamic, interactive ScrollPane used by almost all Editors to display previous creations (maps, characters)
* Rahul Ramesh (rr202)
    * Editor system
        * Map Editor
            * created map editor from ground up, with orgil
        * Character Editor
            * created editor on own. sounds like not much but was a lot, I promise!
            * helped physics w/ hitboxes
    * Render 
        * Created ability to draw stage, and add to it with tiles
* Scott McConnell (skm44)
* Xi Pu (xp19)

### any books, papers, online, or human resources that you used in developing the project
* https://www.gamasutra.com/blogs/MichaelKissner/20151027/257369/Writing_a_Game_Engine_from_Scratch__Part_1_Messaging.php
* https://netopyr.com/2012/03/09/creating-a-sprite-animation-with-javafx/
* https://indiewatch.net/2017/12/02/wanna-make-fighting-game-part-iv-hitboxes-hurtboxes/
* https://indiewatch.net/2017/10/09/wanna-make-fighting-game-part-iii-character-state-machine/
* http://gameprogrammingpatterns.com/game-loop.html
### files used to start the project (the class(es) containing main)
* Main.java
### files used to test the project and errors you expect your program to handle without crashing
* editor
    * error checking to ensure files are either validly saved/loaded or not saved/loaded at all.
### any data or resource files required by the project (including format of non-standard files)
* Game directory
    * Characters directory
        * [Character Name] directory
            * Attacks directory
                * attackproperties.xml
            * Sounds directory
                * soundproperties.xml
            * Sprites directory
                * spriteproperties.xml
                * spritesheet.png
            * characterproperties.xml
            * [Character Name].png
    * Data directory
        * Background directory
        * BGM directory
        * Splash directory
        * Tiles directory
    * Modes directory
    * Replays directory
    * Stages directory
        * [Stage Name] directory
            * [Stage Name].png
            * stageproperties.xml
    * combosetup.xml
    * gameproperties.xml
    * inputsetup.xml
    * tags.properties
### any information about using the program (i.e., command-line/applet arguments, key inputs, interesting example data files, or easter eggs)
* Console commands:
    * Kill a player: ``` kill [id] [lives remaining] ```
    * Set player HP: ``` sethp [id] [hp remaining] ```
    * End the game: ``` gameover [winner id] ([player ranks]) ```
### any decisions, assumptions, or simplifications you made to handle vague, ambiguous, or conflicting requirements
* We decided that the player should not be able to save wherever the player wants
* We also decided to have a set directory structure for game resources
    * Thus, we copied over files that the user input into the game directory for consumption in the game.
### any known bugs, crashes, or problems with the project's functionality
* Editor
    * Works pretty well. Lots of error checking. Custom music isn't fully functional.
### any extra features included in the project
* Replay System
* AI System
### your impressions of the assignment to help improve it in the future
* It was an awesome assignment. We all had a ton of fun doing what we love. We would say that the only thing we want is more flexible specifications. We found it not necessarily cumbersome but uneducational implementing ceratin specifications, such as many of the specifications around the game player.
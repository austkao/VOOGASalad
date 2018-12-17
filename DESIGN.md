VOOGASalad DESIGN
====

## High-level Design Goals

Our high level design goals are:
* Create a game engine specifically tailored for fighting games
* The different elements of our program should be modular, utilizing the EventBus for a majority of communication
* Flexible game authoring environment
* Realistic physics engine
* Appealing game playing environment
* Powerful data reading/writing utility
* Highly customizable gameplay experience

## How to Add New Features

* To add a new type of object that is different than a player, ground, or hurtbox:
    * Create a new concrete sub-class of PhysicsObject
    * Needs to be assigned an id, mass, initial position, and height/width
    * Add a method to PhysicsObject that returns true for this new object only. 
    * To control how this new object behaves during a key input, call the above method and apply the desired physics force to all objects of that type
        * If you want only a specific object to be affected, you can call objects by id
    * To change how this object behaves when colliding with another type of object, add new logic to CollisionHandler.update()
* To add a new type of attack that is different from melee and projectile
    * Create a new concrete sub-class of PhysicsAttack
    * Needs to be assigned an id, parentID, mass, initial position, and height/width
    * To change how this affects other objects when colliding, add new logic to CollisionHandler.update()
    * To change the way it moves, add new forces in the corresponding areas, whether it be in CombatSystem for key input triggers or passiveForceHandler for a passive movement.
* To add a new menu in the editor for editing different types of values
    * Simply extend the parent class editor super
    * Create functionality for loading, saving, and creating
    * Work with xml parser to save whatever changes you made
    * Then make changes to game player/combat system as necessary to ensure your changes are actually heard in the game
* You could create a game like "League of Legends" (a game where the view is top down as opposed to from the side) as well using the PhysicsEngine. By tweaking constants in the physics engine, some of which can be defined in our editor, you can create a diverse set of physics environments to model almost any game that operates in a mostly continous timing. To make a game like Leauge of Legends, one would declare gravity to be 0, and would map all left right down and up keys to create forces on the character according to each direction. For most side-view fighting games, only left right and up add forces to the character. The last step would be to define coeffients of friction for the different blocks to be used. One of the interesting things about the physics engine is that similar in real life, different objects have different coefficients of friction. You could tune the coefficients for different parts of the terrain like forrest, brush, or mud to get the movement effects just right. 

## Major Design Choices

including trade-offs (i.e., pros and cons), made in your project

* Inter-System Communication: 
    * There were two main designs that we were choosing between: 
        * The first one was where we would, in the spirit of Slogo, use some kind of listener to pass updates from the back end to the front end. 
        * The other choice was to look into a novel communication method, the event bus, which none of us had used or heard of before.
    * Observer-Listener
        * Advantages
          * we had used it before
          * simple to implement
        * Disadvantages:
          * too simplistic (low specificity)
          * deprecated
      * Event Bus
        * Advantages
          * extremely flexible and high specificity for receiving events
          * simple to use once implemented
        * Disadvantages
          * not in base java, need to either implement it yourself or rely on external library

* Back-End to Front-End: 
    * There were also two main designs that we considered for this part. At this point we had already decided to use the event bus for inter-system communication, but we were still unsure about whether to use:
        * The event bus for passing key rendering information such as position of sprites through the event bus. 
        * The alternative would be to have the game loop have a consumer from the combat screen, which would accept values that the game loop got from the physics system.
    * Back-end to Front-end:
      * Event bus
        * Advantages
          * capturable and therefore recordable game rendering events during combat
          * maintains decoupling between front end and back end
        * Disadvantages
          * clutters event bus with many events
          * may impact performance at very high frame rates

## Assumptions and Decisions

* We decided that the player should not be able to save wherever the player wants
* We also decided to have a set directory structure for game resources
* While the physics can support any type of game, due to its very general collision system, the editor 
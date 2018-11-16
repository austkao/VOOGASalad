jrf36
Part 1
# What about your API/design is intended to be flexible?
Coordinates are designed to be easily extended to multiple dimensions. 
# How is your API/design encapsulating your implementation decisions?
Encapsulates all location updating logic. 
# How is your part linked to other parts of the project?
Governs how physics bodies are positioned throughout the game. 
# What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?
If another part of the program uses one more dimension than is supported by the rest of the game, the physics engine will automatically truncate that object to 2 dimensions. 
# Why do you think your API/design is good (also define what your measure of good is)?
It is shy, and tells the other guy. 
Part 2
# Discuss the use cases/issues that you are responsible for: are they descriptive, appropriate, reasonably sized?
Most use cases involve how game elements interact with eachother. They are reasonably sized but vital to the game 
# Estimate how long you think each will take and why. What, if anything, makes estimating these tasks uncertain?
Hard to say how long each will take. The biggest thing will be governing how collisions are handled. 
# What feature/design problem are you most excited to work on?
Collision handling
# What feature/design problem are you most worried about working on?
Collision handling
# What major feature do you plan to implement this weekend?
Basic physics without collisions
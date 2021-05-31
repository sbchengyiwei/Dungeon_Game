# Project 3 Prep

**For tessellating hexagons, one of the hardest parts is figuring out where to place each hexagon/how to easily place hexagons on screen in an algorithmic way.
After looking at your own implementation, consider the implementation provided near the end of the lab.
How did your implementation differ from the given one? What lessons can be learned from it?**

Answer:
I didn’t think of writing any helper method firstly, so the drawing method is really long and difficult for reading and thinking. But by writing very well-defined, nicely commented helper methods, it becomes more easily for me to find the mistakes. For example, when drawing the neighbor hexagon, write a find position method is very useful.
-----

**Can you think of an analogy between the process of tessellating hexagons and randomly generating a world using rooms and hallways?
What is the hexagon and what is the tesselation on the Project 3 side?**

Answer:
The process of generating all the hexagons is to find the position of one hexagon and then generate other hexagon s adjacent to it. Then this is similar to the principle of generating rooms. The rooms are also all over the world, and each room cannot overlap. Through the location of a room and random numbers we can also generate other rooms randomly. Then we can use hallways to connect them. 
 Pixels of the sides of hexagons can be seen as the sides of the rooms in project 3.
-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating hexagons.**

Answer:
Create a roomGenerator method, generate a random room, and return to the generated roomList. 
A room needs three variables to determine, the coordinates of the lower left corner, width, and height.  Set them all into the private final variables of the class. Since coordinates are involved, create a Position class to facilitate subsequent operations.
Then, there needs to be a way to print the room.  The four sides are the walls, the middle is the floor,  which is similar to the method of drawing hexagons.
And the room needs to be randomly generated, that is, three variables are randomly generated, position, width, and height, and a private method is compiled as a helper function to return the generated Room class.Finally, determine whether they overlap.
-----
**What distinguishes a hallway from a room? How are they similar?**

Answer:
Hallway plays the role of connecting rooms. Generally, there are two or more openings connected to other hallways or rooms, and each individual hallway(excluding transitions) is a rectangular floor with a width of 1.
 The shape of the room is obviously random, with or without openings.
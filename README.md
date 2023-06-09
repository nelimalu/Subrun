# Subrun
## Execution
When running the .jar file, make sure to run it in jGrasp.
## Description
Subrun is our **ICS4U0** ISP submission. This is an educative video game for ages 10-15 with the goal of spreading awareness of the difficulties of living in a suburb in an entertaining manner.
## Controls
**Left Click** to move through text boxes<br>
**W / A / S / D** or **↑ / ↓ / → / ←** keys for movement

## Focus
Subrun tackles the issue of **urban sprawl** in suburban neighborhoods. The way such neighborhoods, like Vaughan and Oshawa are constructed makes it so that everything is built kilometers apart, making it hard to get to where you need to get to without a car. This ends up contributing to the overall **traffic congestion** in these areas, since everyone is required to own a car to live, while also making travel difficult for youth who can't get a driver's license, or low-income households that can't afford a car.
## Progression
Subrun has 3 stages: **a learning level, a maze level, and an escape room level**. All levels can be replayed, however the next level can only be played of the previous level is completed (eg. Learning level would have to be completed before the user can access the Maze level.)
### Learning Level
The player is put through 11 frames of informative content with focus on the problems of urban sprawl while introducing game ideas revolving around transportation that the player will later be quizzed on in the maze level. Left click or use the enter key to progress to the next frame. Upon completion, the maze level will unlock itself.
### Maze Level
The player is put on a basic map with 3 simplified versions of the games the user will encounter in the escape room level. At each minigame, the user will first have to speak to the NPC at the game, go through dialogue, and answer a question before being able to play. When prompted with a question, the user will be given three multiple choice answers labeled 1, 2, and 3. To answer, simply press the 1, 2, or 3, keys on your keyboard.
- Walking game:
    - Located to the left of the starting location.
    - Use the WASD or arrow keys to dodge the cars.
    - Win by progressing forward far enough.
    - If you get hit by a car, you are sent back to the start.
    - This level is designed to replicate what it's like for a pedestrian to live in a car dependent suburb, where crosswalks are uncommon and highways split neighbourhoods apart.
- Biking game:
    - Location to the right of the starting location.
    - Use the A and D or left and right arrow keys to dodge oncoming cars.
    - Win by progressing forward far enough.
    - If you get hit by a car, you are sent back to the start.
    - This level is meant to replicate what it's like for a cyclist to bike in a car dependent suburb, where there are no bike lanes, they are banned from sidewalks, and they are forced to bike on large congested roads.
- Bus game:
    - Location is straight forward from the starting location. It's far away to replicate the long walks to the bus stop in suburban neighbourhoods.
    - First, identify your destination.
    - Watch the next stop and current stop to make sure you don't miss your stop.
    - When the current stop matches your destination, click anywhere on the screen to exit the bus.
    - If you miss your stop or click too early, you lose and are sent to the beginning
    - If you click at the right time, you win and are given a star.
After completing each minigame, the user will gain a star, trackable at the top left corner. Upon reaching 3 stars, the escape room level will be unlocked.
### Escape Room Level
The player is put into a final map with the 3 games to play. The player can talk to the NPCs on the map to enter a game. Each game has a minimum threshold requirement that must be met, whether it be score or completion. All 3 thresholds must be reached to complete the escape room, upon which the user will be met with a congratulation screen, with options to exit or return to the main menu. The thresholds for the 3 games are:
- Walking game: **5000 score**
- Biking game: **1200 score**
- Bus game: **completion**

## Possible Improvements

The core mechanics of the game are complete, but the player experience could be improved through some quality of life changes, which include:
- A flashing message that indicates once you've reached your required score on the walking and biking games in the escape room level
- Screens before entering the maze and escape room levels, explaining briefly what's required in each
- Making the locked level interface feel more accessible by showing what needs to be done to unlock each level
- Making it more clear where to find the bus game in the maze level
- Prompting the user to exit directly to the main screen rather than manually after completing all 3 stars in the maze level

## Work Divison
| File             | Luka | Brian |
|------------------|------|-------|
| Assets           | 80%  | 20%   |
| BikingGame.java  | 70%  | 30%   |
| BusGame.java     | 50%  | 50%   |
| CharSelect.java  | 20%  | 80%   |
| Controller.java  | 50%  | 50%   |
| EscapeRoom.java  | 70%  | 30%   |
| Exit.java        | 0%   | 100%  |
| Info.java        | 20%  | 80%   |
| Learning.java    | 30%  | 70%   |
| LevelSelect.java | 30%  | 70%   |
| Maze.java        | 70%  | 30%   |
| Menu.java        | 20%  | 80%   |
| MessageBox.java  | 100% | 0%    |
| Obstacle.java    | 90%  | 10%   |
| Player.java      | 80%  | 20%   |
| Splash.java      | 0%   | 100%  |
| Sprite.java      | 80%  | 20%   |
| Teacher.java     | 50%  | 50%   |
| WalkingGame.java | 60%  | 40%   |

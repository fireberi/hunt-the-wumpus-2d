# Hunt The Wumpus 2D

A game made for Software Engineering class, based on the 1973 Hunt the Wumpus game by Gregory Yob.


## Running the Game

Prerequisites:
- Make sure that Java version 21 is installed
- Make sure the JavaFX SDK version 21 binaries are installed
- Make sure Dominion ECS version 0.9.0 is installed

Next, download the code and `cd` into the folder. Then, compile:
```
javac -d ./bin/ -sourcepath ./src/ -classpath /path/to/this/binary/dominion-ecs-api-0.9.0.jar:/path/to/this/binary/dominion-ecs-engine-0.9.0.jar:./src/:./res/ --module-path /path/to/this/binary/javafx-sdk-21.0.3/lib --add-modules javafx.controls ./src/scripts/core/Main.java
```

Next, run the game!
```
java -classpath ./bin/:/path/to/this/binary/dominion-ecs-api-0.9.0.jar:/path/to/this/binary/dominion-ecs-engine-0.9.0.jar:./src/:./res/ --module-path /path/to/this/binary/javafx-sdk-21.0.3/lib --add-modules javafx.controls scripts.core.Main
```

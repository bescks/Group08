----------------------File Structure----------------------
App
├── manifests 				/* unchanged */
├── java
│   ├── it.polimi.test
│   │   ├── boards 			
│   │   │   ├── Boardbasic 		/* Define board class, operations that relates to board are included here  */
│   │   │   └── Cell 			/* Define cells */
│   │   ├── pieces
│   │   │   ├── Archer 			/* Define archer */
│   │   │   ├── Castle 			/* Define castle(a new kind of piece with special rules) */
│   │   │   ├── Dragon 			/* Define Dragon */
│   │   │   ├── Giant 			/* Define Giant  */
│   │   │   ├── Knight 			/* Define Knight */
│   │   │   ├── Mage 			/* Define Mage   */
│   │   │   ├── Squire 			/* Define Squire */
│   │   │   └── Piece 			/* Define parent class function for all of pieces */
│   │   ├── players 				
│   │   │   └── Player 			/* Define player, initialise its pieces, colour and so on */
│   │   ├── MainActivity		/* unchanged */
│   │   ├── Test 			/* Test file for authors. Input a valid 5-character string and the board will be print. */
│   │   └── TurnTest 			/* Test class for professor. */
│   ├── it.polimi.test(androidTest).  	/* unchanged */
│   └── it.polimi.test(Test)		
│       └── ExampleUnitTest		/* Test file with JUnit for professor. We have already set a test example in this file */
└── res 				/* unchanged */

The first release has three packages:

"pieces" is the package for different pieces: Archer, Dragon, Giant, Mage(Its spells are implemented), Knight, Squire and Castle(which is the proof of extensibility)
all the different pieces are extended from father class "Piece".

"boards" is the package for different boards, which is designed in an extensible way.
In this package, "Boardbasic" is the standard board with 6*6 cells and 4 special cells.
"Cell" class will be used in UI design, but not in the first release.

"players" is designed to record different pieces of one player. It is also in an extensible way.
For example, in the future, there may be more than two players, and they have different pieces.


In the standard game, we need two players, one board, and several pieces.
Then the class "test" provide an environment for a game player in strings print mode.
However the class "TurnTest" is just used to show the final result after input instructions, it is just designed for the first release .
# minimal-chess

This task was an interview question, and I decided to create this game as fast as I can, with a limit of 2 working days.

I learn a lot about chess game though, because before I don't know all the rules in this game well.

This was requirements in a simple form(things which I implement):
<br>
 - The board should start in the standard chess starting state with all the 16 pieces lined up for each player
 - Player white always start first(other moves are invalid, On an invalid move the existing player stays in control)
 - The moves must be read in using the supplied UserInputFile class until there are no more moves(jar from libs)
 - All moves must have a piece on the starting square and either an opponent piece or nothing on the destination square. Anything else is invalid.
 - The king can move only 1 square but in any direction
 - The bishop can move any number of squares but only diagonally(can't jump over)
 - The rook can move any number of squares but only horizontally or vertically(can't jump over)
 - The queen can move any number of squares horizontally, vertically or diagonally(can't jump over)
 - The knight can move in an L shape with sides of 2 and 1 squares respectively. That is 8 different possible moves. Unlike other pieces, it jumps over other pieces.
 - The pawn can move one or two squares forward on its first move (when not taking an opponent piece)
 - The pawn can move one square forward on subsequent moves (when not taking an opponent piece)
 - The pawn can move one square forward diagonally if taking an opponent piece
 - After each successful move renders the board in simple ASCII form. It is suggested that player 1 is represented by upper-case characters and player 2 by lower-case characters. The conventional characters to use here are: 
 Rook, Knight, Bishop, King, Queen, Pawn.
 - If the destination square contains an opponent piece then that piece is removed from the board. Unless - that piece is a King where rules around check apply (see later)
 - For pieces other than the knight disallow the move if there are any other pieces in the way between the start and end square.
 - If a move ends with a player’s king under attack that is “check”
 - A player cannot end their move in check
 - If a player starts their move in check this should be displayed as “in check”

I try to cover all these rules inside the tests and try to cover as much as possible.

It is not a fully working solution, but my target was to finish and do as much as possible in 2 days, I think I accomplished this task.

Inside the project there is a lot of places to do refactor, but as functional it is working and run out of time :)

I really enjoy doing this mini project!

## how to run
- go to libs folder
- run java -jar minimal-chess-fat-1.0-SNAPSHOT.jar
-  if you want custom input run :
<br>
java -jar minimal-chess-fat-1.0-SNAPSHOT.jar full/path/to/ful.txt

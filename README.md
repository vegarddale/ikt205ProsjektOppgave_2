# ikt205ProsjektOppgave_2
This project is an online Tic tac toe game using an echo server as backend.
There is a menu where you can create your own game where people can join by the generated game id or join other games.
In the gameplay screen you will se your games id, your player id and the other players id.
The player creating the game will always be player 1 and their marker will be represented by an X, 0 represents an empty slot and player 2's marker will be an O.
player 1 starts off the game by setting their marker, player 1 will not be able to set a marker before another player joins the game.
the second player will not be able to set a marker until player 1 has set their marker.
the player must press the commit button to confirm their marker and the marked square will be disabled.
when a player wins the game a pop up screen will show the winner and the player can exit to the menu by pressing the exit button.
if there are no squares left and there is no winner, a pop up screen will show that the game is drawn.


This app is implemented using fragments for the menu and gamescreen, and dialogs for showing the create and join game pop ups as well as the game over pop up.
the app is using android studios navigation component for navigating between the fragments using navigation arguments for sending the player number and initial game to the game screen.
the app has a gameservice responsible for the requests against the server.
the app has a gamemanager managing the gameservice and it is responsible for storing and updating the current game data as well as the game rules.
the gamemanagers data is wrapped in livedata which is then observed from the gamescreen to check for board updates and if the game is over.
The app is using Hilt for dependency injecting the apps services.
My unit tests are not working as i couldnt get an instance of the gamemanager in the test class.

Here are some photos of the app:
![menu](https://user-images.githubusercontent.com/69875804/118203313-13604800-b45c-11eb-940d-5db860f2306c.PNG)
![tictactoe](https://user-images.githubusercontent.com/69875804/118203319-19562900-b45c-11eb-97b6-329240b7c136.PNG)
![gameover](https://user-images.githubusercontent.com/69875804/118203331-1ce9b000-b45c-11eb-8325-5c2b73ee7e10.PNG)
![draw](https://user-images.githubusercontent.com/69875804/118203337-1fe4a080-b45c-11eb-84e9-69b0daa77c93.PNG)



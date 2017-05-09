Group08
---

__OS:__ Android

__Members:__
 - 863383 / 10513381 / Dongjie Geng
 - 853699 / 10501992 / Jingwei Wan
 - 854314 / 10501510 / Qizhengqiu Lu

1. Min SDK Version: ApI 21
2. Our app are solely developed and tested on Dongjie's android phone( ApI: 22, Resolution: 1080*1920, DPI: 3  ). So it would be work fine on Nexus 5(API 22)
3. As Dongjie's phone have physical navigation button, we set all activities in immersive mode


# Instruction to the Game:</br> #


## MainActivity: ##

   - New Game: create an new GameActivity and start a game
   - High Scores: Show the top 12 high scores of the past game
   - Music: The background music will be start when this switch is checked, otherwise, the music will be stop
   - SFx: The sfx music include attack sound, summon sound, applause sound and so on

## GameActivity: ##

__Action Instruction:__
#### Move & Attack: ####
    1. Select your own piece
    2. If the piece have available move(attack) cells, the move(attack) button will be light
    3. Click the move(attack) button, all the available cells will be shown
    4. Select a valid piece
#### Freeze & Heal: ####
    1. Click the Freeze(Heal) button;
    2. Select a valid piece
#### Revive: ####
    1. Click the Revive button
    2. If there are  available revived piece, the piece will be shown in the board
    3. Select a valid piece
#### Teleport: ####
    1. Click the Teleport button
    2. Select your own valid piece
    3. Select the destination

   For all actions, if you select an invalid piece or your action is invalid, your will hear a sound for invalid action and the action will be cancelled immediately.

#### Combat: ####
   If the combat is happened is in your turn, the combat animation's direction will face to you, otherwise, it will rotate 180 degrees.

__Piece Information:__
  1. In your turn, you can check the state of all pieces, including your opponent pieces.
  2. Click the piece, and it's information will be shown in your area.  
  3. If the piece is frozen, the left turns that the piece can unfrozen after will be appended to it's vitality.
  4. However, if it is your opponent turn, you can do nothing.


## HighScoreActivity: ##

    1. This activity will show top 12 high scores of the past history.
    2. When you firstly install the apk, there is no history in the database, so all of the texts will be set to show it's initial text.
    3. Only when you finished a game(Black win, White win, or Draw), the data of the your game will be added to database.

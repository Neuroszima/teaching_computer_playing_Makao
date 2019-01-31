# teaching_computer_playing_Makao

## about program

This project was final project of a Genetic Algorithms lecture conducted by prof. Janusz Hołyst at Warsaw University of Technology, made by Mateusz Anuszewski and Grzegorz Matyszczak.

The idea was to check if, by the given rules of card game "Makao", genetic algorithm can recreate those rules by creating population of Players, each with random Ruleset, and with the aim function of coins, distributed as reward or substracted as punishment functions.

Coin count decided of the certain Player class instance of better survivability, throwing right cards in the right circumstances (strategy was not included in this problem), rewarded Player with coins, and substracting them if conditions were not met and Player made a mistake. Across generations the aim is to have a Player with near exact same rules, represented by 52 "long" type numbers, as rules are set as byte 1's or 0's, and are checked with bytewise "AND" operations to sae computation time.

Original thought included a text represantation of rules, and, while perhaps more flexible, we decided to take a look at byte representation of the rules, as it would enable us to set greater population numbers at the same time.

## How does the code work (less detail explaination):

1. A set number of n*4 Players are created, given the groups count at the start, and giving them random Ruleset.

2. Players are put into Lounges, 4 per each, to simulate a usual party, that gather to play a game of Makao

3. Instance of Game is started, along giving the Game a judge, a set of Cards forming a Stack, from which players draw 5 cards

4. A Game is conducted in turns. Each turn, a Player throws a card, or decides not to throw, given their ruleset. According to action taken by a Player, Judge (an original rule holder) gives a reward (two coins, you can change a reward at the start of a program if needed), or punishes him and takes reward from him.

5. After all games are finished, a Leaderboard is created and then, a Roulette decides either given Player's instance pass on to next generation or not, as well as Crossing Over and Mutations happen. (standard procedures of genethic-algorithm-based problms)

6. A new set of Players begin new games, and situation loops until the given generations count is reached


The result of a program should be a best Player having most of the rules matching the original ones from the card game. An algorithm can be modified to reflect any basic card game, and the method in this program should give an output of the basic rules of what to do in the given card game.

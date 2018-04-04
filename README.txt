The main features;

* User can decrypt encrypted text from a file
* User can decrypt encrypted text from command line
* User can use a key if he knows it
* If user doesn't know the code simulated annealing will be used to break the key

I am using a formula to find the best optimal temperature for annealing which looks as follows:
10 + 0.087 * (cypherText.length() - 84)
I found that dividing the best optimal temperature by 3 gives me the best result and allows to decrypt the text faster.

Unfortunately if I try to use the TheHobbit-Cypher-Text file to get decrypted I get an error which I cannot fix, because of the text inside that isn't encrypted. If you intend to use it as an example could you please delete the text before the cypher text. I was always testing the encrypted text with exam tips that you gave us to actually test if the annealing works. 

When testing the application you will be asked if you want to enable debug mode or not. I was using "debugging mode" to see how the score was being improved. 

NOTE: Sometimes, very rarely the program will not be able to decrypt the text but after every attempt user is asked whether or not he/she is satisfied with the result. If user isn't satisftied, annealing will be ran again.

The best time to decrypt the exam_hints using my solution was 5sec.

After encrypted text is decrypted, results are saved in result.txt in the root directory of the program.
# Using Simulated Annealing to Break a Playfair Cipher

**Name**: Adrian Sypos
**Module name**: Artificial Intelligence
**Lecturer**: John Healy

## Overview

The field of cryptanalysis is concerned with the study of ciphers, having as its objective the
identification of weaknesses within a cryptographic system that may be exploited to convert
encrypted data (cipher-text) into unencrypted data (plain-text). Whether using symmetric or
asymmetric techniques, cryptanalysis assumes no knowledge of the correct cryptographic key
or even the cryptographic algorithm being used.

Assuming that the cryptographic algorithm is known, a common approach for breaking a cipher
is to generate a large number of keys, decrypt a cipher-text with each key and then examine the
resultant plain-text. If the text looks similar to English, then the chances are that the key is a
good one. The similarity of a given piece of text to English can be computed by breaking the
text into fixed-length substrings, called n-grams, and then comparing each substring to an
existing map of n-grams and their frequency. This process does not guarantee that the outputted
answer will be the correct plain-text, but can give a good approximation that may well be the
right answer.

## Project requirements
You are required to use the simulated annealing algorithm to break a Playfair Cipher. Your
application should have the following minimal set of features:
* A menu-driven command line UI that enables a cipher-text source to be specified (a
file or URL) and an output destination file for decrypted plain-text.
* Decrypt cipher-text with a simulated annealing algorithm that uses a log-probability
and n-gram statistics as a heuristic evaluation function. 

## What is Playfair Cipher?
The Playfair cipher is a digraph substitution cipher. It employs a table where one letter of the alphabet is omitted, and the letters are arranged in a 5x5 grid. Typically, the J is removed from the alphabet and an I takes its place in the text that is to be encoded. Below is an unkeyed grid.

A B C D E
F G H I K
L M N O P
Q R S T U
V W X Y Z

To encode a message, one breaks it into two-letter chunks. Repeated letters in the same chunk are usually separated by an X. The message, "HELLO ONE AND ALL" would become "HE LX LO ON EA ND AL LX". Since there was not an even number of letters in the message, it was padded with a spare X. Next, you take your letter pairs and look at their positions in the grid.

"HE" forms two corners of a rectangle. The other letters in the rectangle are C and K. You start with the H and slide over to underneath the E and write down K. Similarly, you take the E and slide over to the H column to get C. So, the first two letters are "KC". "LX" becomes "NV" in the same way.

"LO" are in the same row. In this instance, you just slide the characters one position to the right, resulting in "MP". The same happens for "ON", resulting in "PO". "EA" becomes "AB" in the same way, but the E is at the far edge. By shifting one position right, we scroll around back to the left side and get A.

"ND" are in a rectangle form and beomes "OC". "AL" are both in the same column, so we just move down one spot. "AL" is changed into "FQ". "LX" is another rectangle and is encoded as "NV".

The resulting message is now "KC NV MP PO AB OC FQ NV" or "KCNVMPPOABOCFQNV" if you remove the spaces.

This encoder will do all of the lookups for you, but you still need to do a few things yourself.

Manually break apart double letters with X (or any other) characters. Some people break apart all doubles, others break all doubles that happen in the same two-letter chunk. This encoder requires neither in order to be more flexible.
Manually make the message length even by adding an X or whatever letter you want. If you don't, the encoder will automatically add an X for you.

All non-letters are ignored and not encoded. The one letter that you select to share a square in the cipher is translated. Numbers, spaces, and punctuation are also skipped. If you leave two letters together in a two-letter chunk, they will be encoded by moving down and right one square ("LL" becomes "RR") where as traditional Playfair ciphers will automatically insert an X for you.

## What is Simulated Annealing?
Simulated annealing is a mathematical and modeling method that is often used to help find a global optimization in a particular function or problem. Simulated annealing gets its name from the process of slowly cooling metal, applying this idea to the data domain.

Simulated annealing (SA) is an excellent approach for breaking a cipher using a randomly generated key. Unlike conventional Hill Climbing algorithms, that are easily side-tracked by local optima, SA uses randomization to avoid heuristic plateaux and attempt to find a global maxima solution.

## Using n-Gram Statistics as a Heuristic Function
An n-gram (gram = word or letter) is a substring of a word(s) of length n and can be used to measure how similar some decrypted text is to English. For example, the quadgrams (4-grams) of the word “HAPPYDAYS” are “HAPP”, “APPY”, “PPYD”, “PYDA”, “YDAY” and “DAYS”. A fitness measure or heuristic score can be computed from the frequency of occurrence of a 4-gram, q, as follows: P(q) = count(q) / n, where n is the total number of 4-grams from a large sample source. An overall probability of the phrase “HAPPYDAYS” can be accomplished by multiplying the probability of each of its 4-grams: P(HAPPYDAYS) = P(HAPP) × P(APPY) × P(PPYD) × P(PYDA) × P(YDAY)

One side effect of multiplying probabilities with very small floating point values is that underflow can occur1 if the exponent becomes too low to be represented. For example, a Java float is a 32-bit IEEE 754 type with a 1-bit sign, an 8-bit exponent and a 23-bit mantissa. The 64-bit IEEE 754 double has a 1-bit sign, a 11-bit exponent and a 52-bit mantissa. A simple way of avoiding this is to get the log (usually base 10) of the probability and use the identity log(a × b) = log(a) + log(b). Thus, the score, h(n), for “HAPPYDAYS” can be computed as a log probability:

`log10(P(HAPPYDAYS)) = log10(P(HAPP)) + log10(P(APPY)) + log10(P(PPYD)) + log10(P(PYDA)) + log10(P(YDAY)`

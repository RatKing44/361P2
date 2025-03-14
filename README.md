# Project 2: Nondeterministic Finite Automata

* Author: Archie Rauenhorst & Adam Taylor
* Class: CS361 Section 001
* Semester: Spring 2025

## Overview

This Java application is an implementation of nondeterministic finite automata, 
which uses states to keep track of transitions between the states.

## Reflection

This project was an overall great time, as I have enjoyed thinking about Finite automata in the lense of Java programs. I would say it was certainly a little more difficult than the last project, just because of the nondeterminism and the eclosures that we had to worry about, but overall, we thoroughly enjoyed it.


- What worked well and what was a struggle? 
  - What worked really well for us this time around was drawing out examples on a whiteboard as we were working on methods. The nondeterminism could definetly be a challenge, and so to help with that we would write examples as we went. Accepts and max copies was definetly what we struggled with the most, but we ended up figuring them out. 
- What concepts still aren't quite clear?
  - Now that we are this far into class, there aren't really any concepts that aren't clear to us.  
- What techniques did you use to make your code easy to debug and modify?
  - We used the debugger a lot to help us work through our bugs. The first night we worked on it I want to say we wrote like four different methods, all of which had one minor error that we were able to sniff out using the debugger.  
- What would you change about your design process?
  - We don't think that we would change much about our design process at all, just because we felt like we worked and implmented it successfully. 
- If you could go back in time, what would you tell yourself about doing this project?
  - If we could go back in time, it would be similar to P1. We would tell ourselves not to get worked up about it because it really isn't all that difficult. 

## Compiling and Using

To compile, execute the following command in the main project directory:
```
$ javac -cp .:/usr/share/java/junit.jar ./test/nfa/NFATest.java
```

Run the compiled class with the command:
```
$ java -cp .:/usr/share/java/junit.jar:/usr/share/java/hamcrest/core.jar
 org.junit.runner.JUnitCore test.nfa.NFATest

```

After putting this in the command lime, it will run the tester file to make sure all of the methods are running properly. 

## Sources used

- We really didn't use any other outside sources for this assignment. 
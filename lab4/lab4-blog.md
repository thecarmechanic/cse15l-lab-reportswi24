# From the Command Line
## 2/27 Lab Report 4
#### Step 1: Login to ieng6**
**Keys pressed:** `<up><up><enter>`  
The command to connect to my remote account, `ssh cah010@ieng6.ucsd.edu` was 2 steps before in the terminal history, so I clicked the up arrow twice to access it and pressed the enter key to run it.  
![Image][screenshots/]

#### Step 2: Clone repository with ssh link
**Keys Pressed:** `<CTRL+R>git clone<enter>`  
    When practicing in lab, I'd cloned and deleted the `cse15l-lab7` repository multiple times, so I knew the command to clone it was in my terminal history, but I didn't know how far back it was buried, so using the up arrow wasn't convenient. Instead I performed a search (using `CTRL+R`)  for "git clone", which pulled up the relevant matching command `git clone git@github.com:thecarmechanic/cse15l-lab7.git`, and pressed the enter key to run it.  
![Image][screenshots/]

#### Step 3: Run tests
**Keys Pressed:** `cd c<tab><enter>` to change my working directory, then `bash t<tab><enter>` to run the tests  
    The repository contains a script for running jUnit tests without typing up the lengthy commands, and I thought it would be easiest to use it if I operated from `cse15l-lab7` as my working directory. I started with `cd`, the command to switch into a specified directory, then hit `c<tab>` to autocomplete the directory name so I didn't have to type it out, and `<enter>` to run everything. To run the test script `test.sh`, I followed a similar process, first typing out the command `bash` the `t<tab>` to autocomplete to the target file I wanted, then `<enter>` to run everything.  

![Image][screenshots/]

#### Step 4: Fix bug in code
**Keys pressed:** `vim Li<tab>.java<enter>`,`44jk``11l``r2``:wq`  
    The jUnit output from the previous step indicates that the source of the symptom is in the `ListExamples.java` file around line 44. To edit the file from the command line using vim, I invoked the command `vim`, typed the first 2 characters in the file name then used `<tab>` to autocomplete. However, since the directory contains multiple files that start with "ListExamples", it only completed to that, and I had to finish it with ".java".  
    ![Image][screenshots/]
    Once I had the Vim editor for the file open, I navigated down to line 44 with the `j` key. There was just a curly brace there, so actual error turned out to be one line above, so I navigated up with the `k` key. Since I only need to change "index1" to "index2" I would need to move the cursor to "1", which is exactly 11 characters over to the left. Then I directly replaced "1" with "2" from Normal mode using the remove key `r`.  
    ![Image][screenshots/]
    With my changes complete, I saved and closed the editor withh `:wq`.  
    ![Image][screenshots/]

#### Step 5: Run tests again
**Keys pressed:** `<up><up><enter>`  
    The `bash test.sh` was two commands up in my terminal history, so I clicked the up arrow twice to access it, and ran it with the enter key.  
    ![Image][screenshots/]

#### Step 6: Commit and push changes to Github
**Keys pressed:** `git add <tab><enter>`  
**Commands typed:** `git commit -m "updated ListExamples.java"<enter>`, `git push<enter>`  
    I used `git add` to stage the changes to commit in `ListExamples.java`, which I autocompleted with the `<tab>` key. This time, it was enough to just click tab and get the entire file name, because that was the only file that changed. I then commited my changes with an indicated message to avoid opening a vim editor, then pushed my changes to Github.  
    ![Image][screenshots/git.png]


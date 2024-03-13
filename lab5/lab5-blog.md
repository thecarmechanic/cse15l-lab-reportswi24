# Putting It All Together
## 3/12 Lab Report 5
### Part I: Debugging Scenario

> #### Unexpected grading script compile error behavior
> _Posted by_ **Some Student**  
> &nbsp;My grading script code behaves as expected for all the "student submission" repository links that successfully compile, but for the "submissions" that fail on the compile step, then the output looks a little weird. For reference, here is the bash script code I wrote to check for an handle compile errors:
> ```
> # set up testing in grading area directory
> cd grading-area
>
> CPATH='.:../lib/hamcrest-core-1.3.jar:../lib/junit-4.13.2.jar'
> javac -cp $CPATH *.java > compile-message.txt # 2>&1
> if [[ $? -ne 0 ]]
> then
>   echo "Compile error:"
>   cat compile-message.txt
>   echo "Your code failed to compile. This could be due to syntax errors, or when your implementation does not match the instructions."
>   exit 1
> fi
> ```
> I redirect any compile output message into a new file. If content is saved to it, the code failed to compile. I copy the error message to the terminal sandwiched between two messages that indicate that a compile message has occured first, and then a brief description to help students consider possible errors.
> However, when I run my grading script from the command line passing the repository `https://github.com/ucsd-cse15l-f22/list-methods-compile-error` as an argument, then it produces the symptom where the error message is printed before my echo statements, even though my code clearly specifies that it should be printed in between. In this case, the failure inducing input is the repository I passed to `grade.sh`, which I can see creates a compile error due to a missing semicolon in the `ListExamples.java` file.
> ![Image][screenshots/unexpected-output-error.png]
> My guess is that on line of my code above, the compiler error thrown somehow isn't getting redirected to the file `compile-message.txt` and is instead getting printed directly to the terminal. But why might this be happening and how can I change this so that it is redirected?

### Comments:
> #### TA Response:
> Hi Some! That is a great guess for what the issue might be. Let's test your hypothesis and see if we can locate the bug. What happens when you remove the `cat compile-message.txt` line? Does the error still print? Next, can you check the contents of `compile-message.txt` and see if there is anything in it?  

> #### Student Response:
> When I removed the `cat compile-message.txt` line, the output to the terminal was no different. This is indeed because the `compile-message.txt` file, which I tried to redirect the compiler message to, is empty. Why is that?
> ![Image][screenshots/debugging]

> #### Ta Response:
> Great observation! So the reason for this is because shell scripts, including bash, handle error outputs separately from normal outputs. In fact, in `bash` you can think of your code `javac -cp $CPATH *.java` as producing two outputs - one normal and one error that are automatically printed to the terminal. If compile succeeds, both outputs are empty and nothing is printed to the terminal because Java doesn't automatically print messages that indicate if compile succeeds. If compile fails, then the normal ouput is empty while the error output is printed to the terminal. Now, when you redirect the output (in this case, to `compile-messages.txt`) the first output (normal), which is empty, is redirected with the first `>`, and the secont output (error) is printed to the terminal. With this in mind, how would you redirect the second output, the error message?
<br/
> #### Student Response:
> Oh, I think I get it now! Should I add a second redirect on the same line to catch the error message into a file?
<br/>
> #### TA Response:
> Yes, you got it! Try that out, and if you get stuck, there are plenty of helpful syntax guides on the internet. Happy debugging :)  
<br/>
**_Coding Context & Debugging Summary_**
- File and directory structure:
  > ```
  > grading-area/
  >    compile-message.txt
  >    ListExamples.java        _**copied over from student-submission/**_
  >    TestListExamples.java    _**copied over from parent directory_
  > 
  > lib/
  >     hamcrest-core-1.3.jar
  >     junit-4.13.2.jar
  > student-submission/
  >     ListExamples.java
  > grade.sh
  > TestListExamples.java
  > ```
- Contents of grade.sh before bug fix:
  ```ruby
  # No set -e : we want to check for errors with this program
  rm -rf student-submission
  rm -rf grading-area
  
  mkdir grading-area

  # clone student submission
  git clone $1 student-submission
  echo 'Finished cloning'

  # check for file to grade
  if [[ $(find 'student-submission/' -name 'ListExamples.java') ]]
  then
   cp -r student-submission/ grading-area
   cp TestListExamples.java grading-area
  else
   echo "Missing ListExamples file. Make sure you submit files with the matching name."
   exit 1
  fi

  # set up testing in grading area directory
  cd grading-area
  CPATH='.:../lib/hamcrest-core-1.3.jar:../lib/junit-4.13.2.jar'
  javac -cp $CPATH *.java > compile-message.txt # BUG HERE!
  
  if [[ $? -ne 0 ]]
  then
    echo "Compile error:"
    cat compile-message.txt
    echo "Your code failed to compile. This could be due to syntax errors, or when your implementation does not match the instructions."
    exit 1
  fi

  java -cp $CPATH org.junit.runner.JUnitCore TestListExamples > test-results.txt

  # calculate score based on tests passed and failed
  lastline=$(cat test-results.txt | tail -n 2|head -n 1)
  allpassed=$(echo $lastline | grep 'OK')
  # when there are no failues
  if [[ -n $allpassed ]]
  then
   # tests=
   echo "Congratulations, you passed all $(echo $lastline | grep -Eo '[0-9]+') tests!"
   exit 0
  fi

  # when there are failures
  tests=$(echo $lastline | awk -F'[, ]' '{print $3}')
  failures=$(echo $lastline | awk -F'[, ]' '{print $6}')
  successes=$(($tests-$failures))
  echo "Your score is $successes/$tests."
  ```
- Bug location: The bug occurs on the line `javac -cp $CPATH *.java > compile-message.txt` from above, from compiling the file `ListExamples.java` which was acquired from cloning the repository `https://github.com/ucsd-cse15l-f22/list-methods-compile-error`. Recall that the student in the scenario above triggered the bug with these lines of code:
  ![Image][screenshots/unexpected-output-error.png]
- Bug fix: The bug can be fixed with a second redirect command to catch the error message in a text file. For instance, `javac -cp $CPATH *.java > compile-message.txt` can be changed to `javac -cp $CPATH *.java > compile-message.txt 2>&1`, which directs the error message into the same file `compile-message.txt`.
_Source:_![Link][https://www.redhat.com/sysadmin/redirect-shell-command-script-output]  
For information on standard outputs and redirects in scripting.


### Part II: Reflection
Building my own grading script taught me the basic ideas behind Autograder, which is used heavily in my CS courses. Now that I know a bit more about how it works, I understand why the guidelines for PAs in the other CSE courses are so detailed and strict -- it's because Autograder checks for very specific things, such as specific files and method headers. That's why we can't change method headers and are asked to only submit the required files and not general directories. I also learned a lot more about `bash` script and the command line. `bash` syntax can be pretty annoying and confusing sometimes, but I appreciated how much it helped streamline certain processes. I especially enjoyed learning how to search files and edit files from terminal with `vim`. Finally, I learned that `jdb` existed, and some commands related to that to use this tool for debugging. I'd definitely like to practice with this framework a bit more because it's one of the skills I'm less comfortable with out of everything we covered this quarter.

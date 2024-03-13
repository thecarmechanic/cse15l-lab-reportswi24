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
>
> However, when I run my grading script from the command line passing the repository `https://github.com/ucsd-cse15l-f22/list-methods-compile-error` as an argument, then it produces the symptom where the error message is printed before my echo statements, even though my code clearly specifies that it should be printed in between. In this case, the failure inducing input is the repository I passed to `grade.sh`, which I can see creates a compile error due to a missing semicolon in the `ListExamples.java` file.
> ![Image][screenshots/unexpected-output-error.png]
> My guess is that on line  of my code above, the compiler error thrown somehow isn't getting redirected to the file `compile-message.txt` and is instead getting printed directly to the terminal. But why might this be happening and how can I change this so that it is redirected?
<br/>
> #### TA Response



### Part II: Reflection
Building my own grading script taught me the basic ideas behind Autograder, which is used heavily in my CS courses. Now that I know a bit more about how it works, I understand why the guidelines for PAs in the other CSE courses are so detailed and strict -- it's because Autograder checks for very specific things, such as specific files and method headers. That's why we can't change method headers and are asked to only submit the required files and not general directories. I also learned a lot more about `bash` script and the command line. `bash` syntax can be pretty annoying and confusing sometimes, but I appreciated how much it helped streamline certain processes. I especially enjoyed learning how to search files and edit files from terminal with `vim`. Finally, I learned that `jdb` existed, and some commands related to that to use this tool for debugging. I'd definitely like to practice with this framework a bit more because it's one of the skills I'm less comfortable with out of everything we covered this quarter.

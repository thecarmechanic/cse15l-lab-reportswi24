# Debugging, File Exploration, and Text Analysis

## 2/13 Lab Report 3

### Part 1: Bugs
Last week, I stepped through the process of indentifying and isolating progam bugs.  
<br/>For the `ArrayExamples` class, I used `JUnit` tests to check for buggy methods. Let's look at one example.
> 🔄 **Buggy Method:** `reverseInPlace(int[] arr)`
> Failure-Inducing Input
> ```
> int[] fails = {3,2,1};
> assertArrayEquals(new int[]{1,2,3}, ArrayExamples.reverseInPlace(fails));
> ```
> 
> Asymptomatic Input
> ```
> int[] passes = {3,3,3};
> assertArrayEquals(new int[]{3,3,3}, input1);
> ```
> 
> Symptom
> ![Image](screenshots/bug-symptom.png)
> From the output after running my tests, it appears that the _failure-inducing input_ is not reversing as expected.
> 
> The Bug
> - Before fix:
>   ```
>   // Changes the input array to be in reversed order
>   static void reverseInPlace(int[] arr) {
>       for(int i = 0; i < arr.length; i += 1) {
>           arr[i] = arr[arr.length - i - 1];
>       }
>   }
>   ```
>   The current issue is that the original value at each position is updated without being retained, so when we reach the later half of the list, the values aren’t being changed
> - After fix:
>   ```
>   // Changes the input array to be in reversed order
>   static void reverseInPlace(int[] arr) {
>       int replaced;
>       for(int i = 0; i < arr.length/2; i += 1) {
>           replaced = arr[i];
>           arr[i] = arr[arr.length - i - 1];
>           arr[arr.length - i - 1] = replaced;
>       }
>   }
>   ```
>   To fix the bug, I created a new variable called `replaced` to store the value being replaced, so it’s not being overwritten. Then I only need to iterate through half of the list so the elements in the latter half of the list are updated with the value stored in the `replaced`.
>   

/* Filename: Driver_prj0.java
 * Student name: Brian Henderson
 * Course: CMPT 435
 * Assignment: Project 0 -- Program trace verification
 * Date: Feb 16 2018 
 * Version: 2.0 
 *
 * This program contains the a program stack trace verification driver
 */
import java.util.Scanner;
import java.util.Stack;

public class Driver_prj0 {

  /* main
   *  parameters:
   *      args -- the array of command line argument values
   *  return value: nothing
   * 
   */
  public static void main(String[] args) {
    // Here we initialize the scanner variable to read lines of input
    Scanner input = new Scanner(System.in);
    String line;

    // the callStack is used for storing the names of functions that have been
    // called and not yet returned
    Stack<String> callStack = new Stack<String>();

    int lineNumber = 0;
    int maximum_depth = 0;
    int depth = 0;
    String[] Words = new String[2];
    String FunctionType = null;
    String FunctionName = null;
    
    // the validTrace is used to store whether the trace is valid or has errors
    boolean validTrace = true;
    
    while (input.hasNext()) {
      line = input.nextLine();
      Words = line.split(" ");
      lineNumber++;
      FunctionType = Words[0];
      FunctionName = Words[1];
      
      
      // if the function type is call, then push to the call stack and adjust depth
      // and maximum depth based on operational condition
      if (FunctionType.equals("call")) {
    	    callStack.push(FunctionName);
    	    depth++;
        maximum_depth = (maximum_depth < depth) ? depth : maximum_depth;
      }
      // function type is return, check for errors and respond appropriately  
      else {
    	    // callStack is empty, means invalid trace and returning from a function
    	    // that was not called
    	    if (callStack.isEmpty()) {
          validTrace = false;
        	  System.out.println("Invalid trace at line " + lineNumber);
        	  System.out.println("Returning from " + FunctionName + " which was not called");
        	  printStack(callStack);
        	  break;
        }
    	    // attempting to return from a different function, rather than the top of the stack
    	    else if (! FunctionName.equals(callStack.peek())){
    	    	  validTrace = false;
    	    	  System.out.println("Invalid trace at line " + lineNumber);
    	    	  System.out.println("Returning from " + FunctionName + " instead of " + callStack.peek());
    	    	  printStack(callStack);
          break;
    	    }
    	    // valid return, continue onto next line
    	    else {
    	    	  callStack.pop();
    	    	  depth--;
    	    }
      }
    }
    
    // if the input finished with an validTrace but the stack is not empty, return error
    if (! callStack.isEmpty() && validTrace) {
    	  validTrace = false;
    	  System.out.println("Invalid trace at line " + lineNumber);
    	  System.out.println("Not all functions returned");
    	  printStack(callStack);
    }
    // if the program executed fully without triggering an invalidTrace flag,
    // the trace is valid 
    if (validTrace) {
    	  System.out.println("Valid trace");
    	  System.out.println("Maximum call depth was " + maximum_depth);
    }
  }  
  
  /* printStack
   *  parameters:
   *      Stack s -- Input stack to print
   *  return value: nothing
   *  prints stack to console
   */
  private static void printStack(Stack s) {
	  System.out.println("Stack trace:");
	  while (! s.isEmpty()) {
		  System.out.println(s.pop());
	  }
  }
 
}

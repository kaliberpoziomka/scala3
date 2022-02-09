package lectures.part1basics

import scala.annotation.tailrec

object Recursion extends App {

  // Stack recursion - NOT GOOD - it stores intermediate results in stack, Stack Overflow Error is easy to occur
  def factorial(n: Int): Int = {
    if (n <= 1) 1
    else {
      println("Computing factorial of " + n + " - I first need factorial of " + (n-1))
      val result = n * factorial(n-1)
      println("Computed factorial of " + n)
      result
    }
  }
  // Stack recursion is bad, because last expression is not recursive call function - it is product of number and call function
  // because of that, it has to store all the intermediate results, so it can then multiply all of that by the n number

//  println(factorial(10))
//  println(factorial(5000))
  // 5000 causes stack-overflow error!

// TAIL RECURSION - GOOD - does not store results in stack memory, it passes result as argument
  def anotherFactorial(n: Int): BigInt = {
    @tailrec // if you not sure if func is tail recursive, this annotation will check
    def factHelper(x: Int, accumulator: BigInt): BigInt = {
      if (x <= 1) accumulator
      else factHelper(x-1, x * accumulator) // <- TAIL RECURSION
    }

    factHelper(n, 1)
  }

//  println(anotherFactorial(5000))

  // In TAIL recursion last stack frame is replaced with another, it does not need to wait to make multiplication by previous number
  // SECRET OF TAIL RECURSION:
  // LAST EXPRESSION MUST BE RECURSIVE FUNCTION CALL
  // if last expression is recursive function call AND some other operation, it gonna be stack recursion

  /*
  EXERCISES
  1. Concatenate a string n times
  2. IsPrime function tail recursive
  3. Fibonacci function tail recursive
   */

  // 1.
  def stringConcatenationTailRecursive(aString: String, n: Int): String = {
    @tailrec
    def recHelper(n: Int, accumulator: String): String = {
      if (n < 1) accumulator
      else recHelper(n - 1, aString + accumulator)
    }
    recHelper(n, "")
  }
  println(stringConcatenationTailRecursive("Hi", 10))

  // 2.
  def isPrime(n: Int): Boolean = {
    def isPrimeTL(t: Int, isStilPrime: Boolean): Boolean = {
      if (!isStilPrime) return false
      else if (t <= 1) true
      else isPrimeTL(t - 1, n % t != 0 && isStilPrime)
    }

    isPrimeTL(n / 2, true)
  }


  // 3.
  def fibonacciTailRecursive(n: Int): Int = {
    def fibHelper(i: Int, last: Int, nextLast: Int): Int = {
      if(i >= n) last
      else fibHelper(i + 1, last + nextLast, last)
    }

    if (n <= 2) 1
    else fibHelper(2, 1, 1)
  }

  println(fibonacciTailRecursive(4))
}

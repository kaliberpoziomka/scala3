package lectures.part2oop

object Exceptions extends App {

  val x: String = null
//  println(x.length)
  // ^^ this will crash
  // 1. throwing exceptions

//  val aWeirdVal: String = throw new NullPointerException // this can be a value, but returns nothign and crashes a program XD

  // throwable classes extend the Throwable class
  // Exception and Error are the major Throwable subtypes
  // Exceptions and Errors will crash program
  // Exceptions - smth wrong with code - like impossible usage of some methods
  // Errors - smth wrong in system bacuse of program run - like stack overflow

  // 2. Catching exceptions
  def getInt(withExceptions: Boolean): Int = {
    if (withExceptions) throw new RuntimeException("No int for you!")
    else 42
  }

  // like everything in scala, try/catch/finally is an expression

  val potentialFail =  try {
      // code that might fail
      getInt(true)
    } catch {
      case e: NullPointerException => println("caught NullPointerException")
      case e: RuntimeException => println("caught a Runtime exception")
    } finally {
      // code that will get executed no matter what
      println("finally")
      // finally is optional and does not infulence the return type of this expression
      // they are used for side effects
    }
  println(potentialFail) //AnyVal

  class MyException extends Exception
  val exception = new MyException
//  throw exception
  println(Int.MaxValue)

  /*
  * 1. Crash program with OutOfMemory error
    2. crash with StackOverflowError
    3. PocketCalculator
      - add(x, y)
      - substract(x, y)
      - multiply(x, y)
      - divide(x, y)

      Throw:
        - OverflowException if add(x, y) exceeds Int.MaxValue
        - UnderflowException if substract(x, y) exceeds Int.MinValue
        - MatchCalculationException for division by 0
  * */

  class OverflowException extends Exception
  class UnderflowException extends Exception
  class MatchCalculationException extends Exception


  class PocketCalculator {
    def checkForException(n: Int): Unit = {
      if(n > Int.MaxValue) throw new OverflowException
      else if(n < Int.MinValue) throw new UnderflowException
    }
    def add(x: Int, y: Int): Int = {
      checkForException(x)
      checkForException(y)
      val result = x + y
      checkForException(result)
      result
    }
    def subtract(x: Int, y:Int): Int = {
      checkForException(x)
      checkForException(y)
      val result = x - y
      checkForException(result)
      result
    }
    def multiply(x: Int, y:Int): Int = {
      checkForException(x)
      checkForException(y)
      val result = x * y
      checkForException(result)
      result
    }
    def divide(x: Int, y:Int): Int = {
      checkForException(x)
      checkForException(y)
      if (y == 0) throw new MatchCalculationException
      val result = x / y
      checkForException(result)
      result
    }
  }



}

package lectures.part1basics

object CBNvsCBV extends App {

  def calledByValue(x: Long): Unit = {
    println("by value: " + x)
    println("by value: " + x)
  }

  def calledByName(x: => Long): Unit = {
    println("by name: " + x)
    println("by name: " + x)
  }

  calledByValue(System.nanoTime())
  calledByName(System.nanoTime())

  // Call by value -> argument to a function is computed before function is called, so we pass only a value
  // visualization:

//  calledByValue(System.nanoTime())
//  System.nanoTime() -> 14657355116772L
//  def calledByValue(x: Long): Unit = {
//    println("by value: " + 14657355116772L)
//    println("by value: " + 14657355116772L)
//  }

  // value is the same as it came into the function

  // Call by name -> LAZY EVALUATION, argument to a function is not a value computed before, but a reference to a function
  // the value is evaluated in the function and only when it must be

//  calledByName(System.nanoTime())
//  def calledByName(x: => Long): Unit = {
//    println("by name: " + System.nanoTime())
//    println("by name: " + System.nanoTime())
//  }
  // it is extremely useful in LAZY STREAMS, and in things that may fail

  // the difference is in the arrow => before the type

// Examples

  def infinite(): Int = 1 + infinite()
  def printFirst(x: Int, y: => Int) = println(x)

//  printFirst(infinite(), 34) -- stack overflow
  printFirst(34, infinite()) // no stack overflow, because lazy evaluation infinite() is never evaluated

}

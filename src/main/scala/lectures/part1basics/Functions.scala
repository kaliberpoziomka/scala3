package lectures.part1basics

object Functions extends App {
  def aFunction(a: String, b: Int): String = {
    a + ' ' + b
  }


  println(aFunction("hello", 3)) // calling a function is also an expression

  // parameterless functions can be called without parentheses
  def aParameterlessFunction: Int = 42
  println(aParameterlessFunction)

  // in imperative OOP languages you use loops
  // in scala (and other functional languages) you use RECURSION
  // NO LOOPS, JUST RECURSION
  def aRepeatedFunction(aString: String, n: Int): String = {
    if (n == 1) aString
    else aString + aRepeatedFunction(aString, n-1)
  }
  println(aRepeatedFunction("Henlo", 5))

// you can use Unit as return type
  def aFunctionWithSideEffects(aString: String): Unit = println(aString)
  aFunctionWithSideEffects("hihi")

  def aBigFunction(n: Int): Int = {
    def aSmallerFunction(a: Int, b: Int): Int = a+b

    aSmallerFunction(n, n-1)
  }

  println(aBigFunction(6))


  /*Excercises

    1. A greeting function (name, age) => "Hi, my name is $name and I am $age years old."
    2. Factorial function
    3. Fibonacci function
    4. Test if a number is prime function

   */
  println("Exercises")
  // 1.
  def greetingFunction(name: String, age: Int): String = "Hi, my name is " + name + " and I am " + age.toString() + " years old."
  println(greetingFunction("Paulinka", 25))
  // 2.
  def factorial(n: Int): Int = {
    if (n <= 0) 1
    else factorial(n-1) * n
  }
  println(factorial(5))
  // 3.
  def fibonacci(n: Int): Int = {
    if (n <= 1) 1
    else fibonacci(n-1) + fibonacci(n-2)
  }
  println(fibonacci(8))
  // 4.
  def isPrime(n: Int): Boolean = {
    def isPrimeUntil(t: Int): Boolean = {
      if (t <= 1) true
      else n % t != 0 && isPrimeUntil(t-1)
    }
    isPrimeUntil(n / 2)
  }
  println(isPrime(37))
  println(isPrime(38))
}

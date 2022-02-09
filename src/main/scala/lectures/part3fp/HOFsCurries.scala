package lectures.part3fp

object HOFsCurries  extends App {

  // Higher Order Function (HOF) -> func that takes func as a parameter or return func
//  val superFunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = ???

  // other HOFs
  // map, flatMap, filter in MyList1

  // function that applies a function n time over a value x
  // nTimes(f, n, x)
  // nTimes(f, 3, x) = f(f(f(x)))
  def nTimes(f: Int => Int, n: Int, x: Int): Int = {
    if(n <= 0) x
    else nTimes(f, n-1, f(x))
  }

  val plusOne: Int => Int = (x: Int) => x +  1
  println(nTimes(plusOne, 10, 1)) // increment 10 times, so its eleven

  def nTimesBetter(f: Int => Int, n: Int): (Int => Int) = {
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTimesBetter(f, n-1)(f(x)) // nTimesBetter returns a function (x: Int) => ... that will be applied to f(x)
    // so it builds a function that calls f() many times, like f(f(f(f(f(x))))) and we will need just an x
  }

  // we can do this with val and anonymous
  val nTimesBetterVal: (f: Int => Int, n: Int) => (Int => Int) = (f: Int => Int, n: Int) => {
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTimesBetter(f, n-1)(f(x))
  }

  val plus101 = nTimesBetter(plusOne, 101)
  val onePlus101 = plus101(1)
  println(onePlus101)

  val plus2000 = nTimesBetterVal(plusOne, 2000)
  println(plus2000(1))

  // curried functions
  val superAdder: Int => (Int => Int) = (x: Int) => (y: Int) => x + y
  val add3 = superAdder(3) // y => 3 + y
  println(add3(10))
  // ale works ac vurried function
  println(superAdder(3)(10))

  // functions with multiple parameter lists
  def curriedFormatter(c: String)(x: Double): String = c.format(x) // this is curried definition function
  val standardFormat: Double => String = x =>  curriedFormatter("%4.2f")(x) // it takes a format and returns a function that will format any Double
  // second function but also another way of creating this function. The argument is by default
  val preciseFormat: Double => String = curriedFormatter("%10.8f") // we need to provide proper types for this functions

  println(standardFormat(Math.PI)) // 3,14
  println(preciseFormat(Math.PI)) // 3,14159265

  // my own crazy function with multiple parameter list
  def addStringsOnBothSidesOfANumber(s1: String, s2: String)(n: Int): String = s1 + n.toString + s2
  val starredNumber: Int => String = addStringsOnBothSidesOfANumber("*", "*")
  println(starredNumber(6))
  // we can also provide an method definition (with def) but here we need to add and explicit argument (which is ok for me)
  def starAndPlusNumber(x: Int): String = addStringsOnBothSidesOfANumber("*", "+")(x)
  println(starAndPlusNumber(9))
  
  /*
   1. Extend MyList1
   - foreach method A => Unit // apply function to every element of a list
    [1,2,3].foreach(x => println(x))
   - sort function ((A, A) => Int) => MyList
    [1,2,3].sort((x, y) => y - x) => [3,2,1]
   - zipWith (list, (A, A) => B) => MyList[B]
    [1,2,3].zipWith([4,5,6], x * y) => [1*4, 2*5, 3*6] = [4,10,18] 
   - fold
    fold(start)(function) => a value
    [1,2,3].fold(0)(x+y) = 6
  
  2. toCurry(f: (Int, Int) => Int) => (Int => Int => Int)
      fromCurry(f: (Int => Int => Int)) => (Int, Int) => Int
  
  3. compose(f,g) => x => f(g(x))
      andThen(f,g) => x => g(f(x))
  
  */
  // 2. toCurry
  def toCurry(f: (Int, Int) => Int): Int => Int => Int = {
    // we return function here, so this lambda will be returned. It has to have type (Int, Int) => Int, because the input type is (Int, Int) => Int
    x => y => f(x, y)
      // when we return function from function, the outer function return type, must be input to lambda
      // and the inner lambda output must be the input of outer -> because we return a function
  }
  def fromCurry(f: Int => Int => Int): (Int, Int) => Int = {
    (x, y) => f(x)(y)
  }

  def compose[A,B,C](f: B => C, g: A => B): A => C = {
        //this also returns a function, that is first executes second function, and later first one
    x => f(g(x))
  }
  def andThen[A,B,C](f: A => B, g: B => C): A => C = {
    x => g(f(x))
  }

  def superAdder2: (Int => Int => Int) = toCurry(_+_) // the same as toCurry((a, b) => a+b)  <- we take a function that takes two parameters (because we returned such a function in toCurry)
  def add4 = superAdder2(4)
  println(add4(17))

  def simpleAdder = fromCurry(superAdder)
  println(simpleAdder(4, 17))

  def add2 = (x: Int) => x + 2
  def times3 = (x: Int) => x * 3
  val composed = compose(add2, times3)
  val ordered = andThen(add2, times3)
  println(composed(4))
  println(ordered(4))

}

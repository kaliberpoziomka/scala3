package lectures.part3fp

object AnonymousFunctions extends App {

  // a lot of typing to create a function
  val doubler = new Function1[Int, Int] {
    override def apply(x: Int): Int = x * 2
  }

  // better way to create a func, like in javascript xd
  // anonymous function (lambda), it is syntactic sugar for the higher one
  val betterDoubler: Int => Int = (x: Int) => x * 2
  // we can write it even better
  val evenBetterDoubler: Int => Int = x => {x * 2}

  // multiple parameters
  val adder: (Int, Int) => Int = (a, b) => {a + b}

  // no parameters
  val justDoSomething = () => {3}

  // methods without parameters MUST BE called without parenthesis
  // BUT LAMBDAS without arguments ALWAYS MUST BE CALLED WITH parenthesis
  println(justDoSomething) // lectures.part3fp.AnonymousFunctions$$$Lambda$19/0x0000000800c27ac8@56cbfb61
  println(justDoSomething()) // 3

  // curly braces with lambdas - also valid, because in curly braces we have block to execute, and later is lambda
  val stringToInt = { (str: String) =>
    str.toInt
  }

  // MOAR syntactic sugar
  val incrementer: Int => Int = (x: Int) => x + 1
  // the same as above
  val niceIncrementer: Int => Int = _ + 1
  val niceAdder: (Int, Int) => Int = _ + _ // equivalent to (a, b) => a + b

  // 1. Rewrite superAdder to lambda
  val superAdderLambda = (x: Int) => (y: Int) => x + y
  println(superAdderLambda(5)(6))
}

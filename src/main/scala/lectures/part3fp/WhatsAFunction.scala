package lectures.part3fp

object WhatsAFunction extends App {
  // DREAM: use functions as first class objects
  // problem: oop - everything is instance of a class

  // function in oop
  class Action {
    def execute(element: Int): String = ???
  }

  trait Action1[A,B] {
    def execute(element: A): B = ???
  }

  // we can create traits with special method apply()
  trait MyFunction[A, B] {
    def apply(element: A): B
  }
  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element*2
  }
  // so now we can call it like a function
  println(doubler(2))

  // scala supports function types by default:
  // Function1[A, B] <- one parameter in, one out
  // Function2
  // ...
  // Function22
  val stringToIntConverter = new Function1[String, Int] {
    override def apply(string: String): Int = string.toInt
  }
  println(stringToIntConverter("5"))

  val adder = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a+b
  }
  //the same is like this
  val adder1: (Int, Int) => Int = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a+b
  }

  // ALL SCALA FUNCTIONS ARE OBJECTS

  // 1. Func which takes 2 strings and concatenate them
  def concatenator: (String, String) => String = new Function2[String, String, String] {
    override def apply(v1: String, v2: String): String = v1+v2
  }
  println(concatenator("Henlo", " Wolrd"))




  // 3.
  val superAdder: Function[Int, Function[Int, Int]] = new Function[Int, Function[Int, Int]] {
    override def apply(x: Int): Function[Int, Int] = {
      new Function[Int, Int] {
        override def apply(y: Int): Int = x + y
      }
    }
  }
  val adder3 = superAdder(3)
  println(adder3(6))
  println(superAdder(6)(9)) // curried function
  
 
}

package lectures.part3fp

object PatternsEverywhere extends App {
  // big idea #1
  try {
    // some code
  } catch {
    case e: RuntimeException => "runtime" // pattern matching
    case npe: NullPointerException => "npe" // pattern matching
    case _ => "something else"  // pattern matching
  }
  // catches are actually matches
  /*
  try {
    //code
  } catch (e) {
    e match {
      case e: RuntimeException => "runtime"
      case npe: NullPointerException => "npe"
      case _ => "something else"
    }
  }
  */

  // big idea #2
  val list = List(1,2,3,4)
  val evenOnes = for {
    x <- list if x % 2 == 0 // this generator is also a pattern matching
  } yield 10 * x
  // generators are also based on PATTERN MATCHING
  val tuples = List((1,2), (3,4))
  val filterTuples = for {
    (first, second) <- tuples // here also decomposing works like in pattern matching, because it is based on pm
  } yield first * second

  // case classes, :: operators are also PM

  // big idea #3
  val tuple = (1,2,3)
  val (a,b,c) = tuple // assigning multiple values with decomposing <- like in python
  println(a)
  // this can be used to every data structure
  val someList = List(1,2,3)
  val head :: tail = someList // decomposing list with ::
  println(head) // 1
  println(tail) // List(2,3)

  // bid idea #4 - partial function
  val mappedList = list.map{
    case v if v % 2 == 0 => v+ " is even"
    case 1 => "the one"
    case _ => "smth else"
  } // partial function literal
  // equivalent to
  val mappedList2 = list.map{x => x match {
      case v if v % 2 == 0 => v + " is even"
      case 1 => "the one"
      case _ => "smth else"
    }
  }
  // works like normal mapping - puts those results in the list
  println(mappedList) // List(the one, 2 is even, smth else, 4 is even)

}

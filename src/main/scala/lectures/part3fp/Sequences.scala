package lectures.part3fp

import scala.util.Random

object Sequences extends App {

  // SEQUENCES
  val aSequence = Seq(1,2,3,4)
  println(aSequence) // List(1,2,3,4) <- because Seq has an apply method that can create a subclasses
  // but declared type of sequence is Seq[A]

  println(aSequence.reverse)
  println(aSequence(2)) // this is get by index
  println(aSequence ++ Seq(5,6,7))
  println(aSequence.sorted) // if data is numerical or comparable (by alphabet) then sorted works out of the box
  // it works by default when the type is ordered

  // Ranges
  val aRange: Seq[Int] = 1 to 3
  aRange.foreach(println)

  // here is 1, 2 because 3 is not included
  val aRangeNonInclusive: Seq[Int] = 1 until 3
  aRangeNonInclusive.foreach(println)

  // ranges are super cool
  // for example if you want to do smth 5 times
  (1 to 5).foreach(x => println("HI"))

  // List - consists of:
  // abstract class List
  // object Nil extends List[Nothing] <<- empty object
  // class :: extends List[A]  <<- this is a list
  val aList = List(1,2,3)
  // prepending and appending
  val prepended = 42 :: aList
  println(prepended)
  //the same as
  println(::.apply(42, aList))
  // or like this
  println(42 +: aList)

  val appended = aList :+ 42
  println(appended)


  val apples5: Seq[String] = List.fill(5)("apple") // fill is a curried function that creates a list with n places and puts there an element
  println(apples5)

  println(aList.mkString("-|-")) // creates an string with elements concatenated with our string we provided in the argument

  // ARRAYS
  // arrays can be mutated in place
  // mapped over Java's native arrays

  val numbers = Array(1,2,3,4)
  val treeElements = Array.ofDim[Int](3) // allocates space for 3 elements without providing those elements. We need to provide a type to it
  println(numbers)
  println(treeElements)
  treeElements.foreach(println) // there are default values

  // mutation
  numbers(2) = 0 // updates value at index to, to number 0
  //      ^ syntax sugar for numbers.update(2,0)  <- update is a special method like apply(), that allows for syntactic sugar
  println(numbers.mkString(" "))

  // arrays and seq
  // implicit conversion
  val numberSeq: Seq[Int] = numbers // numbers is an Array[Int], which is completely unrelated to type Seq
  // Array is a direct mapping over Java's native array. Despite this conversion can be applied
  println(numberSeq) // this is ArraySeq
  // this conversion is possible because of implicit conversion - advanced topic
  // thanks to this, arrays have access to Seq methods, eg. numbers.mkString()

  // Vectors
  // very fast immutable structure
  // good performance with big data

  val vector: Vector[Int] = Vector(1,2,3)
  println(vector)
  // and all the other collection functionality ...

  // vectors vs lists <- performance test
  val maxRuns = 1000
  val maxCapacity = 1_000_000
  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new Random
    val times = for {
      it <- 1 to maxRuns
    } yield { // yields can have their own blocks of code which is so cool <333
      val currentTime = System.nanoTime()
      collection.updated(r.nextInt(maxCapacity), r.nextInt()) // we put a random value at a random index (index between 0 and maxCapacity)
      System.nanoTime() - currentTime
    }
    // we return sum of times and divide it by number of runs
    times.sum * 1.0 / maxRuns
  }

  val numbersList = (1 to maxCapacity).toList
  val numbersVector = (1 to maxCapacity).toVector

  // advantage of a list -> keep reference to tails
  // disadvantage: updating an element in the middle takes long time
  println(getWriteTime(numbersList)) // 2.9539149531E7
  // advantage of vector -> depth of the tree is small
  // disadvantage: on update needs to replace an 32-entire chunk
  println(getWriteTime(numbersVector)) // 3961.624
  
  // VECTORS ARE MUCH FASTER
  // vector is the default implementation of sequence

}

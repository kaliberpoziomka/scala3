package excercises

import scala.math.Integral.Implicits.infixIntegralOps
import scala.reflect.ClassManifestFactory.{Any, Nothing}

abstract class MyList1[+A] {
  // single linked list generic implementation

  /*
    head = first element of the list
    tail = remainder of the list
    isEmpty = is this list empty
    add(int) => new list with this element added
    toString => a string representation
   */
  def head: A // value of first element of a list
  def tail: MyList1[A] // last object
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList1[B] // this makes MyList1 immutable list
  def printElements: String
  // polymorphic call - printElements is polymorphic call
  override def toString: String = s"[${printElements}]"

  // higher order-functions -> funcs which get func as a parameter or returns func
  def map[B](transformer: A => B): MyList1[B]
  def flatMap[B](transformer: A => MyList1[B]): MyList1[B]
  def filter(tester: A => Boolean): MyList1[A]
  // concatenation method
  def ++[B >: A](list: MyList1[B]): MyList1[B]

  // HOFs
  def foreach(f: A => Unit): Unit
  def sort(compare: (A, A) => Int): MyList1[A]
  def zipWith[B,C](list: MyList1[B], zip: (A, B) => C): MyList1[C]
  def fold[B](start: B)(operator: (B, A) => B): B
  def fold2[B](start: B, operator: (A, B) => B): B
}

case object Empty1 extends MyList1[Nothing] { // Empty extends MyListGeneric with concrete type of Nothing
  // because Nothing is a sub-type of every type
  def head: Nothing = throw new NoSuchElementException // head method does not make sense in empty list, so we throw exception
  def tail: MyList1[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true

  def add[B >: Nothing](element: B): MyList1[B] = new CustomList1(h = element, t = Empty1)

  override def printElements: String = ""

  def map[B](transformer: Nothing => B): MyList1[B] = Empty1
  def flatMap[B](transformer: Nothing => MyList1[B]): MyList1[B] = Empty1
  def filter(tester: Nothing => Boolean): MyList1[Nothing] = Empty1
  def ++[B >: Nothing](list: MyList1[B]): MyList1[B] = list

  // HOFs
  override def foreach(f: Nothing => Unit): Unit = () // () is Unit
  override def sort(compare: (Nothing, Nothing) => Int): MyList1[Nothing] = Empty1
  override def zipWith[B, C](list: MyList1[B], zip: (Nothing, B) => C): MyList1[C] = {
    if (!list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else Empty1
  }
  override def fold[B](start: B)(operator: (B, Nothing) => B): B = start
  override def fold2[B](start: B, operator: (Nothing, B) => B): B = start

//  override def fold[B](start: B)(f: (x: Int, y: Int) => Int): (Int, Int) => Int = (x: Int, y: Int) => f(x, y)
}

case class CustomList1[+A](h: A, t: MyList1[A]) extends MyList1[A] { // this will be elements of the list
  def head: A = h
  def tail: MyList1[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList1[B]= new CustomList1(h = element, t = this)
  def printElements: String = { // again recursive iteration over elements
    if(t.isEmpty) "" + this.head  // end condition - return head of last element
    else this.head.toString + ", " + (this.tail).printElements  // return string concatenated from this head and head from tail object
  }

  def filter(predicate: A => Boolean): MyList1[A] = {
    if(predicate(h)) new CustomList1(h, t.filter(predicate))
    else t.filter(predicate)
  }
  /*
    How filter works: // pseudocode
    [1,2,3].map(n % 2 == 0) =
    // test for 1 not passed, so else statement with tail t
    = t.filter([2, 3]) =
    // test for 2 passed so if statement
    = new CustomList1(2, t.filter(new MyPredicate[Int] = override def test(elem: Int): Boolean = elem % 2 == 0)) =
    // test for 3 not passed, so else statement with tail t, but we have new CustomList1 with h = 2
    // t of 3 is Empty1 object
    = new CustomList1(2, Empty1.filter(new MyPredicate[Int] = override def test(elem: Int): Boolean = elem % 2 == 0)) =
    // filter method of Empty object returns Empty1 obj
    = new CustomList1(2, Empty1)
    // filtered list is [2]
   */

  def map[B](transformer: A => B): MyList1[B] = {
    new CustomList1(h = transformer(h), t.map(transformer))
  }
  /*
    How map works:
    [1, 2].map(n * 2) =
    = new CustomList1(2, [2].map(n * 2)) =
    = new CustomList1(2, new CustomList1(4, Empty1.map(n * 2))) =
    = new CustomList1(2, new CustomList1(4, Empty1))) // [2, 4]

   */

  def ++[B >: A](list: MyList1[B]): MyList1[B] = {
    new CustomList1(h, t ++ list)
  }
  /*
    How ++ works:
    [1, 2] ++ [3, 4, 5] =
    = new CustomList1(1, [2] ++ [3, 4, 5] =
    = new CustomList1(1, new CustomList1(2, Empty1 ++ [3, 4, 5])) =
    // ++ in Empty1 obj returns list
    = new CustomList1(1, new CustomList1(2, new CustomList1(3, new CustomList(4, new CustomList1(5, Empty1)))))
   */

    def flatMap[B](transformer: A => MyList1[B]): MyList1[B] = {
      transformer(h) ++ t.flatMap(transformer)
    }
  /*
    How flatMap works:
    [1, 2].flatMap(n => [n, n+1]) =
    = [1, 2] ++ [2].flatMap(n => [n, n+1]) =
    = [1, 2] ++ [2, 3] ++ Empty.flatMap(n => [n, n+1]) =
    = [1, 2] ++ [2, 3] ++ Empty =
    = [1, 2, 3, 4]
   */

  // HOFs
  def foreach(f: A => Unit): Unit = {
    f(this.head)
    this.tail.foreach(f)
  }

  // my implementation
//  override def foreach(f: A => Unit): MyList1[A] = {
//    if (this.tail.isEmpty) {
//      f(this.head)
//      this
//    }
//    else {
//      f(this.head)
//      this.tail.foreach(f)
//    }
//  }

//  override def sort(compare: (A, A) => Int): MyList1[A] = {
//    def insert(x: A, sortedList: MyList1[A]): MyList1[A] = {
//      if (sortedList.isEmpty) new CustomList1(x, Empty1)
//      else if (compare(x, sortedList.head) <= 0) new CustomList1(x, sortedList)
//      else new CustomList1(sortedList.head, insert(x, sortedList.tail))
//    }
//    val sortedTail = this.tail.sort(compare)
//    insert(this.head, sortedTail)
//
//  }

// my implementation
//  override def sort(f: (x: A, y: A) => Int): MyList1[A] = {
//    try{
//      this.tail.head
//      if ((f(this.head, this.tail.head) >= 0) &&) {
//        new CustomList1(this.tail.head, this.tail.sort(f))
//      }
//      else new CustomList1(this.head, this.tail.sort(f))
//    } catch {
//      case e: NoSuchElementException => this
//    }
//
//
//  }

  // sort
  override def sort(compare: (A, A) => Int): MyList1[A] = {

    def insert(x: A, listSorted: MyList1[A]): MyList1[A] = {
      // first statement in recursion is to check if the next element is empty
      // if it is, return first element of the list
      if (this.tail.isEmpty) new CustomList1[A](x, Empty1)
      // next we compare the current number (this.head) with a first element of a sorted list (tailSorted.head)
      // we use a function that we pass. From that function it depends if the list will be ascending or descending
      else if (compare(x, listSorted.head) <= 0) { // if comparison func output lower than 0, then we go here
        new CustomList1[A](x, listSorted)
        // how the list will be sorted depends on the function:
        // 1. (x,y) => x-y   <-  ascending order: here if x is smaller, then it goes on the left and this is good order
        //                       if x would be bigger, it would go to the else statement
        // 2. (x,y) => y-x   <-  descending order: if x is smaller then we go to te else statement
        //                       if x is bigger, we put it on the left
        // because we use subtraction, we use condition <= 0, so we know which way to sort. That is like a main comparison statement.
      } else {
        // if the first statement is not true, that means, that the first element of the sorted list must be to the left, and then we compare rest of the list
        new CustomList1[A](listSorted.head, insert(x, listSorted.tail))

      }
    }

//    val tailSorted = this.tail.sort(compare) // here we go to the end of list (or to the bottom). We assume that this is sorted, because empty is always sorted
//    // we also made a recursive call, so all the numbers are going to pop from this recursion

//    insert(this.head, tailSorted) // here we insert number to sorted list, we add this number either to beginning or at the end
//    // last line cannot be function definition
    insert(this.head, this.tail.sort(compare)) // this is actually more readable for me
  }

  // my implementations very similar to the instructors implementation
  // mine did not have an exception
//  override def zipWith[B >: A](list: MyList1[B], func: (A, B) => B): MyList1[B] = {
//    new CustomList1[B](func(this.head, list.head), this.tail.zipWith(list.tail, func))
//  }

  override def zipWith[B, C](list: MyList1[B], zip: (A, B) => C): MyList1[C] = {
    if (list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else new CustomList1[C](zip(this.head, list.head), this.tail.zipWith(list.tail, zip))
  }


  // my implementation of fold
  //  override def fold[B](start: B)(f: (x: Int, y: Int) => Int): Int = {
  ////    def foldHelper[B](start: B, accumulator: Int = 0)(f: (x: Int, y: Int) => Int): (Int, Int) => Int = {
  ////      if(this.head == start) {
  ////        (x, y) => foldHelper(this.tail.head, f(x,y))(f)
  ////      }
  ////      else foldHelper(this.tail.head)(f)
  ////    }
  ////    foldHelper(0, 0)
  //    if(start == 0) (this.head: Int, : Int) => f(x, y)
  //    else this.tail.fold(start-1)(f)
  //  }
  override def fold[B](start: B)(operator: (B, A) => B): B = {
    this.tail.fold(operator(start, this.head))(operator)
  }
// my trening to understand
//  override def fold[B](start: B)(operator: (B, A) => B): B = {
//    // this.head must be a second argument, because types in operator must be (B, A) => B
//    val newStart = operator(start, this.head)
//    this.tail.fold(newStart)(operator)
//  }
  /*
  how fold works:
  [1,2,3].fold(0)(_+_) =
  = [2,3].fold(0+1)(_+_) =
  = [3].fold(0+1+2)(_+_) =
  = [].fold(0+1+2+3)(_+_) = // empty returns just a start value
  = 6
  */

  override def fold2[B](start: B, operator: (A, B) => B): B = {
    this.tail.fold2(operator( this.head, start), operator)
  }


}

//trait MyPredicate[-T] {
//  def test(elem: T): Boolean
//}
//
//trait MyTransformer[-A, B] {
//  def transform(elem: A): B
//}

object ListTest1 extends App {
  // I should have the possibility to create empty elements of a list
  val listOfIntegers: MyList1[Int] = new CustomList1[Int](1, new CustomList1[Int](2, new CustomList1[Int](3, Empty1)))
  val cloneListOfIntegers: MyList1[Int] = new CustomList1[Int](1, new CustomList1[Int](2, new CustomList1[Int](3, Empty1)))
  val anotherListOfIntegers: MyList1[Int] = new CustomList1[Int](4, new CustomList1[Int](5, Empty1))
  val listOfStrings: MyList1[String] = new CustomList1[String]("Hello", new CustomList1[String]("Scala", Empty1))
  val listEmpty: MyList1[Int] = Empty1
  println(Empty1.isEmpty)



  println(listOfIntegers.toString)
  println(listOfStrings)

  println(listOfIntegers.map(new Function[Int, Int] {
    override def apply(elem: Int): Int = elem * 2
  }))
  // the same with lambda
  println(listOfIntegers.map(elem => elem * 2))
  // even shorter notation
  println(listOfIntegers.map(_ * 2))

  println(listOfIntegers.filter(new Function[Int, Boolean] {
    override def apply(elem: Int): Boolean = elem % 2 == 0
  }))
  // the same with lambda
  println(listOfIntegers.filter(elem => {elem % 2 == 0}))
  // even shorter notation
  println(listOfIntegers.filter(_ % 2 == 0))

  println(listOfIntegers ++ anotherListOfIntegers)
  println(listOfIntegers.flatMap(new Function[Int, MyList1[Int]] {
    override def apply(elem: Int): MyList1[Int] = new CustomList1[Int](elem, new CustomList1[Int](elem + 1, Empty1))
  }))
    // the same wth lambda
    println(listOfIntegers.flatMap(elem => new CustomList1[Int](elem, new CustomList1[Int](elem + 1, Empty1))))
  // here it wont work
  println(listOfIntegers == cloneListOfIntegers) // true because it is instance of case class and data inside is the same

  println("foreach method:")
  listOfIntegers.foreach(println)

  println("sort method:")
  println(listOfIntegers.sort((x,y) => x-y))

  println("zipWith method:")
  println(listOfIntegers.zipWith(listOfIntegers, (x: Int, y: Int) => x + y))
  println(listOfIntegers.zipWith(listOfIntegers, _ * _)) // also valid
  println(listOfStrings.zipWith[String, String](listOfStrings, _ + "-" + _))
  println(anotherListOfIntegers.zipWith(listOfStrings, _ + ":" + _))

  println("fold method:")
  println(listOfIntegers.fold(10)(_-_))
  val someFoldBase = listOfIntegers.fold(10)
  println(someFoldBase(_*_))

  println("fold2 method:")
  println(listOfIntegers.fold2(10, _+_))

  // for comprehensions works for our custom list, because we have a right functions signatures to do this (signatures are in documentation)
  // so if we want that out own collections support for-comprehension, we need to have a right functions signatures
  val combinations = for {
    n <- listOfIntegers
    string <- listOfStrings
  } yield n + "-" + string
  println(combinations)


}
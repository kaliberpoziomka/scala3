package excercises

import scala.runtime.Nothing$

abstract class MyList {
  /*
    head = first element of the list
    tail = remainder of the list
    isEmpty = is this list empty
    add(int) => new list with this element added
    toString => a string representation
   */
  def head: Int // value of first element of a list
  def tail: MyList // last object
  def isEmpty: Boolean
  def add(element: Int): MyList // this makes MyList immutable list
  def printElements: String
  // polymorphic call - printElements is polymorphic call
  override def toString: String = s"[${printElements}]"
}

object Empty extends MyList { // objects can extends classes
  def head: Int = throw new NoSuchElementException // head method does not make sense in empty list, so we throw exception
  def tail: MyList = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add(element: Int): MyList = new CustomList(h = element, t = Empty)

  override def printElements: String = ""
}

class CustomList(h: Int, t: MyList) extends MyList { // this will be elements of the list
  def head: Int = h
  def tail: MyList = t
  def isEmpty: Boolean = false
  def add(element: Int): MyList = new CustomList(h = element, t = this)
  def printElements: String = { // again recursive iteration over elements
    if(t.isEmpty) "" + this.head  // end condition - return head of last element
    else this.head + " " + (this.tail).printElements  // return string concatenated from this head and head from tail object
  }
}

object ListTest extends App {
  val list = new CustomList(1, Empty)
  println(list.head)
  val list2 = new CustomList(1, new CustomList(2, new CustomList(3, Empty)))
  println(list2.tail.tail.head)

  println(list.add(4).head)

  println(list.toString)
  println(list2.toString)

  val list3 = new CustomList(1, Empty).add(2).add(3).add(4) // not ideal because this makes reversed list
  println(list3.toString)
}
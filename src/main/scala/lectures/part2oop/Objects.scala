package lectures.part2oop

object Objects extends App {
  // class level functionality - functionality that does not depend on an instance of a class
  // SCALA DOES NOT HAVE CLASS-LEVEL FUNCTIONALITY ("static")
  // Scala does have Object - which is class-level-like functionality
  object Person { // here we defined type of object, and it's only instance
    // object can have val, var, method definitions
    // objects can be defined the same way as classes, but the do not have parameters
    val N_EYES = 2
    def canFly: Boolean = false

    // FACTORY method - that builds persons
    def from(mother: Person, father: Person): Person = new Person("Bobby")
    // but we can create an apply method from this
    def apply(mother: Person, father: Person): Person = new Person("Bobby")

  }
  println(Person.N_EYES)
  println(Person.canFly)

  // Scala object is an SINGLETONE INSTANCE = when we define object, we also define its only instance
  val mary = Person
  val john = Person
  println(mary == john) // mary and john point to the same instance - Person object, but Person object instance is only ONE

  // Common practise is to write a class with the same name as object in the same file
  // to separate instance-level functionality (defined in class)
  // from "static"/"class"-level functionality (defined in object)
  // COMPANIONS - pair of Object and Class in the same scope with the same name
  class Person(val name: String) {

  }
  val mary2 = new Person("Marry")
  val john2 = new Person("John")
  println(mary2 == john2) // false - they are different

  val bobby = Person.from(mary2, john2)
  val bobby2 = Person(mary2, john2) // so now Person singleton object is callable like a function

  // Scala Applications = Scala object with
  // def main(args: Array[String]): Unit
  // we can use main in our objects to make code runnable
}
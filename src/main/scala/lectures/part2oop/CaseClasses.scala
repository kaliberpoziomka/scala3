package lectures.part2oop

object CaseClasses extends App {
  // case classes are to create lightweight data structures with little boilerplate
  // lots of cool features

  case class Person(name: String, age: Int)
  // 1. class parameters are fields
  val jim = new Person("Jim", 34)
  println(jim.name)
  // 2. sensible toString <- nice printing by default
  println(jim)
  // 3. equals and hashCode are implemented out of the box
  val jim2 = new Person("Jim", 34)
  println(jim == jim2) // true, because data is the same, but normal case would return false
  // 4. case classes have handy copy method
  val jim3 = jim.copy(age = 45) // another instance of person, with only different age
  // 5. case classes have companion objects
  val thePerson = Person // this instantiated companion object for case class Person
  // companion objects have handy factory methods
  val mary = Person("Mary", 23) // we don't use "new" keyword to instantiate case class
  // 6. case classes are serializable
  // which makes case classes especially useful with distributed systems:
  // you can send instances of case classes through the network and in between JVMs (AKKA framework)
  // 7. case classes have extractor patterns = case classes can be used in PATTERN MATCHING (PM is one of most powerful features of Scala)

  // there are case objects - they are like case classes but ofc they are objects
  case object UnitedKindgom { // they only don't get companion obects - because they are companion objects
    def name: String = "The UK of GB and NI"
  }
}

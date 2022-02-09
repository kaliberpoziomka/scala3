package lectures.part2oop

object Generics extends App {
  class MyList[A] // GENERIC CLASS
    // you can use A as any type

  val listOfIntegers = new MyList[Int]
  val listOfStrings = new MyList[String]

  class MyMap[Key, Value] // multiple generic parameters

  // this is also applicable to traits
  trait MyTraitList[A]

  // GENERIC METHODS
  object MyList {
    def empty[A]: MyList[A] = ??? // we can build a function that works on this generic type A
  }
  // usage of generic method
  val emptyListOfIntegers = MyList.empty[Int] // this will return MyList of Int

  // variance problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal
  // problem: If a Cat extends Animal, does the List of Cat also extends a List of Animal
  // 1 answer: YES - List[Cat] extends List[Animal] <- this is called COVARIANCE
  // declaration of covariant list:
  class CovariantList[+A] // +A means that it is COVARIANT List
  val animal: Animal = new Cat // the same as we used POLYMORPHISM with classes
  val animalList: CovariantList[Animal] = new CovariantList[Cat] // we can use COVARIANCE with Lists
  // once I created a CovariantList of Animal, can I add any Animal to it?
  // can we animalList.add(new Dog) ?? HARD QUESTION => answer is below in the MyList2 example, it return list o Animals, which is a super-class

  // 2 answer: NO <- this is called INVARIANCE
  // example of invariant list
  class InvariantList[A] // MyList[A] is also an invariant list
  // here if we define something to be an animal, we cannot create an object with subclass
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal] // we have to instantiate the same type of list as variable

  // 3 answer: HELL NO! <- CONTRAVARIANCE
  // contravariant list example
  class ContravariantList[-A]
  val contravariantList: ContravariantList[Cat] = new ContravariantList[Animal]
  // contravariance does not make sense with list example, but Trainer will make sense

  // if I want a Trainer of cat, I can take trainer of animals, so he can be trainer of dogs, dinosaurs, but also trainer of cats
  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]

  // BOUNDED TYPES
  // bounded types allow you to use generic classes only for certain types,
  // that are either a subclass of a different type, or superclass of a different type
  // UPPER BOUNDED TYPE
  class Cage[A <: Animal](animal: A) // class cage only accepts parameter A, which are subtypes of Animal or Animal
  val cage = new Cage(new Dog) // Dog is acceptable type of parameter to Cage, because Dog is an subtype of Animal

  // LOWER BOUNDED TYPE
  class Cage2[A >: Animal](animal: A) // acceptable are super types of Animal

  // Bounded types resolve big problem which is in Covariance
  // example
  // this example resolves question: what if we add to a list of Cat a Dog <- this will be the list of Animals then
  // here is implementation
  class MyList2[+A] { // covariant generic type
    def add[B >: A](element: B): MyList2[B] = ??? // if to a list of A we add B, which is super-type of A, then this list is a list of B
    /*
      A = Cat
      B = Dog (instance of a class Dog, but type is Animal - Polymorphism)
      if we as to a list of Cats a Dog, then this list will be the list of Animals
     */
  }

  // Excercise - expand MyList to be generic

}

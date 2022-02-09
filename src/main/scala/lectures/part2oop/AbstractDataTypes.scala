package lectures.part2oop

object AbstractDataTypes extends App {

  // ABSTRACT CLASSES
  // classes which contain unimplemented or abstract fields or methods
  // they are meant to be extended
  abstract class Animal {
    val creatureType: String
    def eat: Unit
  }

  class Dog extends Animal {
    override val creatureType: String = "Canine"
    def eat: Unit = println("crunch crunch") // we do not need to write "override" keyword, because compiler knows that Animal is an abstract class
  }

  // TRAITS
  // traits by default have abstract fields and methods
  // but unlike abstract classes, traits can be inherited by classes without limit
  // so class can inherit many traits
  trait Carnivore { // this trait describe what other animals can this animals can eat
    def eat(animal: Animal): Unit
    val preferredMeat: String = "fresh meat"
  }

  class Crocodile extends Animal with Carnivore { // class Crocodile inherits from Animal ab class and Carnivore trait
    override val creatureType: String = "croco" // field from Animal abstract class
    def eat: Unit = "omnomnom"  // method from Animal abstract class
    def eat(animal: Animal): Unit = println(s"I'm a croc and I'm eating ${animal.creatureType}")  // method from Carnivore trait
  }
  val dog = new Dog
  val crocodile = new Crocodile
  crocodile.eat(dog)

  // ABSTRACT CLASS AND TRAIT
  // abstract class and trait can have abstract and non-abstract members
  // differences:
  // 1. Traits do not have constructor parameters
  // 2. You can only extend one class, but you can inherit multiple traits
  // 3. We choose Trait before abstract class when it describes a type of behavior
  //    Trait = Behavior
  //    Abstract Class = Thing

}

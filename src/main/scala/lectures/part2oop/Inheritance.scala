package lectures.part2oop

object Inheritance extends App {

  class Animal {
    val creatureType = "wild"
    val color = "black"
    def eat = println("nomnom")
    private def privateEat: Unit = println("omnomnom")
    protected def protectedEat: Unit = println("omnomnom")
    def makeSound: Unit = println("grr")
    final def notOverrideMethod = println("You cannot override this")

  }
  // Cat subclass, Animal superclass
  class Cat extends Animal {
    def catDinner = {
//      privateEat <- cannot call private method
      protectedEat // <- this works because protected method can be instantiated in subclass
    }
  }
  // single class inheritance = you can inherit one class at the time

  val cat = new Cat
  cat.eat
  cat.catDinner

  // constructors
  class Person(name: String, age: Int = 0) {
//    def this(name: String) = this(name, 0) // auxiliary constructor
  }
  class Adult(name: String, age: Int, idCard: String) extends Person(name) // here we must provide arguments in class that we extend from

  // OVERRIDING
  class Dog(override val color: String) extends Animal { // we can override value in constructor
    override def protectedEat: Unit = println("crunch crunch") // we can override function from super class (even if it is protected)
    override def eat: Unit = println("CRUNCH CRUNCH")
    override val creatureType: String = "domestic" // overriding works for methods as well as for vals and vars

    override def makeSound: Unit = {
      println("szczek szczek")
      super.makeSound
    }
  }
  val dog = new Dog("yellow")
  dog.protectedEat // it is not protected now (because we override it without protected operator)
  println(dog.creatureType)
  println(dog.color)

  // another overriding technique
  class Dog2(dogType: String) extends Animal {
    override val creatureType: String = dogType // this is also valid, the same as in Dog
  }

  // TYPE SUBSTITUTION = POLYMORPHISM
  // power of polymorphism - we can use type Animal, but use functionality of a Dog.
  // The method call will always go to the most overridden method available:
  // so Animal eat func prints "nomnom" but on Dog it is overridden to "crunch crunch"
  val unknownAnimal: Animal = new Dog("blue")
  unknownAnimal.eat
  println(unknownAnimal.color)

  // overRIDING - supplying different implementation (of methods and fields) in derived classes
  // overLOADING - supplying multiple methods with different signatures, but in the same name and in the same class

  // SUPER
  // super is used when you want to reference field or method from the parent class
  dog.makeSound

  // PREVENTING OVERRIDES
  // 1) use the keyword "final" before method, field or class
  //    when you use final before method or field you cannot override them,
  //    but when you use final before class then you cannot extend class
  //    final classes: Numeric, String ...
  // 2) use the keyword "sealed"
  //    seal the class - you can extend class in this file, but cannot extend in other files




}

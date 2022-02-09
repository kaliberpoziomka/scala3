package lectures.part2oop

object AnonymousClasses extends App {

  abstract class Animal {
    def eat: Unit
  }
  // Anonymous Class
  val funnyAnimal: Animal = new Animal { // we instantiated abstract class with overriding its fields?? No, compiler created Anonymous Class
    override def eat: Unit = println("HAHAHAHAHA")
  }
  println(funnyAnimal.getClass)
  // when we created funnyAnimal, compiler created class:
  // class lectures.part2oop.AnonymousClasses$$anon$1
  // and then instantiated it and assigned to funnyAnimal

  // Compiler did something like:
  // class AnonymousClasses$$anon$1 extends Animal {
  //  override def eat: Unit = println("HAHAHAHAHA")
  // }

  // Anonymous Classes work for abstract and non-abstract classes

  class Person(name: String) {
    def sayHi: Unit = println(s"Hi, my name is $name, how can I help?")
  }

  val jim = new Person(name = "Jim") { // you have to provide parameters to Anonymous Class constructor
    override def sayHi: Unit = println(s"Hi, I'm Jim")
  }

  // and Anonymous Classes also work with traits

  /*
    1. Generic Trait MyPredicate[-T] => method with small test on a value
      method test(t): Boolean

      class EvenPredicate extends MyPredicate[Int]
    2. Generic trait MyTransformer[-A, B] => transform type A to type B, every subclass will have different implementation of that method
       with a method transform(A): B

      class StringToIntTransformer extends MyTransformer[String, Int]
    3. MyList:
      - map(transformer) => MyList[B]
      - filter(predicate) => MyList
      - flatMap(transformer from A to MyList[B]) => MyList[B]


   */

  // 1.
  trait MyPredicate[T] {
    def test[T]: Boolean
  }
  class EvenPredicate(n: Int) extends MyPredicate[Int] {
    def test[Int]: Boolean = n % 2 == 0
  }
  println((new EvenPredicate(10)).test)

  class EvenPredicateFloat(n: Float) extends MyPredicate[Float] {
    def test[Float]: Boolean = n % 2f == 0f
  }
  println((new EvenPredicateFloat(15)).test)

  // 2.
  trait MyTransformer[A, B] {
    def transform[A]: B
  }
  class StringToIntTransformer(s: String) extends MyTransformer[String, Int] {
    override def transform[String]: Int = s.toInt
  }
  println((new StringToIntTransformer("1")).transform)
}

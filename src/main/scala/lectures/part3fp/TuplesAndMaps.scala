package lectures.part3fp

import scala.annotation.tailrec

object TuplesAndMaps extends App {

  // tuples = finite ordered "lists"
  val aTupleLongerVersion = Tuple2[Int, String](2, "hello, scala") // Tuple2[Int, String] = (Int, String)
  val aTuple = (2, "Hello, scala") // the same as in python!

  println(aTuple._1) //first element
  println(aTuple.copy(_2 = "goodbye Java"))
  println(aTuple.swap) // ("hello, scala", 2)

  // Maps - key -> value
  val aMap: Map[String, Int] = Map() //constructor like a list

  val phonebook = Map( //we can specify tuples or pairings
                  ("Jim", 555), // tuple
                  "Daniel" -> 789 // paring
                  ).withDefaultValue(-1) //if we will want to access an element that is not in map, then it returns -1. Otherwise it would throw Exception
  println(phonebook)

  // map ops
  println(phonebook.contains("Jim"))
  println(phonebook("Jim"))
  println(phonebook("Marry"))

  // ad a pairing
  val newPairing = "Mary" -> 678
  val newPhonebook = phonebook + newPairing // this returns new Map, because maps are immutable
  println(newPhonebook)

  // functionals on maps
  // map, flatMap, filter

  println(phonebook.map(pair => pair._1.toLowerCase -> pair._2)) // map takes a pairing

  println(phonebook.map(x => x._2 * 10))

  // filters
  println(phonebook.filter(x => x._1.startsWith("Jim")))

  // conversions
  println(phonebook.toList)
  println(List(("Daniel", 555)).toMap)

  // groupBy
  val names = List("Bob", "James", "Angela", "Mary", "Jim")
  println(names.groupBy(name => name.charAt(0))) // groups a list by the character at position 0
  println(names.groupBy(x => x.length))

  /*
  1. What would happen if I had two original entries "Jim" -> 555 and "JIM" -> 900 in toLowerCase
  2. Simplified social network
    Person = String
    - add a person to the network
    - remove a person
    - friend (mutual)
    - unfriend (mutual)
    - number of friends
    - person with most friends
    - how many people have no friends
    - if there is social connection between two people (direct or not)
  */

  // 1. duplicates
  val myMap = Map("Jim" -> 555, "JIM" -> 900)
  println(myMap.map(x => x._1.toLowerCase -> x._2)) // just last jim stays in the map

  // 2. social network
  val SocialNetwork: Map[String, Set[String]] = Map()
  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    if((network.filter(_._1 == person)).toList.nonEmpty) println("This user is already added to the network")
    network + (person -> Set())
  }
  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    //auxilary function - for removing this person from all friends lists
    def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] = {
      if(friends.isEmpty) networkAcc
      else removeAux(friends.tail, unfriend(networkAcc, person, friends.head))
    }
    val unfriended = removeAux(network(person), network)
    unfriended - person // minus removes person key entirely
  }
  def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)
    network + (a -> (friendsA + b)) + (b -> (friendsB + a))
  }
  def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)
    network + (a -> (friendsA - b)) + (b -> (friendsB - a))
  }
  def numberOfFriends(network: Map[String, Set[String]], person: String): Int = {
    if (!network.contains(person)) 0
    else network(person).size
  }
  def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int = {
    network.count(pair => network(pair._1).isEmpty)
  }
  def personWithMostFriends(network: Map[String, Seq[String]]): String = {
    // my solution:
//    val maxNumOfFriends = network.map(_._2.size).max
//    val mapToReturn = network.filter(x => x._2.size == maxNumOfFriends)
//    mapToReturn.toList.head._1
    // shorter solution - not working?
    val temp = network.maxBy(pair => pair._2.size) // this will return a first element of a pairing, selected by max value of size of second element af a pairing
    temp._1
  }
  def socialConnection(network: Map[String, Set[String]], a: String, b: String): Boolean = {
    // breadth first search algorithm
    //we want to find b in network starting from a
    @tailrec // tailrec!! because when it is returning itself, it only returns itself without other operations
    def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean ={
      if (discoveredPeople.isEmpty) false
      else {
        val person = discoveredPeople.head
        if (person == target) true
        else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail) // node exists in consideredPeople so we do not new Nodes
        else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person)) // add next layer to the queue
      }
    }

    bfs(b, Set(), network(a) + a)
  }
//  def isThereASocialConnection(network: Map[String, List[String]], person: String, otherPerson: String): Boolean = {}
  val sn = add(SocialNetwork, "Paulinka")
  val sn1 = friend(add(sn, "Krzysiek"), "Paulinka", "Krzysiek")
  // we cannot mutate map records, but we can substitute a pair with a new one
  val sn2 = add(sn1, "Ola") + ("Ola" -> Set("Krzysiek"))


  val empty: Map[String, Set[String]] = Map()
  val people = add(add(add(empty, "Bob"), "Marry"), "Jim")
  val jimBob = friend(people, "Bob", "Jim")
  val testNet = friend(jimBob, "Bob", "Marry")
  println(testNet)
  println(numberOfFriends(testNet, "Bob"))
//  println(personWithMostFriends(testNet))
  println(nPeopleWithNoFriends(testNet))

  val network =
//    friend(
      friend(
        add(add(add(empty, "Bob"), "Marry"), "Jim"),
    "Jim", "Marry")
//    , "Jim", "Bob")
  println(network)
  println(socialConnection(network, "Marry", "Bob")) // they have common friend = Jim










}

package lectures.part2oop



object Enums extends App {

 enum Permissions {
   case READ, WRITE, EXECUTE, NONE


   def openDocument: Unit = {
     if (this == READ) println("opening document...")
     else println("reading not allowed")
   }
 }

 enum PermissionsWithBits(bits: Int) {
   case READ extends PermissionsWithBits(4)
   case WRITE extends PermissionsWithBits(2)
   case EXECUTE extends PermissionsWithBits(1)
   case NONE extends PermissionsWithBits(0)
 }


 object PermissionsWithBits { // you can have here fields from enum with the same name
   def fromBits(bits: Int): PermissionsWithBits = PermissionsWithBits.NONE
 }

  println(PermissionsWithBits.fromBits(4))


  val somePermissions: Permissions = Permissions.READ
  println(somePermissions)
  somePermissions.openDocument

  // standard API of enums
  val somePermissionsOrdinal = somePermissions.ordinal // return number of field that was used
  println(somePermissionsOrdinal)

  // you can see all possible values of the enum
  val allPermissions = PermissionsWithBits.values
  allPermissions.foreach(println)

  val readPermissions: Permissions = Permissions.valueOf("READ") // parsing string, returns Permissions.READ
}
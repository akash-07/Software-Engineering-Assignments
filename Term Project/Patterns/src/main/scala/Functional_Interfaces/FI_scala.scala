package Functional_Interfaces

/**
  * Created by @kash on 9/8/2017.
  */

/* Note: Lambdas have been introduced in Java 8 which are not being used here. This example
 * demonstrates usage of higher order functions over Functional Interfaces design pattern
 * followed in traditional Java programming. Take a look at the Java code in FI_Java.java
 * before peeking into the Scala code below. */

object FI_scala extends App{

  //The same Person class in Java can by realized with these two lines in Scala
  case class Person(firstName: String, middleName: String, lastName: String)  {
    override def toString: String = firstName + " " + middleName + " " + lastName
  }

  val p1 = Person("Akash","B","Dhasade")
  val p2 = Person("Akash","","Cyz")
  val p3 = Person("Saket","D","Joshi")

  // The Functional Equivalent of a interface
  type Comparator = (Person,Person) => Int

  /* Defining the composed Comparator (does the same thing as in Java example earlier).
  * Interestingly the paramater comparators to composedComparison can be another variable in the scope
  * of the composedComparsion rather than a paramater itself. The variable will be bound to
  * the function via this construct called 'closure'. Hence even when we update or modify
  * comparators, the function uses the modified comparators only. This is taken care of by the
  * compiler and the runtime environment. */
  def composedComparison(comparators:Comparator*) =
   (p1: Person, p2: Person) => {
     val ans: Int = comparators.map(cmp => cmp(p1,p2)).find(_ != 0).getOrElse(0)
     ans < 0
   }

  def firstNameComparison(p1: Person, p2: Person) = p1.firstName.compare(p2.firstName)

  /* Defining comparsion by lastName. Notice the type of lastNameComparison. The above function can
   * also be written in the manner below. This is how we are indirectly implementing an interface
   * by defining a type called Comparator and using functions as first class citizens. */
  def lastNameComparison: Comparator = (p1: Person, p2: Person) => p1.lastName.compare(p2.lastName)

  /* Defining function compareByFirstLast using firstNameComparison and lastNameComparison. */
  val compareByFirstLast = composedComparison(firstNameComparison,lastNameComparison)

  var personList = List(p3,p2,p1)
  println("Before sorting: ")
  for (elem <- personList) {
    print(elem + ", ")
  }
  personList = personList.sortWith(compareByFirstLast)
  println("\nAfter sorting: ")
  for (elem <- personList) {
    print(elem + ", ")
  }
}

/*
int x = 101,a=100,b=150;
auto func = [x] (int a, int b) { return (x>a and x<b); };
while(func(a,b))
  x++;
 */
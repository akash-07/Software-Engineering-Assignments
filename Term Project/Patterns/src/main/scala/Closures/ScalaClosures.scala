package Closures

/**
  * Created by @kash on 9/8/2017.
  */
object ScalaClosures extends App{
  var greeting = "Hello"
  /* Notice that the function sayHello depends on String greeting not in the lexical
   * scope of the function. */
  def sayHello(name: String) = println(s"$greeting $name")

  val foo = new Foo
  /* We are passing this function to method sayHello of Foo. */
  foo.sayHello(sayHello,"Akash")

  /* Notice that we are changing the value of greeting and calling sayHello of Foo.
   * Interesting the runtime environment knows the changed value of greeting and the
    * function sayHello makes use of correct value of greeting. The most important
    * difference between this Functional Program in Scala and an equivalent Object Oriented
    * Program in Java demonstrated earlier is that the overhead of binding the function to
    * dependent parameters is taken care of by the compiler and runtime environment unlike Java
    * where we need to explicitly put them in a wrapper class and keeping passing the class
    * around. The hardwork Java programmers need to do maintaining wrappers is evident now. */

  greeting = "Hola"
  foo.sayHello(sayHello,"Sam")
  greeting = "Yo"
  foo.sayHello(sayHello,"Dhoni")
}

/* class Foo has a method named sayHello that takes a function from type String to Unit
 * along with String name. */
class Foo {
  def sayHello(f: String => Unit, name: String)  {
    f(name)
  }
}

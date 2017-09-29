package nullObjectPattern

/**
  * Created by @kash on 9/28/2017.
  */
trait Option[+A] {
  def map[B](f: A => B): Option[B] = this match {
    case Some(a) => Some(f(a))
    case None => None
  }

  def flatMap[B](f: A => Option[B]): Option[B] = map(f) getOrElse(None)

  def getOrElse[B >: A](default: => B):B = this match {
    case Some(a) => a
    case None => default
  }

  def orElse[B >: A](default: => Option[B]):Option[B] = this match {
    case Some(a) => Some(a)
    case _ => default
  }

  def filter(p: A => Boolean): Option[A] = this match {
    case Some(a) => if(p(a)) Some(a) else None
    case None => None
  }
}

case class Some[A](get: A) extends Option[A]
case object None extends Option[Nothing]

object test extends App {
  case class Employee(name: String, department: String, manager: Option[String])
  def lookupByName(name: String): Option[Employee] = name match {
    case "Joe" => Some(Employee("Joe", "Finances", Some("Julie")))
    case "Mary" => Some(Employee("Mary", "IT", None))
    case "Izumi" => Some(Employee("Izumi", "IT", Some("Mary")))
    case _ => None
  }

  def getDepartment: (Option[Employee]) => Option[String] =
    _ map (_.department)

  def getManager: (Option[Employee]) => Option[String] =
    _ flatMap (e => e.manager)

  def getManager(employee: Option[Employee]): Option[String] =
    employee.flatMap(_.manager)
  getDepartment(lookupByName("Joe"))
  getDepartment(lookupByName("Mary"))
  getDepartment(lookupByName("Foo"))
}
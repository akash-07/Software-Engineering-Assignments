### Dealing with Exceptions

**Why no `Exceptions`?**

Let's first understand why we don't want to throw `Exceptions` in our code. 
Throwing exceptions breaks referential transparency. It means that we cannot substitute the 
function body with the function call everywhere in our code. The reason is my function is not a 
total function.

**Total Function:** A function which is defined for all values of it's input. 

**Partial Function:** A function which is not defined for some of the input values.

Let's take an example straightaway. Here is a function to compute the mean of a list which is 
undefined if the list is empty.

```scala
def mean(xs: List[Double]): Double = 
  if(xs.isEmpty) throw new ArithmeticException("Mean of an empty list")
  else xs.sum/xs.length
```

`mean` is a partial function in this context because it is not defined when the list is empty. 
The very fact that `mean` is a function from `List[Double]` to `Double` is itself a **lie**. 
Because the function makes assumptions about it's inputs which are not implied by it's input types. 
This is true for all functions that throw `Exception`s when we look at them from mathematical point of view. The problem 
we are trying to solve is therefore getting rid of `Exception`s from our code.

**What's are all the possible things that we can do?**

1) The first one is to return some arbitrary or Bogus value like 0.0/0.0 ( `Double.NaN`).
2) We could even return a `NULL` value but it is not defined for primitive types. If we can think of 
sentinel values then we must be aware that we cannot produce a sentinel value all of a sudden when we 
look at functions that take type parameters.

For eg. What is the sentinel value in this case?
```scala
max[A](xs: List[A]): (greater: (A,A) => Boolean): A
```
3) Thirdly we are tempted to ask the caller himself for some value that we can return in cases 
where the input is undefined.

```scala
def mean(xs: List[Double], onEmpty: Double): Double = 
  if(xs.isEmpty)  onEmpty
  else xs.sum/ xs.length
```
Even though `mean` is a total function here, it has drawbacks. The callers have to frequently take 
care of passing this extra value everytime they call a very simple `mean` function. It's clearly not 
feasible.

**`Option` data type**

We want to reflect in the output type that an output does not always exist.
We introduce the `Option` type. `Option[A]` can take two values: `Some(A)` or `None`.
When the answer is defined, it will be be `Some(A)` and when it is undefined it will be `None`.

I will just post a skeleton of `Option` type below. The actual implementation is quite similar.

```scala
trait Option[+A] 
case class Some[+A](get: A) extends Option[A]
case object None extends Option[Nothing]
```

Now we can re-look at our `mean` function.

```scala
def mean(xs: List[Double]): Option[Double] = 
  if(xs.isEmpty) None
  else Some(xs.sum/xs.length)
```

What can say about this `mean` function? It's a total function now. Each input value is mapped to 
one output value and the return type reflects the possibility that the result is not always defined.

What are some common occasions where `Option` type is returned? When we ask for `headOption` on a list or when we 
look up into a `Map` for a value by a given key.

**How to best visualize `Option` type?**

We can think of it as a box or a wrapper for our normal data types.The important fact is that the 
box can be empty when the result is undefined but it's still a remains to be a box.

Int ==> Box containing Some(Int) or None 

String ==> Box containing Some(String) or None

The second way to visualize an `Option` type is to think of it as a `List` containing atmost one 
element. We can define some functions which will help us write concise and 
meaningful codes when we have to deal with `Option` type.

I will just give template or a skeleton for these functions and explain how we can use them. 
The implementation details are omitted.

```scala
trait Option[A] {
  def map[B](f: A => B): Option[B]
  def flatMap[B](f: A => Option[B]): Option[B]
  def filter(p: A => Boolean): Option[A]
  def getOrElse[B >: A](default: => B): B
  def orElse[B >: A](other: => Option[B]): Option[B]
}
```

**Functions on `Option` type**

Bear with me for some time. We just need to walk through a few things before we can realize the 
essence of this type in error handling. The task which we are going to see is a sequence of 
computations where any computation in the sequence can fail and affect the next one. We will see how to deal with 
this using the `Option` type. So let's look at few functions.

**`map`**: This function can be used to transform the result inside an `Option` if it exists. We can 
think of this as proceeding with a computation assuming no error has occured. In other words we 
are trying to defer the error to later code. Let us recall the type signature of `map`.

```scala
def map[B](f: A => B): Option[B]
```

I will try to explain this with an example. The following example will be used throughout the 
documentation.

We have a function named `lookupByName` that takes a `name: String` and returns an `Option[Employee]` if 
an Employee with such a name exists. The definiton of `Employee` class is given below. An Employee is 
defined by name, department and manager. Notice that a manager may or may not exist for an employee. 
This is captured by type of `manager: Option[String]`.

```scala
def lookupByName(name: String): Option[Employee] = name match {
  case "Joe" => Some(Employee("Joe", "Finances", Some("Julie")))
  case "Mary" => Some(Employee("Mary", "IT", None))
  case "Izumi" => Some(Employee("Izumi", "IT", Some("Mary")))
  case _ => None
}

case class Employee(name: String, department: String, manager: Option[String])
```
Next what I want is to get the Employee's department from the name of employee. I will 
essentially peek into the table and it will return me the right employee if such an employee 
exists and `None` is it doesn't. 

So my first computation in the sequence of computations is to look for an employee by it's name. My 
second computation would be to get the department of the employee only if my first computation returns some 
employee or else I would like to return `None`. My `map` function is exactly serves the purpose. 
It takes in a function `f: A => B` that takes a value of type `A` and returns a value of type `B`. The only thing which `map` does is, it first unwraps the value from the box, applies `f` and packs it again in the box for me. So essentially it takes care of unwrapping and wrapping for me. 

Visual Metaphor:

Box(having an employee) => take the employee out => get it's department => pack it in a box

Box(empty) => nothing to take out => return empty box

So here is the function in Scala:

```scala
def getDepartment: (Option[Employee]) => Option[String] = _ map (_.department)
```
We run it in a scala interpreter after loading the scripts and see the result. Since an employee 
named Joe exists in the department, we get `Some(Finances)` as the department of Joe. On the other 
hand there is no employee named Foo, so the function returns a `None`. Look how semantically convincing 
functions are these over the functions that throw Exceptions.

```scala
scala> getDepartment(lookupByName("Joe"))
res1: Option[String] = Some(Finances)

scala> getDepartment(lookupByName("Foo"))
res2: Option[String] = None
```

**`flatMap`:** It applies function `f` to `Option` if it is 
not `None` but it may also fail. This failure is captured in the 
type of `f: A => Option[B]` which says `f` might or might not give 
a value of type `B`. Let us recall the type signature of `flatMap`.

```scala
def flatMap[B](f: A => Option[B]): Option[B]
```
Let's see how we can use it. We want to find the manager of an employee. 
Recall that every employee may or may not have a manager. Here is 
the `getManager` method,

```scala
def getManager: Option[Employee] => Option[String] = 
    _ flatMap (e => e.manager)
```





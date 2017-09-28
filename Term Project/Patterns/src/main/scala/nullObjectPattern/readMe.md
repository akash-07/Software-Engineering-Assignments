**The `null` Object Pattern**

`null` values essentially denote absence of an object or absence of an instance of 
some reference type. Suppose we were to call a method on an object  
 of a class where the object is the result of some other computation.
  But it might so happen that we end up 
 getting a `null` value instead of a concrete object. To make sure we don't call methods on 
 `null` values, we end up writing `null` checks through out our code 
 and it becomes messy. A rather general solution would be to 
 create a business meaning-less `null` object. This `null` object 
  specifies the default behaviour in the absence of real objects. The two 
  main benefits here are:
  
  1) We can avoid scattering null checks throughout our code which keeps 
  our code clean and easier to read.
  2) We can centralize the logic that deals with handling absence of 
  null value.
  
  As an example, we are to lookup a `Map` from integers to `Person` by an `id`. 
   So we have an interface for a Person that defines some methods and we 
   have two implementations of the interface, `RealPerson` and `NullPerson`. 
   In the case where the lookup returns `null`, we return an instance of 
   `NullPerson` which defines how the methods should behave by default. 
   Also we have a builder method for a `Person` which takes first name 
   and last name and returns an instance of the Person. We also want 
   to handle the case where either of the first name or last name strings 
   are empty, in which case we return a an instance of `NullPerson`.
   
   ```java
    abstract class Person {
        protected String firstName;
        protected String lastName;
        public String getName() {
            return this.firstName + " " + this.lastName;
        }
    }
    
    /* Actual class for Person.*/
    class RealPerson extends Person{
        RealPerson(String firstName, String lastName){
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
    
    /* Default class which defines default behaviour.*/
    class NullPerson extends Person  {
        NullPerson() {
            this.firstName = "John";
            this.lastName = "Parker";
        }
    }
    
    class PersonExample {
        Map<Integer,Person> personMap;
    
        PersonExample(Map<Integer,Person> personMap) {
            this.personMap = personMap;
        }
    
        /*When we don't find a person mapped to given key, we return NullPerson(). */
        public Person fetchPerson(int id)   {
            Person p = personMap.get(id);
            if(p == null)  return new NullPerson();
            else return p;
        }
    
        /*If any of first name or last name is null we return a NullPerson().*/
        public Person buildPerson(String firstName, String lastName)    {
            if(firstName.equals("") || lastName.equals(""))
                return new NullPerson();
            else
                return new RealPerson(firstName, lastName);
        }
    }
```

**Functional Replacement**

We always strive for totality in Functional World. By totality, we mean 
that the functions are total, they are defined for all values of input. 
We are going to handle the absence of a value in a type-safe manner with 
the help of `Option` type in Scala, equivalent to `Maybe` type of haskell. 
`Option[A]` can take two values, `Some(a)` or `None` where `a` is of type `A`. 
We can look at `Option` type in this way: For all types in real world, there 
exists a parallel world of `Option`s. A string `"hello"` in real world is 
`Some("hello")` in the parallel world. Any non existent value in real world 
is defined to be `None` in the parallel world. `Option` can be thought of as 
a container which can have atmost one value. A function that returns an 
`Option[A]` can be thought of as a function which might return a value of 
type `A`, but in some cases it can return `None` signifying non 
existence of a value in a type-safe manner. 

To work out the above example in Scala, let's define a `case class Person` in repl.

```scala
scala> case class Person(firstName: String = "John", lastName: String = "Parker")
defined class Person
```

Notice the default values have been defined for `firstName` and `lastName`. 
Next let's define few persons and a mapping from `Int` to `String` called `personMap`. 

```scala
scala> val p1 = Person("Akash","Dhasade")
p1: Person = Person(Akash,Dhasade)

scala> val p2 = Person("Saket","Joshi")
p2: Person = Person(Saket,Joshi)

scala> val p3 = Person("Tejashree")
p3: Person = Person(Tejashree,Parker)

scala> val personMap = Map(1 -> p1, 2 -> p2, 3 -> p3)
personMap: scala.collection.immutable.Map[Int,Person] = 
Map(1 -> Person(Akash,Dhasade), 2 -> Person(Saket,Joshi), 3 -> Person(Tejashree,Parker))
```

We have the `getOrElse` method defined on `Option` type as well as 
for `Map`. If the value is not `None`, then `getOrElse` pulls out the or 
unwraps the value from `Some` and if it is `None`, it results in the 
 default value provided as an arguement. For `Map`, we can use it as 
 follows:
 
 ```scala
scala> personMap.getOrElse(2,Person())
res2: Person = Person(Saket,Joshi)

scala> personMap.getOrElse(5,Person())
res3: Person = Person(John,Parker)
```

Since `personMap` did not have any value corresponding to key 5, we 
returned the default `Person()`. Now we can write the `fetchPerson` function 
as follows:

```scala
scala> def fetchPerson(personMap: Map[Int,Person], id:Int): Person =
     | personMap.getOrElse(id, Person())
fetchPerson: (personMap: Map[Int,Person], id: Int)Person
```
Let's check whether it works as desired.

```scala
scala> fetchPerson(personMap, 1)
res5: Person = Person(Akash,Dhasade)

scala> fetchPerson(personMap, 5)
res6: Person = Person(John,Parker)
```

**The jargen of `nulls`!**

It's fairly possible and correct to have a particular integer mapped 
to a `null` value. So let's do it by adding such a key value pair to 
our `personMap` in Java.

```java
Person p1 = new RealPerson("Akash", "Dhasade");
Person p2 = new RealPerson("Saket", "Joshi");
Person p3 = new RealPerson("Harry","");
Map<Integer, Person> personMap = new HashMap<>();
personMap.put(1, p1);
personMap.put(2, p2);
personMap.put(3, p3);
personMap.put(5, null);
```
And then if we do this: 

```java
System.out.println(personMap.get(5));   // prints null
System.out.println(personMap.get(6));   // prints null
```

Essentially the first `null` was the `null` in my data structure but the 
second `null` was due the absence of a value corresponding to key 6. This 
happens to be a common problem in Java, where we really don't know 
whether the `null` is my `null` or was it returned due to the absence of 
a value.

Let's look at the same thing in Scala. The `personMap` defined earlier 
was immutable so we define another `personMap` with a null value mapping 
in Scala repl as follows:
 
```scala
scala> val personMap = Map(1 -> p1, 2 -> p2, 3 -> p3, 5 -> null)
personMap: scala.collection.immutable.Map[Int,Person] = Map(1 -> Person(Akash,Dhasade), 2 -> Person(Saket,Joshi), 3 -> Person(Tejashree,Parker), 5 -> null)
```
 
 The `.get` method on `Map`s takes in a key of type `K` and rather than returning a 
value of type `V`, it returns `Option[V]`. This allows us to have a 
`null` value mapping since whenever I ask for such a mapping by passing 
in the corresponding key, I get the `null` value wrapped in a `Some`.
 
```scala
scala> personMap.get(5)
res1: Option[Person] = Some(null)

scala> personMap.get(6)
res2: Option[Person] = None
```

Unlike Java, we are able to recognize whether the `null` corresponds to 
my `null` or absence of some value. Notice that there was no key-value 
pair corresponding to key 6, hence it returned `None`. And essentially since 
5 was mapped to a `null`, it returned `Some(null)`. A very simple 
`Option` type aids us so much when dealing with `null` values.
 
**Conclusion:** We can use `Option` type wherever there is possibility of 
 non-existence of some Object. We can make the function return `Option[A]` 
 if we are not sure whether the function can always return a value of type 
 `A`. Once we step into the parallel world of `Options`, we can keep working 
 in the same world by lifting our functions that operate on types in real 
 world to ones that operate in the world of `Option`s. Functional languagues 
 provide handful of functions to deal with these situations, take `map`, 
 `flatmap`, `foldleft`, etc. Once we are done with all computations, we can 
 then unwrap the value and return to world of real types successfully. We will 
 talk about these things in the section titled *handling exceptions*.
   
   
    
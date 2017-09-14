**Template Pattern:**

The Gang Of Four states template pattern as : *Define the skeleton of an algorithm, deferring 
some steps to subclasses*. Essentially the template pattern is used when two or more implementations 
of same algorithm exist. It lets subclasses redefine certains steps of an algorithm without 
changing the algorithm's structure. The template pattern comes under behavioural patterns as it's 
used to manage algorithms and relationships between objects.

A template class looks like this: 

```java
public abstract class TemplateExample {
  public void operate() {
    firstOperation();
    secondOperation();
    thirdOperation();
  }
  
  public void thirdOperation() {
    /* Third operation */
  }
  protected abstract void firstOperation();
  protected abstract void secondOperation();
}
```

There is an `abstract class TemplateExample` which defines a method named `operate()`. `operate()` 
in turn calls three methods, the implementation of two of which is provided by subclasses as follows:  

```java
class subClass1 extends TemplateExample {
  public abstract void firstOperation() {
    /* Definition of first operation specific to subClass1 */
  }
  
  public abstract void secondOperation() {
    /* Definition of second operation specific to subClass1 */
  }
}
```
Just to state an example, consider you are cooking few dishes for some guests arriving 
in your house. You realize that there are some steps common to all dishes and these can be 
done just once for all of them. For Ex. `boilWater()`, you might just boil the water once and use 
it for all dishes. But there are certain things specific to your dishes, still common to all dishes. 
For Ex. `addSauce()`, the type of sauce you add to dishes might be different for different dishes 
but the task of adding sauce is common to all. So the implementation of `addSauce()` can be 
deferred to individual dishes keeping the outline same in one `abstract class makeDish()`. 

**Functional Replacement**

The intent of template methods is to define a skeleton for the algorithm and let callers plug 
in the details. Instead of using classes, we would now go with higher order functions and 
function composition to realize the algorithm. We do so by passing suboperations as function
parameters to a function and make it return a new function that does the full operation for us.
This goes as: 

```scala
def operate(firstOperation: () => Unit,
            secondOperation: () => Unit) =  
  () => { firstOperation();   secondOperation();  /* Own stuff for third operation */ }
```

The `operate()` function essentially takes two other functions as parameters and spits out another 
function that takes no parameters but essentially calls these two functions along with some 
additional stuff implemented by itself. So the `operate` here essentially acts as the `operate()` 
method of the `abstract class TemplateExample` but without the notion of sub-classes and their vivid 
implementations of same method. A more concrete illustration in both Java and Scala has been coded 
and can be found in the `src\main\scala\Template_Pattern` package.

**Conclusion**

The idea was to map Template pattern in OO world to some way of realizing the same thing in FP 
world. Essentially, function composition and higher-order functions seems feasible but still there 
are some questions that arise pertaining to the use if it. While doing this exercise, I realised 
that FP is not about not using classes and objects but it's more about side effects and immutability. 
**So as long as you have non-side effecting functions inside classes of OO, you are still using 
Functional style (maybe unknowningly). Essentially one should be asking, can I write the same thing 
in the form of non-side effecting functions? So that's the challenging part which I am still trying 
to figure out and learn.** 

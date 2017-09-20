package Closures;

/**
 * Created by @kash on 9/8/2017.
 */
public class JavaClosure {
     public static void main(String[] args)    {
         Greeting greeting = new Greeting();
         greeting.setGreeting("Hello");
         greeting.sayHello("Akash");
         /* Invoking sayHello after the String greeting has been modified outputs the correct greeting. */
         greeting.setGreeting("Hola");
         greeting.sayHello("Sam");
         Bar bar = new Bar(greeting);
         bar.sayHello("Virat");
         /* Invoking sayHello after the String greeting has been modified outputs the correct greeting.
          * Notice that the greeting variable has been modified. */
         greeting.setGreeting("Yo");
         bar.sayHello("Dhoni");
     }
}

/* This example demonstrates a Java version of Closure. We often need a function that
* that gives us result based on some paramaters in the scope of the function. When these paramaters are changed the
* value output for the same input is also changed accordingly. But essentially the paramater on which this function
 * depends is in the scope of the function. Whenever some other class not in the same scope wants to use this function,
 * we need to pass around something which holds both the paramaters and the function(an instance of class in this case)
 * such that the function makes use of the correct paramaters(which might have been updated over a period of time).
 * At the end of the day, we do extra work of passing the full instance around rather than just the function itself.*/

class Greeting {
    private String greeting;

    public void setGreeting(String greeting)    {
        this.greeting = greeting;
    }

    /* The function sayHello makes use of String greeting which is not in the lexical scope of this function. */
    public void sayHello(String name)   {
        System.out.println(greeting + " " + name);
    }
}

class Bar {
    private Greeting greeting;

    public Bar(Greeting greeting) {
        this.greeting = greeting;
    }

    /* This function inturn calls the sayHello method of Greeting class. Note that this method makes use of
     * String greeting not in the lexical scope of the function. */
    public  void sayHello(String name)  {
        greeting.sayHello(name);
    }
}

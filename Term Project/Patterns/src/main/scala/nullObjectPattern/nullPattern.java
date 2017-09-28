package nullObjectPattern;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by @kash on 9/27/2017.
 */

/*An example specifying null object pattern. Whenever we come across a null reference
 * we choose to return a null object which defines a default behaviour rather then returning
  * null itself. Large scale use of null object means that your program won't fail fast.
   * There would be some nulls which would get converted to null objects making it difficult for us
   * to track the bug down. So the null object pattern has to used judiciously.*/
public class nullPattern {
    public static void main(String[] args) {
        Person p1 = new RealPerson("Akash", "Dhasade");
        Person p2 = new RealPerson("Saket", "Joshi");
        Person p3 = new RealPerson("Harry","");
        Map<Integer, Person> personMap = new HashMap<>();
        personMap.put(1, p1);
        personMap.put(2, p2);
        personMap.put(3, p3);
        personMap.put(5, null);
        PersonExample pe = new PersonExample(personMap);
        System.out.println(pe.fetchPerson(1).getName());
        System.out.println(pe.fetchPerson(2).getName());
        System.out.println(pe.fetchPerson(4).getName());
        System.out.println(pe.buildPerson("Tejashree","").getName());
        System.out.println(personMap.get(5));
        System.out.println(personMap.get(6));
    }
}

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
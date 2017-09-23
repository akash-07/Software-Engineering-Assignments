package Functional_Interfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by @kash on 9/8/2017.
 */
public class FI_Java {
    public static void main(String[] args)    {
        Person p1 = new Person("Akash","B","Dhasade");
        Person p2 = new Person("Akash","","xyz");
        Person p3 = new Person("Saket","D","Joshi");
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(p3);
        persons.add(p1);
        persons.add(p2);
        System.out.println("Before sorting: ");
        for(Person p : persons) System.out.print(p + ", ");
        // Defining my won composed Comparator in terms of firstName and lastName Comparators
        ComposedComparator myComposedComparator = new ComposedComparator(new firstNameComparison(), new lastNameComparison());
        //Using the composed comparator to sort
        Collections.sort(persons,myComposedComparator);
        System.out.println("\nAfter sorting: ");
        for(Person p : persons) System.out.print(p + ", ");
    }
}

/* The default Comparator interface looks somewhat like this. Our classes will implement the same default
 *  comparator interface.
 *  interface Comparator<T> {
 *    int compare(T o1, T o2);
 *  }
*/


/* The class given below implements the Functional Interface Comparator and defines it's own
  * comparison method based on the first name of the person. */
class firstNameComparison implements Comparator<Person> {
    public int compare(Person p1, Person p2) {
        return p1.firstName.compareTo(p2.firstName);
    }
}

/* A class implementing Comparator interface to define comparsion based on last name of the person. */
class lastNameComparison implements Comparator<Person> {
    public int compare(Person p1, Person p2)  {
        return p1.lastName.compareTo(p2.lastName);
   }
}

/* This class carries state via the array of comparators. It takes in a number of different
  * comparators in order. The compare method applies these comparisons in the same order and
  * picks the first non-equal result of comparison. In other words if two objects match on first
  * comparison by first comparator then second comparator is tried. If they match on second then
  * third is tried and so on until you get some non-equal match. Hence the name composed comparsion.
  * Essentially we need to write a lot of boiler plate code via the use of interfaces to realize
  * simple comparators (even for both state carrying and non state carrying.)
  * */
class ComposedComparator implements Comparator<Person>  {
    private Comparator<Person>[] comparators;
    public ComposedComparator(Comparator<Person>... comparators) {
        this.comparators = comparators;
    }
    public int compare(Person p1, Person p2) {
        for(Comparator cmp: comparators)  {
            int lessThan = cmp.compare(p1,p2);
            if(lessThan != 0)   return lessThan;
        }
        return 0;
    }
}
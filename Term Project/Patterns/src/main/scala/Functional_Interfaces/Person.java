package Functional_Interfaces;

/**
 * Created by @kash on 9/8/2017.
 */
public class Person {
    public String firstName;
    public String middleName;
    public String lastName;

    public Person(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String toString() {
        return firstName + " " + middleName + " " + lastName;
    }
}
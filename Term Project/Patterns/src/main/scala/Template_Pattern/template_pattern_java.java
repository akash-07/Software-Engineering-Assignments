package Template_Pattern;

import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by @kash on 9/13/2017.
 */
public class template_pattern_java {
    public static void main(String[] args)   {
        gradeReporterTemplate myHistogramReporter = new fullGradeConverter();
        List<Double> grades = Arrays.asList(9.0,8.4, 7.3, 8.5, 10.0, 9.2, 8.9);
        myHistogramReporter.reportGrades(grades);
    }
}

abstract class gradeReporterTemplate {
    public final void reportGrades(List<Double> grades)  {
        printGradeReport(numToLetter(grades));
    }

    abstract protected List<String> numToLetter(List<Double> grades);
    abstract protected void printGradeReport(List<String> letterGrades);
}

class fullGradeConverter extends gradeReporterTemplate  {
    protected List<String> numToLetter(List<Double> grades) {
        List<String> letterGrades = new ArrayList<String>();
        for(Double d: grades)   {
            String letter;
            if(d == 10)
                letter = "S";
            else if(d < 10 && d >= 9)
                letter = "A";
            else if(d < 9 && d >= 8)
                letter = "B";
            else if(d < 8 && d >= 7)
                letter = "C";
            else if(d < 7 && d >= 6)
                letter = "D";
            else if(d >= 0)
                letter = "E";
            else letter = "N/A";
            letterGrades.add(letter);
        }
        return letterGrades;
    }

    protected void printGradeReport(List<String> letterGrades)    {
        int ACount = 0, BCount = 0, CCount = 0, DCount = 0,
                ECount = 0, NACount = 0, SCount = 0;
        for(String letter: letterGrades)    {
            if(letter == "A") ACount++;
            else if(letter == "S") SCount++;
            else if(letter == "B") BCount++;
            else if(letter == "C") CCount++;
            else if(letter == "D") DCount++;
            else if(letter == "E") ECount++;
            else if(letter == "NA") NACount++;
        }

        int i = 0;
        System.out.println("----Printing marks histogram----");
        System.out.print("\nS: ");    while(i++ < SCount) System.out.print("*");  i = 0;
        System.out.print("\nA: ");    while(i++ < ACount) System.out.print("*");  i = 0;
        System.out.print("\nB: ");    while(i++ < BCount) System.out.print("*");  i = 0;
        System.out.print("\nC: ");    while(i++ < CCount) System.out.print("*");  i = 0;
        System.out.print("\nD: ");    while(i++ < DCount) System.out.print("*");  i = 0;
        System.out.print("\nE: ");    while(i++ < ECount) System.out.print("*");  i = 0;
        System.out.print("\nN/A: ");    while(i++ < NACount) System.out.print("*");
    }
}
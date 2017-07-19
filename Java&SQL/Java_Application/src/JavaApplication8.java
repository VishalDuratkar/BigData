
/*
 * Q:- WAP in java to create generic class to accept employee age and salary. 
 */

import java.util.Scanner; 
 
class genricEmployee<A,S> 
{ 
private A age; 
private S salary; 
public genricEmployee() { 
super(); 
} 
 
public genricEmployee(A age, S salary) { 
super(); 
this.age = age; 
this.salary = salary; 
} 
public void acceptDisplay() 
{ 
Scanner sc =new Scanner(System.in); 
System.out.print("Enter the age of the employee - "); 
age= (A)sc.next(); 
System.out.println(); 
System.out.print("Enter the Salary of Employee - "); 
salary = (S)sc.next(); 
System.out.println(); 
} 
public void displayEmp() 
{ 
System.out.println("Employee Age -" + age + ", Salary - " + salary); 
} 
 
} 
public class JavaApplication8 
{ 
public static void main(String[] args) 
{ 
genricEmployee<Integer,Float> emp = new genricEmployee<Integer, Float>(100, 112220.0F); 
emp.displayEmp(); 
genricEmployee<Integer,Integer> emp1 = new genricEmployee<Integer, Integer>(); 
emp1.acceptDisplay(); 
emp1.displayEmp(); 
} 
} 
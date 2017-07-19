/*
Q:- WAP to accpet 10 numbers using array and display the sum and average. 

 */
import java.util.Arrays; 
import java.util.Scanner; 
 

public class JavaApplication2 { 
 
 int[] arrayInt = new int[10]; 
 void accpetArray() 
 { 
  Scanner sc = new Scanner(System.in); 
  System.out.println("Enter the array element :- "); 
  for(int i=0; i<10; i++) 
  { 
   System.out.print(i+1 +" Element :- "); 
   arrayInt[i]= sc.nextInt(); 
   System.out.println(); 
  } 
 } 

 void displayArray() 
 { 
  for (int number : arrayInt) 
  { 
   System.out.print(number + " "); 
  } 
  System.err.println(""); 
 } 
 public void sumAverage() 
 { 
  int temp = 0; 
  for(int x: arrayInt) 
   temp+=x; 
  System.err.println("Sum of Array :- " + temp); 
  System.err.println("Average of Array :- " + ((float)temp/10)); 
  
 } 
 public static void main(String[] args) 
 { 
  // TODO code application logic here 
  JavaApplication2 obj = new JavaApplication2(); 
  obj.accpetArray(); 
  System.err.println("Array before sorting....."); 
  obj.displayArray(); 
  obj.sumAverage(); 
 
 } 
} 
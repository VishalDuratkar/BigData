/*
Q:- WAP using array to accept 10 numbers and display it in ascending number. 
package javaapplication1; 
 */
import java.util.Arrays; 
import java.util.Scanner; 
 
/** 
?* 
?* @author DUVISHAL 
?*/ 
public class JavaApplication1 { 
 
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
 void sortArray() 
 { 
  int temp = 0; 
  for(int i=0; i < 10; i++){ 
   for(int j=1; j < (10-i); j++){ 
    if(arrayInt[j-1] > arrayInt[j]){ 
     temp = arrayInt[j-1]; 
     arrayInt[j-1] = arrayInt[j]; 
     arrayInt[j] = temp; 
   } 
   } 
  } 
 } 
 public static void main(String[] args) { 
  // TODO code application logic here 
  JavaApplication1 obj = new JavaApplication1(); 
  obj.accpetArray(); 
  System.err.println("Array before sorting....."); 
  obj.displayArray(); 
  System.err.println("Array after sorting....."); 
  obj.sortArray(); 
  obj.displayArray(); 
 
 } 
 

} 
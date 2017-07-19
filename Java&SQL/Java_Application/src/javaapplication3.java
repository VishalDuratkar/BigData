import java.util.Arrays; 
import java.util.Scanner; 
 
/** 
?* 
?* @author DUVISHAL 
?
Q:- WAP to accpet marks of 10 students using array and find name of highest scorer. 
*/ 
class JavaApplication3 { 
 
 String[][] arrayStudent = new String[2][10]; 
 void accpetArray() 
 { 
  Scanner sc = new Scanner(System.in); 
  for(int i=0; i<10; i++) 
  { 
   System.out.print("Enter the name of Student = "); 
   arrayStudent[0][i]= sc.next(); 
   System.out.println(); 
   System.out.print("Enter the marks = "); 
   arrayStudent[1][i]= sc.next(); 
   System.out.println(); 
  } 
 } 

 void displayArray() 
 { 
  for (int i=0; i<10; i++) { 
   System.out.println("Name of Student = " + arrayStudent[0][i] + " and marks = " + arrayStudent[1][i] ); 
  } 
  System.err.println(""); 
 } 

 void findMaxMarks() 
 { 
  int temp = Integer.parseInt(arrayStudent[1][1]); 
  int index = 0; 
  for(int i=1; i<10; i++) 
  { 
   if(Integer.parseInt(arrayStudent[1][i]) > temp) 
    { 
     temp=Integer.parseInt(arrayStudent[1][i]); 
     index = i ; 
    } 
  } 
  System.err.println("Student with highest marks = " + arrayStudent[0][index] + " and marks = " +arrayStudent[1][index]); 
 } 

 public static void main(String[] args) { 
  // TODO code application logic here 
  JavaApplication3 obj = new JavaApplication3(); 
  obj.accpetArray(); 
  System.err.println("Array before sorting....."); 
  obj.displayArray(); 
  obj.findMaxMarks(); 
    
 } 
} 
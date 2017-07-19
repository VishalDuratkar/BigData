 
/* 
?* To change this license header, choose License Headers in Project Properties. 
?* To change this template file, choose Tools | Templates 
?* and open the template in the editor. 

Q: Program for addition, substraction and Multiplication of two 3*3 Matrix.
?*/ 

 
import java.util.Arrays; 
import java.util.Scanner; 
 

public class JavaApplication4 { 
 int[][] firstMatrix = new int[3][3]; 
 int[][] secondMatrix = new int[3][3]; 
 int[][] productMatrix = new int[3][3]; 
 String[][] arrayStudent = new String[2][10]; 
 void accpetMatrix() 
 { 
  Scanner sc = new Scanner(System.in); 
  System.out.println("Enter the elements of first martix - \n"); 
  for (int i = 0; i < 3; i++) 
  { 
   for (int j = 0; j < 3; j++) 
   { 
    System.err.print("Element [" + i +"][" + j +"]"); 
    firstMatrix[i][j]=sc.nextInt(); 
    System.err.println(); 
   } 
  } 
  System.out.println("Enter the elements of second martix - \n"); 
  for (int i = 0; i < 3; i++) 
  { 
   for (int j = 0; j < 3; j++) 
   { 
    System.err.print("Element [" + i +"][" + j +"]"); 
    secondMatrix[i][j]=sc.nextInt(); 
    System.err.println(); 
   } 
  } 
 } 
 void displayMatrix() 
 { 
  System.out.println("First Matrix - "); 
  for (int i = 0; i < 3; i++) 
  { 
   for (int j = 0; j < 3; j++) 
   { 
    System.out.print(firstMatrix[i][j] + " "); 
   } 
   System.out.println(); 
  } 
  System.out.println("Second Matrix - "); 
  for (int i = 0; i < 3; i++) 
  { 
   for (int j = 0; j < 3; j++) 
   { 
    System.out.print(secondMatrix[i][j] + " "); 
   } 
   System.out.println(); 
  }  
 }  
 void matrixProduct() 
 { 
  System.out.println("Product of ?Matrix - "); 
  for (int i = 0; i < 3; i++) 
  { 
   for (int j = 0; j < 3; j++) 
   { 
    for (int k = 0; k < 3; k++) 
    { 
     productMatrix[i][j] = productMatrix[i][j] + firstMatrix[i][k] * secondMatrix[k][j]; 
    } 
   } 
  } 
  for (int i = 0; i < 3; i++) 
  { 
   for (int j = 0; j < 3; j++) 
   { 
    System.out.print(productMatrix[i][j] + " "); 
   } 
   System.out.println(); 
  } 
 } 
 void matrixAddition() 
 { 
  System.out.println("Addition of ?Matrix - "); 
  for (int i = 0; i < 3; i++) 
  { 
   for (int j = 0; j < 3; j++) 
   { 
    productMatrix[i][j] = firstMatrix[i][j] + secondMatrix[i][j];    
   } 
  } 
  for (int i = 0; i < 3; i++) 
  { 
   for (int j = 0; j < 3; j++) 
   { 
    System.out.print(productMatrix[i][j] + " "); 
   } 
   System.out.println(); 
  } 
 } 
 void matrixSubstraction() 
 { 
  System.out.println("Substraction of ?Matrix - "); 
  for (int i = 0; i < 3; i++) 
  { 
   for (int j = 0; j < 3; j++) 
   { 
    productMatrix[i][j] = firstMatrix[i][j] - secondMatrix[i][j];    
   } 
  } 
  for (int i = 0; i < 3; i++) 
  { 
   for (int j = 0; j < 3; j++) 
   { 
    System.out.print(productMatrix[i][j] + " "); 
   } 
   System.out.println(); 
  } 
 } 
 public static void main(String[] args) { 
  // TODO code application logic here 
	 JavaApplication4 obj = new JavaApplication4(); 
  obj.accpetMatrix(); 
  obj.displayMatrix(); 
  obj.matrixProduct(); 
  obj.matrixAddition(); 
  obj.matrixSubstraction(); 
 } 
 

} 
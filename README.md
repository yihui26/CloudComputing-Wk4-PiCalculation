# CloudComputing-Wk4-PiCalculation
The program was on Cloudera, accessed by Oracle VM's VirtualBox.
The codes were built in Java, and the compiled codes ran on Hadoop to test the accuracy of Pi.

Steps:
1. Generate numbers from GenerateLargeRandomNumbers.java program
2. PiCalculation.java program does map reduce methods to calculate Pi.

The general idea is that if you have a circle in a square and you throw darts randomly at the combination, you will have enough tarts that would hit inside the circle, and outside the circle but in the square. 
Since (Area of circle = pi * r^2 ) , (Area of square = 2r*2r = 4r^2), therefore (Area of circle/Area of Square = 1/4 pi).
With that in mind, (count of darts in the circle / total count of darts in the square)* 4 = estimated pi.

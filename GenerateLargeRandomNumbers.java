import java.util.Scanner;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateLargeRandomNumbers {
	public static void main(String[] args) {


		//Get input values
		System.out.println("How many random numbers to generate:");
		Scanner input =new Scanner(System.in);
		int RandomNumCount = input.nextInt();
		System.out.println("What's the radius number?");
		int radius = input.nextInt();
		int diameter = radius * 2;
		
		//declaring variables
		int xvalue = 0;
		int yvalue = 0;
		int inside = 0;
		int outside = 0;

		//open output file to write
		BufferedWriter bw = null;
		FileWriter fw = null;
		try{
			System.out.println("\n==Writing to file ===");
			
			fw = new FileWriter("PiCalculationInput.txt");
			bw = new BufferedWriter(fw);
			
			//set the first number to be the radius
			bw.write(radius + " ");
						
			//Generate random numbers
			Random rand = new Random();
			for(int i=0;i<RandomNumCount;i++){
			   xvalue = (int)(Math.random()*diameter);
			   yvalue = (int)(Math.random()*diameter);

			   bw.write(xvalue + " " + yvalue + " ");
			   
			   double check = Math.sqrt(Math.pow((radius-xvalue), 2) + Math.pow((radius-yvalue), 2));
				if (check < radius) {
					inside++;
				} else if (check == radius){
					if (rand.nextBoolean()){
						inside++;
					} else{
						outside++;
					}
				} else {
					outside++;
				}
				
			}
			
			
			
		}catch (Exception e){
			e.printStackTrace();
		} finally{
			try{
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
				System.out.println("===Written to file PiCalculationInput.txt===");
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
		
		//Calculating pi with console
		System.out.println("");
		System.out.println("inside value " + inside);
		System.out.println("outside value " + outside);
		double possibility = (double)inside / (double)(inside + outside);
		System.out.println("p:" + possibility);
		double piValue = 4 * possibility;
		System.out.println("π value is " + piValue);

	}

	
}
import java.io.IOException;
import java.util.Random;
import java.util.*;
import java.lang.Object;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat; 
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat; 
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat; 
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat; 

public class PiCalculation {

	public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
		
		private final static IntWritable one = new IntWritable(1); 
		private static final int UPPER_CASE = 0;
		private static final int LOWER_CASE = 0;
		private Text word = new Text();
		
		public void map(LongWritable key, Text value, Context context) 
			throws IOException, InterruptedException {		
				//retrieve x and y values
				String line = value.toString();
				StringTokenizer tokenizer = new StringTokenizer(line);
				int xvalue = 0;
				int yvalue = 0;
				int radius = 0;
				//Assume first value is the radius.
				if (tokenizer.hasMoreTokens()){
					radius = (int)(Integer.parseInt(tokenizer.nextToken()));
				}
				//Check x and y values.
				while (tokenizer.hasMoreTokens()) { 
					xvalue = (int)(Integer.parseInt(tokenizer.nextToken()));
					if (tokenizer.hasMoreTokens()){
						yvalue = (int)(Integer.parseInt(tokenizer.nextToken()));
					}else{
						yvalue = 0;
					}	
					//Check inside or outside of circle.			
					//Also, points on circumference has 50% probability of being inside/outside of circle.
					Random rand = new Random();
					double check = Math.sqrt(Math.pow((radius-xvalue), 2) + Math.pow((radius-yvalue), 2));
					if (check < radius) {
						word.set("inside");
					} else if (check == radius){
						if (rand.nextBoolean()){
							word.set("inside");
						} else{
							word.set("outside");
						}
					} else {
						word.set("outside");
					}
					context.write(word, one); 
				}
		}
	}
	
	public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {
		
		public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException { 
				int sum = 0;
				for (IntWritable val : values) {
					sum += val.get(); 
				}
				context.write(key, new IntWritable(sum)); 
			}
	}

	public static void main(String[] args) 
		throws Exception { 
		
			long startTime = System.currentTimeMillis();
			
			Configuration conf = new Configuration();
			Job job = new Job(conf, "PiCalculation"); 
			job.setJarByClass(PiCalculation.class); 
			job.setOutputKeyClass(Text.class); 
			job.setOutputValueClass(IntWritable.class);

			job.setMapperClass(Map.class); 
			job.setReducerClass(Reduce.class); 
			job.setCombinerClass(Reduce.class);

			job.setInputFormatClass(TextInputFormat.class); 
			job.setOutputFormatClass(TextOutputFormat.class);

			FileInputFormat.addInputPath(job, new Path(args[0])); 
			FileOutputFormat.setOutputPath(job, new Path(args[1]));

			job.waitForCompletion(true); 
			
			System.out.print("Time taken: ");
			System.out.print(System.currentTimeMillis()-startTime);
			System.out.print("miliseconds.\n");
	}

} 
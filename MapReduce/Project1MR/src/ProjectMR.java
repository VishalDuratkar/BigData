import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;

/*
1)Transaction date and time
2)Customer I.D 
3)Age: 10 possible values 
A <25,B 25-29,C 30-34,D 35-39,E 40-44,F 45-49,G 50-54,H 55-59,I 60-64,J >65 
4)Residence Area: 8 possible values, A-F: zip code area: 105,106,110,114,115,221,G:others, H: Unknown Distance to store, from the closest: 115,221,114,105,106,110 
5)Product subclass (category)
6)Product ID 
7)Qty or Number of units
8)Total Cost 
9)Total Sales
			0				1		2	3		4		5			6	7	8
2001-01-01 00:00:00;	00141833;	F;	F;	130207;	4710105011011;	2;	44;	52
 */
public class ProjectMR 
{
	public static class MapperRetail extends Mapper<LongWritable, Text, IntWritable, Text>
	{
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
		{
			String[] parts = value.toString().split(";");
			String r_key = parts[0].substring(5, 7);
			int rkey = Integer.parseInt(r_key);
			context.write(new IntWritable(rkey), new Text(parts[1].trim()+ ";" + parts[8].trim()));
		}
	}
	
	public static class ReducerRetaill extends Reducer<IntWritable, Text, IntWritable, Text>
	{
		public static Text outputValue = new Text();
		public void reducer(IntWritable key, Iterable<Text> value, Context context) throws IOException, InterruptedException
		{
			int temp;
			int max=0;
			String tempRecord = "";
			for(Text line : value)
			{
				String[] arr= line.toString().split(";");
				temp = Integer.parseInt(arr[1]);
				if(temp > max )
				{
					max=temp;
					tempRecord = line.toString();
				}
			}
			outputValue.set(tempRecord);
			context.write(key, outputValue);
		}
	}
	public static void main(String[] args ) throws IOException, ClassNotFoundException, InterruptedException
	{
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf,"Retaill Data");
		
		job.setJarByClass(ProjectMR.class);
		job.setMapperClass(MapperRetail.class);
		job.setReducerClass(ReducerRetaill.class);
		
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}

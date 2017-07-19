import java.io.IOException;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;


/*
 * Find out the top 4 or top 10 product being sold in the monthly basis and in all the 4 months.. 
 * Criteria for top should be sales amount
 * 
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
 * */
public class ProjectMR2 
{
	public static class MapperRetail extends Mapper<LongWritable, Text, LongWritable, Text>
	{
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
		{
			String[] parts = value.toString().split(";");
			long pid = Long.parseLong(parts[5].trim());
			String month = parts[0].substring(5, 7);
			String totalSale = parts[8];
			context.write(new LongWritable(pid), new Text(month.trim()+ ";" + totalSale.trim()));
		}
	}
	
	public static class ReducerRetaill extends Reducer<LongWritable, Text, Text, Text>
	{
		public static TreeMap<Long, Long> jan = new TreeMap<Long, Long>();
		public static TreeMap<Long, Long> feb = new TreeMap<Long, Long>();
		public static TreeMap<Long, Long> nov = new TreeMap<Long, Long>();
		public static TreeMap<Long, Long> dec = new TreeMap<Long, Long>();
		public void reducer(LongWritable key, Iterable<Text> value, Context context) throws IOException, InterruptedException
		{
			for(Text txt:value)
			{
				String[] parts= txt.toString().split(";");
				int month = Integer.parseInt(parts[0]);
				long tempSaleValue = Long.parseLong(parts[1].trim());
				long tempKey = key.get();
				if(month == 1)
				{
					if(jan.containsKey(tempKey))
					{
						long tempValue = jan.get(tempKey);						
							jan.put(tempKey, tempSaleValue + tempValue);
					}
					else
						jan.put(tempKey, tempSaleValue);
				}
				else if(month == 2)
				{
					if(feb.containsKey(tempKey))
					{
						long tempValue = feb.get(tempKey);						
						feb.put(tempKey, tempSaleValue + tempValue);
					}
					else
						feb.put(tempKey, tempSaleValue);
				}
				else if(month == 11)
				{
					if(nov.containsKey(tempKey))
					{
						long tempValue = nov.get(tempKey);						
						nov.put(tempKey, tempSaleValue + tempValue);
					}
					else
						nov.put(tempKey, tempSaleValue);
				}
				else if(month == 12)
				{
					if(dec.containsKey(tempKey))
					{
						long tempValue = dec.get(tempKey);						
						dec.put(tempKey, tempSaleValue + tempValue);
					}
					else
						dec.put(tempKey, tempSaleValue);
				}
			}
		}
		
	}
	
	public static void main(String[] args ) throws IOException, ClassNotFoundException, InterruptedException
	{
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf,"Retaill Data");
		
		job.setJarByClass(ProjectMR2.class);
		job.setMapperClass(MapperRetail.class);
		job.setReducerClass(ReducerRetaill.class);
		
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}

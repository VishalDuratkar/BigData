
import java.io.IOException;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;



public class MyStringSearch 
{
	public static class tokenizer extends Mapper<LongWritable, Text, Text, IntWritable>
	{
		public void map(LongWritable key, Text value, Context context) throws IOException,InterruptedException
		{
			String mysearchText = context.getConfiguration().get("myText").toString();
			String line = value.toString().toLowerCase();
			if(mysearchText!=null)
			{
				if(line.contains(mysearchText))
				{
					context.write(new Text(line), new IntWritable(1));
				}
			}			
		}
	}
	
	public static class intSumReducer extends Reducer<Text, IntWritable, Text, IntWritable>
	{
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
		{
			int sum=0;
			for(IntWritable i : values)
			{	sum+=i.get();	}
			context.write(key, new IntWritable(sum));
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		Configuration conf = new Configuration();
		conf.set("myText", args[2]);
		/*
		if(args.length > 2)
		{
			conf.set("myText", args[2]);
		}
		else
		{
			System.out.println("Insufficient argument");
			//System.exit(1);
		}
		*/
		Job job = Job.getInstance(conf,"String Search...");
		job.setJarByClass(MyStringSearch.class);
		job.setMapperClass(tokenizer.class);
		job.setReducerClass(intSumReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}

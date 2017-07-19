import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;


public class WordCount 
{

	public static class mapClass extends Mapper<LongWritable, Text, Text, IntWritable>
	{
		public void map(LongWritable key, Text value, Context context)
		{
			String line = value.toString();
			StringTokenizer st = new StringTokenizer(line);
			try
			{
				while(st.hasMoreTokens())
				{
					context.write(new Text((st.nextToken()).toLowerCase()), new IntWritable(1));
				}
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}	
		}
	}
	
	public static class reduceClass extends Reducer<Text, IntWritable, Text, IntWritable>
	{
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
		{
			int sum=0;
			for(IntWritable val : values)
			{
				sum+=val.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Word Count");
		job.setJarByClass(WordCount.class);
		job.setMapperClass(mapClass.class);
		job.setCombinerClass(reduceClass.class);
		job.setReducerClass(reduceClass.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		 FileInputFormat.addInputPath(job, new Path(args[0]));
		 FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
		
	}
}

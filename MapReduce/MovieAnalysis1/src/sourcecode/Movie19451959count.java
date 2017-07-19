package sourcecode;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.*;


//Find the number of movies released between 1945 and 1959
//Column1: Movie ID,Column2: Movie name,Column3: Year of release,Column4: Rating of the movie,Column5: Movie duration in seconds

public class Movie19451959count 
{
	public static class  MapClass extends Mapper<LongWritable,Text,Text,Text> 
	{
		public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException
		{
			String[] str=value.toString().split(",");
			int year=Integer.parseInt(str[2]);
			if(year>=1945 && year <=1959 )
			{
				Text outkey=new Text("1");
				String str1=str[1];
				context.write(new Text(outkey), new Text(str1));
			}			
		}
	}
	public static class  ReduceClass extends Reducer<Text,Text,NullWritable,IntWritable>
	{
		public IntWritable result =new IntWritable();
		public void reduce(Text key,Iterable<Text> value,Context context) throws IOException, InterruptedException
		{
			int count=0;
			for(Text val:value)
				count++;
			result.set(count);
			context.write(NullWritable.get(),result);
		}
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException 
	{
			Configuration conf = new Configuration();
			Job job = Job.getInstance(conf);
			job.setJarByClass(Movie19451959count.class);
			job.setMapperClass(MapClass.class);
			job.setReducerClass(ReduceClass.class);
			
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			
			job.setOutputKeyClass(NullWritable.class);
			job.setOutputValueClass(IntWritable.class);
			
			FileInputFormat.addInputPath(job, new Path(args[0]));
		    FileOutputFormat.setOutputPath(job, new Path(args[1]));
		    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}

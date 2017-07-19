import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.*;

//Find the number of movies having rating more than 3.9
public class MoviesRating 
{
	public static class  MapClass extends Mapper<LongWritable,Text,Text,Text> 
	{
		public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException
		{
			String[] str=value.toString().split(",");
			if(!str[3].isEmpty())
			{
				Double rating=Double.parseDouble(str[3]);
				if(rating > 3.9)		
					{
						Text outkey=new Text("1");
						String str1=str[1];
						context.write(new Text(outkey), new Text(str1));
                    }
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
			context.write(null,result);
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException 
	{
			Configuration conf = new Configuration();
			Job job = Job.getInstance(conf);
			job.setJarByClass(MoviesRating.class);
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

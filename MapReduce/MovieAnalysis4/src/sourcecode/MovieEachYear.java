package sourcecode;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.*;

//Find the list of years and number of movies released each year
public class MovieEachYear 
{
	public static class MapClass extends Mapper<LongWritable,Text,Text,Text>
	   {
			Text result=new Text();
			public void map(LongWritable key, Text value, Context context)
			{	    	  
	         try
	         {
	            String[] str = value.toString().split(",");	 
	            String movies = str[1];
	            result.set(movies);
	            context.write(new Text(str[2]),result);	            
	        }
	         catch(Exception e)
	         {
	            System.out.println(e.getMessage());
	         }
	      }
	   }
	
	  public static class ReduceClass extends Reducer<Text,Text,Text,LongWritable>
	   {
		    private LongWritable result = new LongWritable();
		    public void reduce(Text key, Iterable<Text> values,Context context) throws IOException, InterruptedException 
		    {
		      long sum = 0;
		         for (Text val : values)
		        	 sum++;
		      result.set(sum);		      
		      context.write(key, result);
		    }
	   }
	  public static void main(String[] args) throws Exception 
	  {
		  	Configuration conf = new Configuration();
			Job job = Job.getInstance(conf);
			job.setJarByClass(MovieEachYear.class);
		    job.setMapperClass(MapClass.class);
		    job.setReducerClass(ReduceClass.class);
		    job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(LongWritable.class);
		    FileInputFormat.addInputPath(job, new Path(args[0]));
		    FileOutputFormat.setOutputPath(job, new Path(args[1]));
		    System.exit(job.waitForCompletion(true) ? 0 : 1);
		  }
}

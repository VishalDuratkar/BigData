import java.io.IOException;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;


public class KeyValueToText 
{
	public static class keyvaluemapper extends Mapper<Text, Text, Text, Text>
	{
		public void map(Text key, Text value, Context context) throws IOException, InterruptedException
		{
			context.write(key, value);
		}
	}
	public static void main(String[] args ) throws IOException, InterruptedException, ClassNotFoundException 
	{
		Configuration conf = new Configuration();
		conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", "#");
		Job job = Job.getInstance(conf,"Break string into two part i.e. key and value");
		job.setJarByClass(KeyValueToText.class);
		job.setMapperClass(keyvaluemapper.class);
		job.setNumReduceTasks(0);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}









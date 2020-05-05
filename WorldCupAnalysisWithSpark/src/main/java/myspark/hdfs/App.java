package myspark.hdfs;

import java.io.IOException;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class App {
	static SparkSession spark;

	public static void main(String[] args) throws IOException {
		System.setProperty("hadoop.home.dir", "D:\\hadoop-3.0.0");
		spark = SparkSession.builder().master("local").appName("SparkHDFS").getOrCreate();
		String path = "hdfs://localhost:8020/WorldCup/WorldCupPlayers.csv";

		// hadoop machine run these commands from termina
		// hdfs dfs -mkdir /WorldCup
		// hdfs dfs -copyFromLocal /...WorldCupPlayers.csv /WorldCup

		Dataset<Row> hdfs_data = spark.read().option("inferschema", true).option("heder", false).csv(path);
		System.out.println(hdfs_data.count());
		hdfs_data.printSchema();

	}
}

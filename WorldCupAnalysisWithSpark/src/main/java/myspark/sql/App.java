package myspark.sql;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class App {

	public static void main(String[] args) {

		SparkSession spark = SparkSession.builder().master("local").appName("SparkSQL").getOrCreate();

		Dataset<Row> wcData = spark.read().option("multiline", true).json("WorldCup.json");
		System.out.println(wcData.count());
		wcData.printSchema();
		Dataset<Row> wcTurkeyData = wcData.filter(new Column("countryName").equalTo("TUR"));
		System.out.println(wcTurkeyData.count());
		Dataset<Row> wcTurkeyGoalData = wcTurkeyData.filter(new Column("event").contains("G"));
		wcTurkeyGoalData.show();

	}

}

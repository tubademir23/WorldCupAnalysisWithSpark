package myspark.sql;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class AppSqlScript {
	static SparkSession spark;
	static final String WC_VIEW_NAME = "WorldCup";

	public static void main(String[] args) {

		spark = SparkSession.builder().master("local").appName("SparkSQLScript").getOrCreate();

		Dataset<Row> wcData = spark.read().option("multiline", true).json("WorldCup.json");
		wcData.createOrReplaceTempView(WC_VIEW_NAME);

		// same result: wcData.select("roundId", "matchId", "countryName");
		Dataset<Row> sql = spark.sql("select roundId, matchId, countryName from " + WC_VIEW_NAME);

		sql.show();
	}

}

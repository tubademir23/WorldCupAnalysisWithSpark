package myspark.sql;

import static org.apache.spark.sql.functions.callUDF;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;

public class App {
	static SparkSession spark;
	static final String GOAL_UDF_NAME = "getGoalCount";
	static final String EVENT_COLUMN_NAME = "event";
	static final String SCORE_COLUMN_NAME = "score";

	public static void main(String[] args) {

		spark = SparkSession.builder().master("local").appName("SparkSQL").getOrCreate();

		Dataset<Row> wcData = spark.read().option("multiline", true).json("WorldCup.json");
		System.out.println(wcData.count());
		wcData.printSchema();
		Dataset<Row> wcTurkeyGoalData = wcData.filter(new Column("countryName").equalTo("TUR"));
		wcTurkeyGoalData = setGoalScore(wcTurkeyGoalData);

		Dataset<Row> wcTurkeyHattrickData = wcTurkeyGoalData.filter(new Column(SCORE_COLUMN_NAME).gt(2))
				.select("shirtNumber", "playerName", SCORE_COLUMN_NAME);

		System.out.println("____________________________Turkey player who had hattrick____________________________");
		wcTurkeyHattrickData.show();
	}

	public static Dataset<Row> setGoalScore(Dataset<Row> wcTurkeyGoalData) {

		spark.udf().register(GOAL_UDF_NAME, (UDF1<String, Integer>) (columnValue) -> {
			String[] split = columnValue.split(" ");
			int score = 0;
			for (String str : split) {
				if (str.startsWith("G"))
					score++;
			}

			return score;
		}, DataTypes.IntegerType);
		return wcTurkeyGoalData.withColumn(SCORE_COLUMN_NAME, callUDF(GOAL_UDF_NAME, new Column(EVENT_COLUMN_NAME)));
	}
}

package myspark.sql;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import myspark.model.WCPlayersModel;

public class AppEncoder {
	static SparkSession spark;
	static final String WC_VIEW_NAME = "WorldCup";

	public static void main(String[] args) {

		spark = SparkSession.builder().master("local").appName("SparkEncoder").getOrCreate();
		Encoder<WCPlayersModel> encoderWCCup = Encoders.bean(WCPlayersModel.class);

		Dataset<WCPlayersModel> wcData = spark.read().option("multiline", true).json("WorldCup.json").as(encoderWCCup);
		wcData = wcData.filter(new Column("countryName").equalTo("TUR"));
		wcData.printSchema();
		System.out.println(wcData.count());
		// filter(new Column("countryName").equalTo("TUR"))

		wcData.foreach(kv -> {
			kv.printStr();
		});

	}
}

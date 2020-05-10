package myspark.hdfs;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import myspark.model.Match;
import myspark.model.PlayersModel;
import myspark.model.Round;
import myspark.model.WCPlayersModel;

public class App implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static SparkSession spark;
	static Dataset<WCPlayersModel> hdfs_data = null;

	public static void main(String[] args) throws IOException {
		// below line is unnecessary if HADOOP_HOME variable added on sys. env.
		// System.setProperty("hadoop.home.dir", "D:\\hadoop-3.0.0");
		spark = SparkSession.builder().master("local").appName("Spark_HDFS").getOrCreate();
		Encoder<WCPlayersModel> encoderWCCup = Encoders.bean(WCPlayersModel.class);

		String path = "hdfs://localhost:8020/WorldCup/WorldCupPlayers.csv";

		/*
		 * hadoop machine run these commands from terminal
		 * 
		 * @hdfs dfs -mkdir /WorldCup
		 * 
		 * @hdfs dfs -copyFromLocal /...WorldCupPlayers.csv /WorldCup
		 */

		/*
		 * Be careful for if csv includes header or not.
		 * 
		 * @if headerless then map columns with below code
		 * 
		 * @hdfs_data.withColumnRenamed("_c0","roundID");
		 * 
		 * @else use StructType(Array(StructField
		 */
		hdfs_data = spark.read().option("inferschema", true).option("header", true).csv(path).as(encoderWCCup);

		hdfs_data.printSchema();

		Encoder<Match> matchEncoder = Encoders.bean(Match.class);
		Encoder<Round> roundEncoder = Encoders.bean(Round.class);
		Dataset<Row> hdfs_match_data = hdfs_data.select(new Column("matchID")).distinct();

		Integer i = 0;
		PlayersModel pm = new PlayersModel();

		Dataset<Round> round_data = hdfs_data.map((MapFunction<WCPlayersModel, Round>) (model) -> {
			Round round = pm.addLineAsModel(model);
			return round;
		}, roundEncoder);
		// round_data.select(new Column("team1.teamName"), new Column("team2.teamName"), new Column("matchId")).show(10);
		round_data.printSchema();
		Round r = pm.getRounds().get(Long.parseLong("208"));
		if (r != null) {
			Map<Long, Match> matches = r.getMatches();
			if (matches != null) {
				Match m = matches.get(Long.parseLong("1230"));
				if (m != null)
					System.out.println(m.getMatchId() + m.getTeam1().getTeamName() + ":" + m.getTeam1().getPlayers().size() + m.getTeam2().getTeamName() + ":" + m.getTeam2().getPlayers().size());
				else
					System.out.println("m null");
			} else
				System.out.println("matches null");
		} else
			System.out.println("r null");

		;
		/*
		 * match_data.filter(new Column("").equalTo("TUR")).foreach(match -> { System.out.println(match.getMatchId() + ": " + match.getTeam1().getTeamName() + "\t" + match.getTeam1().getTeamName()); });
		 */

	}

}

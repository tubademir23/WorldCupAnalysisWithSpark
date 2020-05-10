package myspark.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;

import com.mongodb.spark.MongoSpark;

import avro.shaded.com.google.common.collect.Iterators;
import myspark.model.GroupPlayerMatch;
import myspark.model.Match;
import myspark.model.Player;
import myspark.model.PlayersModel;
import myspark.model.Round;
import myspark.model.Team;
import myspark.model.WCPlayersModel;
import scala.Tuple2;;

public class App implements Serializable {

	static JavaRDD<WCPlayersModel> WCPlayers_Data = null;
	static PlayersModel pm = null;
	static JavaSparkContext context = null;

	public App(String path, SparkSession spark) {
		context = spark == null ? new JavaSparkContext("local", "Analysis App") : new JavaSparkContext(spark.sparkContext());

		JavaRDD<String> Raw_Data = context.textFile(path);
		pm = new PlayersModel();
		WCPlayers_Data = pm.mapPlayersModelRDD(Raw_Data);
	}

	public static void main(String[] args) {

		SparkSession spark = SparkSession.builder().master("local").appName("MongoSpark").config("spark.mongodb.input.uri", "mongodb://127.0.0.1:27017/sample.WCCollection").config("spark.mongodb.output.uri", "mongodb://127.0.0.1:27017/sample.WCCollection").getOrCreate();

		App app = new App("WorldCupPlayers.csv", spark);
		// pm.toStrOutList(Players_Data.collect());
		// pm.writeModelSummary();
		JavaRDD<GroupPlayerMatch> result = getWithRDDCountMatchForAllPlayers("TUR");
		saveResultToMongoDB(result);
		// getWithModelCountMatchForAllPlayers();

		String playerName = "RONALDO";

		sampleWithRDDFilterByPlayerName(playerName);

		app.sampleWithModelFilterByPlayerName(playerName);

		app.context.close();

	}

	private static void saveResultToMongoDB(JavaRDD<GroupPlayerMatch> result) {
		JavaRDD<org.bson.Document> mongoRDD = result.map(new Function<GroupPlayerMatch, Document>() {
			public Document call(GroupPlayerMatch match) throws Exception {
				return Document.parse("{PlayerName:'" + match.getPlayerName() + "', Count:" + match.getMatchCount() + "}");
			}
		});
		MongoSpark.save(mongoRDD);
	}

	private static JavaRDD<GroupPlayerMatch> getWithRDDCountMatchForAllPlayers(String countryName) {
		long startTime = System.currentTimeMillis();
		JavaRDD<WCPlayersModel> data = WCPlayers_Data;
		if (countryName != "") {
			data = WCPlayers_Data.filter(new Function<WCPlayersModel, Boolean>() {
				public Boolean call(WCPlayersModel model) throws Exception {
					return model.getCountryName().equals(countryName);
				}
			});
		}
		JavaPairRDD<String, Iterable<Long>> Pair_Player_Data = data.mapToPair(new PairFunction<WCPlayersModel, String, Long>() {
			public Tuple2<String, Long> call(WCPlayersModel cupModel) throws Exception {
				return new Tuple2<String, Long>(cupModel.getPlayerName(), cupModel.getMatchID());
			}

		}).groupByKey();
		/**
		 * @this sample is without groupplayermodel
		 * @Pair_Player_Data.foreach(new VoidFunction<Tuple2<String, Iterable<String>>>()
		 * @{ public void call(Tuple2<String, Iterable<String>> tuple2) throws Exception
		 * @{ Iterator<String> values = tuple2._2().iterator(); System.out.println("PL:" + tuple2._1 + "\t MC: " + Iterators.size(values)); } });
		 */
		JavaRDD<GroupPlayerMatch> Result_Data = Pair_Player_Data.map(new Function<Tuple2<String, Iterable<Long>>, GroupPlayerMatch>() {
			public GroupPlayerMatch call(Tuple2<String, Iterable<Long>> tuple2) throws Exception {
				Iterator<Long> values = tuple2._2().iterator();
				int size = Iterators.size(values);
				return new GroupPlayerMatch(tuple2._1, size);
			};
		});
		return Result_Data;
		/*
		 * Result_Data.foreach(new VoidFunction<GroupPlayerMatch>() { public void call(GroupPlayerMatch match) throws Exception { System.out.println("P:" + match.getPlayerName() + "\t C: " + match.getMatchCount()); } });
		 */

	}

	private static void getWithModelCountMatchForAllPlayers() {
		// System.out.println(pm.getRounds().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
		// data -> data
		// .getValue().getMatches().entrySet().stream().collect(Collectors.toMap(Team::getTeam,data
		// -> data
		// .getValue().getTeam1().getTeamName().mapToDouble(Number::doubleValue))));

		final Map<String, Round> tmp = new HashMap<>();
		Long id = new Long(111);
		Round round1 = new Round(id);
		round1.getMatches().put(id, new Match(id));
		round1.getMatches().get(id).setTeam1(new Team("Round1Match1Team1"));
		round1.getMatches().get(111).getTeam1().getPlayers().put("Round1Match1Team1Player1", new Player("L", id, "Round1Match1Team1Player1", "GK", null));
		round1.getMatches().get("Match1").getTeam1().getPlayers().put("Round1Match1Team1Player1", new Player("N", id, "Round1Match1Team1Player2", "D", ("G41'")));
		round1.getMatches().get("Match1").setTeam2(new Team("Round1Match1Team2"));
		round1.getMatches().get("Match1").getTeam2().getPlayers().put("Round1Match1Team2Player1", new Player("L", id, "Round1Match1Team2Player1", "GK", null));

		Round round2 = new Round(id * 2);
		round2.getMatches().put(id * 2, new Match(id * 2));
		round2.getMatches().get(222).setTeam1(new Team("Round2Match2Team1"));
		round2.getMatches().get(222).getTeam1().getPlayers().put("Round2Match2Team1Player1", new Player("M", id, "Round2Match2Team1Player1", "GK", null));
		round2.getMatches().get(222).getTeam1().getPlayers().put("Round1Match1Team1Player1", new Player("S", id, "Round2Match2Team1Player2", "L", ("G41'")));
		round2.getMatches().get(222).setTeam2(new Team("Round2Match2Team2"));
		round2.getMatches().get(222).getTeam2().getPlayers().put("Round2Match2Team2Player1", new Player("M", id, "Round2Match2Team2Player1", "GK", null));
		round2.getMatches().get(222).getTeam2().getPlayers().put("Round1Match1Team2Player1", new Player("S", id, "Round2Match2Team2Player2", "L", ("G41'")));

		tmp.put("Round1", round1);
		tmp.put("Round2", round2);

		System.out.println(tmp.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, data -> data.getValue().getMatches().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, match -> match.getValue().getTeam1().getPlayers().entrySet().stream().count())))));

		// .mapToDouble(Number::doubleValue).sum())));
	}

	// This method uses only RDD filter transformation,
	// Results: Time: 12ms, Count:33 for RONALDO

	private static void sampleWithRDDFilterByPlayerName(String playerName) {
		long startTime = System.currentTimeMillis();

		JavaRDD<WCPlayersModel> Player_Data = WCPlayers_Data.filter(new Function<WCPlayersModel, Boolean>() {
			public Boolean call(WCPlayersModel cupModel) throws Exception {
				return cupModel.getPlayerName().equals(playerName);
			}
		});

		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		System.out.format("With RDD: \t %s \tEst. Time: %d \tC: %d\n", playerName, elapsedTime, Player_Data.count());
	}

	/*
	 * This method uses local model(Round, match, team, player and event bean) Results: Time: 1, Count:33 for RONALDO
	 */
	/*
	 * ADV's-> Timing: 12 times better, Correctness: Using HashMap, clear repeated
	 * 
	 * @DADV: player-match calculate may be worse
	 * 
	 * @TODO: calculate memory usages
	 */
	private void sampleWithModelFilterByPlayerName(String playerName) {
		long startTime = System.currentTimeMillis();

		List<Match> mm = new ArrayList<Match>();
		pm.getRounds().forEach((k, round) -> round.getMatches().forEach((km, match) -> {
			if (match.getTeam1().getPlayers().containsKey(playerName))
				mm.add(match);
			else if (match.getTeam2().getPlayers().containsKey(playerName))
				mm.add(match);
		}));
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		System.out.format("With Model: \t %s \tEst. Time: %d \tC: %d\n", playerName, elapsedTime, mm.size());

	}
}

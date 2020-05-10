package myspark.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;

public class PlayersModel implements Serializable {
	private static Map<Long, Round> rounds;

	public PlayersModel() {
		if (rounds == null)
			rounds = new HashMap<Long, Round>();
	}

	public static Map<Long, Round> getRounds() {
		return rounds;
	}

	public static void setRounds(Map<Long, Round> rounds) {
		PlayersModel.rounds = rounds;
	}

	public JavaRDD<WCPlayersModel> mapPlayersModelRDD(JavaRDD<String> String_Data) {

		JavaRDD<WCPlayersModel> PlayersModel_Data = String_Data.map(new Function<String, WCPlayersModel>() {
			public WCPlayersModel call(String line) throws Exception {

				String[] split = line.split(",", -1);

				WCPlayersModel model = new WCPlayersModel((Long) Long.parseLong(split[0]), (Long) Long.parseLong(split[1]), split[2], split[3], split[4], (Long) Long.parseLong(split[5]), split[6], split[7], split[8]);
				addLineAsModel(model);
				return model;

			}
		});
		PlayersModel_Data.collect();

		return PlayersModel_Data;

	}

	public void writeModelSummary() {
		System.out.println("Rounds :" + rounds.size());
		rounds.forEach((key, round) -> {
			System.out.format("Round: %s, Match Count: %s, Total Player: %s%n ", key, round.getMatches().size(), round.getMatchDetails());
		});
	}

	public Round addLineAsModel(WCPlayersModel model) {
		Round round = null;
		if (!rounds.containsKey(model.getRoundID())) {
			round = new Round(model.getRoundID());
			rounds.put(model.getRoundID(), round);
		} else
			round = rounds.get(model.getRoundID());
		round.roundModel(model);
		return round;

	}

	public void toStrOutList(List<WCPlayersModel> list) {
		for (WCPlayersModel playerModel : list) {
			System.out.println(playerModel.toString());
		}
		System.out.println(list.size());
	}

}

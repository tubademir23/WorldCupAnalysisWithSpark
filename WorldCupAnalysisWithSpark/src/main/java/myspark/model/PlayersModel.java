package myspark.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;

public class PlayersModel implements Serializable {
	private static Map<String, Round> rounds;

	public PlayersModel() {
		if (rounds == null)
			rounds = new HashMap<String, Round>();
	}

	public static Map<String, Round> getRounds() {
		return rounds;
	}

	public static void setRounds(Map<String, Round> rounds) {
		PlayersModel.rounds = rounds;
	}

	public JavaRDD<WCPlayersModel> mapPlayersModel(JavaRDD<String> String_Data) {

		JavaRDD<WCPlayersModel> PlayersModel_Data = String_Data.map(new Function<String, WCPlayersModel>() {
			public WCPlayersModel call(String line) throws Exception {

				String[] split = line.split(",", -1);

				WCPlayersModel model = new WCPlayersModel(split[0], split[1], split[2], split[3], split[4], split[5],
						split[6], split[7], split[8]);
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
			System.out.format("Round: %s, Match Count: %s, Total Player: %s%n ", key, round.getMatches().size(),
					round.getMatchDetails());
		});
	}

	private static void addLineAsModel(WCPlayersModel model) {
		Round round = null;
		if (!rounds.containsKey(model.roundID)) {
			round = new Round(model.roundID);
			rounds.put(model.roundID, round);
		} else
			round = rounds.get(model.roundID);
		round.roundModel(model);

	}

	public void toStrOutList(List<WCPlayersModel> list) {
		for (WCPlayersModel playerModel : list) {
			System.out.println(playerModel.toString());
		}
		System.out.println(list.size());
	}

}

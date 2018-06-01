import com.google.gson.Gson;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.File;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.util.ArrayList;

// 
// Decompiled by Procyon v0.5.30
// 

public class Main {
	
	//need to add a screen to set username and password
	static final String username = "mitchellgmr"; //username for email account used to 
	static final String password = "Fire_Pieman"; //password for email account, currently incorrect password
	static final long waitTime = 300000L;
	static Time expire;
	static Time current;
	private static String id;
	private static String auth;
	static ArrayList<String> userIDs;
	static ArrayList<PlayerContact> contactInfo;
	static int numGames;
	static GMRObject gmr;
	static int numLoops;

	static {
		Main.userIDs = new ArrayList<String>();
		Main.contactInfo = new ArrayList<PlayerContact>();
		Main.numLoops = 0;
	}

	public static void main(final String[] args)
			throws AddressException, MessagingException, IOException, InterruptedException {
		boolean firstInit = true;
		Display[] frames = null;
		boolean[][] hours = null;
		int[] playerInfo = null;
		int[] numText = null;

		firstInit = !load();

		if (!firstInit) {
			firstInit = false;
			frames = new Display[Main.numGames == 0 ? 1 : Main.numGames];
			hours = new boolean[Main.numGames][3];
			playerInfo = new int[Main.numGames];
			numText = new int[Main.numGames];
		}

		while (true) {
			System.out.println("this is the " + Main.numLoops + " loop");
			setCurrentTime();
			final int x = 100;
			int y = 100;
			if (firstInit) { // || (Main.numGames != Main.gmr.Games.size()
				// firstInit, or if the number of games has changed, need to
				// reload data and
				// persist new information to disk
				setup(firstInit);
				save();
				firstInit = false;
				frames = new Display[Main.numGames == 0 ? 1 : Main.numGames];
				hours = new boolean[Main.numGames][3];
				playerInfo = new int[Main.numGames];
				numText = new int[Main.numGames];
			}

			if (Main.numGames != 0) {
				boolean check = true;

				try {
					for(int i = 0; i <  Main.numGames; ++i){
						Game game = Main.gmr.Games.get(i);
					}
					
				} catch (Exception e) {
					check = false;

				}

				if (check) {
					for (int i = 0; i < Main.numGames; ++i) {
						final Game game = Main.gmr.Games.get(i);
						setExpireTime(game);
						final long currentPlayerId = game.CurrentTurn.UserId;
						String currentPlayer = "AI";
						for (int j = 0; j < Main.gmr.Players.size(); ++j) {
							if (Main.gmr.Players.get(j).SteamID == currentPlayerId) {
								currentPlayer = Main.gmr.Players.get(j).PersonaName;
								if (playerInfo[i] != j) {
									numText[i] = 0;
									playerInfo[i] = j;
								}
							}
						}
						final Time timeLeft = Time.subtract(Main.expire, Main.current);
						boolean text = false;
						if (numText[i] == 0) {
							text = true;
						}
						if ((timeLeft.getHour() == 11 && !hours[i][0]) || (timeLeft.getHour() == 5 && !hours[i][1])
								|| (timeLeft.getHour() == 0 && !hours[i][2])) {
							text = true;
							switch (timeLeft.getHour()) {
							case 11: {
								hours[i][0] = true;
								break;
							}
							case 5: {
								hours[i][1] = true;
								break;
							}
							case 0: {
								hours[i][2] = true;
								break;
							}
							}
						} else if (timeLeft.getHour() == 24 || timeLeft.getHour() == 10 || timeLeft.getHour() == 4) {
							for (int k = 0; k < hours[i].length; ++k) {
								hours[i][k] = false;
							}
						}
						if (timeLeft.getHour() == 0 && timeLeft.getMin() > 25 && timeLeft.getMin() < 35) {
							text = true;
						}
						if (text) {
							System.out.println("\ttext sent");
							final String number = String.valueOf(Main.contactInfo.get(playerInfo[i]).getNumber()) + "@"
									+ convertCarrier(Main.contactInfo.get(playerInfo[i]).getCarrier());
							GoogleMail.Send(username, password, number, "New turn",
									"Your turn in " + game.Name + " expires in " + timeLeft.toString()
											+ "\n Please text Mitchell at 425-999-6343 if you have any issues.");
							final int[] array = numText;
							final int n = i;
							++array[n];
						}
						try {
							System.out.println("\t Current Player in game " + i + " is " + currentPlayer);
							System.out.println("\t Time left in game " + i + " is " + timeLeft.toString());
							(frames[i] = new Display(currentPlayer, game.Name, timeLeft.toString(), numText[i], x, y))
									.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
						y += 300;
					}
				}
				if (!check) {
					try {
						(frames[0] = new Display("Could not find information", "Trying again soon...", "0", 0, x, y)).setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} else {

				try {
					(frames[0] = new Display("No Players", "No Games", "0", 0, x, y)).setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			Thread.sleep(300000L);
			Display[] array2;
			for (int length = (array2 = frames).length, l = 0; l < length; ++l) {
				final Display frame = array2[l];
				frame.dispose();
			}
			System.out.println("\tCurrent id is " + Main.id);
			System.out.println("\tCurrent auth key is " + Main.auth);
			try{
				getGMRInfo("http://multiplayerrobot.com/api/Diplomacy/GetGamesAndPlayers?playerIDText=" + Main.id
						+ "&authKey=" + Main.auth);
			}catch  (Exception  IOException){
				System.out.println("rip io line 183");
			}
			
			++Main.numLoops;
		}
	}

	public static String convertCarrier(final String a) {
		switch (a) {
		case "T-Mobile": {
			return "tmomail.net";
		}
		case "AT&T": {
			return "txt.att.net";
		}
		case "Verizon": {
			return "vtext.com";
		}
		default:
			break;
		}
		return "carrier not found";
	}

	public static void save() throws FileNotFoundException {
		final PrintStream ps = new PrintStream(new File("GMRTextData.txt"));
		ps.println(Main.numGames);
		ps.println(Main.id);
		ps.println(Main.auth);
		for (int i = 0; i < Main.contactInfo.size(); ++i) {
			ps.println(Main.contactInfo.get(i));
		}
	}

	public static boolean load() throws IOException {
		Scanner scanner;
		try {
			scanner = new Scanner(new File("GMRTextData.txt"));
		} catch (FileNotFoundException e) {

			System.out.println("could not load");
			return false;
		}
		
		int lineCount = 0;
		
		String games = scanner.nextLine();
		Scanner numGame = new Scanner(games);
		Main.numGames = numGame.nextInt();
		numGame.close();
		
		String bigId = scanner.nextLine();
		Scanner getId = new Scanner(bigId);
		Main.id = getId.next();
		getId.close();
		
		String theAuth = scanner.nextLine();
		Scanner getAuth = new Scanner(theAuth);
		Main.auth = getAuth.next();
		getAuth.close();
		
		while (scanner.hasNextLine()) {
			String store = scanner.nextLine();
			Scanner storeScan = new Scanner(store);
			PlayerContact c = new PlayerContact(storeScan.nextLong(), storeScan.next(), storeScan.next());
			Main.contactInfo.add(c);
			++lineCount;
			storeScan.close();
		}
		
		scanner.close();
		
		if (lineCount > 0) {
			final String url = "http://multiplayerrobot.com/api/Diplomacy/GetGamesAndPlayers?playerIDText=" + Main.id
					+ "&authKey=" + Main.auth;
			getGMRInfo(url);
		}

		return lineCount != 0;
	}

	public static void setExpireTime(final Game game) {
		final Turn turn = game.CurrentTurn;
		String finish = turn.Expires;
		String fHour;
		String fMinute;

		if (finish != null) {
			final int t = finish.indexOf("T");
			finish = finish.substring(t + 1, t + 9);
			final int colon = finish.indexOf(":");
			fHour = finish.substring(0, colon);
			fMinute = finish.substring(colon + 1, colon + 3);
		} else {
			// For now, if there is no expire time assume it is 12 hours
			fHour = "12";
			fMinute = "00";
		}
		Main.expire = new Time(Integer.parseInt(fHour), Integer.parseInt(fMinute));

	}

	public static void setCurrentTime() {
		final Date date = new Date();
		final String gmt = date.toGMTString();
		final int colon = gmt.indexOf(":");
		final String cHour = gmt.substring(colon - 2, colon);
		final String cMinute = gmt.substring(colon + 1, colon + 3);
		Main.current = new Time(Integer.parseInt(cHour), Integer.parseInt(cMinute));
		System.out.println("\ttime is " + Main.current);
	}

	public static void setId(final String id) {
		Main.id = id;
		System.out.println("set id");
	}

	public static void setAuth(final String auth) {
		Main.auth = auth;
		System.out.println("set auth");
	}

	public static void getGMRInfo(final String url) throws IOException {
		final URL GMR = new URL(url);
		
			final BufferedReader in = new BufferedReader(new InputStreamReader(GMR.openStream()));
			String json = "";
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				json = String.valueOf(json) + inputLine + "\n";
			}
			in.close();
			System.out.println("\tThis is the Json: " + json);
			final Gson gson = new Gson();
			Main.gmr = (GMRObject) gson.fromJson(json, (Class) GMRObject.class);
		
		
	}

	public static void setup(boolean showUI) throws IOException {
		if (showUI) {
			try {
				final Startup frame = new Startup();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String url = "http://multiplayerrobot.com/api/Diplomacy/GetGamesAndPlayers?playerIDText=" + Main.id
				+ "&authKey=" + Main.auth;
		getGMRInfo(url);
		Main.numGames = Main.gmr.Games.size();
		if (Main.numGames != 0) {
			for (int i = 0; i < Main.numGames; ++i) {
				final ArrayList<GamePlayer> players = Main.gmr.Games.get(i).Players;
				for (int numPlayers = players.size(), j = 0; j < numPlayers; ++j) {
					boolean repeat = false;
					final String pName = new StringBuilder().append(players.get(j).UserId).toString();
					if (pName.equals("0")) {
						repeat = true;
					}
					for (int w = 0; w < Main.userIDs.size(); ++w) {
						if (pName.equals(Main.userIDs.get(w))) {
							repeat = true;
						}
					}
					if (!repeat) {
						Main.userIDs.add(pName);
					}
				}
			}
			String newID = new StringBuilder().append(Main.userIDs.get(0)).toString();
			for (int k = 1; k < Main.userIDs.size(); ++k) {
				newID = String.valueOf(newID) + "_" + Main.userIDs.get(k);
			}
			Main.id = newID;
			url = "http://multiplayerrobot.com/api/Diplomacy/GetGamesAndPlayers?playerIDText=" + newID + "&authKey="
					+ Main.auth;
			getGMRInfo(url);
			final ArrayList<Player> players2 = Main.gmr.Players;
			for (int numPlayers = players2.size(), j = 0; j < numPlayers; ++j) {
				final PlayerContact playa = new PlayerContact(players2.get(j).SteamID);
				try {
					final Phone frame2 = new Phone(players2.get(j).PersonaName, playa);
					frame2.setVisible(true);
					Main.contactInfo.add(playa);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} else {
			System.out.println("No active games!");
		}
	}
}

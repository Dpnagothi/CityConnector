package com.connection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Connector {

	public static final String SEPERATOR = ",";

	public static String connected(String origin, String destination) {
		try {
			Map<String, Set<String>> cityMap = parseFile();
			boolean connected = isConnected(cityMap, origin, destination);
			return setResult(connected);
		} catch (Exception e) {
			return "no";
		}
	}

	private static Map<String, Set<String>> parseFile() throws IOException {
		Map<String, Set<String>> cityToNodeMap = new HashMap<String, Set<String>>();

		BufferedReader br = null;
		try {
			Reader fileReader = new FileReader("/resources/City.txt");
			br = new BufferedReader(fileReader);
			String line = br.readLine();
			while (line != null && !line.isEmpty()) {
				String[] cities = line.split(SEPERATOR);
				String city1 = cities[0].trim();
				String city2 = cities[1].trim();

				Set<String> firstConnections = getConnections(cityToNodeMap, city1);
				Set<String> secondConnections = getConnections(cityToNodeMap, city2);

				secondConnections.add(city1);
				firstConnections.add(city2);

				line = br.readLine();
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}

		return cityToNodeMap;
	}

	private static Set<String> getConnections(Map<String, Set<String>> map, String city) {
		if (!map.containsKey(city)) {
			map.put(city, new HashSet<String>());
		}
		return map.get(city);
	}

	private static boolean isConnected(Map<String, Set<String>> cityMap, String origin, String destination) {
		boolean connected = origin.equals(destination);
		if (cityMap.containsKey(origin) && cityMap.containsKey(destination)) {
			Queue<String> citiesToVisit = new LinkedList<String>();
			Set<String> citiesAlreadyVisited = new HashSet<String>();

			citiesToVisit.add(origin);

			while (!citiesToVisit.isEmpty() && !connected) {
				String city = citiesToVisit.poll();
				connected = city.equals(destination);

				Set<String> possibleConnections = cityMap.get(city);
				for (String possibleCity : possibleConnections) {
					if (!citiesAlreadyVisited.contains(possibleCity)) {
						citiesToVisit.add(possibleCity);
						citiesAlreadyVisited.add(possibleCity);
					}
				}
			}
		}

		return connected;
	}

	private static String setResult(boolean connected) {
		if (connected) {
			return "yes";
		} else {
			return "no";
		}
	}

}

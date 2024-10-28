package it.unibo.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Example class using {@link List} and {@link Map}.
 *
 */
public final class UseListsAndMaps {

	private static final int MIN_VALUE_INCLUDED = 1000;
	private static final int MAX_VALUE_EXCLUDED = 2000;

	private UseListsAndMaps() {
	}

	/**
	 * @param s
	 *          unused
	 */
	public static void main(final String... s) {
		/*
		 * 1) Create a new ArrayList<Integer>, and populate it with the numbers
		 * from 1000 (included) to 2000 (excluded).
		 */
		List<Integer> arrayList = new ArrayList<>();

		for (int i = MIN_VALUE_INCLUDED; i < MAX_VALUE_EXCLUDED; i++) {
			arrayList.add(i);
		}

		/*
		 * 2) Create a new LinkedList<Integer> and, in a single line of code
		 * without using any looping construct (for, while), populate it with
		 * the same contents of the list of point 1.
		 */
		List<Integer> linkedList = new LinkedList<>(arrayList);

		/*
		 * 3) Using "set" and "get" and "size" methods, swap the first and last
		 * element of the first list. You can not use any "magic number".
		 * (Suggestion: use a temporary variable)
		 */
		for (int i = 0; i < arrayList.size() - 1 - i; i++) {
			int tmp = arrayList.get(arrayList.size() - 1 - i);
			arrayList.set(arrayList.size() - 1 - i, arrayList.get(i));
			arrayList.set(i, tmp);
		}

		/*
		 * 4) Using a single for-each, print the contents of the arraylist.
		 */
		System.out.print("[");
		for (Integer integer : arrayList) {
			System.out.print(integer + ", ");
		}
		System.out.println("]");

		/*
		 * 5) Measure the performance of inserting new elements in the head of
		 * the collection: measure the time required to add 100.000 elements as
		 * first element of the collection for both ArrayList and LinkedList,
		 * using the previous lists. In order to measure times, use as example
		 * TestPerformance.java.
		 */
		testAddFirst(arrayList, linkedList);

		/*
		 * 6) Measure the performance of reading 1000 times an element whose
		 * position is in the middle of the collection for both ArrayList and
		 * LinkedList, using the collections of point 5. In order to measure
		 * times, use as example TestPerformance.java.
		 */
		testGetMiddle(arrayList, linkedList);

		/*
		 * 7) Build a new Map that associates to each continent's name its
		 * population:
		 *
		 * Africa -> 1,110,635,000
		 *
		 * Americas -> 972,005,000
		 *
		 * Antarctica -> 0
		 *
		 * Asia -> 4,298,723,000
		 *
		 * Europe -> 742,452,000
		 *
		 * Oceania -> 38,304,000
		 */
		Map<String, Long> map = new HashMap<>();
		map.put("Africa", 1_110_635_000l);
		map.put("Americas", 972_005_000l);
		map.put("Antarctica", 0l);
		map.put("Asia", 4_298_723_000l);
		map.put("Europe", 742_452_000l);
		map.put("Oceania", 38_304_000l);

		/*
		 * 8) Compute the population of the world
		 */
		long sum = 0l;
		for (long population : map.values()) {
			sum = sum + population;
		}

		System.out.println(sum);
	}

	private static void testAddFirst(List<Integer> arrayList, List<Integer> linkedList) {
		// #region ArrayList
		// Prepare a variable for measuring time
		long time = System.nanoTime();
		// Run the benchmark
		for (int i = 1; i <= 100_000; i++) {
			arrayList.addFirst(i);
		}
		// Compute the time and print result
		time = System.nanoTime() - time;
		var millis = TimeUnit.NANOSECONDS.toMillis(time);
		System.out.println(// NOPMD
				"Inserting " + linkedList.size() + " ints in a Set took " + time + "ns (" + millis + "ms)"); // #endregion

		// #region LinkedList
		// Prepare a variable for measuring time
		time = System.nanoTime();
		// Run the benchmark
		for (int i = 1; i <= 100_000; i++) {
			linkedList.addFirst(i);
		}
		// Compute the time and print result
		time = System.nanoTime() - time;
		millis = TimeUnit.NANOSECONDS.toMillis(time);
		System.out.println(// NOPMD
				"Inserting "
						+ linkedList.size()
						+ " ints in a Set took "
						+ time
						+ "ns ("
						+ millis
						+ "ms)");
		// #endregion
	}

	private static void testGetMiddle(List<Integer> arrayList, List<Integer> linkedList) {
		// #region ArrayList
		int middle = arrayList.size() / 2;
		// Prepare a variable for measuring time
		long time = System.nanoTime();
		// Run the benchmark
		for (int i = 1; i <= 1_000; i++) {
			arrayList.get(middle);
		}
		// Compute the time and print result
		time = System.nanoTime() - time;
		var millis = TimeUnit.NANOSECONDS.toMillis(time);
		System.out.println(// NOPMD
				"Inserting " + linkedList.size() + " ints in a Set took " + time + "ns (" + millis + "ms)"); // #endregion

		// #region LinkedList
		middle = linkedList.size() / 2;
		// Prepare a variable for measuring time
		time = System.nanoTime();
		// Run the benchmark
		for (int i = 1; i <= 1_000; i++) {
			linkedList.get(middle);
		}
		// Compute the time and print result
		time = System.nanoTime() - time;
		millis = TimeUnit.NANOSECONDS.toMillis(time);
		System.out.println(// NOPMD
				"Inserting "
						+ linkedList.size()
						+ " ints in a Set took "
						+ time
						+ "ns ("
						+ millis
						+ "ms)");
		// #endregion

	}
}

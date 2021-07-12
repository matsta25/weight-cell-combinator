import org.paukov.combinatorics3.Generator;

import java.util.*;

public class WeightCellCombinator {

    public static final int ALL_COUNT = 10;
    public static final int TARGET_COUNT = 3;
    public static List<Integer> WEIGHTS = new ArrayList<>();
    public static final int TARGET = 1000;

    public static Map<Integer, List<Integer>> results = new HashMap<>();

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        seedWEIGHTS();

        Generator.combination(WEIGHTS)
                .simple(TARGET_COUNT)
                .stream()
                .forEach(integers -> {
                    int sum = integers.stream().mapToInt(Integer::valueOf).sum();
                    int average = sum / TARGET_COUNT;
                    results.put(average, integers);
                });

        Map.Entry<Integer, List<Integer>> best = results.entrySet()
                .stream()
                .min(Comparator.comparingInt(i -> Math.abs(i.getKey() - TARGET)))
                .orElseThrow(() -> new NoSuchElementException("No target present"));

        int deviation = Math.abs(best.getKey() - TARGET);

        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000 + ".");

        System.out.println("Best results: " + best.getValue() + ", average: " + best.getKey() + ", deviation: " + deviation + ".");

        WEIGHTS.forEach(integer -> {
                    if (best.getValue().contains(integer)) {
                        System.out.println("->\t" + integer + "\t<-\t");
                    } else {
                        System.out.println("\t" + integer + "\t");
                    }
                });
    }

    private static void seedWEIGHTS() {
        Random rand = new Random();

        for (int i = 0; i < ALL_COUNT; i++) {
            WEIGHTS.add((int) (Math.random() * 200 + 900));
        }
    }
}

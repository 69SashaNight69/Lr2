import java.util.*;
import java.util.concurrent.*;

public class PrimeRangeProcessor {
    private static final int MIN_ARRAY_SIZE = 40;
    private static final int MAX_ARRAY_SIZE = 60;
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 1000;

    private static final int[][] RANGES = {
            {1, 100}, {101, 200}, {201, 300}, {301, 400},
            {401, 500}, {501, 600}, {601, 700}, {701, 800},
            {801, 900}, {901, 1000}
    };

    public static void main(String[] args) {
        int arraySize = new Random().nextInt(MAX_ARRAY_SIZE - MIN_ARRAY_SIZE + 1) + MIN_ARRAY_SIZE;
        int[] numbers = new Random().ints(arraySize, MIN_VALUE, MAX_VALUE + 1).toArray();

        System.out.println("Generated Array: " + Arrays.toString(numbers));

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number N to check primes up to N in each range: ");
        int N = scanner.nextInt();

        Map<String, CopyOnWriteArrayList<Integer>> rangesMap = distributeNumbersByRanges(numbers);
        rangesMap.forEach((range, nums) -> System.out.println("Range " + range + ": " + nums));

        ExecutorService executor = Executors.newFixedThreadPool(RANGES.length);
        List<Future<List<Integer>>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        for (int[] range : RANGES) {
            CopyOnWriteArrayList<Integer> rangeNumbers = rangesMap.getOrDefault(range[0] + "-" + range[1], new CopyOnWriteArrayList<>());
            Callable<List<Integer>> task = new PrimeCheckerTask(rangeNumbers, N);
            futures.add(executor.submit(task));
        }

        executor.shutdown();  // Запит на завершення всіх потоків
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                System.out.println("Some tasks did not complete within the timeout.");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.out.println("Execution was interrupted.");
            executor.shutdownNow();
        }

        System.out.println("Prime numbers in each range up to " + N + ":");
        try {
            for (int i = 0; i < RANGES.length; i++) {
                Future<List<Integer>> future = futures.get(i);
                if (future.isDone() && !future.isCancelled()) {
                    List<Integer> primes = future.get();  // Отримання результату після перевірки isDone()
                    System.out.println("Range " + RANGES[i][0] + "-" + RANGES[i][1] + ": " + primes);
                } else if (future.isCancelled()) {
                    System.out.println("Task for range " + RANGES[i][0] + "-" + RANGES[i][1] + " was cancelled.");
                } else {
                    System.out.println("Task for range " + RANGES[i][0] + "-" + RANGES[i][1] + " is still in progress.");
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " milliseconds");
    }

    private static Map<String, CopyOnWriteArrayList<Integer>> distributeNumbersByRanges(int[] numbers) {
        Map<String, CopyOnWriteArrayList<Integer>> rangesMap = new HashMap<>();
        for (int num : numbers) {
            for (int[] range : RANGES) {
                if (num >= range[0] && num <= range[1]) {
                    String rangeKey = range[0] + "-" + range[1];
                    rangesMap.computeIfAbsent(rangeKey, k -> new CopyOnWriteArrayList<>()).add(num);
                }
            }
        }
        return rangesMap;
    }

}

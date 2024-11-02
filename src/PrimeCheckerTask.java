import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

class PrimeCheckerTask implements Callable<List<Integer>> {
    private final List<Integer> numbers;
    private final int maxPrime;

    PrimeCheckerTask(List<Integer> numbers, int maxPrime) {
        this.numbers = numbers;
        this.maxPrime = maxPrime;
    }

    @Override
    public List<Integer> call() {
        return numbers.stream()
                .filter(num -> num <= maxPrime && isPrime(num))
                .collect(Collectors.toList());
    }

    private boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}

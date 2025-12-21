public static final Path FILE_PATH = Path.of("resources/day03/lobby.txt");
//public static final Path FILE_PATH = Path.of("resources/day03/lobby_small.txt");

/***
 *
 * find the largest possible joltage each bank can produce. In the above example:
 *
 *     In 987654321111111, you can make the largest joltage possible, 98, by turning on the first two batteries.
 *     In 811111111111119, you can make the largest joltage possible by turning on the batteries labeled 8 and 9, producing 89 jolts.
 *     In 234234234234278, you can make 78 by turning on the last two batteries (marked 7 and 8).
 *     In 818181911112111, the largest joltage you can produce is 92.
 *
 *     For lobby_small.txt -> result is: 357
 *     For lobby.txt -> result is: 16946
 *
 * The total output joltage is the sum of the maximum joltage from each bank, so in this example, the total output joltage is 98 + 89 + 78 + 92 = 357.
 *
 * There are many batteries in front of you. Find the maximum joltage possible from each bank; what is the total output joltage?
 *
 */

void main() throws IOException {
    try (var lines = Files.lines(FILE_PATH)) {
        long totalSum = lines
                .filter(Predicate.not(String::isBlank))
                .mapToLong(this::maxJoltage)
                .sum();

        System.out.println("total is: " + totalSum);
    }
}

private long maxJoltage(String line) {
    int maxJoltage = -1;
    int highestFirst = line.charAt(0) - '0';

    for (int i = 1; i < line.length(); i++) {
        int current = line.charAt(i) - '0';
        maxJoltage = Math.max(maxJoltage, highestFirst * 10 + current);
        highestFirst = Math.max(highestFirst, current);
    }
    return maxJoltage;
}
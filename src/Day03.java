public static final Path FILE_PATH = Path.of("resources/day03/lobby.txt");
//public static final Path FILE_PATH = Path.of("resources/day03/lobby_small.txt");

/***
 * Part I:
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
 * Find the maximum joltage possible from each bank
 *
 * Part II:
 * Now, you need to make the largest joltage by turning on exactly twelve batteries within each bank.
 * The joltage output for the bank is still the number formed by the digits of the batteries you've turned on;
 * the only difference is that now there will be 12 digits in each bank's joltage output instead of two.
 */

void main() throws IOException {
    try (var lines = Files.lines(FILE_PATH)) {
        long totalSum = lines
                .filter(Predicate.not(String::isBlank))
                .mapToLong(line -> maxJoltage(line, 12))
                .sum();

        System.out.println("total is: " + totalSum);
    }
}

private long maxJoltage(String line, int keep) {
    int toRemove = line.length() - keep;
    var stack = new StringBuilder();

    for (char current : line.toCharArray()) {
        while (toRemove > 0 && !stack.isEmpty() && stack.charAt(stack.length() - 1) < current) {
            stack.deleteCharAt(stack.length() - 1);
            toRemove--;
        }
        stack.append(current);
    }

    // In case we didn't remove enough (monotonic input)
    return Long.parseLong(stack.substring(0, keep));
}
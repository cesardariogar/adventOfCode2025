static final String FILE_PATH = "resources/day02/gift_shop_small.txt";

record Range(long start, long end) {
    static Range parse(String s) {
        String[] parts = s.trim().split("-");
        return new Range(
                Long.parseLong(parts[0]),
                Long.parseLong(parts[1])
        );
    }
}


/***
 * find the invalid IDs by looking for any ID which is made only of some sequence of digits repeated twice.
 * So, 55 (5 twice), 6464 (64 twice), and 123123 (123 twice) would all be invalid IDs.
 */
void main() throws IOException {
    String input = Files.readString(Path.of(FILE_PATH));
    long totalSum = Stream.of(input.split(","))
            .map(Range::parse)
            .mapToLong(this::sumInvalidIdsInRange)
            .sum();
    ;
    System.out.println("Total Sum of invalid Id's: " + totalSum);
}

private long sumInvalidIdsInRange(Range range) {
    return LongStream.rangeClosed(range.start(), range.end())
            .filter(this::isInvalidId)
            .sum();
}

private boolean isInvalidId(long id) {
    String s = String.valueOf(id);
    int length = s.length();
    for (int parts = 2; parts <= length; parts++) {
        if (length % parts != 0) {
            continue;
        }

        int blockSize = length / parts;
        String block = s.substring(0, blockSize);
        String seq = block.repeat(parts);

        if (seq.equals(s)) {
            return true;
        }
    }

    return false;
}

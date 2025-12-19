final String filePath = "resources/day02/gift_shop.txt";
long totalSumOfInvalidIds = 0;

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
    String input = Files.readString(Path.of(filePath));
    Stream.of(input.split(","))
            .map(Range::parse)
            .forEach(this::processRange);
    System.out.println("Total Sum of invalid Id's: " + totalSumOfInvalidIds);
}

private void processRange(Range range) {
    for (long current = range.start(); current <= range.end(); current++) {
        if (idHasRepeatedSequence(current)) {
            totalSumOfInvalidIds += current;
        }
    }
}

private boolean idHasRepeatedSequence(long id) {
    String idStr = String.valueOf(id);
    int halfIdx = idStr.length() / 2;
    return idStr.substring(0, halfIdx).equals(idStr.substring(halfIdx));
}
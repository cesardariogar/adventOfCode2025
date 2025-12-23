//static final String FILE_PATH = "resources/Day05/cafeteria_small.txt";
static final String FILE_PATH = "resources/Day05/cafeteria.txt";

record Range(long start, long end) {
    static Range parse(String s) {
        String[] parts = s.trim().split("-");
        return new Range(
                Long.parseLong(parts[0]),
                Long.parseLong(parts[1])
        );
    }

    boolean contains(long value) {
        return value >= start && value <= end;
    }
}

/***
 *
 * The Elves in the kitchen explain the situation: because of their complicated new inventory management system, they can't figure out which of their ingredients are fresh and which are spoiled. When you ask how it works, they give you a copy of their database (your puzzle input).
 * The database operates on ingredient IDs. It consists of a list of fresh ingredient ID ranges, a blank line, and a list of available ingredient IDs.
 * The fresh ID ranges are inclusive: the range 3-5 means that ingredient IDs 3, 4, and 5 are all fresh. The ranges can also overlap; an ingredient ID is fresh if it is in any range.
 *
 * The Elves are trying to determine which of the available ingredient IDs are fresh. In this example, this is done as follows:
 *
 *     Ingredient ID 1 is spoiled because it does not fall into any range.
 *     Ingredient ID 5 is fresh because it falls into range 3-5.
 *     Ingredient ID 8 is spoiled.
 *     Ingredient ID 11 is fresh because it falls into range 10-14.
 *     Ingredient ID 17 is fresh because it falls into range 16-20 as well as range 12-18.
 *     Ingredient ID 32 is spoiled.
 *
 * So, in this example, 3 of the available ingredient IDs are fresh.
 * Process the database file from the new inventory management system. How many of the available ingredient IDs are fresh?
 */
void main() throws IOException {
    var sections = Files.readString(Path.of(FILE_PATH)).split("\\R\\R"); // blank line (any line ending)

    var ranges = sections[0].lines()
            .filter(line -> !line.isBlank())
            .map(Range::parse)
            .sorted(Comparator.comparingLong(Range::start))
            .toList();

    var merged = mergeRanges(ranges);

    var ingredientIds = sections[1].lines()
            .filter(line -> !line.isBlank())
            .mapToLong(Long::parseLong);

    // Part I
    long freshCount = ingredientIds
            .filter(id -> merged.stream().anyMatch(range -> range.contains(id)))
            .count();

    // Part II
    long totalIdsSum = merged
            .stream()
            .mapToLong(r -> r.end() - r.start() + 1)
            .sum();

    System.out.println("Part I :" + freshCount);
    System.out.println("Part II :" + totalIdsSum);
}

private List<Range> mergeRanges(List<Range> ranges) {
    List<Range> merged = new ArrayList<>();

    Range current = ranges.getFirst();
    for (int i = 1; i < ranges.size(); i++) {
        Range next = ranges.get(i);

        if (next.start() <= current.end() + 1) {
            current = new Range(current.start(), Math.max(current.end(), next.end()));
        } else {
            merged.add(current);
            current = next;
        }
    }

    merged.add(current);
    return merged;
}



void main() throws IOException {
    String FILE_PATH = "resources/Day07/laboratories.txt"; // Part I = 1640, Part II = 40999072541589
//    String FILE_PATH = "resources/Day07/laboratories_small.txt"; // Part I = 21, Part II = 40

    List<String> grid = Files.readAllLines(Path.of(FILE_PATH));
    int startColumn = grid.get(0).indexOf('S');
    int part1Splits = countSplits(grid, startColumn);
    long part2Timelines = countTimelines(grid, startColumn);

    System.out.println("Part I: " + part1Splits);
    System.out.println("Part II: " + part2Timelines);
}

private static int countSplits(List<String> grid, int startColumn) {
    Map<Integer, Long> activeColumns = Map.of(startColumn, 1L);
    int splitCount = 0;

    for (int row = 1; row < grid.size(); row++) {
        Map<Integer, Long> nextRow = new HashMap<>();
        String line = grid.get(row);

        for (var entry : activeColumns.entrySet()) {
            int col = entry.getKey();
            long count = entry.getValue();

            if (isSplitter(line, col)) {
                splitCount++;
                addTo(nextRow, col - 1, count);
                addTo(nextRow, col + 1, count);
            } else {
                addTo(nextRow, col, count);
            }
        }

        activeColumns = nextRow;
    }

    return splitCount;
}

private static long countTimelines(List<String> grid, int startColumn) {
    Map<Integer, Long> activeColumns = new HashMap<>();
    activeColumns.put(startColumn, 1L);

    for (int row = 1; row < grid.size(); row++) {
        Map<Integer, Long> nextRow = new HashMap<>();
        String line = grid.get(row);

        for (var entry : activeColumns.entrySet()) {
            int col = entry.getKey();
            long count = entry.getValue();

            if (isSplitter(line, col)) {
                addTo(nextRow, col - 1, count);
                addTo(nextRow, col + 1, count);
            } else {
                addTo(nextRow, col, count);
            }
        }

        activeColumns = nextRow;
    }

    return activeColumns.values()
            .stream()
            .mapToLong(Long::longValue)
            .sum();
}

private static boolean isSplitter(String line, int col) {
    return col >= 0 && col < line.length() && line.charAt(col) == '^';
}

private static void addTo(Map<Integer, Long> map, int key, long value) {
    map.merge(key, value, Long::sum);
}
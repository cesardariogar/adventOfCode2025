void main() throws IOException {
    String FILE_PATH = "resources/Day07/laboratories.txt"; // Part I = 1640, Part II = 40999072541589
//    String FILE_PATH = "resources/Day07/laboratories_small.txt"; // Part I = 21, Part II = 40

    var lines = Files.readAllLines(Path.of(FILE_PATH));
    int start = lines.getFirst().indexOf('S');
    int splitCounter = 0;

    Map<Integer, Long> pathEdgesMap = new HashMap<>();
    pathEdgesMap.put(start, 1L);

    for (int r = 1; r < lines.size(); r++) {
        Map<Integer, Long> next = new HashMap<>();
        String row = lines.get(r);

        for (var entry : pathEdgesMap.entrySet()) {
            int col = entry.getKey();
            long count = entry.getValue();

            if (col >= 0 && col < row.length() && row.charAt(col) == '^') {
                splitCounter++;
                next.put(col - 1, next.getOrDefault(col - 1, 0L) + count);
                next.put(col + 1, next.getOrDefault(col + 1, 0L) + count);
            }
            else {
                next.put(col, next.getOrDefault(col, 0L) + count);
            }
        }
        pathEdgesMap = next;
    }

    System.out.println("Part I " + splitCounter);
    long result = 0;
    for (long count : pathEdgesMap.values()) {
        result += count;
    }
    System.out.println("Part II: " + result);
}
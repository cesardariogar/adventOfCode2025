// static final String FILE_PATH = "resources/day04/printing_small.txt"; // Total = 13;
static final String FILE_PATH = "resources/day04/printing.txt"; // Part I = Total = 1547, Part II = 8948
final char PAPER_ROLL = '@';
final int MAX_ADJACENT_ROLLS = 3;

static final int[][] NEIGHBORS = {
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1}, {0, 1},
        {1, -1}, {1, 0}, {1, 1}
};

void main() throws IOException {
    var lines = Files.readAllLines(Path.of(FILE_PATH))
            .stream()
            .filter(Predicate.not(String::isEmpty))
            .collect(Collectors.toList());

    int totalRemoved = 0;
    int removed;

    do {
        removed = processMaze(lines);
        totalRemoved += removed;
    } while (removed > 0);

    System.out.println("Total: " + totalRemoved);
}

private int processMaze(List<String> lines) {
    int removed = 0;

    for (int row = 0; row < lines.size(); row++) {
        var current = new StringBuilder(lines.get(row));

        for (int col = 0; col < current.length(); col++) {
            if (isPaperRoll(current.charAt(col))) {
                int adjacent = countAdjacentRolls(lines, row, col);

                if (adjacent <= MAX_ADJACENT_ROLLS) {
                    current.setCharAt(col, '.');
                    removed++;
                }
            }
        }
        lines.set(row, current.toString());
    }

    return removed;
}

private int countAdjacentRolls(List<String> lines, int row, int col) {
    return (int) Arrays.stream(NEIGHBORS)
            .map(offset -> new int[]{row + offset[0], col + offset[1]})
            .filter(pos -> isInside(lines, pos[0], pos[1]))
            .filter(pos -> isPaperRoll(lines.get(pos[0]).charAt(pos[1])))
            .count();
}

private boolean isInside(List<String> lines, int row, int col) {
    return row >= 0 && row < lines.size()
            && col >= 0 && col < lines.get(row).length();
}

private boolean isPaperRoll(char c) {
    return (c == PAPER_ROLL);
}



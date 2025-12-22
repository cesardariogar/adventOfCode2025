// static final String FILE_PATH = "resources/day04/printing_small.txt"; // Total = 13;
static final String FILE_PATH = "resources/day04/printing.txt"; // Part I = Total = 1547, Part II = 8948
final char PAPER_ROLL = '@';
final int MAX_ADJACENT_ROLLS = 3;

void main() throws IOException {
    List<String> linesList;
    try (var lines = Files.lines(Path.of(FILE_PATH))) {
        linesList = lines
                .filter(Predicate.not(String::isEmpty))
                .collect(Collectors.toList());
    }

    int rollsCanBeRemovedTotal = 0;
    int currentlyRemoved = 0;
    do {
        rollsCanBeRemovedTotal += (currentlyRemoved = processMaze(linesList));
    } while (currentlyRemoved != 0);
    System.out.println("Total: " + rollsCanBeRemovedTotal);
}

private int processMaze(List<String> lines) {
    int blockAvailableRollsSum = 0;
    for (int l = 0; l < lines.size(); l++) {
        StringBuilder line = new StringBuilder(lines.get(l));
        for (int c = 0; c < line.length(); c++) {
            if (isPaperRoll(line.charAt(c)) == 1) {
                boolean hasBackColumn = (c > 0);
                boolean hasFrontColumn = (c <= line.length() - 2);
                boolean hasLineAbove = (l > 0);
                boolean hasLineBelow = (l <= lines.size() - 2);
                int currentAdjacentRolls = 0;
                if (hasBackColumn) {
                    currentAdjacentRolls += isPaperRoll(line.charAt(c - 1));
                    if (hasLineAbove) {
                        currentAdjacentRolls += isPaperRoll(lines.get(l - 1).charAt(c - 1));
                    }
                    if (hasLineBelow) {
                        currentAdjacentRolls += isPaperRoll(lines.get(l + 1).charAt(c - 1));
                    }
                }
                if (hasLineAbove) {
                    currentAdjacentRolls += isPaperRoll(lines.get(l - 1).charAt(c));
                }
                if (hasLineBelow) {
                    currentAdjacentRolls += isPaperRoll(lines.get(l + 1).charAt(c));
                }
                if (hasFrontColumn) {
                    currentAdjacentRolls += isPaperRoll(line.charAt(c + 1));
                    if (hasLineAbove) {
                        currentAdjacentRolls += isPaperRoll(lines.get(l - 1).charAt(c + 1));
                    }
                    if (hasLineBelow) {
                        currentAdjacentRolls += isPaperRoll(lines.get(l + 1).charAt(c + 1));
                    }
                }
                if (currentAdjacentRolls <= MAX_ADJACENT_ROLLS) {
                    blockAvailableRollsSum++;
                    line.setCharAt(c, '.');
                    lines.set(l, line.toString());
                }
            }
        }
    }
    return blockAvailableRollsSum;
}

private int isPaperRoll(char c) {
    return (c == PAPER_ROLL) ? 1 : 0;
}



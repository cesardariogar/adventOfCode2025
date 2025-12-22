// static final String FILE_PATH = "resources/day04/printing_small.txt"; // Total = 13;
static final String FILE_PATH = "resources/day04/printing.txt"; // Total = 1547
final char PAPER_ROLL = '@';
final int MAX_ADJACENT_ROLLS = 3;

void main() throws IOException {
    int availablePaperRollsTotal = 0;
    try (var lines = Files.lines(Path.of(FILE_PATH))) {
        List<String> linesList = lines
                .filter(Predicate.not(String::isEmpty))
                .toList();
        availablePaperRollsTotal = processMaze(linesList);
    }
    System.out.println("Total: " + availablePaperRollsTotal);
}

private int processMaze(List<String> lines) {
    int blockAvailableRollsSum = 0;
    for (int l = 0; l < lines.size(); l++) {
        String line = lines.get(l);
        for (int c = 0; c < line.length(); c++) {
            char current = line.charAt(c);
            if (isPaperRoll(current) == 0) {
                continue;
            } else {
                int currentAdjacentRolls = 0;
                boolean hasBackColumn = (c > 0);
                boolean hasFrontColumn = (c <= line.length() - 2);
                boolean hasLineAbove = (l > 0);
                boolean hasLineBelow = (l <= lines.size() - 2);
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
                }
            }
        }
    }
    return blockAvailableRollsSum;
}

private int isPaperRoll(char c) {
    return (c == PAPER_ROLL) ? 1 : 0;
}



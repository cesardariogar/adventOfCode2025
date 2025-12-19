final int DIAL_SIZE = 100;
final int DIAL_INITIAL_POSITION = 50;
static int LANDINGS_ON_ZERO = 0;
static int CROSSINGS_ON_ZERO = 0;

record Movement(boolean rotateLeft, int steps) {}

class CircularLock {
    int position;

    public CircularLock(int currentPosition) {
        this.position = currentPosition;
    }

    public void rotateDial(Movement move) {
        int unboundedNext = (move.rotateLeft()) ? (position - move.steps) : (position + move.steps);
        int next = Math.floorMod(unboundedNext, DIAL_SIZE); // Position bounded between 0-99 (DIAL_SIZE)
        LANDINGS_ON_ZERO += (next == 0) ? 1 : 0;
        CROSSINGS_ON_ZERO += calculateCrossings(move.rotateLeft(), unboundedNext, next);
        this.position = next;
    }

    private int calculateCrossings(boolean rotateLeft, int unboundedNext, int next) {
        int rotationsThroughZero = Math.abs(Math.floorDiv(unboundedNext, DIAL_SIZE));
        if (rotateLeft) {
            if (next == 0) rotationsThroughZero++;
            if (position == 0) rotationsThroughZero--;
        }
        return rotationsThroughZero;
    }
}

private Movement parseMove(String line) {
    boolean rotateToLeft = (line.charAt(0) == 'L');
    int steps = Integer.parseInt(line.substring(1));
    return new Movement(rotateToLeft, steps);
}

/***
 * Day 01 - Part I
 * Only counting landings on zero - secret_entrance_small.txt = Result is 3 landings in zero.
 * Day 01 - part II
 * Counts when crossing through position zero - secret_entrance_small.txt = Result is 6 landings in zero.
 */
void main() throws IOException {
    long start = System.nanoTime();

    String filePath = "resources/day01/secret_entrance.txt";
    CircularLock lock = new CircularLock(DIAL_INITIAL_POSITION);
    try (var lines = Files.lines(Path.of(filePath))) {
        lines
                .filter(Predicate.not(String::isBlank))
                .map(this::parseMove)
                .forEach(lock::rotateDial);
    }
    System.out.println("Landings: " + LANDINGS_ON_ZERO);
    System.out.println("Crossings: " + CROSSINGS_ON_ZERO);

    long duration = System.nanoTime() - start;
    System.out.println("Execution time (ms): " + duration / 1_000_000);
}





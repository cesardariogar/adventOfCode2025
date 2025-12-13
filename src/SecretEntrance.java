final int DIAL_SIZE = 100;
private record DialState(int position, int zeroCrossings) { }
private record Movement(Direction direction, int steps){
    public boolean isLeft() {
        return direction == Direction.LEFT;
    }
}
private enum Direction { LEFT, RIGHT};

void main() throws IOException {
    long start = System.nanoTime();

    String filePath = "Day01/secret_entrance_small.txt";
    int position = 50;
    int dialZeroLandings = 0;

    try (Stream<String> lines = Files.lines(Path.of(filePath))) {
        for (Movement move : lines.filter(s -> !s.isBlank())
                .map(this::parseMove)
                .toList()) {

            DialState lineResult = rotateDial(position, move);
            position = lineResult.position();
            dialZeroLandings += lineResult.zeroCrossings();
        }
    }
    IO.println("Result: " + dialZeroLandings);

    long duration = System.nanoTime() - start;
    System.out.println("Execution time (ms): " + duration / 1_000_000);
}

private Movement parseMove(String line) {
    Direction direction = (line.charAt(0) == 'L') ? Direction.LEFT : Direction.RIGHT;
    int steps = Integer.parseInt(line.substring(1));
    return new Movement(direction, steps);
}

private DialState rotateDial(int oldPosition, Movement move) {
    int sum = (move.isLeft()) ? (oldPosition - move.steps) : (oldPosition + move.steps);
    // Number of full rotations = zero crossings
    int zeroCrossings = Math.abs(Math.floorDiv(sum, DIAL_SIZE));
    // Final wrapped position 0–99
    int newPosition = Math.floorMod(sum, DIAL_SIZE);
    if (move.isLeft()) {
        if (newPosition == 0) zeroCrossings++;
        if (oldPosition == 0) zeroCrossings--;
    }

    return new DialState(newPosition, zeroCrossings);
}

/***
 *
 * Day 01
 * Part I: Only counting landings on zero
 * secret_entrance_small.txt = Result is 3 landings in zero.
 *
 * part II: Counts not just landings but also when crossing through zero
 *  * secret_entrance_small.txt = Result is 6 landings in zero.
 */
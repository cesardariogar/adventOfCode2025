
void main() throws IOException {
    String FILE_PATH = "resources/Day07/laboratories_small.txt";
//    String FILE_PATH = "resources/Day07/laboratories_small.txt";

    var lines = Files.readAllLines(Path.of(FILE_PATH));
    int width = lines.getFirst().length();
    int start = lines.getFirst().indexOf('S');
    Set<Integer> beams = new HashSet<>();
    beams.add(start);

    int splitCounter = 0;
    for (int row = 1; row < lines.size(); row++) {
        String line = lines.get(row);
        Set<Integer> next = new HashSet<>();
        for (int col : beams) {
            if (line.charAt(col) == '^') {
                splitCounter++;     // count EVERY hit
                if (col - 1 >= 0) next.add(col - 1);
                if (col + 1 < width) next.add(col + 1);
            } else {
                next.add(col);
            }
        }
        beams = next;
    }

    System.out.println(splitCounter);
}
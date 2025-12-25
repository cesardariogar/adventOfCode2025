static final char MARKER = '@';
String FILE_PATH = "resources/Day06/trash_compactor.txt";               // total = 5733696195703, part II = 10951882745757
//String FILE_PATH = "resources/Day05/trash_compactor_small.txt";       // total = 4277556

private static class DataColumn {
    List<Long> numbers = new ArrayList<>();
    List<Long> cephalopodNumbers = new ArrayList<>();
    List<String> rows = new ArrayList<>();
    String operand;
}

private long eval(List<Long> numbers, String operand) {
    return switch (operand) {
        case "+" -> numbers.stream().mapToLong(Long::longValue).sum();
        case "*" -> numbers.stream().mapToLong(Long::longValue).reduce(1, (a, b) -> a * b);
        default -> throw new IllegalArgumentException("Unknown operator: " + operand);
    };
}

private List<Long> transposeRows(List<String> rows) {
    int max = rows.stream().mapToInt(String::length)
            .max()
            .orElse(0);

    var padded = rows.stream()
            .map(r -> " ".repeat(max - r.length()) + r)
            .toList();

    return IntStream.range(0, max)
            .mapToObj(col ->
                    padded.stream()
                            .map(row -> row.charAt(col))
                            .filter(c -> c != ' ')
                            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                            .toString()
            )
            .map(Long::parseLong)
            .toList();
}

void main() throws IOException {
    var lines = Files.readAllLines(Path.of(FILE_PATH));
    List<DataColumn> dataColumns = normalizeInput(lines);

    // Part I
    long numbersTotal = dataColumns.stream()
            .mapToLong(column -> eval(column.numbers, column.operand)).sum();

    // Part II
    long transposedNumbersTotal = dataColumns.stream()
            .mapToLong(column -> eval(column.cephalopodNumbers, column.operand)).sum();

    System.out.println("Part I - total: " + numbersTotal);
    System.out.println("Part II - total: " + transposedNumbersTotal);
}

private List<DataColumn> normalizeInput(List<String> lines) {
    String operandsLine = lines.getLast();
    int[] columnIdx = getColumnsIdx(operandsLine);
    String[] operands = operandsLine.trim().split("\\s+");

    int cols = columnIdx.length + 1;
    List<DataColumn> dataColumns =
            Stream.generate(DataColumn::new)
                    .limit(cols)
                    .toList();

    // Fill rows + numeric values
    for (String line : lines.subList(0, lines.size() - 1)) {
        String[] parts = splitIntoColumns(line, columnIdx);

        for (int c = 0; c < cols; c++) {
            DataColumn dc = dataColumns.get(c);
            dc.rows.add(parts[c]);
            dc.numbers.add(Long.parseLong(parts[c].trim()));
        }
    }

    // Attach operands
    for (int i = 0; i < cols; i++) {
        dataColumns.get(i).operand = operands[i];
    }

    // Build cephalopod numbers (vertical read)
    dataColumns.forEach(dc -> dc.cephalopodNumbers = transposeRows(dc.rows));

    return dataColumns;
}

private static String[] splitIntoColumns(String line, int[] columnIdx) {
    StringBuilder sb = new StringBuilder(line);
    for (int idx : columnIdx) {
        sb.setCharAt(idx, MARKER);
    }
    return sb.toString().split(String.valueOf(MARKER));
}

private int[] getColumnsIdx(String operandsLine) {
    return IntStream
            .range(0, operandsLine.length() - 1)
            .filter(i -> (i > 0) && (operandsLine.charAt(i + 1) != ' '))
            .toArray();
}
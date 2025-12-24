static final char MARKER = '@';

private static class DataColumn {
    List<Long> numbers = new ArrayList<>();
    List<Long> cephalopodNumbers = new ArrayList<>();
    List<String> rows = new ArrayList<>();
    String operand;

    private Long applyOperand(List<Long> numbers, String operand) {
        LongBinaryOperator op = switch (operand) {
            case "+" -> Long::sum;
            case "*" -> (a, b) -> a * b;
            default -> throw new IllegalArgumentException("Unknown operator: " + operand);
        };
        return numbers.stream().mapToLong(Long::longValue).reduce(op).orElseThrow();
    }
}

private List<Long> transposeRows(List<String> rows) {
    int maxWidth = rows.stream()
            .mapToInt(String::length)
            .max()
            .orElse(0);

    List<Long> transposedNumbers = new ArrayList<>();
    IntStream.range(0, maxWidth).forEach(col -> {
        StringBuilder newNumber = new StringBuilder();
        rows.forEach(currentRow -> {
            char c = currentRow.charAt(col);
            if (c != ' ') {newNumber.append(c);}
        });
        transposedNumbers.add(Long.parseLong(newNumber.toString()));
    });

    return transposedNumbers;
}


void main() throws IOException {
    List<String> lines = getInput();
    List<DataColumn> dataColumns = normalizeInput(lines);

    // Part I
    long numbersTotal = dataColumns.stream()
            .mapToLong(column -> column.applyOperand(column.numbers, column.operand))
            .sum();
    System.out.println("Part I - total: " + numbersTotal);

    // Part II
    long transposedNumbersTotal = dataColumns.stream()
            .mapToLong(column -> column.applyOperand(column.cephalopodNumbers, column.operand))
            .sum();
    System.out.println("Part II - total: " + transposedNumbersTotal);
}


private List<DataColumn> normalizeInput(List<String> lines) {
    int[] columnIdx = getColumnsIdx(lines.getLast());
    String[] operands = lines.getLast().trim().split("\\s+");

    List<DataColumn> dataColumns = IntStream.range(0, columnIdx.length + 1)
            .mapToObj(i -> new DataColumn())
            .toList();

    IntStream.range(0, lines.size() - 1).forEach(row -> {
        String[] columns = getColumns(lines.get(row), columnIdx);
        // Populate Data by Column
        IntStream.range(0, columnIdx.length + 1).forEach(col -> {
            dataColumns.get(col).rows.add(columns[col]);
            dataColumns.get(col).numbers.add(Long.valueOf(columns[col].trim()));
            dataColumns.get(col).operand = operands[col];
        });
    });

    dataColumns.forEach(column -> column.cephalopodNumbers = transposeRows(column.rows));

    return dataColumns;
}

private static String[] getColumns(String line, int[] columnIdx) {
    StringBuilder sb = new StringBuilder(line);
    Arrays.stream(columnIdx).forEach(idx -> sb.setCharAt(idx, MARKER));
    return sb.toString().split(String.valueOf(MARKER));
}

private int[] getColumnsIdx(String operandsLine) {
    return IntStream
            .range(0, operandsLine.length() - 1)
            .filter(i -> (i > 0) && (operandsLine.charAt(i + 1) != ' '))
            .toArray();
}

private List<String> getInput() throws IOException {
    String FILE_PATH = "resources/Day06/trash_compactor.txt";               // total = 5733696195703
    //String FILE_PATH = "resources/Day05/trash_compactor_small.txt";       // total = 4277556

    return Files.readAllLines(Path.of(FILE_PATH), StandardCharsets.UTF_8);
}
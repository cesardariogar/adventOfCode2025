final String FILE_PATH = "resources/Day06/trash_compactor.txt"; // total = 5733696195703
// final String FILE_PATH = "resources/Day05/trash_compactor_small.txt"; // total = 4277556

static class Data {
    Deque<Long> data = new ArrayDeque<>();
    String operand;

    public Long processData() {
        LongBinaryOperator op = operatorFrom(operand);
        return data.stream().mapToLong(Long::longValue).reduce(op).orElseThrow();
    }
}

static LongBinaryOperator operatorFrom(String op) {
    return switch (op) {
        case "+" -> Long::sum;
        case "*" -> (a, b) -> a * b;
        case "-" -> (a, b) -> a - b;
        case "/" -> (a, b) -> a / b;
        default -> throw new IllegalArgumentException("Unknown operator: " + op);
    };
}

void main() throws IOException {
    try (var input = Files.lines(Path.of(FILE_PATH))) {
        List<String[]> lines = input
                .filter(Predicate.not(String::isBlank))
                .map(l -> l.trim().split("\\s+"))
                .toList();

        List<Data> dataGroup = Stream.of(lines.getFirst()).map(col -> new Data()).toList();
        for (int row = 0; row < lines.size(); row++) {
            boolean isLastLine = row == lines.size() - 1;
            for (int col = 0; col < lines.get(row).length; col++) {
                if (isLastLine) {
                    dataGroup.get(col).operand = lines.get(row)[col];
                } else {
                    dataGroup.get(col).data.push(Long.parseLong(lines.get(row)[col]));
                }
            }
        }
        Long total = dataGroup.stream().mapToLong(Data::processData).sum();
        System.out.println(total);
    }
}

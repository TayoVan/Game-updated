import java.util.Scanner;

public class Main {
    private static int fieldSize = 3;
    private static boolean[][] occupied;
    private static char[][] board;
    private static char currentPlayer = 'X';
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = getUserInput();

            switch (choice) {
                case 1:
                    playGame();
                    break;
                case 2:
                    configureSettings();
                    break;
                case 3:
                    showRules();
                    break;
                case 4:
                    System.out.println("Вихід.");
                    return;
                default:
                    System.out.println("Опаньки, літер у нашій грі нема, зробіть вибір цифрою)");
            }
        }
    }

    private static void showMenu() {
        System.out.println("1. Грати");
        System.out.println("2. Налаштування");
        System.out.println("3. Правила");
        System.out.println("4. Вихід");
        System.out.print("Оберіть пункт: ");
    }

    private static int getUserInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Опаньки, літер у нашій грі нема, зробіть вибір цифрою)");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void playGame() {
        occupied = new boolean[fieldSize][fieldSize];
        board = new char[fieldSize][fieldSize];
        
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                board[i][j] = ' ';
            }
        }

        while (true) {
            printBoard();
            System.out.println("Гравець " + currentPlayer + ", ваш хід. Введіть рядок і стовпець (1-" + fieldSize + "): ");
            int row = getUserInput() - 1;
            int col = getUserInput() - 1;

            if (row < 0 || row >= fieldSize || col < 0 || col >= fieldSize || occupied[row][col]) {
                System.out.println("Неправильний хід, спробуйте ще раз.");
                continue;
            }

            occupied[row][col] = true;
            board[row][col] = currentPlayer;

            if (checkWin(row, col)) {
                printBoard();
                System.out.println("Гравець " + currentPlayer + " виграв!");
                break;
            }

            if (isBoardFull()) {
                printBoard();
                System.out.println("Опаньки, схоже нічия! Ви повертаєтеся на головне меню");
                break;
            }

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    private static boolean isBoardFull() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (!occupied[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkWin(int row, int col) {
        if (checkLine(row, 0, 0, 1)) return true;
        if (checkLine(0, col, 1, 0)) return true;
        if (row == col && checkLine(0, 0, 1, 1)) return true;
        if (row + col == fieldSize - 1 && checkLine(0, fieldSize - 1, 1, -1)) return true;

        return false;
    }

    private static boolean checkLine(int startRow, int startCol, int deltaRow, int deltaCol) {
        char first = board[startRow][startCol];
        if (first == ' ') return false;

        for (int i = 1; i < fieldSize; i++) {
            int row = startRow + i * deltaRow;
            int col = startCol + i * deltaCol;
            if (board[row][col] != first) {
                return false;
            }
        }
        return true;
    }

    private static void printBoard() {
        System.out.println();

        for (int i = 0; i < fieldSize; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j < fieldSize; j++) {
                System.out.print(" " + board[i][j] + " |");
            }
            System.out.println();
            if (i < fieldSize - 1) {
                System.out.print("  ");
                for (int j = 0; j < fieldSize; j++) {
                    System.out.print("---+");
                }
                System.out.println();
            }
        }
    }

    private static void configureSettings() {
        while (true) {
            System.out.println("Налаштування");
            System.out.println("1. Вибір розміру поля");
            System.out.println("0. Повернутися до меню");
            System.out.print("Оберіть пункт: ");

            int settingsChoice = getUserInput();
            if (settingsChoice == 0) {
                break;
            } else if (settingsChoice == 1) {
                selectFieldSize();
            }
        }
    }

    private static void selectFieldSize() {
        System.out.println("Оберіть розмір поля:");
        System.out.println("1. 3x3");
        System.out.println("2. 5x5");
        System.out.println("3. 7x7");
        System.out.println("4. 9x9");

        int fieldChoice = getUserInput();
        switch (fieldChoice) {
            case 1: fieldSize = 3; break;
            case 2: fieldSize = 5; break;
            case 3: fieldSize = 7; break;
            case 4: fieldSize = 9; break;
            default:
                System.out.println("Опаньки, такого розміру немає. Встановлена стандартна гра 3х3");
                fieldSize = 3;
        }
    }

    private static void showRules() {
        System.out.println("Правила: 1) Кожен гравець ходить по черзі. 2) Хрестик ходить першим. 3) Щоб виграти, потрібно мати 3 фігури в лінію.");
        System.out.println("Натисніть 0 щоб повернутися в меню.");
        scanner.nextInt();
    }
}
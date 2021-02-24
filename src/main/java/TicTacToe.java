import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicTacToe {
    static Scanner in;

    static FieldState board[][];
    static int numStep;
    static String winner;

    public static void main(String[] args) {
        in = new Scanner(System.in);
        //инициализируем поле
        initField();
        numStep = 0;
        winner = "";
        //Начинаем игру
        System.out.println("Welcome to Tic Tac Toe");
        System.out.println("______________________");
        while (winner.length() == 0 || numStep == 8) {//Если кто-то выйграл или закончились ходы, то заканчиваем игру
            printBoard();
            numStep++;
            if (numStep % 2 == 1) {//Если номер хода нечетный, то значит ходит игрок, иначе компьютер
                stepPlayer();
            } else {
                stepComputer();
            }
            //Проверяем, выйграл ли кто
            checkWin();
        }
        printBoard();
        if (winner.length() == 0) {
            System.out.println("Game is over. Draw!");
            return;
        }
        System.out.println("Game is over. Winner is " + winner);
    }

    private static void stepPlayer() {
        //Спрашиваем ход
        System.out.println("Player, input your move (e.g. B2)");
        String playerMove;
        playerMove = in.nextLine();
        while (true) {
            if (!checkInput(playerMove))
                System.out.println("Incorrect input, please input correct move (e.g. B2)");
            if (!markStep(playerMove, FieldState.X))
                System.out.println("Cell in not empty, please input correct move (e.g. B2)");
            else
                break;

            playerMove = in.nextLine();
        }
    }

    private static void stepComputer() {
        Random random = new Random();
        String step;
        //Генерим рандомный ход
        while (true) {
            int leftLimit = 65; // буква 'A'
            int rightLimit = 68; // буква 'C'
            int x = random.ints(leftLimit, rightLimit)
                    .findFirst()
                    .getAsInt();
            int y = random.ints(1, 4)
                    .findFirst()
                    .getAsInt();
            step = Character.toString((char) x) + y;
            if (markStep(step, FieldState.O))//Если сгенерированный ход уже существует на поле, то повторяем еще раз
                break;
        }
        System.out.println("Step computer is " + step);


    }

    private static void checkWin() {
        //Перебираем все варианты. Для доски 3х3 это самый простой вариант
        //Вертикали
        if (board[0][0] != FieldState.E && board[0][0] == board[0][1] && board[0][0] == board[0][2])
            winner = board[0][0].toString();
        if (board[1][0] != FieldState.E && board[1][0] == board[1][1] && board[1][0] == board[1][2])
            winner = board[1][0].toString();
        if (board[2][0] != FieldState.E && board[2][0] == board[2][1] && board[2][0] == board[2][2])
            winner = board[2][0].toString();

        //Горизонтали
        if (board[0][0] != FieldState.E && board[0][0] == board[1][0] && board[0][0] == board[2][0])
            winner = board[0][0].toString();
        if (board[0][1] != FieldState.E && board[0][1] == board[1][1] && board[0][1] == board[2][1])
            winner = board[0][1].toString();
        if (board[0][2] != FieldState.E && board[0][2] == board[1][2] && board[0][2] == board[2][2])
            winner = board[0][2].toString();

        //Диагонали
        if (board[0][0] != FieldState.E && board[0][0] == board[1][1] && board[0][0] == board[2][2])
            winner = board[0][0].toString();
        if (board[2][0] != FieldState.E && board[2][0] == board[1][1] && board[2][0] == board[0][2])
            winner = board[2][0].toString();
    }

    private static boolean markStep(String step, FieldState who) {
        int x, y;
        switch (step.charAt(0)) {
            case 'A':
                x = 0;
                break;
            case 'B':
                x = 1;
                break;
            case 'C':
                x = 2;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + step.charAt(0));
        }
        y = Character.getNumericValue(step.charAt(1)) - 1;

        if (board[x][y] != FieldState.E)//Если на этой ячейке уже кто-то отметил свой ход, то возвращаем ошибку
            return false;
        board[x][y] = who;

        return true;
    }

    private static boolean checkInput(String playerMove) {
        if (playerMove.length() != 2)
            return false;
        Pattern p = Pattern.compile("[ABC][123]");//. represents single character
        Matcher m = p.matcher(playerMove);
        return m.matches();
    }

    private static void printBoard() {
        System.out.println("/---|---|---\\");
        System.out.println("| " + board[0][0] + " | " + board[0][1] + " | " + board[0][2] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + board[1][0] + " | " + board[1][1] + " | " + board[1][2] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + board[2][0] + " | " + board[2][1] + " | " + board[2][2] + " |");
        System.out.println("/---|---|---\\");
    }

    private static void initField() {
        board = new FieldState[3][3];
        Arrays.fill(board[0], FieldState.E);
        Arrays.fill(board[1], FieldState.E);
        Arrays.fill(board[2], FieldState.E);
    }
}

enum FieldState {
    E, X, O;
}

enum Player {
    X, O;
}


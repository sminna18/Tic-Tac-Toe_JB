import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

class coordinate {
    int i;
    int j;
    int result;
}

public class Main {

    public static void map_print(Character[][] maps) {
        Character empty_char = '_';
        System.out.println("---------");
        for (int i = 0; i < 9; i++) {
            if (i == 0 || i == 3 || i == 6)
                System.out.print("| ");
            if (empty_char.equals(maps[i / 3][i % 3]))
                System.out.print("  ");
            else
                System.out.print(maps[i / 3][i % 3] + " ");
            if (i == 2 || i == 5 || i == 8)
                System.out.println("|");
        }
        System.out.println("---------");
    }

    public static char win_checker(Character[][] map, int x_count, int o_count, boolean print) {
        Character empty_char = '_';
        if (map[0][0].equals(map[0][1]) && map[0][0].equals(map[0][2]) && !empty_char.equals(map[0][0])) {
            if (print)
                System.out.println(map[0][0] + " wins");
            return map[0][0];
        }
        else if (map[1][0].equals(map[1][1]) && map[1][0].equals(map[1][2]) && !empty_char.equals(map[1][0])) {
            if (print)
                System.out.println(map[1][0] + " wins");
            return map[1][0];
        }
        else if (map[2][0].equals(map[2][1]) && map[2][0].equals(map[2][2]) && !empty_char.equals(map[2][0])) {
            if (print)
                System.out.println(map[2][0] + " wins");
            return map[2][0];
        }
        else if (map[0][0].equals(map[1][0]) && map[0][0].equals(map[2][0]) && !empty_char.equals(map[0][0])) {
            if (print)
                System.out.println(map[0][0] + " wins");
            return map[0][0];
        }
        else if (map[0][1].equals(map[1][1]) && map[0][1].equals(map[2][1]) && !empty_char.equals(map[0][1])) {
            if (print)
                System.out.println(map[0][1] + " wins");
            return map[0][1];
        }
        else if (map[0][2].equals(map[1][2]) && map[0][2].equals(map[2][2]) && !empty_char.equals(map[0][2])) {
            if (print)
                System.out.println(map[0][2] + " wins");
            return map[0][2];
        }
        else if (map[0][0].equals(map[1][1]) && map[0][0].equals(map[2][2]) && !empty_char.equals(map[0][0])) {
            if (print)
                System.out.println(map[1][1] + " wins");
            return map[1][1];
        }
        else if (map[2][0].equals(map[1][1]) && map[2][0].equals(map[0][2]) && !empty_char.equals(map[2][0])) {
            if (print)
                System.out.println(map[1][1] + " wins");
            return map[1][1];
        }
        else if (x_count + o_count == 9) {
            if (print)
                System.out.println("Draw");
            return '=';
        }
        return 'c';
    }

    public static void easy_move(Character[][] map, char x_o) {
        int a = 0;
        int b = 0;
        Random random = new Random();

        while (true) {
            a = random.nextInt(3);
            b = random.nextInt(3);
            if (map[a][b] == '_') {
                map[a][b] = x_o;
                System.out.println("Making move level \"easy\"");
                break;
            }
        }
    }

    public static coordinate minimax(Character[][] map, char x_o, int n, int x_count, int o_count, char const_x_o) {
        char result = win_checker(map, x_count, o_count, false);
        coordinate main_coordinate =  new coordinate();
        if (result != 'c') {
            if (result == '=') {
                main_coordinate.result = 0;
                return main_coordinate;
            }
            else if (const_x_o == result) {
                main_coordinate.result = 1;
                return main_coordinate;
            }
            else {
                main_coordinate.result = -1;
                return main_coordinate;
            }
        }
        else {
            int max = -5;
            int min = 5;
            main_coordinate.i = 0;
            main_coordinate.j = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (map[i][j] == '_') {
                        Character[][] tmp_map = new Character[3][3];
                        for (int x = 0; x < 3; x++)
                            System.arraycopy(map[x], 0, tmp_map[x], 0, 3);
                        coordinate tmp_coordinate;
                        char reverse_x_o;
                        if (x_o == 'X')
                            reverse_x_o = 'O';
                        else
                            reverse_x_o = 'X';
                        tmp_map[i][j] = reverse_x_o;
                        tmp_coordinate = minimax(tmp_map, reverse_x_o, n * -1, x_count + 1, o_count, const_x_o);
                        if ( n < 0 && tmp_coordinate.result > max) {
                            max = tmp_coordinate.result;
                            main_coordinate.i = i;
                            main_coordinate.j = j;

                        }
                        if ( n > 0 && tmp_coordinate.result < min) {
                            min = tmp_coordinate.result;
                            main_coordinate.i = i;
                            main_coordinate.j = j;
                        }
                    }
                }
            }
            if (n < 0)
                main_coordinate.result = max;
            else
                main_coordinate.result = min;
            return main_coordinate;
        }
    }

    public static void hard_move(Character[][] map, char x_o, int x_count, int o_count) {

        char reverse_x_o;
        if (x_o == 'X')
            reverse_x_o = 'O';
        else
            reverse_x_o = 'X';
        coordinate main_cordinat = minimax(map, reverse_x_o, -1, x_count, o_count, x_o);
        map[main_cordinat.i][main_cordinat.j] = x_o;
        System.out.println("Making move level \"hard\"");
    }



    public static void medium_move(Character[][] map, char x_o) {
        int a = 0;
        int b = 0;

        char reverse_x_o;
        if (x_o == 'O')
            reverse_x_o = 'X';
        else
            reverse_x_o = 'O';

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Character[][] tmp_map = new Character[3][3];
                for (int n = 0; n < 3; n++)
                    System.arraycopy(map[n], 0, tmp_map[n], 0, 3);
                if (tmp_map[i][j] == '_') {
                    tmp_map[i][j] = x_o;
                    if (win_checker(tmp_map, 2, 2, false) != 'c') {
                        map[i][j] = x_o;
                        System.out.println("Making move level \"medium\"");
                        return;
                    }
                    tmp_map[i][j] = reverse_x_o;
                    if (win_checker(tmp_map, 2, 2, false) != 'c') {
                        map[i][j] = x_o;
                        System.out.println("Making move level \"medium\"");
                        return;
                    }
                }
            }
        }

        Random random = new Random();

        while (true) {
            a = random.nextInt(3);
            b = random.nextInt(3);
            if (map[a][b] == '_') {
                map[a][b] = x_o;
                System.out.println("Making move level \"medium\"");
                break;
            }
        }
    }


    public static void user_move(Character[][] map, char x_o) {

        int i = 1;
        int a = 0;
        int b = 0;

        while(i == 1) {
            Scanner scan_str = new Scanner(System.in);
            try {
                System.out.print("Enter the coordinates:");
                a = scan_str.nextInt();
                b = scan_str.nextInt();
                i = 0;
                if (a > 3 || a < 1 || b > 3 || b < 1) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    i = 1;
                }
                else if (map[a -1][b -1] != '_') {
                    System.out.println("This cell is occupied! Choose another one!");
                    i = 1;
                }
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
            }
        }
        map[a - 1][b - 1] = x_o;
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x_count = 0;
        int o_count = 0;
        int user_1 = 0;
        int user_2 = 0;
        Character[][] map = new Character[3][3];
        for (int i = 0; i < 9; i++) {
            map[i / 3][i % 3] = '_';
        }
        String[] param = new String[10];
        while(true) {
            param = scanner.nextLine().split(" ");
            if (param.length == 1 && "exit".equals(param[0]))
                return;
            if (param.length == 3 && "start".equals(param[0])
                    && ("user".equals(param[1]) || "easy".equals(param[1]) || "medium".equals(param[1]) || "hard".equals(param[1]))
                    && ("user".equals(param[2]) || "easy".equals(param[2]) || "medium".equals(param[2]) || "hard".equals(param[2]))) {
                switch (param[1]) {
                    case "user":
                        map_print(map);
                        break;
                    case "easy":
                        user_1 = 1;
                        break;
                    case "medium":
                        user_1 = 2;
                        break;
                    case "hard":
                        user_1 = 3;
                        break;
                }
                switch (param[2]) {
                    case "easy":
                        user_2 = 1;
                        break;
                    case "medium":
                        user_2 = 2;
                        break;
                    case "hard":
                        user_2 = 3;
                        break;
                }
                break;
            }
            System.out.println("Bad parameters!");
        }
        while(true) {
            int a = 1;
            int b = 1;
            int i = 1;

            if (x_count == o_count) {
                if (user_1 == 0)
                    user_move(map, 'X');
                else if (user_1 == 1)
                    easy_move(map, 'X');
                else if (user_1 == 2)
                    medium_move(map, 'X');
                else
                    hard_move(map, 'X', x_count, o_count);
                x_count++;
            }
            else {
                if (user_2 == 0)
                    user_move(map, 'O');
                else if (user_2 == 1)
                    easy_move(map, 'O');
                else if (user_2 == 2)
                    medium_move(map, 'O');
                else
                    hard_move(map, 'O', x_count, o_count);
                o_count++;
            }
            map_print(map);
            if (win_checker(map, x_count, o_count, true) != 'c')
                return;
        }
    }
}




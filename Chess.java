import java.util.*;

public class Chess {
    String board[][];
    String piece;
    int i;
    int j;
    int movedI;
    int movedJ;
    char player;
    char opponent;

    ArrayList<ArrayList<Integer>> al;

    Chess() {

        al = new ArrayList<>();

        board = new String[8][8];
        for (int i = 0; i < board.length; i++) {
            Arrays.fill(board[i], "   ");
        }

       
        board[0][0] = "'C'";

        board[0][7] = ".Q.";
        board[1][7] = ".Q.";

        ///////////////////////////////////////////////////////////////////////

        // for (int i = 0; i < 8; i++) {
        //     board[1][i] = ".P.";
        //     board[6][i] = "'P'";
        // }
        
        // board[0][0] = ".R.";
        // board[0][1] = ".K.";
        // board[0][2] = ".B.";
        // board[0][3] = ".Q.";
        // board[0][4] = ".C.";
        // board[0][5] = ".B.";
        // board[0][6] = ".K.";
        // board[0][7] = ".R.";

        // board[7][0] = "'R'";
        // board[7][1] = "'K'";
        // board[7][2] = "'B'";
        // board[7][3] = "'Q'";
        // board[7][4] = "'C'";
        // board[7][5] = "'B'";
        // board[7][6] = "'K'";
        // board[7][7] = "'R'";


    }

    void setPiece(String piece) {
        this.piece = piece;
    }

    int getI(String input) {
        return (int) input.charAt(1) - 49;
    }

    int getJ(String input) {
        return (int) input.charAt(0) - 97;
    }

    void setPlayer(char player) {
        this.player = player;
        if (player == '\'') {
            this.opponent = '.';
        } else {
            this.opponent = '\'';
        }
    }

    String getPiece(int i, int j) {

        return board[i][j];
    }

    void flipBoard(String board[][]) {
        System.out.println("Flipping board");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length / 2; j++) {
                String temp = board[i][j];
                board[i][j] = board[i][(board[i].length - 1) - j];
                board[i][(board[i].length - 1) - j] = temp;
            }
        }

        for (int i = 0; i < board.length / 2; i++) {
            String temp[] = board[i];
            board[i] = board[(board.length - 1) - i];
            board[(board.length - 1) - i] = temp;
        }

        // for (int i = 0; i < board.length; i++) {
        // System.out.print((i + 1) + " ! ");
        // for (int j = 0; j < board[i].length; j++) {
        // System.out.print(board[i][j] + " ");
        // }
        // System.out.println();
        // }
        showBoard();

    }

    boolean validateMove(String chosenPiece, int i, int j, int movedI, int movedJ) {
        if (chosenPiece == null || chosenPiece.charAt(0) != this.player) {
            System.out.println("Wrong move at if validateMove");
            return false;
        }

        System.out.println("----Chosen piece " + chosenPiece);
        boolean flag = false;
        char piece = chosenPiece.charAt(1);
        switch (piece) {
            case 'P':
                if (movedI == i - 1 && movedJ == j && board[movedI][movedJ].equals("   ")) { // simple pawn move
                    flag = true;
                } else if (movedI == i - 2 && i == 6 && movedJ == j) { // double push for first time
                    flag = true;
                } else if (board[movedI][movedJ].charAt(0) == this.opponent && movedI == i - 1
                        && (movedJ == j - 1 || movedJ == j + 1)) { // takes another piece
                    flag = true;
                }
                break;

            case 'R':
                if (movedJ == j) { // vertical movement
                    boolean isMovable = true;
                    for (int start = i - 1; start > movedI; start--) { // up
                        if (board[start][j] != "   ") {
                            System.out.println("---- " + start);
                            System.out.println("Blocked by " + board[start][j]);
                            isMovable = false;
                            break;
                        }
                        System.out.println(start + " " + board[start][j]);
                    }

                    for (int start = i + 1; start < movedI; start++) { // down
                        if (board[start][j] != "   ") {
                            System.out.println("---- " + start);
                            System.out.println("Blocked by " + board[start][j]);
                            isMovable = false;
                            break;
                        }
                        System.out.println(start + " " + board[start][j]);
                    }

                    if (isMovable && board[movedI][movedJ].charAt(0) == this.player) {
                        isMovable = false;
                    } else if (isMovable && board[movedI][movedJ].charAt(0) == this.opponent) {
                        isMovable = true;
                    }

                    if (isMovable == true) {
                        flag = true;
                    }
                } else if (movedI == i) { // horizontal
                    boolean isMovable = true;
                    for (int start = j + 1; start < movedJ; start++) { // right
                        if (board[i][start] != "   ") {
                            System.out.println("---- " + start);
                            System.out.println("Blocked by " + board[i][start]);
                            isMovable = false;
                            break;
                        }
                        System.out.println(start + " --" + board[i][start]);
                    }
                    for (int start = j - 1; start > movedJ; start--) { // left
                        if (board[i][start] != "   ") {
                            System.out.println("---- " + start);
                            System.out.println("Blocked by " + board[i][start]);
                            isMovable = false;
                            break;
                        }
                        System.out.println(start + " --" + board[i][start]);
                    }

                    if (isMovable && board[movedI][movedJ].charAt(0) == this.player) {
                        isMovable = false;
                    } else if (isMovable && board[movedI][movedJ].charAt(0) == this.opponent) {
                        isMovable = true;
                    }
                    if (isMovable == true) {
                        flag = true;
                    }

                }
                break;

            case 'B':
                boolean isMovable = true;
                int iDiff = Math.abs(movedI - i);
                int jDiff = Math.abs(movedJ - j);

                if (iDiff != jDiff) { // coordinates check alone
                    isMovable = false;
                }

                if (isMovable) { // check if any pieces are in between

                    // left up:
                    if (movedI - i < 0 && movedJ - j < 0) {
                        for (int start = i - 1, startJ = j - 1; start > movedI && startJ > movedJ; start--, startJ--) {
                            System.out.println("--- " + start + " " + startJ);
                            if (board[start][startJ] != "   ") {
                                isMovable = false;
                                System.out.println("Blocked by " + board[start][startJ]);
                                break;
                            }
                        }
                    }
                    // left down
                    else if (movedI - i > 0 && movedJ - j < 0) {
                        for (int start = i + 1, startJ = j - 1; start < movedI && startJ > movedJ; start++, startJ--) {
                            System.out.println("--- " + start + " " + startJ);
                            if (board[start][startJ] != "   ") {
                                isMovable = false;
                                System.out.println("Blocked by " + board[start][startJ]);
                                break;
                            }
                        }
                    }

                    // right up
                    else if (movedI - i < 0 && movedJ - j > 0) {
                        for (int start = i - 1, startJ = j + 1; start > movedI && startJ < movedJ; start--, startJ++) {
                            System.out.println("--- " + start + " " + startJ);
                            if (board[start][startJ] != "   ") {
                                isMovable = false;
                                System.out.println("Blocked by " + board[start][startJ]);
                                break;
                            }
                        }
                    }

                    // right down
                    else if (movedI - i > 0 && movedJ - j > 0) {
                        for (int start = i + 1, startJ = j + 1; start < movedI && startJ < movedJ; start++, startJ++) {
                            System.out.println("--- " + start + " " + startJ);
                            if (board[start][startJ] != "   ") {
                                isMovable = false;
                                System.out.println("Blocked by " + board[start][startJ]);
                                break;
                            }
                        }
                    }

                    if (isMovable && board[movedI][movedJ].charAt(0) == this.player) {
                        isMovable = false;
                    } else if (isMovable && board[movedI][movedJ].charAt(0) == this.opponent) {
                        isMovable = true;
                    }

                }

                if (isMovable == true) {
                    flag = true;
                }

                break;

            case 'K':
                // String l1 = board[i-2][j+1];
                // String l2 = board[i-1][j+2];
                // String l3 = board[i+1][j+2];
                // String l4 = board[i+2][j+1];
                // String l5 = board[i+2][j-1];
                // String l6 = board[i+1][j-2];
                // String l7 = board[i-1][j-2];
                // String l8 = board[i-2][j-1];

                if (board[movedI][movedJ].charAt(0) == this.player) {
                    flag = false;
                } else if (movedI == i - 2 && movedJ == j + 1) {
                    flag = true;
                } else if (movedI == i - 1 && movedJ == j + 2) {
                    flag = true;
                } else if (movedI == i + 1 && movedJ == j + 2) {
                    flag = true;
                } else if (movedI == i + 2 && movedJ == j + 1) {
                    flag = true;
                } else if (movedI == i + 2 && movedJ == j - 1) {
                    flag = true;
                } else if (movedI == i + 1 && movedJ == j - 2) {
                    flag = true;
                } else if (movedI == i - 1 && movedJ == j - 2) {
                    flag = true;
                } else if (movedI == i - 2 && movedJ == j - 1) {
                    flag = true;
                } else {
                    flag = false;
                }
                break;

            case 'Q':
                boolean isQueenMovable = true;
                int qIDiff = Math.abs(movedI - i);
                int qJDiff = Math.abs(movedJ - j);

                if (qIDiff == qJDiff) { // coordinates check alone
                    System.out.println("---InDfffff---");
                    // left up:
                    if (movedI - i < 0 && movedJ - j < 0) {
                        for (int start = i - 1, startJ = j - 1; start > movedI && startJ > movedJ; start--, startJ--) {
                            System.out.println("--- " + start + " " + startJ);
                            if (board[start][startJ] != "   ") {
                                isQueenMovable = false;
                                System.out.println("Blocked by " + board[start][startJ]);
                                break;
                            }
                        }
                    }
                    // left down
                    else if (movedI - i > 0 && movedJ - j < 0) {
                        for (int start = i + 1, startJ = j - 1; start < movedI && startJ > movedJ; start++, startJ--) {
                            System.out.println("--- " + start + " " + startJ);
                            if (board[start][startJ] != "   ") {
                                isQueenMovable = false;
                                System.out.println("Blocked by " + board[start][startJ]);
                                break;
                            }
                        }
                    }

                    // right up
                    else if (movedI - i < 0 && movedJ - j > 0) {
                        for (int start = i - 1, startJ = j + 1; start > movedI && startJ < movedJ; start--, startJ++) {
                            System.out.println("--- " + start + " " + startJ);
                            if (board[start][startJ] != "   ") {
                                isQueenMovable = false;
                                System.out.println("Blocked by " + board[start][startJ]);
                                break;
                            }
                        }
                    }

                    // right down
                    else if (movedI - i > 0 && movedJ - j > 0) {
                        for (int start = i + 1, startJ = j + 1; start < movedI && startJ < movedJ; start++, startJ++) {
                            System.out.println("--- " + start + " " + startJ);
                            if (board[start][startJ] != "   ") {
                                isQueenMovable = false;
                                System.out.println("Blocked by " + board[start][startJ]);
                                break;
                            }
                        }
                    }

                    if (board[movedI][movedJ].charAt(0) == this.opponent || board[movedI][movedJ].charAt(0) == ' ') {
                        flag = true;
                    }
                }

                else {

                    if (movedJ == j) { // vertical movement
                        isQueenMovable = true;
                        for (int start = i - 1; start > movedI; start--) { // up
                            if (board[start][j] != "   ") {
                                System.out.println("---- " + start);
                                System.out.println("Blocked by " + board[start][j]);
                                isQueenMovable = false;
                                break;
                            }
                            System.out.println(start + " " + board[start][j]);
                        }

                        for (int start = i + 1; start < movedI; start++) { // down
                            if (board[start][j] != "   ") {
                                System.out.println("---- " + start);
                                System.out.println("Blocked by " + board[start][j]);
                                isQueenMovable = false;
                                break;
                            }
                            System.out.println(start + " " + board[start][j]);
                        }

                        if (isQueenMovable && board[movedI][movedJ].charAt(0) == this.player) {
                            isQueenMovable = false;
                        } else if (isQueenMovable && board[movedI][movedJ].charAt(0) == this.opponent) {
                            isQueenMovable = true;
                        }

                        if (isQueenMovable == true) {
                            flag = true;
                        }
                    } else if (movedI == i) { // horizontal
                        isQueenMovable = true;
                        for (int start = j + 1; start < movedJ; start++) { // right
                            if (board[i][start] != "   ") {
                                System.out.println("---- " + start);
                                System.out.println("Blocked by " + board[i][start]);
                                isQueenMovable = false;
                                break;
                            }
                            System.out.println(start + " --" + board[i][start]);
                        }
                        for (int start = j - 1; start > movedJ; start--) { // left
                            if (board[i][start] != "   ") {
                                System.out.println("---- " + start);
                                System.out.println("Blocked by " + board[i][start]);
                                isQueenMovable = false;
                                break;
                            }
                            System.out.println(start + " --" + board[i][start]);
                        }

                        if (isQueenMovable && board[movedI][movedJ].charAt(0) == this.player) {
                            isQueenMovable = false;
                        } else if (isQueenMovable && board[movedI][movedJ].charAt(0) == this.opponent) {
                            isQueenMovable = true;
                        }
                        if (isQueenMovable == true) {
                            flag = true;
                        }
                    }

                }

                break;

            case 'C':
                int movedIDiff = Math.abs(movedI - i);
                int movedJDiff = Math.abs(movedJ - j);

                if (movedIDiff > 1 || movedJDiff > 1) { // check moving single square
                    System.out.println("in if");

                    flag = false;
                    break;
                } else if (board[movedI][movedJ].equals("   ")) {// check if the square is empty, then movable
                    flag = true;
                    break;
                } else if (board[movedI][movedJ].charAt(0) == this.player) { // if the square contains the same player
                                                                             // piece
                    flag = false;
                    System.out.println("in elif");

                    break;
                } else if (board[movedI][movedJ].charAt(0) == this.opponent) { // if the square is an opponent piece,
                    flag = true; // if it is in check, the marker will notify
                    break;
                }
                break;
        }
        // if (flag == true) {
        // board[movedI][movedJ] = chosenPiece;
        // board[i][j] = " ";
        // System.out.println("Moved");
        // showBoard();
        // } else {
        // System.out.println("Wrong move");
        // }
        return flag;
    }

    void markAllAttacks(int marker[][]) {
        pawnMovable(marker);
        rookMovable(marker);
        bishopMovable(marker);
        knightMovable(marker);
        queenMovable(marker);
        kingMovable(marker);

        System.out.println("\n     ───── ───── ───── ───── ───── ───── ───── ───── ");

        for (int mi = 0; mi < marker.length; mi++) {
            System.out.print((mi + 1) + "  ");
            System.out.print(" |  ");
            for (int mj = 0; mj < marker[mi].length; mj++) {
                System.out.print(marker[mi][mj] + "  |  ");
            }
            System.out.println("");
            System.out.println("     ───── ───── ───── ───── ───── ───── ───── ───── ");
        }

        System.out.println();
        System.out.print("    ");
        for (int ci = 97; ci < 105; ci++) {
            char c = (char) ci;
            System.out.print("   " + c + "  ");
        }
        System.out.println();
        System.out.println();
    }

    void markPawnAttacks(int marker[][], int i, int j) {

        if ((i + 1 < marker.length && j - 1 >= 0) && this.board[i + 1][j - 1].charAt(0) != this.opponent) {
            marker[i + 1][j - 1] = 1;
            if (board[i + 1][j - 1].charAt(1) == 'C' && board[i + 1][j - 1].charAt(0) != this.opponent) {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(0);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }
        if ((i + 1 < marker.length && j + 1 < marker.length) && this.board[i + 1][j + 1].charAt(0) != this.opponent) {
            marker[i + 1][j + 1] = 1;
            if (board[i + 1][j + 1].charAt(1) == 'C' && board[i + 1][j + 1].charAt(0) != this.opponent) {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(0);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }
    }

    void pawnMovable(int marker[][]) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].charAt(0) == this.opponent && board[i][j].charAt(1) == 'P') {
                    markPawnAttacks(marker, i, j);
                }
            }
        }
    }

    void markRookAttacks(int marker[][], int i, int j) {
        // down
        for (int start = i + 1; start < marker.length; start++) {
            if (board[start][j].equals("   ")) {
                marker[start][j] = 1;
            } else if (board[start][j].charAt(0) == this.player) {
                marker[start][j] = 1;
                if (board[start][j].charAt(1) == 'C') {
                    ArrayList<Integer> newAl = new ArrayList<>();
                    newAl.add(4);
                    newAl.add(i);
                    newAl.add(j);
                    this.al.add(newAl);
                }
                break;
            } else { // same pieces
                break;
            }
        }

        // up
        for (int start = i - 1; start >= 0; start--) {
            if (board[start][j].equals("   ")) {
                marker[start][j] = 1;
            } else if (board[start][j].charAt(0) == this.player) {
                marker[start][j] = 1;
                if (board[start][j].charAt(1) == 'C') {
                    ArrayList<Integer> newAl = new ArrayList<>();
                    newAl.add(4);
                    newAl.add(i);
                    newAl.add(j);
                    this.al.add(newAl);
                }
                break;
            } else {
                break;
            }
        }

        // left
        for (int start = j - 1; start >= 0; start--) {
            if (board[i][start].equals("   ")) {
                marker[i][start] = 1;
            } else if (board[i][start].charAt(0) == this.player) {
                marker[i][start] = 1;
                if (board[i][start].charAt(1) == 'C') {
                    ArrayList<Integer> newAl = new ArrayList<>();
                    newAl.add(4);
                    newAl.add(i);
                    newAl.add(j);
                    this.al.add(newAl);
                }
                break;
            } else {
                break;
            }
        }

        // right
        for (int start = j + 1; start < marker.length; start++) {
            if (board[i][start].equals("   ")) {
                marker[i][start] = 1;
            } else if (board[i][start].charAt(0) == this.player) {
                marker[i][start] = 1;
                if (board[i][start].charAt(1) == 'C') {
                    ArrayList<Integer> newAl = new ArrayList<>();
                    newAl.add(4);
                    newAl.add(i);
                    newAl.add(j);
                    this.al.add(newAl);
                }
                break;
            } else {
                break;
            }
        }
    }

    void rookMovable(int marker[][]) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].charAt(0) == this.opponent && board[i][j].charAt(1) == 'R') {
                    markRookAttacks(marker, i, j);
                }
            }
        }
    }

    void markBishopAttacks(int marker[][], int i, int j) {

        // System.out.println("Bishop at "+i+" "+j);

        // left up
        for (int start = i - 1, startJ = j - 1; start >= 0 && startJ >= 0; start--, startJ--) {
            if (board[start][startJ].equals("   ")) {
                marker[start][startJ] = 1;
            } else if (board[start][startJ].charAt(0) == this.player) {
                marker[start][startJ] = 1;
                if (board[start][startJ].charAt(1) == 'C') {
                    ArrayList<Integer> newAl = new ArrayList<>();
                    newAl.add(3);
                    newAl.add(i);
                    newAl.add(j);
                    this.al.add(newAl);
                }
                break;
            } else {
                break;
            }
        }

        // left down
        for (int start = i + 1, startJ = j - 1; start < marker.length && startJ >= 0; start++, startJ--) {
            if (board[start][startJ].equals("   ")) {
                marker[start][startJ] = 1;
            } else if (board[start][startJ].charAt(0) == this.player) {
                marker[start][startJ] = 1;
                if (board[start][startJ].charAt(1) == 'C') {
                    ArrayList<Integer> newAl = new ArrayList<>();
                    newAl.add(3);
                    newAl.add(i);
                    newAl.add(j);
                    this.al.add(newAl);
                }
                break;
            } else {
                break;
            }
        }

        // right up
        for (int start = i - 1, startJ = j + 1; start >= 0 && startJ < marker.length; start--, startJ++) {
            if (board[start][startJ].equals("   ")) {
                marker[start][startJ] = 1;
            } else if (board[start][startJ].charAt(0) == this.player) {
                marker[start][startJ] = 1;
                if (board[start][startJ].charAt(1) == 'C') {
                    ArrayList<Integer> newAl = new ArrayList<>();
                    newAl.add(3);
                    newAl.add(i);
                    newAl.add(j);
                    this.al.add(newAl);
                }
                break;
            } else {
                break;
            }
        }

        // right down
        for (int start = i + 1, startJ = j + 1; start < marker.length && startJ < marker.length; start++, startJ++) {
            // System.out.println("in right downnnnnnnnnnnnnnnn");
            if (board[start][startJ].equals("   ")) {
                // System.out.println("Markig "+start+" "+startJ);
                marker[start][startJ] = 1;
            } else if (board[start][startJ].charAt(0) == this.player) {
                marker[start][startJ] = 1;
                if (board[start][startJ].charAt(1) == 'C') {
                    ArrayList<Integer> newAl = new ArrayList<>();
                    newAl.add(3);
                    newAl.add(i);
                    newAl.add(j);
                    this.al.add(newAl);
                }
                break;
            } else {
                break;
            }
        }
    }

    void bishopMovable(int marker[][]) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].charAt(0) == this.opponent && board[i][j].charAt(1) == 'B') {
                    markBishopAttacks(marker, i, j);
                }
            }
        }
    }

    void markKnightAttacks(int marker[][], int i, int j) {
        // l1 i-2, j+1

        if (i - 2 >= 0 && j + 1 < marker.length && board[i - 2][j + 1].charAt(0) != this.opponent) {
            marker[i - 2][j + 1] = 1;
            if (board[i - 2][j + 1].charAt(1) == 'C') {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(2);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }

        // l2 i-1 J+2
        if (i - 1 >= 0 && j + 2 < marker.length && board[i - 1][j + 2].charAt(0) != this.opponent) {
            marker[i - 1][j + 2] = 1;
            if (board[i - 1][j + 2].charAt(1) == 'C') {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(2);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }

        // l3 i+1 j+2
        if (i + 1 < marker.length && j + 2 < marker.length && board[i + 1][j + 2].charAt(0) != this.opponent) {
            marker[i + 1][j + 2] = 1;
            if (board[i + 1][j + 2].charAt(1) == 'C') {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(2);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }

        // l4 i+2 j+1

        if (i + 2 < marker.length && j + 1 < marker.length && board[i + 2][j + 1].charAt(0) != this.opponent) {
            marker[i + 2][j + 1] = 1;
            if (board[i + 2][j + 1].charAt(1) == 'C') {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(2);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }

        // l5 i+2 j-1
        if (i + 2 < marker.length && j - 1 >= 0 && board[i + 2][j - 1].charAt(0) != this.opponent) {
            marker[i + 2][j - 1] = 1;
            if (board[i + 2][j - 1].charAt(1) == 'C') {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(2);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }

        // l6 i+1 j-2
        if (i + 1 < marker.length && j - 2 >= 0 && board[i + 1][j - 2].charAt(0) != this.opponent) {
            marker[i + 1][j - 2] = 1;
            if (board[i + 1][j - 2].charAt(1) == 'C') {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(2);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }

        // l7 i-1 j-2
        if (i - 1 >= 0 && j - 2 >= 0 && board[i - 1][j - 2].charAt(0) != this.opponent) {
            marker[i - 1][j - 2] = 1;
            if (board[i - 1][j - 2].charAt(1) == 'C') {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(2);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }

        // l8 i-2 j-1
        if (i - 2 >= 0 && j - 1 >= 0 && board[i - 2][j - 1].charAt(0) != this.opponent) {
            marker[i - 2][j - 1] = 1;
            if (board[i - 2][j - 1].charAt(1) == 'C') {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(2);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }
    }

    void knightMovable(int marker[][]) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].charAt(0) == this.opponent && board[i][j].charAt(1) == 'K') {
                    markKnightAttacks(marker, i, j);
                }
            }
        }
    }

    void markQueenAttacks(int marker[][], int i, int j) {
        // left up
        for (int start = i - 1, startJ = j - 1; start >= 0 && startJ >= 0; start--, startJ--) {
            if (board[start][startJ].equals("   ")) {
                marker[start][startJ] = 1;
            } else if (board[start][startJ].charAt(0) == this.player) {
                marker[start][startJ] = 1;
                if (board[start][startJ].charAt(1) == 'C') {
                    ArrayList<Integer> newAl = new ArrayList<>();
                    newAl.add(5);
                    newAl.add(i);
                    newAl.add(j);
                    this.al.add(newAl);
                }
                break;
            } else {
                break;
            }
        }

        // left down
        for (int start = i + 1, startJ = j - 1; start < marker.length && startJ >= 0; start++, startJ--) {
            if (board[start][startJ].equals("   ")) {
                marker[start][startJ] = 1;
            } else if (board[start][startJ].charAt(0) == this.player) {
                marker[start][startJ] = 1;
                if (board[start][startJ].charAt(1) == 'C') {
                    ArrayList<Integer> newAl = new ArrayList<>();
                    newAl.add(5);
                    newAl.add(i);
                    newAl.add(j);
                    this.al.add(newAl);
                }
                break;
            } else {
                break;
            }
        }

        // right up
        for (int start = i - 1, startJ = j + 1; start >= 0 && startJ < marker.length; start--, startJ++) {
            if (board[start][startJ].equals("   ")) {
                marker[start][startJ] = 1;
            } else if (board[start][startJ].charAt(0) == this.player) {
                marker[start][startJ] = 1;
                if (board[start][startJ].charAt(1) == 'C') {
                    ArrayList<Integer> newAl = new ArrayList<>();
                    newAl.add(5);
                    newAl.add(i);
                    newAl.add(j);
                    this.al.add(newAl);
                }
                break;
            } else {
                break;
            }
        }

        // right down
        for (int start = i + 1, startJ = j + 1; start < marker.length && startJ < marker.length; start++, startJ++) {
            // System.out.println("in right downnnnnnnnnnnnnnnn");
            if (board[start][startJ].equals("   ")) {
                // System.out.println("Markig "+start+" "+startJ);
                marker[start][startJ] = 1;
            } else if (board[start][startJ].charAt(0) == this.player) {
                marker[start][startJ] = 1;
                if (board[start][startJ].charAt(1) == 'C') {
                    ArrayList<Integer> newAl = new ArrayList<>();
                    newAl.add(5);
                    newAl.add(i);
                    newAl.add(j);
                    this.al.add(newAl);
                }
                break;
            } else {
                break;
            }
        }

        // down
        for (int start = i + 1; start < marker.length; start++) {
            if (board[start][j].equals("   ")) {
                marker[start][j] = 1;
            } else if (board[start][j].charAt(0) == this.player) {
                marker[start][j] = 1;
                if (board[start][j].charAt(1) == 'C') {
                    ArrayList<Integer> newAl = new ArrayList<>();
                    newAl.add(5);
                    newAl.add(i);
                    newAl.add(j);
                    this.al.add(newAl);
                }
                break;
            } else { // same pieces
                break;
            }
        }

        // up
        for (int start = i - 1; start >= 0; start--) {
            if (board[start][j].equals("   ")) {
                marker[start][j] = 1;
            } else if (board[start][j].charAt(0) == this.player) {
                marker[start][j] = 1;
                if (board[start][j].charAt(1) == 'C') {
                    ArrayList<Integer> newAl = new ArrayList<>();
                    newAl.add(5);
                    newAl.add(i);
                    newAl.add(j);
                    this.al.add(newAl);
                }
                break;
            } else {
                break;
            }
        }

        // left
        for (int start = j - 1; start >= 0; start--) {
            if (board[i][start].equals("   ")) {
                marker[i][start] = 1;
            } else if (board[i][start].charAt(0) == this.player) {
                marker[i][start] = 1;
                if (board[i][start].charAt(1) == 'C') {
                    ArrayList<Integer> newAl = new ArrayList<>();
                    newAl.add(5);
                    newAl.add(i);
                    newAl.add(j);
                    this.al.add(newAl);
                }
                break;
            } else {
                break;
            }
        }

        // right
        for (int start = j + 1; start < marker.length; start++) {
            if (board[i][start].equals("   ")) {
                marker[i][start] = 1;
            } else if (board[i][start].charAt(0) == this.player) {
                marker[i][start] = 1;
                if (board[i][start].charAt(1) == 'C') {
                    ArrayList<Integer> newAl = new ArrayList<>();
                    newAl.add(5);
                    newAl.add(i);
                    newAl.add(j);
                    this.al.add(newAl);
                }
                break;
            } else {
                break;
            }
        }
    }

    void queenMovable(int marker[][]) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].charAt(0) == this.opponent && board[i][j].charAt(1) == 'Q') {
                    markQueenAttacks(marker, i, j);
                }
            }
        }
    }

    void markKingAttacks(int marker[][], int i, int j) {

        // left up
        if (i - 1 >= 0 && j - 1 >= 0 && board[i - 1][j - 1].charAt(0) != this.opponent) {
            marker[i - 1][j - 1] = 1;
            if (board[i - 1][j - 1].charAt(1) == 'C') {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(1);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }
        // up
        if (i - 1 >= 0 && board[i - 1][j].charAt(0) != this.opponent) {
            marker[i - 1][j] = 1;
            if (board[i - 1][j].charAt(1) == 'C') {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(1);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }
        // right up
        if (i - 1 >= 0 && j + 1 < marker.length && board[i - 1][j + 1].charAt(0) != this.opponent) {
            marker[i - 1][j + 1] = 1;
            if (board[i - 1][j + 1].charAt(1) == 'C') {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(1);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }
        // right
        if (j + 1 < marker.length && board[i][j + 1].charAt(0) != this.opponent) {
            marker[i][j + 1] = 1;
            if (board[i][j + 1].charAt(1) == 'C') {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(1);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }
        // right down
        if (i + 1 < marker.length && j + 1 < marker.length && board[i + 1][j + 1].charAt(0) != this.opponent) {
            marker[i + 1][j + 1] = 1;
            if (board[i + 1][j + 1].charAt(1) == 'C') {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(1);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }
        // down
        if (i + 1 < marker.length && board[i + 1][j].charAt(0) != this.opponent) {
            marker[i + 1][j] = 1;
            if (board[i + 1][j].charAt(1) == 'C') {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(1);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }
        // left down
        if (i + 1 < marker.length && j - 1 >= 0 && board[i + 1][j - 1].charAt(0) != this.opponent) {
            marker[i + 1][j - 1] = 1;
            if (board[i + 1][j - 1].charAt(1) == 'C') {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(1);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }
        // left
        if (j - 1 >= 0 && board[i][j - 1].charAt(0) != this.opponent) {
            marker[i][j - 1] = 1;
            if (board[i][j - 1].charAt(1) == 'C') {
                ArrayList<Integer> newAl = new ArrayList<>();
                newAl.add(1);
                newAl.add(i);
                newAl.add(j);
                this.al.add(newAl);
            }
        }
    }

    void kingMovable(int marker[][]) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].charAt(0) == this.opponent && board[i][j].charAt(1) == 'C') {
                    markKingAttacks(marker, i, j);
                    break;
                }
            }
        }
    }

    int getKingsI(String board[][]) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].charAt(0) == this.player && board[i][j].charAt(1) == 'C') {
                    return i;
                }
            }
        }
        return -1;
    }

    int getKingsJ(String board[][]) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].charAt(0) == this.player && board[i][j].charAt(1) == 'C') {
                    return j;
                }
            }
        }
        return -1;
    }

    boolean isKingMovable(int i, int j, int marker[][], String board[][]) {
        System.out.println("IN kings movable---------");
        System.out.println(i + " " + j);

        if (i - 1 >= 0 && j - 1 >= 0 && board[i - 1][j - 1].equals("   ") && marker[i - 1][j - 1] == 0) {
            System.out.println(1);
            return true;
        }
        if (i - 1 >= 0 && board[i - 1][j].equals("   ") && marker[i - 1][j] == 0) {
            System.out.println(2);
            return true;
        }
        if (i - 1 >= 0 && j + 1 < marker.length && board[i - 1][j + 1].equals("   ") && marker[i - 1][j + 1] == 0) {
            System.out.println(3);

            return true;
        }
        if (j + 1 < marker.length && board[i][j + 1].equals("   ") && marker[i][j + 1] == 0) {
            System.out.println(4);

            return true;
        }
        if (i + 1 < marker.length && j + 1 < marker.length && board[i + 1][j + 1].equals("   ")
                && marker[i + 1][j + 1] == 0) {
            System.out.println(5);

            return true;
        }
        if (i + 1 < marker.length && board[i + 1][j].equals("   ") && marker[i + 1][j] == 0) {
            System.out.println(6);

            return true;
        }
        if (i + 1 < marker.length && j - 1 >= 0 && board[i + 1][j - 1].equals("   ") && marker[i + 1][j - 1] == 0) {
            System.out.println(7);

            return true;
        }
        if (j - 1 >= 0 && board[i][j - 1].equals("   ") && marker[i][j - 1] == 0) {
            System.out.println(8);

            return true;
        }

        return false;
    }

    boolean isBlockable(int kingsI, int kingsJ, int marker[][]) {
        if (this.al.size() > 1) { // if more than one pieces are checking, then cant block
            return false;
        }
        if (al.get(0).get(0) == 2) { // if a knight is checking, then cant block
            return false;
        }
        if (al.get(0).get(0) == 0) { // if a pawn is attacking , then cant block
            return false;
        }

        int pieceI = al.get(0).get(1);
        int pieceJ = al.get(0).get(2);

        int iDiff = Math.abs(kingsI - pieceI);
        int jDiff = Math.abs(kingsJ - pieceJ);

        // rook or queen from up
        if (kingsJ == pieceJ && kingsI > pieceI) { // if both j coordinates are same && checking i is greater than kings
                                                   // i
            for (int start = (pieceI) + 1; start < kingsI; start++) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j].charAt(0) == this.player && board[i][j].charAt(1) != 'C') {
                            boolean canBlock = validateMove(board[i][j], i, j, start, kingsJ);
                            if (canBlock == true) {
                                System.out.println("---canblock " + canBlock + " " + board[i][j] + " " + i + " " + j
                                        + " " + start + " " + kingsJ);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // rook or queen from down
        else if (kingsJ == pieceJ && kingsI < pieceI) { // if both j coordinates are same && checking peice i is less
                                                        // than kings i
            for (int start = (kingsI) + 1; start < pieceI; start++) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j].charAt(0) == this.player && board[i][j].charAt(1) != 'C') {
                            boolean canBlock = validateMove(board[i][j], i, j, start, kingsJ);
                            if (canBlock == true) {
                                System.out.println("---canblock " + canBlock + " " + board[i][j] + " " + i + " " + j
                                        + " " + start + " " + kingsJ);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // rook or queen from left
        else if (kingsI == pieceI && kingsJ > pieceJ) { // if both i coordinates are same && checking peice j is less
                                                        // than kings j
            for (int start = (pieceJ) + 1; start < kingsJ; start++) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j].charAt(0) == this.player && board[i][j].charAt(1) != 'C') {
                            boolean canBlock = validateMove(board[i][j], i, j, kingsI, start);
                            if (canBlock == true) {
                                System.out.println("---canblock " + canBlock + " " + board[i][j] + " " + i + " " + j
                                        + " " + start + " " + kingsJ);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // rook or queen from right
        else if (kingsI == pieceI && kingsJ < pieceJ) { // if both i coordinates are same && checking peice j is greater
                                                        // than kings j
            for (int start = kingsJ + 1; start < (pieceJ); start++) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j].charAt(0) == this.player && board[i][j].charAt(1) != 'C') {
                            boolean canBlock = validateMove(board[i][j], i, j, kingsI, start);
                            if (canBlock == true) {
                                System.out.println("---canblock " + canBlock + " " + board[i][j] + " " + i + " " + j
                                        + " " + start + " " + kingsJ);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // bishop or queen from left up
        else if (iDiff == jDiff && kingsI > pieceI && kingsJ > pieceJ) { // piece in left up
            for (int startI = pieceI + 1, startJ = pieceJ + 1; startI < kingsI && startJ < kingsJ; startI++, startJ++) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j].charAt(0) == this.player && board[i][j].charAt(1) != 'C') {
                            boolean canBlock = validateMove(board[i][j], i, j, startI, startJ);
                            if (canBlock == true) {
                                System.out.println("---canblock " + canBlock + " " + board[i][j] + " " + i + " " + j
                                        + " " + startI + " " + startJ);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // bishop or queen from right down
        else if (iDiff == jDiff && kingsI < pieceI && kingsJ < pieceJ) { // piece in right down
            for (int startI = kingsI + 1, startJ = kingsJ + 1; startI < pieceI && startJ < pieceJ; startI++, startJ++) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j].charAt(0) == this.player && board[i][j].charAt(1) != 'C') {
                            boolean canBlock = validateMove(board[i][j], i, j, startI, startJ);
                            if (canBlock == true) {
                                System.out.println("---canblock " + canBlock + " " + board[i][j] + " " + i + " " + j
                                        + " " + startI + " " + startJ);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // bishop or queen from right up
        else if (iDiff == jDiff && kingsI > pieceI && kingsJ < pieceJ) { // piece in right up
            for (int startI = pieceI + 1, startJ = pieceJ - 1; startI < kingsI
                    && startJ >= kingsJ; startI++, startJ--) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j].charAt(0) == this.player && board[i][j].charAt(1) != 'C') {
                            boolean canBlock = validateMove(board[i][j], i, j, startI, startJ);
                            if (canBlock == true) {
                                System.out.println("---canblock " + canBlock + " " + board[i][j] + " " + i + " " + j
                                        + " " + startI + " " + startJ);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // bishop or queen from left down
        else if (iDiff == jDiff && kingsI < pieceI && kingsJ > pieceJ) { // piece in left down
            for (int startI = kingsI + 1, startJ = kingsJ - 1; startI < pieceI
                    && startJ >= pieceJ; startI++, startJ--) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j].charAt(0) == this.player && board[i][j].charAt(1) != 'C') {
                            boolean canBlock = validateMove(board[i][j], i, j, startI, startJ);
                            if (canBlock == true) {
                                System.out.println("---canblock " + canBlock + " " + board[i][j] + " " + i + " " + j
                                        + " " + startI + " " + startJ);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;

    }

    boolean isCapturable(){
        if(al.size() > 1){
            return false;
        }
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(board[i][j].charAt(0) == this.player){
                    boolean canCapture = validateMove(board[i][j], i, j, al.get(0).get(1), al.get(0).get(2));
                    if(canCapture == true){
                        System.out.println("IsCapturable "+board[i][j]+" "+i+" "+j+" "+al.get(0).get(1)+" "+al.get(0).get(2));
                        return true;
                    }
                }
            }
        }

        return false;
    }
    void showBoard() {

        System.out.println("\n     ───── ───── ───── ───── ───── ───── ───── ───── ");

        for (int i = 0; i < board.length; i++) {
            System.out.print((i + 1) + "  ");
            System.out.print(" │ ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " │ ");
            }
            System.out.println("");
            System.out.println("     ───── ───── ───── ───── ───── ───── ───── ───── ");
        }

        System.out.println();
        // System.out.print(" ");

        // for (int i = 0; i < 8; i++) {
        // }
        // System.out.println();
        System.out.print("    ");
        for (int i = 97; i < 105; i++) {
            char c = (char) i;
            System.out.print("   " + c + "  ");
        }
        System.out.println();
        System.out.println();

        // System.out.println(" ───── ───── ───── ───── ───── ───── ───── ───── ");
    }

    public static void main(String args[]) {
        Chess obj = new Chess();
        Scanner sc = new Scanner(System.in);

        // obj.showBoard();

        // int arr[][] = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};

        obj.showBoard();
        // obj.flipBoard(obj.board);

        while (true) {

            obj.setPlayer('\'');
            obj.al = new ArrayList<>();

            String in1 = null;
            int i = 0;
            int j = 0;
            String chosenPiece = null;

            String in2 = null;
            int movedI = 0;
            int movedJ = 0;

            int kingsI = obj.getKingsI(obj.board);
            int kingsJ = obj.getKingsJ(obj.board);

            int marker[][] = new int[8][8];

            obj.markAllAttacks(marker);

            if (marker[kingsI][kingsJ] == 1 && obj.al.size()>=1 && obj.isKingMovable(kingsI, kingsJ, marker, obj.board) == false) {
                System.out.println("!!!!!!!!!!!!!!------------------ matee");
                return;
            }

            System.out.println("----------Is Movable " + obj.isKingMovable(kingsI, kingsJ, marker, obj.board));
            System.out.println("Pawn attacks; " + obj.al);
            // System.out.println("Is capturable " + obj.isCapturable());

            // for (int x = 0; x < marker.length; x++) {
            // for (int y = 0; y < marker.length; y++) {
            // System.out.print(marker[x][y] + " ");
            // }
            // System.out.println();
            // }

            System.out.println("----Checking " + marker[kingsI][kingsJ]);

            boolean isValidated = false;

            boolean isCheck = true;

            while (isValidated == false || isCheck == true) {

                if (marker[kingsI][kingsJ] == 1) {
                    System.out.println("In Check");
                }
                System.out.print("['] Enter Position of the piece to move: ");
                in1 = sc.next();
                i = obj.getI(in1);
                j = obj.getJ(in1);
                chosenPiece = obj.getPiece(i, j);

                System.out.print("['] Enter position to move this piece: ");
                in2 = sc.next();
                movedI = obj.getI(in2);
                movedJ = obj.getJ(in2);

                System.out.println("######### " + obj.validateMove(chosenPiece, i, j, movedI, movedJ) + " "
                        + marker[kingsI][kingsJ]);

                String movedPiece = obj.board[movedI][movedJ];
                if (obj.validateMove(chosenPiece, i, j, movedI, movedJ) == true) {
                    isValidated = true;
                    obj.board[movedI][movedJ] = chosenPiece;
                    obj.board[i][j] = "   ";
                } else {
                    System.out.println("Wrong move else");
                }

                marker = new int[8][8];
                obj.markAllAttacks(marker);

                kingsI = obj.getKingsI(obj.board);
                kingsJ = obj.getKingsJ(obj.board);

                if (marker[kingsI][kingsJ] == 1) {
                    System.out.println("In Check, move again");
                    obj.board[movedI][movedJ] = movedPiece;
                    obj.board[i][j] = chosenPiece;
                    isCheck = true;
                    isValidated = false;
                } else {
                    // obj.board[movedI][movedJ] = chosenPiece;
                    // obj.board[i][j] = " ";
                    System.out.println("Moved");
                    obj.showBoard();
                    isCheck = false;
                }

                System.out.println("------Validationnn " + obj.validateMove(chosenPiece, i, j, movedI, movedJ));

            }
            // obj.validateMove(chosenPiece, i, j, movedI, movedJ);

            obj.flipBoard(obj.board);

            obj.setPlayer('.');
            obj.al = new ArrayList<>();

            String in3 = null;
            int i2 = 0;
            int j2 = 0;
            String chosenPiece2 = null;

            String in4 = null;
            int movedI2 = 0;
            int movedJ2 = 0;

            int kingsI2 = obj.getKingsI(obj.board);
            int kingsJ2 = obj.getKingsJ(obj.board);
            System.out.println("&&&Kingsi2 " + kingsI2 + " kingsj2 " + kingsJ2);
            int marker2[][] = new int[8][8];

            obj.markAllAttacks(marker2);
            // System.out.println("======================================================"+marker2[kingsI2][kingsJ2] +" "+obj.al+" "+obj.isKingMovable(kingsI2, kingsJ2, marker2, obj.board));
            if (marker2[kingsI2][kingsJ2] == 1 && obj.al.size()>=1 && obj.isKingMovable(kingsI2, kingsJ2, marker2, obj.board) == false) {
                System.out.println("!!!!!!!!!!!!!!------------------ matee");
                return;
            }

            System.out.println("------ attacks; " + obj.al);

            // for (int x = 0; x < marker2.length; x++) {
            // for (int y = 0; y < marker2.length; y++) {
            // System.out.print(marker2[x][y] + " ");
            // }
            // System.out.println();
            // }

            System.out.println("----Checking " + marker2[kingsI2][kingsJ2]);

            boolean isValidated2 = false;

            boolean isCheck2 = true;

            while (isValidated2 == false || isCheck2 == true) {

                if (marker2[kingsI2][kingsJ2] == 1) {
                    System.out.println("In Check");
                }
                System.out.print("[.] Enter Position of the piece to move: ");
                in3 = sc.next();
                i2 = obj.getI(in3);
                j2 = obj.getJ(in3);
                chosenPiece2 = obj.getPiece(i2, j2);

                System.out.print("[.] Enter position to move this piece: ");
                in4 = sc.next();
                movedI2 = obj.getI(in4);
                movedJ2 = obj.getJ(in4);

                String movedPiece2 = obj.board[movedI2][movedJ2];
                if (obj.validateMove(chosenPiece2, i2, j2, movedI2, movedJ2) == true) {
                    isValidated2 = true;
                    obj.board[movedI2][movedJ2] = chosenPiece2;
                    obj.board[i2][j2] = "   ";
                } else {
                    System.out.println("Wrong move else");
                }

                marker2 = new int[8][8];
                obj.markAllAttacks(marker2);

                kingsI2 = obj.getKingsI(obj.board);
                kingsJ2 = obj.getKingsJ(obj.board);
                if (marker2[kingsI2][kingsJ2] == 1) {
                    System.out.println("In Check, move again");
                    obj.board[movedI2][movedJ2] = movedPiece2;
                    obj.board[i2][j2] = chosenPiece2;
                    isCheck2 = true;
                    isValidated2 = false;
                } else {
                    // obj.board[movedI][movedJ] = chosenPiece;
                    // obj.board[i][j] = " ";
                    System.out.println("Moved");
                    obj.showBoard();
                    isCheck2 = false;
                }
            }

            obj.flipBoard(obj.board);

        }
    }
}

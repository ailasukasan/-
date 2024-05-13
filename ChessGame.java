import java.util.Scanner;
import java.awt.Point;
import java.util.Random;

public class ChessGame {

​    private static final int BOARD_GRAD_SIZE = 15;

​    public class ChessData {
​        int[][] chessMap = new int[BOARD_GRAD_SIZE][BOARD_GRAD_SIZE];
​        int[][] scoreMap = new int[BOARD_GRAD_SIZE][BOARD_GRAD_SIZE];
​        boolean playerFlag;
​    }

​    public class Point {
​        int row;
​        int col;

​        public Point(int row, int col) {
​            this.row = row;
​            this.col = col;
​        }
​    }

​    public void initializeChessData(ChessData data) {
​        if (data == null)
​            return;

​        for (int row = 0; row < BOARD_GRAD_SIZE; row++) {
​            for (int col = 0; col < BOARD_GRAD_SIZE; col++) {
​                data.chessMap[row][col] = 0;
​                data.scoreMap[row][col] = 0;
​            }
​        }
​        data.playerFlag = true;
​    }

​    public void updateGameMap(ChessData data, int row, int col) {
​        if (data == null)
​            return;

​        if (data.playerFlag) {
​            data.chessMap[row][col] = 1;
​        } else {
​            data.chessMap[row][col] = -1;
​        }

​        data.playerFlag = !data.playerFlag;
​    }

​    public boolean checkWin(ChessData game, int row, int col) {
​        for (int i = 0; i < 5; i++) {
​            if (col - i >= 0 &&
​                    col - i + 4 < BOARD_GRAD_SIZE &&
​                    game.chessMap[row][col - i] == game.chessMap[row][col - i + 1] &&
​                    game.chessMap[row][col - i] == game.chessMap[row][col - i + 2] &&
​                    game.chessMap[row][col - i] == game.chessMap[row][col - i + 3] &&
​                    game.chessMap[row][col - i] == game.chessMap[row][col - i + 4]) {
​                return true;
​            }
​        }

​        for (int i = 0; i < 5; i++) {
​            if (row - i >= 0 &&
​                    row - i + 4 < BOARD_GRAD_SIZE &&
​                    game.chessMap[row - i][col] == game.chessMap[row - i + 1][col] &&
​                    game.chessMap[row - i][col] == game.chessMap[row - i + 2][col] &&
​                    game.chessMap[row - i][col] == game.chessMap[row - i + 3][col] &&
​                    game.chessMap[row - i][col] == game.chessMap[row - i + 4][col]) {
​                return true;
​            }
​        }

​        for (int i = 0; i < 5; i++) {
​            if (row + i < BOARD_GRAD_SIZE &&
​                    row + i - 4 >= 0 &&
​                    col - i >= 0 &&
​                    col - i + 4 < BOARD_GRAD_SIZE) {
​                boolean isWin = true;
​                for (int j = 0; j < 5; j++) {
​                    if (game.chessMap[row + i - j][col - i + j] != game.chessMap[row + i][col - i]) {
​                        isWin = false;
​                        break;
​                    }
​                }
​                if (isWin) {
​                    return true;
​                }
​            }
​        }

​        for (int i = 0; i < 5; i++) {
​            if (row - i >= 0 &&
​                    row - i + 4 < BOARD_GRAD_SIZE &&
​                    col - i >= 0 &&
​                    col - i + 4 < BOARD_GRAD_SIZE) {
​                boolean isWin = true;
​                for (int j = 0; j < 5; j++) {
​                    if (game.chessMap[row - i + j][col - i + j] != game.chessMap[row - i][col - i]) {
​                        isWin = false;
​                        break;
​                    }
​                }
​                if (isWin) {
​                    return true;
​                }
​            }
​        }

​        return false;
​    }

​    public Point actionByAI(ChessData data) {
​        calculateScore(data);

​        int maxScore = 0;
​        Point[] maxPoints = new Point[BOARD_GRAD_SIZE * BOARD_GRAD_SIZE];
​        int k = 0;

​        for (int row = 0; row < BOARD_GRAD_SIZE; row++) {
​            for (int col = 0; col < BOARD_GRAD_SIZE; col++) {
​                if (data.chessMap[row][col] == 0) {
​                    if (data.scoreMap[row][col] > maxScore) {
​                        k = 0;
​                        maxScore = data.scoreMap[row][col];
​                        maxPoints[k] = new Point(row, col);
​                        k++;
​                    } else if (data.scoreMap[row][col] == maxScore) {
​                        maxPoints[k] = new Point(row, col);
​                        k++;
​                    }
​                }
​            }
​        }

​        Random rand = new Random();
​        int index = rand.nextInt(k);
​        return maxPoints[index];
​    }

​    public void calculateScore(ChessData data) {
​        int personNum;
​        int botNum;
​        int emptyNum;

​        for (int row = 0; row < BOARD_GRAD_SIZE; row++) {
​            for (int col = 0; col < BOARD_GRAD_SIZE; col++) {
​                if (row >= 0 && col >= 0 && data.chessMap[row][col] == 0) {
​                    int[][] directs = {
​                            { 1, 0 },
​                            { 1, 1 },
​                            { 0, 1 },
​                            { -1, 1 }
​                    };

​                    for (int k = 0; k < 4; k++) {
​                        int x = directs[k][0];
​                        int y = directs[k][1];

​                        personNum = 0;
​                        botNum = 0;
​                        emptyNum = 0;

​                        for (int i = 1; i <= 4; i++) {
​                            if (row + i * y >= 0 && row + i * y < BOARD_GRAD_SIZE &&
​                                    col + i * x >= 0 && col + i * x < BOARD_GRAD_SIZE &&
​                                    data.chessMap[row + i * y][col + i * x] == 1) {
​                                personNum++;
​                            } else if (row + i * y >= 0 && row + i * y < BOARD_GRAD_SIZE &&
​                                    col + i * x >= 0 && col + i * x < BOARD_GRAD_SIZE &&
​                                    data.chessMap[row + i * y][col + i * x] == 0) {
​                                emptyNum++;
​                                break;
​                            } else {
​                                break;
​                            }
​                        }

​                        for (int i = 1; i <= 4; i++) {
​                            if (row - i * y >= 0 && row - i * y < BOARD_GRAD_SIZE &&
​                                    col - i * x >= 0 && col - i * x < BOARD_GRAD_SIZE &&
​                                    data.chessMap[row - i * y][col - i * x] == 1) {
​                                personNum++;
​                            } else if (row - i * y >= 0 && row - i * y < BOARD_GRAD_SIZE &&
​                                    col - i * x >= 0 && col - i * x < BOARD_GRAD_SIZE &&
​                                    data.chessMap[row - i * y][col - i * x] == 0) {
​                                emptyNum++;
​                                break;
​                            } else {
​                                break;
​                            }
​                        }

​                        if (personNum == 1) {
​                            data.scoreMap[row][col] += 10;
​                        } else if (personNum == 2) {
​                            if (emptyNum == 1) {
​                                data.scoreMap[row][col] += 30;
​                            } else if (emptyNum == 2) {
​                                data.scoreMap[row][col] += 40;
​                            }
​                        } else if (personNum == 3) {
​                            if (emptyNum == 1) {
​                                data.scoreMap[row][col] += 60;
​                            } else if (emptyNum == 2) {
​                                data.scoreMap[row][col] += 200;
​                            }
​                        } else if (personNum == 4) {
​                            data.scoreMap[row][col] += 20000;
​                        }

​                        emptyNum = 0;

​                        for (int i = 1; i <= 4; i++) {
​                            if (row + i * y > 0 && row + i * y < BOARD_GRAD_SIZE &&
​                                    col + i * x > 0 && col + i * x < BOARD_GRAD_SIZE &&
​                                    data.chessMap[row + i * y][col + i * x] == -1) {
​                                botNum++;
​                            } else if (row + i * y > 0 && row + i * y < BOARD_GRAD_SIZE &&
​                                    col + i * x > 0 && col + i * x < BOARD_GRAD_SIZE &&
​                                    data.chessMap[row + i * y][col + i * x] == 0) {
​                                emptyNum++;
​                                break;
​                            } else {
​                                break;
​                            }
​                        }

​                        for (int i = 1; i <= 4; i++) {
​                            if (row - i * y > 0 && row - i * y < BOARD_GRAD_SIZE &&
​                                    col - i * x > 0 && col - i * x < BOARD_GRAD_SIZE &&
​                                    data.chessMap[row - i * y][col - i * x] == -1) {
​                                botNum++;
​                            } else if (row - i * y > 0 && row - i * y < BOARD_GRAD_SIZE &&
​                                    col - i * x > 0 && col - i * x < BOARD_GRAD_SIZE &&
​                                    data.chessMap[row - i * y][col - i * x] == 0) {
​                                emptyNum++;
​                                break;
​                            } else {
​                                break;
​                            }
​                        }

​                        if (botNum == 0) {
​                            data.scoreMap[row][col] += 5;
​                        } else if (botNum == 1) {
​                            data.scoreMap[row][col] += 10;
​                        } else if (botNum == 2) {
​                            if (emptyNum == 1) {
​                                data.scoreMap[row][col] += 25;
​                            } else if (emptyNum == 2) {
​                                data.scoreMap[row][col] += 50;
​                            }
​                        } else if (botNum == 3) {
​                            if (emptyNum == 1) {
​                                data.scoreMap[row][col] += 55;
​                            } else if (emptyNum == 2) {
​                                data.scoreMap[row][col] += 300;
​                            }
​                        } else if (botNum >= 4) {
​                            data.scoreMap[row][col] += 30000;
​                        }
​                    }
​                }
​            }
​        }
​    }

​    public static void main(String[] args) {
​        ChessGame game = new ChessGame();
​        ChessData data = game.new ChessData();
​        game.initializeChessData(data);

​        Scanner scanner = new Scanner(System.in);

​        // 游戏主循环
​        while (true) {
​            Point point = game.actionByAI(data);
​            System.out.println("AI选择下在这里: (" + point.row + ", " + point.col + ")");
​            game.updateGameMap(data, point.row, point.col);
​            if (game.checkWin(data, point.row, point.col)) {
​                System.out.println("对不起，你输了，AI获胜");
​                break;
​            }

​            // 玩家输入逻辑
​            System.out.println("轮到你下了，请输入坐标 (例如, '3 4'): ");
​            int playerRow = scanner.nextInt();
​            int playerCol = scanner.nextInt();

​            if (playerRow < 0 || playerRow >= BOARD_GRAD_SIZE || playerCol < 0 || playerCol >= BOARD_GRAD_SIZE) {
​                System.out.println("你不能下在这里，请重新输入！");
​                continue;
​            }

​            if (data.chessMap[playerRow][playerCol] != 0) {
​                System.out.println("你不能下在这里，这里已经有棋子了，下在其他地方吧");
​                continue;
​            }

​            game.updateGameMap(data, playerRow, playerCol);
​            if (game.checkWin(data, playerRow, playerCol)) {
​                System.out.println("恭喜你赢了！");
​                break;
​            }

​            // 打印棋盘状态
​            printChessboard(data);
​        }
​    }

​    // 打印棋盘状态
​    private static void printChessboard(ChessData data) {
​        for (int row = 0; row < BOARD_GRAD_SIZE; row++) {
​            for (int col = 0; col < BOARD_GRAD_SIZE; col++) {
​                if (data.chessMap[row][col] == 1) {
​                    System.out.print("X ");
​                } else if (data.chessMap[row][col] == -1) {
​                    System.out.print("O ");
​                } else {
​                    System.out.print(". ");
​                }
​            }
​            System.out.println();
​        }
​    }
}


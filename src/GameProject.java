import java.util.Random;

public class GameProject {

	static final int GAME_LOOP_NUMBER = 100;
	static final int HEIGHT = 15;
	static final int WIDTH = 15;
	static final Random RANDOM = new Random();

	public static void main(String[] args) throws InterruptedException {
		String playerMark = "O";
		int playerRow = 2;
		int playerColumn = 2;
		Direction playerDirection = Direction.RIGHT;

		String enemyMark = "-";
		int enemyRow = 7;
		int enemyColumn = 4;
		Direction enemyDirection = Direction.LEFT;
	
		String[][] level = new String[HEIGHT][WIDTH];
		initLevel(level);
		
		addRandomWalls(level, 1, 1);

		for (int iterationNr = 1; iterationNr <= GAME_LOOP_NUMBER; iterationNr++) {
			// player of enemy
			if (iterationNr % 15 == 0) {
				playerDirection = changeDirection(playerDirection);
			}
			int[] playerCoordinates = makeMove(playerDirection, level, playerRow, playerColumn);
			playerRow = playerCoordinates[0];
			playerColumn = playerCoordinates[1];

			// entry of enemy
			if (iterationNr % 10 == 0) {
				enemyDirection = changeDirection(enemyDirection);
			}
			int[] enemyCoordinates = makeMove(enemyDirection, level, enemyRow, enemyColumn);
			enemyRow = enemyCoordinates[0];
			enemyColumn = enemyCoordinates[1];
			
			draw(level, playerMark, playerRow, playerColumn, enemyMark, enemyRow, enemyColumn);
			
			addSameDelay(200L,iterationNr);
			
			if (enemyRow == playerRow && enemyColumn == playerColumn) {
				System.out.println("The End of the Game");
				break;
			}
		}
	}

	static void addRandomWalls(String[][] level, int nrOfHorizontalWalls, int nrOfVerticalWalls) {
		for (int i = 0; i < nrOfHorizontalWalls; i++) {
			addHorizontalWall(level);
		}
		for (int i = 0; i < nrOfVerticalWalls; i++) {
			addVerticalWall(level);
		}
	}
	
	static void addHorizontalWall(String[][] level) {
		// TODO player und enemy avoid the wall.
		int wallWidth = RANDOM.nextInt(WIDTH - 3);
		int wallRow = RANDOM.nextInt(HEIGHT - 2) + 1;
		int wallColumn = RANDOM.nextInt(WIDTH - 2 - wallWidth);
		for (int i = 0; i < wallWidth; i++) {
			level[wallRow][wallColumn + i] = "X";
		}
	}
	
	static void addVerticalWall(String[][] level) {
		int wallHeight = RANDOM.nextInt(HEIGHT - 3);
		int wallRow = RANDOM.nextInt(HEIGHT - 2 - wallHeight);
		int wallColumn = RANDOM.nextInt(WIDTH - 2) + 1;
		for (int i = 0; i < wallHeight; i++) {
			level[wallRow + i][wallColumn] = "X";
		}
	}
	
	private static void addSameDelay(long timeout, int iterationNr) throws InterruptedException {
		System.out.println("-------" + iterationNr + "-------");
		Thread.sleep(timeout);
	}
	
	static int[] makeMove(Direction direction, String[][] level, int row, int column) {
		switch (direction) {
		case UP:
			if (level[row - 1][column].equals(" ")) {
				row--;
			}
			break;
		case DOWN:
			if (level[row + 1][column].equals(" ")) {
				row++;
			}
			break;
		case LEFT:
			if (level[row][column - 1].equals(" ")) {
				column--;
			}
			break;
		case RIGHT:
			if (level[row][column + 1].equals(" ")) {
				column++;
			}
			break;
		}
		return new int[] {row, column};
	}

	static void initLevel(String[][] level) {
		for (int row = 0; row < level.length; row++) {
			for (int column = 0; column < level[row].length; column++) {
				if (row == 0 || column == 0 || row == HEIGHT - 1 || column == WIDTH - 1) {
					level[row][column] = "X";
				} else {
					level[row][column] = " ";
				}
			}
		}
	}

	static Direction changeDirection(Direction direction) {
		switch (direction) {
		case RIGHT:
			return Direction.DOWN;
		case DOWN:
			return Direction.LEFT;
		case LEFT:
			return Direction.UP;
		case UP:
			return Direction.RIGHT;
		}
		return direction;
	}

	static void draw(String[][] board, String playerMark, int playerRow, int playerColumn, String enemyMark, int enemyRow, int enemyColumn) {
		for (int row = 0; row < HEIGHT; row++) {
			for (int column = 0; column < WIDTH; column++) {
				if (row == playerRow && column == playerColumn) {
					System.out.print(playerMark);
				} else if (row == enemyRow && column == enemyColumn) {
					System.out.print(enemyMark);
				} else {
					System.out.print(board[row][column]);
				}
			}
			System.out.println();
		}
	}
}
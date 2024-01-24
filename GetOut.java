import java.util.Random;
import java.util.Scanner;

public class GetOut {

    boolean coinReached = false; 

    public String[][] playField = new String[10][10]; 
    String door = "\u001B[34m [] \u001B[0m"; 
    int xDoor, yDoor;  

    String player = "\u001B[32m ++ \u001B[0m";
    int xPlayer, yPlayer; 

    String snake = "\u001B[31m -- \u001B[0m";
    int xSnake, ySnake; 

    String muenze = "\u001B[33m <> \u001B[0m";
    int xCoin, yCoin; 

    String GOLD = "\u001B[33m";
    String GREEN = "\u001B[32m";
    String RED = "\u001B[31m";
    String BLUE = "\u001B[34m";
    String RESET = "\u001B[0m";

    public static void main(String[] args) {
        GetOut run = new GetOut(); 
        run.main();
    }

    private void main() {
        boolean isGameActive = true;
        init();
        printPlayField();
        do {
            movePlayer();
            clearConsole();
            if(xPlayer == xSnake && yPlayer == ySnake) {
                clearConsole();
                System.out.println(RED + "Game Over! Du wurdest von der Schlange gefressen!" + RESET);
                isGameActive = false;
                break;
            }
            if(xPlayer == xCoin && yPlayer == yCoin) {
                coinReached = true; 
                System.out.println(GOLD + "Coin erreicht!" + RESET);
            }
            if (xPlayer == xDoor && yPlayer == yDoor && coinReached) {
                clearConsole();
                System.out.println(GOLD + "Du hast gewonnen und die Tür erreicht!" + RESET);
                isGameActive = false;
                break;
            }
            moveSnake(); 
            printPlayField();
        } while (isGameActive); 
    }
    

    public void init() {
        for(int i=0; i<playField.length; i++) {
            for(int j=0; j<playField[i].length; j++) {
                playField[i][j] = " .. ";  
            }
        }
        setDoor();
        setPlayer();
        setSnake();
        setCoin();
    }

    public void printPlayField() {
        for(int i=0; i<playField.length; i++) {
            for(int j=0; j<playField[i].length; j++) {
                System.out.print(playField[i][j]);
            }
            System.out.println();
        }
    }

    public void setDoor() {
        Random random = new Random(); 
        xDoor = random.nextInt(0,10); 
        yDoor = random.nextInt(0,10); 
        playField[yDoor][xDoor] = door; 
    }

    public void setPlayer() {
        Random random = new Random(); 
        xPlayer = random.nextInt(0,10); 
        yPlayer = random.nextInt(0,10); 
        playField[yPlayer][xPlayer] = player; 
    }

    public void setSnake() {
        Random random = new Random(); 
        xSnake = random.nextInt(0,10); 
        ySnake = random.nextInt(0,10); 
        playField[ySnake][xSnake] = snake; 
    } 

    public void setCoin() {
        Random random = new Random(); 
        xCoin = random.nextInt(0,10); 
        yCoin = random.nextInt(0,10); 
        playField[yCoin][xCoin] = muenze; 
    }

    public void movePlayer() {
        System.out.println("w a s d für Bewegung des Spielers");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
    
        if (userInput.equals("w")) {
            if (yPlayer > 0 && !(xPlayer == xDoor && yPlayer-1 == yDoor && !coinReached)) {
                playField[yPlayer][xPlayer] = " .. ";
                yPlayer--;
                playField[yPlayer][xPlayer] = player;
            } else {
                System.out.println("Weiter kann man nicht gehen!");
            }
    
        } else if (userInput.equals("s")) {
            if (yPlayer < 9 && !(xPlayer == xDoor && yPlayer+1 == yDoor && !coinReached)) {
                playField[yPlayer][xPlayer] = " .. ";
                yPlayer++;
                playField[yPlayer][xPlayer] = player;
            } else {
                System.out.println("Weiter kann man nicht gehen!");
            }
    
        } else if (userInput.equals("a")) {
            if (xPlayer > 0 && !(xPlayer-1 == xDoor && yPlayer == yDoor && !coinReached)) {
                playField[yPlayer][xPlayer] = " .. ";
                xPlayer--;
                playField[yPlayer][xPlayer] = player;
            } else {
                System.out.println("Weiter kann man nicht gehen!");
            }
    
        } else if (userInput.equals("d")) {
            if (xPlayer < 9 && !(xPlayer+1 == xDoor && yPlayer == yDoor && !coinReached)) {
                playField[yPlayer][xPlayer] = " .. ";
                xPlayer++;
                playField[yPlayer][xPlayer] = player;
            } else {
                System.out.println("Weiter kann man nicht gehen!");
            }
        }
    }

    public void moveSnake() {
        int prevX = xSnake;
        int prevY = ySnake;
        boolean moveX, moveY; 
        Random random = new Random(); 
        int randomInt = random.nextInt(1,3); 
        
        if (randomInt == 1) {
            if(yPlayer > ySnake) {
                ySnake = ySnake + 1; 
            }
            else if(yPlayer < ySnake){
                ySnake = ySnake - 1; 
            }
            moveX = false;
            moveY = true;
        } else if (randomInt == 2) {
            if(xPlayer > xSnake) {
                xSnake = xSnake + 1; 
            }
            else if(xPlayer < xSnake){
                xSnake = xSnake - 1; 
            }
            moveX = true;
            moveY = false;
        } else {
            moveX = false;
            moveY = false;
        }
        
        if ((xCoin == xSnake && yCoin == ySnake) || (xDoor == xSnake && yDoor == ySnake) || (xPlayer == xSnake && yPlayer == ySnake)) {
            clearConsole();
            System.out.println(RED + "Game Over! Du wurdest von der Schlange gefressen!" + RESET);
            System.exit(0);  // Beendet das Programm
        }
        
        playField[prevY][prevX] = " .. ";
        playField[ySnake][xSnake] = snake;
    }
    
    
    public void clearConsole() {
        try {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package Score;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

//detta är en JTextArea som visar alla spelare och deras poäng
public class Leaderboard extends JTextArea {
    int width, height;
    ArrayList<Player> playerList;
    Scanner fr; //fr står för file reader
    File playerFile = new File("src/Score/highScore.txt");
    userNameField userNameField;
    public Player currentPlayer;

    //konstruktorn
    public Leaderboard(int width, int height, userNameField userNameField) {
        this.width = width;
        this.height = height - 100;
        setBorder(ScoreKeeper.border);
        setEditable(true);
        setLayout(new GridLayout());
        playerList = new ArrayList<>();
        this.userNameField = userNameField;
        createScanner();
    }

    //försöker skapa skannern
    private void createScanner() {
        try {
            fr = new Scanner(playerFile);
            readFile(fr);
        } catch (FileNotFoundException e) {//ger ett felmeddelande om den inte kan hitta textfilen med alla spelare
            System.out.println("filen fanns inte");
        }
    }

    //läser in textfilen och lägger till alla spelare i systemet
    protected void readFile(Scanner fr) {
        while (fr.hasNext()) {
            playerList.add(convertPlayer(fr.nextLine().trim()));
        }
        try {
            currentPlayer = playerList.getFirst();
        } catch (NoSuchElementException e){
            currentPlayer = new Player("null: ", 0);
            playerList.add(currentPlayer);
        }
        updateList();
    }

    //skapar en Player av en sträng som kan läggas in i en lista
    protected Player convertPlayer(String s) {
        String scoreNumbers;
        try {
            scoreNumbers = s.substring(s.lastIndexOf(": ")).replaceAll("\\D+","");

        } catch (StringIndexOutOfBoundsException e) {
            Player p = new Player("Invalid name: ", 0);
            System.out.println(p);
            return p;
        }
        int numberIndex = s.indexOf(scoreNumbers);
        int score = Integer.parseInt(scoreNumbers);
        String name = s.substring(0, numberIndex);
        return new Player(name, score);
    }

    //kollar om den valda spelaren har spelat tidigare och skapar annars en ny
    public void existingPlayer() {
        String playerName = userNameField.getPlayer();
        int playerIndex = searchPlayerList(playerName);
        if (playerIndex != -1) {
            currentPlayer = playerList.get(playerIndex);
        } else {
            currentPlayer = new Player(playerName, 0);
            playerList.add(currentPlayer);
            updateList();
        }
        System.out.println(currentPlayer);
    }

    //Använder en sökalgoritm för att hitta den valda spelaren. Om den inte finns returneras -1
    private int searchPlayerList(String s) {
        for (int i = 0; i < playerList.size(); i++) {
            String temp = playerList.get(i).name;
            if (temp.equals(s)) {
                return i;
            }
        }
        return -1;
    }

    //skriver om och uppdaterar text-rutan
    public void updateList() {
        sortPlayerList();
        this.setText("");
        for (Player p : playerList) {
            this.append(p.toString() + "\n");
        }

    }

    //skriver och uppdaterar textfilen
    public void writeToFile() {
        //System.out.println("antal spelare " + playerList.size());
        try {
            FileWriter wr = new FileWriter(playerFile);
            wr.write("");
            for (Player p : playerList) {
                String s = p.toString();
                wr.write(s + "\n");
            }
            wr.close();
        } catch (IOException e) {
            System.out.println("fel: " + e.getMessage());
        }
    }

    //sorterar så att spelaren med högst poäng står överst
    private void sortPlayerList() {
        int length = playerList.size();
        for (int i = 0; i < length - 1; i++) {
            int smallest = i;
            for (int j = i + 1; j <= length - 1; j++) {
                if (playerList.get(j).score > playerList.get(smallest).score) {
                    smallest = j;
                }
            }
            Player t = playerList.get(smallest);
            playerList.set(smallest, playerList.get(i));
            playerList.set(i, t);
        }
        writeToFile();
    }
}

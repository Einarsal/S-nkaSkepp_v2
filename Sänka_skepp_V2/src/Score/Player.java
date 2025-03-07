package Score;

//en klass som för att ha datatypen Player som kan lagras i en arraylist
public class Player  {
    String name;
    public int score;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    //en toString för att kunna skriva ut spelaren i text arean
    public String toString() {
        return name + score;
    }
}

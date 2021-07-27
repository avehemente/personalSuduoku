public class Main {

    public static void main(String[] args) {

        Board b = new Board();
        b.generateBoard(25);
        System.out.println(b);
        b.solve();
        System.out.println(b);
        System.out.println(b.validBoardState());
    }
}

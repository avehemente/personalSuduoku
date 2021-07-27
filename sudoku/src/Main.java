public class Main {

    public static void main(String[] args) {

        Board b = new Board();
        b.placeNumber(1, 8, 1);
        System.out.println(b);
        System.out.println(b.validBoardState());
    }
}

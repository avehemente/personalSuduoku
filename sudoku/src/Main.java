public class Main {

    public static void main(String[] args) {

        Board b = new Board();
        b.placeNumber(7, 8, 8);
        b.placeNumber(7, 0,1);
        b.placeNumber(7, 7, 8);
        System.out.println(b);
        System.out.println(b.validBoardState());
    }
}

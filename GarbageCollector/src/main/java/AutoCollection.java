public class AutoCollection {
    public static void main(String[] args) {
        //Strong, soft, weak, fantom - references
        processAnonimous();
    }

    public static void processAnonimous() {
        Thing thing1, thing2, thing3, thing4, thing5, thing6;
        thing1 = new Thing(); thing1.show();
        thing2 = new Thing(); thing2.show();
        {
            thing3 = new Thing(); thing3.show();
            thing4 = new Thing(); thing4.show();

        }

        thing5 = new Thing(); thing5.show();
        thing6 = new Thing(); thing6.show();

        thing1.show();

    }
}


class Thing {
    private static final String names[] = {
            "Stapler", "Eraser", "Pen", "Pencil", "Marker",
            "Calculator", "Glue", "Scissors", "Notebook", "Envelope"};
    private static int counter = 0;
    private static long heapFreeSize = Runtime.getRuntime().freeMemory();
    private int id;
    private String name;

    public Thing() {
        id = counter++;
        name = names[(int) Math.floor(Math.random() * names.length)];
    }


    @Override
    public String toString() {
        heapFreeSize = Runtime.getRuntime().freeMemory();
        return "Thing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", heapFreeSize=" + heapFreeSize +
                '}';
    }

    public void show() {
        System.out.println(toString());
    }

    @Override
    protected void finalize() {
        --counter;
    }
}
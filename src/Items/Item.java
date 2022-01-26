public class Item {
    String name;
    String id = "Item";
    String desc;
    boolean isWearable = false;
    String wearloc;

    public void look() {
        System.out.println(name);
    }

}

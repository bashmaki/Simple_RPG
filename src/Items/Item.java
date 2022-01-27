public class Item {
    String name;
    String id = "Item";
    String desc;
    boolean isWearable = false;
    String wearloc;

    public void look() {
        System.out.println(name);
    }

//    public void isWearlock() {
//        Class<? extends PC> equipments = Game_Objects.pc.getClass(Diamond_Ring x, Flaming_Sword y);
//    }

}

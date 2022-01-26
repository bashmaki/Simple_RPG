public class NPC {
    String name;
    String id = "NPC";
    String desc;
    int hp;
    int accuracy;
    int inRoom;

    public void look() {    //19. Метод look() в NPC очень похож на метод look() в PC
        System.out.println(name);
        System.out.println("accuracy: " + accuracy);
        System.out.println("hp: " + hp);
    }
}

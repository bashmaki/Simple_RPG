import java.util.ArrayList;
import java.util.List;

public class Game_Objects {
    static PC pc = new PC(); // 3 Создаем статический объект класса PC (это ваш игрок)
    static List<Room> room = new ArrayList<Room>();    //7. Создали ArrayList для Room

    static List<Object> NPCDataBase = new ArrayList<Object>();  //12. Создали лист с типом Object, который будет принимать разных персонажей
    static List<Object> ItemDataBase = new ArrayList<Object>(); //22. это будет база данных всех предметов в игре, на которые мы можем ссылаться в цикле.

    static RNG rng = new RNG();

    public static void initializeNPCArray() {
        NPCDataBase.add(new NPC());     //новых игровых персонажей
        NPCDataBase.add(new Troll());   //и троллей
        NPCDataBase.add(new Dragon());   //и драков
    }

    public static void initializeItemArray() {  //23. Входной пункт для всех создаваемых вещей
        ItemDataBase.add(new Item());
        ItemDataBase.add(new Flaming_Sword());
        ItemDataBase.add(new Diamond_Ring());
    }

}

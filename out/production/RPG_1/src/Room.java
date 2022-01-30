import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Room { //6 Создаем класс "Комната"
    int number;
    String name;
    List<String> desc = new ArrayList<String>();	//Заносим в лист посещение комнат (это будет гибкий массив, который будет содержать номер комнаты, куда мы зашли, и название этих локаций)
    List<String> exits = new ArrayList<String>();		//и выход из них(содержит два значения, слово "выход" и номер комнаты, куда игровой персонаж будет выходить)

    List<NPC> npc = new ArrayList<NPC>(); //14. Лист npc будет принимать любой NPC, который будет создан
    ArrayList<Item> item = new ArrayList<Item>();   //20. Создали arraylist типа Item, в переменную item складываем наши Arraylist которые касаются экипировки (экипировка лежит в комнате)
    public Room(int x)		//конструктор, который будет принимать один параметр - номер комнаты
    {
        number = x;
    }
    
    public Optional<NPC> getNpc(String name)
    {
        return npc.stream().filter(n -> n.id.equalsIgnoreCase(name)).findFirst();
    }
}

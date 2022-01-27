import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Class.forName;

public class MonsterThread {    //42. Создали класс
    Game_Logic currentGL;

    public MonsterThread(Game_Logic gl) {   //a)
        currentGL = gl;
    }

    public void startMonsterThread() {  //43. Метод запускающий монстров в игре
        Thread one = new Thread() { //Создали новый поток
            public void run() {
                while (true) {
                    //System.out.println("Something Happens");
                    populateGame(); //вызываем метод "заполнить игру"
                    try {
                        Thread.sleep(1000); //ждем "одну" секунду и вызываем снова метод "заполнить игру"
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //System.out.println("Something Happens Again");
                }
            }
        };
        one.start();
    }

    public void populateGame() {    //44. Метод о заполнении игры персонажами
        int roomMobCount = 0;   //переменная для персонажей игрового мира

        List<String> lines = new ArrayList<String>();
        try {
            lines = currentGL.readLines("C:\\Users\\user\\IdeaProjects\\RPG_1\\src\\TextFiles\\MonsterLocs.txt");   //считываем файл и заносим его в переменную  лист типа String lines
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            String[] words = lines.get(i).split(" ");   //сканируем и разделяем там, где есть пробел
            if (words[0].equals("Name:")) { //если первое слово Name
                for (int y = 0; y < Game_Objects.room.size(); y++) {    //мы идем через все комнаты
                    if (Game_Objects.room.get(y).number == Integer.parseInt(words[2])) { // b) мы увидим, о какой комнате идет речь. Если видим третью позицию, хорошо, делаем что-то дальше
                        for (int z = 0; z < Game_Objects.room.get(y).npc.size(); z++) { //проверяем в комнате всех монстров
                            if (Game_Objects.room.get(y).npc.get(z).id.equalsIgnoreCase(words[1])) {    //проверяем комнату на любого монстра, который имеет id номер 1 (Dragon или Troll)
                                roomMobCount++; //если обнаружили, добавляем одну штуку. Если не прописать этой строки, то каждую секунду будет создаваться новый монстр
                            }
                        }
                    }
                }
                if (roomMobCount == 0) {    //Идем далее, если в комнате монстр не обнаружен, то
                    for (int y = 0; y < Game_Objects.room.size(); y++) {    //мы идем через все комнаты
                        if (Game_Objects.room.get(y).number == Integer.parseInt(words[2])) {    //заходим в комнату 5, номер этой комнаты соответсвует 3 позиции в файле MonsterLocs.txt
                            try {
                                Game_Objects.room.get(y).npc.add((NPC) Class.forName(words[1]).getDeclaredConstructor().newInstance());  //добавляем монстра npc который берет тип монстра из второй позиции файла Monster.txt (для пятой комнаты это Dragon)
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            roomMobCount = 0;
        }
    }
}

/*
a) Поток-монстр принимает экземпляр класса игровой логики. Так вот как я это делаю. У меня есть игровая логика currentGL, и когда мы создаем поток монстра,
мы передаем экземпляр игровой логики, который мы использовали, и просто называем его текущей игровой логикой. И это дает нам доступ ко всем методам внутри игровой логики и всему тому, что мы можем использовать.

b) В этом случае он пройдёт по всем комнатам и проверит номер комнаты. И он увидит, находится ли вторая позиция массива (третья по счету). В этом случае пять - это то, что вытащили
 */
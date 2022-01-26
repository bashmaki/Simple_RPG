import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Class.*;

public class Game_Logic {
    public Game_Logic() {                       //7. Прописали игровую логику перемещения по комнатам
        Game_Objects.room.add(new Room(0));  //(0)   //8 Первое, что случается с запуском игры - добавили в вызванный конструктор Game_Logic() новую комнату с номером "1"
       // Game_Objects.room.get(0).name = "Floating in Space";    //Размещаемся в игре

        //part8 (38 п.)
        List<String> roomInfo = new ArrayList<>();  //38.Создаем лист типа String
        try {
            roomInfo = readLines("C:\\Users\\user\\IdeaProjects\\RPG_1\\src\\TextFiles\\RoomDescription.txt");  //и пытаемся прочесть в него файл, указанный по этому адресу
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < roomInfo.size(); i++) {  //пройдемся по всему считаному файлу и помещенному в ArrayList
            String[] firstWord = roomInfo.get(i).split(" ");
            String[] everythingElse = roomInfo.get(i).split(":");

            if(firstWord[0].equals("RoomName:")) {
                int currentRoomSize = Game_Objects.room.size();
                Game_Objects.room.add(new Room(currentRoomSize));
                Game_Objects.room.get(Game_Objects.room.size() - 1).name = everythingElse[1];
                Game_Objects.room.get(Game_Objects.room.size() - 1).number = (currentRoomSize);

                int roomcount = 0;
                for(int z = 0; z < roomInfo.size(); z++) {
                    String[] nextFirstWord = roomInfo.get(z).split(" ");
                    if(nextFirstWord[0].equals("RoomName:")) {      //если мы видем в arraylist текст "Roomname", то
                        roomcount++;                                //создаем комнату с новым номером
                    }
                    if (roomcount == currentRoomSize) {
                        if (nextFirstWord[0].equals("Desc:")) {
                            String[] nextEverythingElse = roomInfo.get(z).split(":");
                            Game_Objects.room.get(Game_Objects.room.size() - 1).desc.add(nextEverythingElse[1]);
                        }
                    }
                }
                roomcount = 0;
                for(int z = 0; z < roomInfo.size(); z++) {
                    String[] nextFirstWord = roomInfo.get(z).split(" ");
                    if(nextFirstWord[0].equals("RoomName:")) {
                        roomcount++;
                    }
                    if(roomcount == currentRoomSize) {
                        if (nextFirstWord[0].equals("Exit:")) {
                            String[] nextEverythingElse = roomInfo.get(z).split(":");
                            Game_Objects.room.get(Game_Objects.room.size() -1).exits.add(nextEverythingElse[1]);
                        }
                    }
                }
            }
        }
        //еnd of part 8 (38п.)

//        Game_Objects.room.get(0).desc.add("Desc Line 1");
//        Game_Objects.room.get(0).desc.add("Desc Line 2");
//        Game_Objects.room.get(0).desc.add("Desc Line 3");
//        Game_Objects.room.get(0).desc.add("Desc Line 4");
//        Game_Objects.room.get(0).exits.add("South 2");
//        Game_Objects.room.get(0).exits.add("North 3");
    }

    public void waitForCommand() {
        if (Game_Objects.pc.inRoom == 0) {    //2 Если игровой объект создан и находится в комнате
            createCharacter();                //тогда создаем объект с заданными характеристиками
        }
        System.out.println("What do?");
        Scanner sc = new Scanner(System.in);
        String com = sc.nextLine();
        // parse the command by spaces
        // read each word into an array valueString s = "This is a sample
        // sentence.";
        String[] words = com.split(" ");
        processCommand(words);

    }

    public void processCommand(String[] x) {
        if (x[0].equals("look")) //9. Если команда для комнаты равна "look" - тогда выполняем метод look()
        {
            look(x);    //10. метод запустится, так как мы при запуске добавили комнату 1
        }
        if (x[0].equals("summon")) {    //15. мы даем команду вызвать созданных в игровом мире персонажей
            summon(x);
        }
        if (x[0].equals("create")) { //29. первый индекс команды создания экипировки
            create_item(x);
        }
        if(x[0].equals("get")) {    //30. Первый индекс для команды взять экипировку
            get(x);
        }
        if(x[0].equals("wear")) {   //35. Команда одеть игрока
            Game_Objects.pc.wear(x);
        }
        if(x[0].equals("eq")) {     //36. Проверить надетые вещи
            Game_Objects.pc.eq();
        }
        if(x[0].equals("remove")) { //37. Команда снять вещь
            Game_Objects.pc.remove(x);
        }
        if(x[0].equals("move")) {   //41.Первое слово к команде перемещения по локациям
            move(x);
        }
    }

    public void look(String[] x) {
        if (x.length == 1) {    //Если мы будем писать в консоли одно слово (просто "look"), когда мы стоим на начальной позиции, мы будем делать следующее:
            for (int i = 0; i < Game_Objects.room.size(); i++) {    //0<1, потому что мы задали в конструктор Game_Objects() цифру 1
                if (Game_Objects.room.get(i).number == Game_Objects.pc.inRoom) {    //Устанавливаем pc.Room то же значение, что и room.number (1). Другими словами, пока игрок находится в данной комнате
                    System.out.println(Game_Objects.room.get(i).name);  //выводим на экран первую комнату
                    for (int y = 0; y < Game_Objects.room.get(i).desc.size(); y++) {    //пройдемся через все описания комнат
                        System.out.println(Game_Objects.room.get(i).desc.get(y));   //Выводим на экран каждое описание комнаты
                    }
                    System.out.println("Exists: ");     //решаем, куда нам выйти из комнаты
                    for (int y = 0; y < Game_Objects.room.get(i).exits.size(); y++) {   //перебираем все возможные варианты выхода из комнаты(вверх или вниз)
                        String exitNameFull = Game_Objects.room.get(i).exits.get(y);    //мы получим размер массива List<String> exits
                        String[] exitName = exitNameFull.split(" ");    //создадим массив строки, так как команда выхода у нас состоит из трех слов: "North/South" + пробел + цифра, которые разделенны пробелом
                        System.out.println(exitName[1]);//[1]    //вывели на экран результат нашего перемещения
                    }

                    for (int y = 0; y < Game_Objects.room.get(i).npc.size(); y++) {  //17. Добавили логику для метода в случае, если x.length == 1 (если в комнате одновременно находится пользователь и игровой персонаж). Другими словами, когда в комнате npc
                        System.out.println(Game_Objects.room.get(i).npc.get(y).desc); //Мы перебираем все объекты в комнате, в которых стояли, и печатаем описание для них
                    }
                    for (int y = 0; y < Game_Objects.room.get(i).item.size(); y++) { //26. Проходимся циклом через все item в комнате
                        System.out.println(Game_Objects.room.get(i).item.get(y).desc);  //и выводим на печать описания экипировок/ки (description)
                    }
                }
            }
        }
        if (x.length == 2) { //если мы пишем в консоли два слова:
            if (x[1].equals("self")) {   //и второе слово будет "self"
                Game_Objects.pc.look();   //вызовем метод lookStatus(), который покажет нам текущее состояние игрока
                System.out.println("You are carring: ");      //28. Вы экипированы
                for (int i = 0; i < Game_Objects.pc.item.size(); i++) {
                    System.out.println(Game_Objects.pc.item.get(i).name);   //и название экипировки
                }
            }

            for (int y = 0; y < Game_Objects.room.size(); y++) { //18.Проходимся по всем комнатам
                if (Game_Objects.room.get(y).number == Game_Objects.pc.inRoom) { //и смотрим, кто в них находится.
                    for (int i = 0; i < Game_Objects.room.get(y).npc.size(); i++) {  //проверяем все npc так же, как мы делали в методе summon()
                        if (x[1].equalsIgnoreCase(Game_Objects.room.get(y).npc.get(i).id)) { //Если мы используем тип "троль", и в этой комнате уже есть такой перснож npc c таким id, тогда  будет вызван метод look() внутри NPC
                            Game_Objects.room.get(y).npc.get(i).look();
                        }
                    }
                }
            }
        }
    }   //Т.о. мы создали возможность перемещения по локациям

    public void summon(String[] x) {    //16. метод в котором обработана логика вызова игрового персонажа(троля и т.д.)
        if (x.length == 1) { // Если мы ввели одно слово, тогда игра спросит у нас: "Вызвать кого именно?"
            System.out.println("Summon what exactly?");
        }
        if (x.length == 2) {
            for (int i = 0; i < Game_Objects.NPCDataBase.size(); i++) {//мы собираемся перебрать всю базу данных, все объекты которые были добавлены сюда
                NPC localNPC = (NPC) Game_Objects.NPCDataBase.get(i);   //создаем локальную переменную в которую будет класть элемент из базы данных, соответсвующий нашему индексу цикла for. Делаем даункаст, так как объекты в листе NPCDataBase хранятся в виде переменных типа Object
                if (localNPC.id.equalsIgnoreCase(x[1])) {    //сравниваем строку из id игрового персонажа(например, тип "Troil"), с введенным игроком и игнорируя при этом регистр
                    for (int y = 0; y < Game_Objects.room.size(); y++) { //мы собираемся проверить для каждой комнаты
                        if (Game_Objects.room.get(y).number == Game_Objects.pc.inRoom) { //мы возьмем каждую комнату и сравним с той комнатой, где находится наш игровой персонаж (мы хотим знать, какую комнату мы собираемся добавить на ПК из-за типа пользователя, который будет в комнате)
                            try {
                                Game_Objects.room.get(y).npc.add((NPC) forName(localNPC.id).getDeclaredConstructor().newInstance());    // (а) мы собираемся взять комнату с игровым персонажем(монстром), в которой находится игрок(я) и мы собираемся добавить туда игровой персонаж. Для этого для NPC(с явным приведением) мы вызываем метод forName() - который вызовет стрим игрового персонажа, а getDeclaredConstructor возвращает объект Constructor, который отражает указанный конструктор класса предстваленного этим объектом. На вызванном конструкторе вызываем метод newInstance, который использует конструктор для создания и инициализации нового экземпляра класса объявления конструктора с указанными параметрами инициализации.
                                System.out.println("You summon a " + Game_Objects.room.get(y).npc   //мы собираемся получить комнату y. Мы взглянем на список npc и теперь получим список npc.
                                        .get(Game_Objects.room.get(y).npc.size() - 1).name);  //Game_Object get y — это комната, в которой сейчас находится пользователь. (npc.size()-1) - это последний npc, который был добавлен и только что мы добавили еще одни npc, поэтому, если мы добавили npc в список npc внутри комнаты, он будет иметь размер один. Но добавляется нулевая позиция
                            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    public void createCharacter() { //5 создаем игрового персонажа
        System.out.println("Welcome to the Game. What is your name?");
        Scanner sc = new Scanner(System.in);
        Game_Objects.pc.name = sc.next();   //Задаем ему указанное нами имя
        System.out.println("For the sake of simplicity, the gods are going to give you 100hp and 75 accuracy to start");
        Game_Objects.pc.hp = 100;
        Game_Objects.pc.accuracy = 75;
        Game_Objects.pc.inRoom = 2;
    }

    public void create_item(String[] x) {   //25. метод для создания экипировки
        if (x.length == 1) {
            System.out.println("Create what exactly?");
        }
        if (x.length == 2) {
            for (int i = 0; i < Game_Objects.ItemDataBase.size(); i++) { //проходимся циклом по всей базе данных экипировки
                Item localItem = (Item) Game_Objects.ItemDataBase.get(i); //создаем копию для каждой единици экипировки и поместили ее в локальную переменную, чтобы работать с ней внутри метода
                if (localItem.id.equalsIgnoreCase(x[1])) {   //проверяем на условие, что второй элемент массива нашей вводимой в консоль команды соответсвует локальной переменной
                    for (int y = 0; y < Game_Objects.room.size(); y++) { //теперь мы пройдемся по всем комнатам
                        if (Game_Objects.room.get(y).number == Game_Objects.pc.inRoom) { //мы можем видеть, в какой комнате находится игрок. нам нужно знать, в какой комнате находится pc, и что нам нужно что-то сделать в этой комнате
                            try {
                                Game_Objects.room.get(y).item.add((Item) forName(localItem.id).getDeclaredConstructor().newInstance()); //мы собираемся к Game_Objects.room.get(y) добавить к item и сдаункастить все, что находится в localItem.id как новый объект конструктора нашего класса
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            System.out.println("You create a "  //если все получилось, мы выводим на печать
                                    + Game_Objects.room.get(y).item.get(Game_Objects.room.get(y).item.size() - 1).name);  //берем комнату, в которой мы находимся, берем лист item-ов в этой комнате минус один, и печатаем имя
                        }
                    }
                }
            }
        }
    }


    public void get(String[] x) {   //27. Создаем метод, в котором пропишем логику взятия экипировки
        if (x.length == 1) {
            System.out.println("Get what exactly?");
        }
        if (x.length == 2) {
            for (int i = 0; i < Game_Objects.ItemDataBase.size(); i++) { //проходим циклом через все item нашей ItemDataBase
                for (int y = 0; y < Game_Objects.room.size(); y++) {  //проходимся через все комнаты в игре
                    if (Game_Objects.room.get(y).number == Game_Objects.pc.inRoom) { // и мы идем определять еще раз какая комната отработала, потому что pc в комнате. И мы не получим что-то после того, как получили раннее выйдя из данной комнаты
                        for (int z = 0; z < Game_Objects.room.get(y).item.size(); z++) { //далее проходим циклом по всем item в данной комнате
                            if (x[1].equalsIgnoreCase(Game_Objects.room.get(y).item.get(z).id)) {    // и если индекс 1 нашей команды будет равен любому item в комнате
                                Item localItem = Game_Objects.room.get(y).item.get(z);  //создаем локальную переменную, с которой будем работать

                                Game_Objects.pc.item.add(localItem);    //мы собираемся добавить к pc item лист созданной переменной localItem
                                System.out.println("You pick up a " + localItem.name);  //сообщаем: "ты забираешь то-то"
                                Game_Objects.room.get(y).item.remove(z);    //удаляем экипировку из комнаты
                                break;  //вызываем команду прервать, так как без нее выскочит ошибка outOfBoundExeption при последующей итерацией цикла

                            }
                        }
                    }
                }
            }
        }
    }

    public List<String> readLines(String filename) throws IOException { //39.Считываем файл и помещаем его в List
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line= bufferedReader.readLine()) != null) { //читаем каждую линию в файле
            lines.add(line);
        }
        bufferedReader.close();
        return lines;
    }

    public void move(String[] x) {  //40. Создали метод перемещения по комнатам
        if(x.length == 1) {
            System.out.println("Move where?");
        }
        if(x.length == 2) { //е)
            //Важный момент!//
           moveLoop:   //б)
           //Важный момент//
            for(int i = 0; i < Game_Objects.room.size(); i++) { //г)  пройдемся циклом по всем комнатам
                if(Game_Objects.room.get(i).number == Game_Objects.pc.inRoom) {  //д)    если мы находимся в той комнате, которая сейчас идет в цикле
                    for(int y = 0; y < Game_Objects.room.get(i).exits.size(); y++) { //пройдемся по всем Exit-ам из комнат согласно файлу
                        String exitRequested = Game_Objects.room.get(i).exits.get(y); // создаем переменную, куда помещаем выходы из комнаты
                        String[] exitArray = exitRequested.split(" ");  //поместим все exit из комнаты в массив, разделяя пробелом
                        if(x[1].equalsIgnoreCase(exitArray[1])) {   //если команда игрока(второе слово) равна exit-ам массива
                            Game_Objects.pc.inRoom = Integer.parseInt((exitArray[2]));  //индекс массива 2 парсим в инт, так как получаемое значение - строка.
                            String[] badProgramming = new String[1];
                            badProgramming[0] = "nada";
                            look(badProgramming);
                            ////////////////
                            break moveLoop; //в)
                            ////////////////
                        }
                    }
                }
            }
        }
    }

}

/*
а) Когда мы пишем в консоли слово "Троль", id класса Троль равно "Троль", - создали объект класса Троль - игровой персонаж

б) Причина, по которой вы иногда перемещаете 2 комнаты подряд, заключается в том из-за того, как устроен цикл.
Если вы, например, переместитесь на юг из комнаты 1 в комнату 2, вы завершите одну итерацию цикла. Однако цикл будет разбиваться по частям, постоянно проверяя, выполняются ли условия.
 Однако строка (г-262) по-прежнему будет действительна, и она будет проверять следующую комнату в массиве, которая будет комнатой 2.
 Поскольку вы только что переместились из комнаты 1 в комнату 2, условие 2 == pc.inRoom (строка д - 263) будет выполнено. будет истинным,
 поэтому он будет повторять всю команду перемещения снова.
Это может быть довольно неприятно, потому что, когда у вас есть несколько комнат с увеличивающимися номерами, выстроенными в линию (1 выходит на юг во 2, 2 выходит на юг в 3,
3 выходит на юг в 4), 1 команда перемещения немедленно переместит вашего персонажа из комнаты 1 в комнату. 4.
К счастью, есть исправление. вставьте между строками е) и г) метку цикла, например "moveLoop:". Затем используйте команду break после вызова функции цикла (строка в),
указав, к какому циклу следует перейти, в данном случае это будет «break moveLoop;». Это должно исправить это.

Пример метки:
Например, у тебя 5 вложенных циклов и ты хочешь, при выполнении некоторого условия, выйти из трех из них, но не из всех. Метки позволяют сделать это красиво:

Пример
label1: for (int i=0;i<10;i++)
 label2: for (int j=0;j<10;j++)
  label3: for (int k=0;k<10;k++)
   if (i==j && j==k)
    break label2;
В данном примере, при выполнении команды break, мы выйдем не из цикла с переменной k, а из цикла помеченного меткой label2 – т.е. выйдем сразу из двух циклов k и j.
 */
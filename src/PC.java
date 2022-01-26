import java.util.ArrayList;

public class PC {
    String name;
    int hp;
    int accuracy;
    int inRoom = 0; //4 переменная, которая говорит, что объект только что создан (проверен первый запуск)
    ArrayList<Item> item = new ArrayList<Item>();   //21. Добавили ArrayList для героя пользователя (оружие находится с игроком)
    ArrayList<Item> wornItems = new ArrayList<Item>(); //31. Создали ArrayList типа Item и назвали wornItems (одетая экипировка)

    public void look() {
        System.out.println("hp: " + hp);
        System.out.println("accuracy: " + accuracy);
    }

    public void wear(String[] x) {  //32. метод, где описана логика одевания экипы
        if(wornItems.size() == 0) { //если мы еще ничего не надели
            for(int i = 0; i < item.size(); i++) {  //проходимся по всему инвентарю игрока
                if(x[1].equalsIgnoreCase(item.get(i).id) && item.get(i).isWearable) {   //cобираемся проверить, экипировка, которую несет с собой игрок, равна ли той, что мы хотим использовать?
                    wornItems.add(item.get(i)); //кольцо не надето на палец? если нет - надеваем
                    System.out.println("You wear a " + item.get(i).name + " my Lord!");
                    item.remove(i); //и убираем кольцо из экипировки (оно уже надето на палец)
                    break;
                }
            }
        }
            else {  //рассмотрим случай, когда на игрока уже что-то надето
            boolean isWearing = false;  //создадим дополнительную переменную
                for(int i = 0; i < wornItems.size(); i++) { //собираемся проверить все использованные вещи на игроке
                    for(int z = 0; z < item.size(); z++) {  //заглянем в экипу персонажа
                        if(x[1].equalsIgnoreCase(item.get(z).id) && item.get(z).isWearable) {   //если экипировка одновременно находится у игрока и одновременно используется
                            if(item.get(z).wearloc.equals(wornItems.get(i).wearloc)) {  //мы хотим посмотреть, проверить каждый предмет в экипировке относительно предмета, который мы хотим надеть (в этой строке также проверим предметы в экипировке и надетые и сравним их по назначению)
                                System.out.println("You already have something worn in that location.");
                                isWearing = true;   //надето? - правда (переназначили значение)

                            }
                        }
                    }
                    if(!isWearing) {    //если не надето
                        wornItems.add(item.get(i)); //добавляем предмет из экипировки в надетые
                        System.out.println("You wear a " + item.get(i).name);
                        item.remove(i); //и удаляем предмет из экипировки
                        break;
                    }
                }
        }
    }

    public void remove(String[] x) {    //33. Метод для удаления экипировки
        for(int i = 0; i < wornItems.size(); i++) { // Проходимся по всем одетым вещам
            if(wornItems.get(i).id.equalsIgnoreCase(x[1])) { //если предмет, который мы хотим удалить, соответсвует тому предмету, согласно команды с консоли мы хотим удалить
                System.out.println("You remove a " + wornItems.get(i).id);
                item.add(wornItems.get(i)); //добавляем предмет в экипировку
                wornItems.remove(i);    //удаляем предмет из используемых
            }
        }
    }

    public void eq() {  //34. Метод для проверки статуса надетой экипировки
        for(int i = 0; i < wornItems.size(); i++) { //для всей экипировки, которая надета
            System.out.println(wornItems.get(i).name + ":" + wornItems.get(i).wearloc); //имя экипировки и  место применения
        }
    }
}

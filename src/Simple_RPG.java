public class Simple_RPG {
    static Game_Logic gl = new Game_Logic(); //1. Создаем статическую переменную класса игровой логики

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //Game_Objects.initializeNPCArray(); //13. После того, как была создана игровая логика, заселяем игру персонажами
        Game_Objects.initializeItemArray(); //24. Инициализировали создаваемые вещи (класса Item и ресурсной папки Items)
        MonsterThread mt = new MonsterThread(gl);   //45. Создали переменную класса MonsterThread. Нам нужен поток монстров, и нам нужно как-то его назвать, и нам нужно создать его экземпляр. Мы передаем ему игровую логику
        mt.startMonsterThread();    //46. на переменной класса MonsterThread вызвали метод startMonsterThread()
        while(true)
        {
            game_loop();
        }

    }
    public static void game_loop()
    {
        gl.waitForCommand();
    }
}

/*
2:
3:
4:
5:
6: 20-30
7: 31-37
8: (create a map) 38-39
9: (move command) 40-41
10: (Threading) 42-44
11 (Combat ) 50-
 */
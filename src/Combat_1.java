import java.awt.font.GlyphMetrics;

public class Combat_1 {
    public void attack(String[] x) {    //51.
        for (int i = 0; i < Game_Objects.room.size(); i++) {  //пройдемся по всем комнатам
            if (Game_Objects.room.get(i).number == Game_Objects.pc.inRoom) { //сверили комнаты с комнатой, в которой находится игрок
                for (int y = 0; y < Game_Objects.room.get(i).npc.size(); y++) {  //пройдемся по всем монстрам в данной комнате, где находится игрок
                    if (Game_Objects.room.get(i).npc.get(y).id.equalsIgnoreCase(x[1])) { //Выясняем, что за тварь мы хотим атаковать
                        int npc_hit = Game_Objects.rng.returnRandom(100);   //рандомно создаем очки урона у  монстра
                        npc_hit = npc_hit + (Game_Objects.room.get(i).npc.get(y).accuracy / 2); //урон равен базовому урону и плюс половина точности
                        if (npc_hit > 50) {  //если очки урона больше 50
                            int npc_damage = Game_Objects.rng.returnRandom(10); //повреждение будет рандомным числом до 10
                            Game_Objects.pc.hp = Game_Objects.pc.hp - npc_damage;   //жизнь игрока уменьшается на размер нанесенного урона
                            System.out.println("The " + Game_Objects.room.get(i).npc.get(y).name + " hit you of " + npc_damage);    //выводим сообщение о полученом ущербе
                        } else {
                            System.out.println("The " + Game_Objects.room.get(i).npc.get(y).name + " missed");  //либо же монстр промахнулся
                        }



//                        int pc_hit = Game_Objects.rng.returnRandom(100);    //рандомный урон игрока (до 100)
//                        pc_hit = npc_hit + (Game_Objects.room.get(i).npc.get(y).accuracy / 2);    //урон будет равен полученному рандомно размеру урона плюс точность / 2
//                        if (pc_hit > 50) {   //если наносимый ущерб будет больше 50
//                            int pc_damage = Game_Objects.rng.returnRandom(10);  //сокращаем этот урон до (1-10)
//                            Game_Objects.room.get(i).npc.get(y).hp = Game_Objects.room.get(i).npc.get(i).hp - pc_damage;    //и уменьшаем жизнь монстра на размер урона нанесенного игроком
//                            if (Game_Objects.room.get(i).npc.get(y).hp <= 0) {   //если здоровье монстра меньше или 0 - он погибает
//                                npc_death(i, y);
//                            }
//                        } else if (Game_Objects.pc.hp <= 0) {
//                            pc_death(i, y);
//                        } else {
//                            System.out.println("You missed");   //либо же вы пропустили
//                        }

                    }
                }
            }
        }

//        for (int i = 0; i < Game_Objects.pc.wornItems.size(); i++) {    //пройдемся по всей экипировке, надетой на игрока
//            //здесь у меня проблема взять конкретно меч и/или кольцо
//            if(Game_Objects.pc.wornItems.get(i).wearloc == Game_Objects.) { //здесь мы сравниваем экипировку которая идет в цикле, с той, которую мы применяем в данный момент (меч и/или кольцо)
//                //и прописать следующую логику
//                //if you don't have any equpment&carring : pc_hit = random(10);
//                //if you are carring only Flaming_Sword: pc_hit take from Flaming_Sword (int damage = 20)
//                //if you are carring with Diamond_Ring (wear on finger): pc_hit= pc_hit(from Flaming_Sword) * 2
//            }
//        }

    }

    public void npc_death(int i, int y) {   //52.
        System.out.println("A " + Game_Objects.room.get(i).npc.get(y).name + " has died");
        Game_Objects.room.get(i).npc.remove(y);
    }

    public void pc_death(int i, int y) {    //53.
        System.out.println("My lord, you are die!");
        System.out.println("You travelling is over...");
    }
}

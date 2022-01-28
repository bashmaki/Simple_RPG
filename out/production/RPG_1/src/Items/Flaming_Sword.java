public class Flaming_Sword extends Item{    //19 создали экипировку - пламенный меч
    int accuracy = 10;
   static int damage = 20;

    public Flaming_Sword( ) {
        id = "Flaming_Sword";
        name = "Flaming Sword";
        desc = "A Flaming Sword lies here";
        isWearable = true;
        wearloc = "wield";
    }

    public Flaming_Sword (int accuracy, int damage) {
        this.accuracy = accuracy;
        this.damage = damage;
    }
}

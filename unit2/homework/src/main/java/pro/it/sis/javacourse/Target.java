package pro.it.sis.javacourse;

//Класс цели, содержит стартовое количество очков жизни, название, информацию о сопротивлении, иформацию живо ли оно еще и метод получить удар

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class Target {
   @Getter
   @NonNull
   private String name;
   @Getter
   @NonNull
   private Resistance resistance;
   @Getter
   private boolean isDead;
   @Getter
   @NonNull
   private int points;

   public boolean hit(Weapon weapon) {
      if(!isDead) {
         points = points - calculateHit(weapon.getDamage(), resistance);
         if (points <= 0 ) {
            points = 0;
            isDead = true;
         }
         return true;
      } else return false; // не бей лежачего
   }

   private int calculateHit(Damage damage, Resistance resistance)
   {
      int fire = Math.max(damage.getFireDamage()-resistance.getFireResistance(), 0);
      int ice = Math.max(damage.getIceDamage()-resistance.getIceResistance(), 0);
      int physical = Math.max(damage.getPhysicalDamage()-resistance.getPhysicalResistance(), 0);
      return  fire + ice + physical;
   }
}

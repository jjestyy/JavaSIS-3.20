/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package pro.it.sis.javacourse;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    @Test public void testAppHasAGreeting() {
//        App classUnderTest = new App();
//        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }


    @Test public void testApp() {

        TargetWithFireResistance targetWithFireResistance = new TargetWithFireResistance();
        TargetWithIceResistance targetWithIceResistance = new TargetWithIceResistance();
        WeaponWithFireDamage weaponWithFireDamage = new WeaponWithFireDamage();
        WeaponWithIceDamage weaponWithIceDamage = new WeaponWithIceDamage();

        //все же нельзя исключать ситуацию когда нужна возможность задать параметры вручную
        Resistance noResistance = new Resistance(0,0,0);
        Target targetWithoutResistance = new Target("Копейщик первого уровня", noResistance,1);
        Damage justPhysicalDamage = new Damage(40, 0,0);
        Weapon justWeapon = new Weapon("Скалка кухонная", justPhysicalDamage);

        //про без сопротивления
        assertTrue(targetWithoutResistance.hit(weaponWithFireDamage));
        assertTrue(targetWithoutResistance.isDead());
        assertEquals(0, targetWithoutResistance.getPoints());
        //про не бей мертвых
        assertFalse(targetWithoutResistance.hit(weaponWithFireDamage));
        //про огонь
        //сопротивление тому чем бьют
        assertTrue(targetWithFireResistance.hit(weaponWithFireDamage));
        assertEquals(13, targetWithFireResistance.getPoints());
        assertFalse(targetWithFireResistance.isDead());
        //сопротивление не тому
        assertTrue(targetWithFireResistance.hit(weaponWithIceDamage));
        assertEquals(0, targetWithFireResistance.getPoints());
        assertTrue(targetWithFireResistance.isDead());
        //про лед
        //сопротивление тому чем бьют
        assertTrue(targetWithIceResistance.hit(weaponWithIceDamage));
        assertEquals(30, targetWithIceResistance.getPoints());
        assertFalse(targetWithIceResistance.isDead());
        //сопротивление не тому
        assertTrue(targetWithIceResistance.hit(weaponWithFireDamage));
        assertEquals(0, targetWithFireResistance.getPoints());
        assertTrue(targetWithFireResistance.isDead());

    }
}

package pro.it.sis.javacourse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class Damage {
    @Getter
    private int physicalDamage;
    @Getter
    private int fireDamage;
    @Getter
    private int iceDamage;
}

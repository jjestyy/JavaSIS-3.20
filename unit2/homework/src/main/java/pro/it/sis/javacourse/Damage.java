package pro.it.sis.javacourse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class Damage {
    @Getter
    @NonNull
    private int physicalDamage;
    @Getter
    @NonNull
    private int fireDamage;
    @Getter
    @NonNull
    private int iceDamage;
}

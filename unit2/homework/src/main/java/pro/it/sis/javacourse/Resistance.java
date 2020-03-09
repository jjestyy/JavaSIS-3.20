package pro.it.sis.javacourse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class Resistance {
    @Getter
    @NonNull
    private int physicalResistance;
    @Getter
    @NonNull
    private int fireResistance;
    @Getter
    @NonNull
    private int iceResistance;
}

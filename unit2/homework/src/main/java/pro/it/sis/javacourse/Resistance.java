package pro.it.sis.javacourse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class Resistance {
    @Getter
    private int physicalResistance;
    @Getter
    private int fireResistance;
    @Getter
    private int iceResistance;
}

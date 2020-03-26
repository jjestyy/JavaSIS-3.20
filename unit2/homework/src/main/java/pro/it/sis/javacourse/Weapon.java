package pro.it.sis.javacourse;

//Класс оружия, содержит информацию об уроне и название

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class Weapon {
    @Getter
    @NonNull
    private String name;
    @Getter
    @NonNull
    private Damage damage;

}

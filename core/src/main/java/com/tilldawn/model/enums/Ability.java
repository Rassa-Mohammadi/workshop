package com.tilldawn.model.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Ability {
    VITALITY,
    DAMAGER,
    PROCREASE,
    AMOCREASE,
    SPEEDY;

    public static Ability getAbility(String ablityName) {
        for (Ability ability : Ability.values()) {
            if (ability.name().equals(ablityName)) {
                return ability;
            }
        }
        return null;
    }

    public static List<Ability> get3RandomAbility() {
        List<Ability> abilityList = Arrays.asList(Ability.values());
        Collections.shuffle(abilityList);
        return abilityList.subList(0, 3);
    }
}

package io.github.mribby.junkjack;

public enum Kind {
    // Items
    consumable,
    equippable,
    weapon,
    trinket,
    throwable,
    ammo,
    loot,
    placeable,
    special,
    decoration,
    miniblock,
    container,
    circuitry,
    hat,
    chest,
    legs,
    feet,

    // Blocks
    object,
    block,
    deco,
    deco_front,

    // Misc
    none;

    public static final Kind[] values = Kind.values();

    public static Kind getKind(String name) {
        if (name.equals("deco-front")) {
            return deco_front;
        }

        for (Kind kind : values) {
            if (name.equals(kind.toString())) {
                return kind;
            }
        }

        return none;
    }
}

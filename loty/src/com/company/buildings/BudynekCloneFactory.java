package com.company.buildings;
import java.util.Hashtable;
public class BudynekCloneFactory {

    private static Hashtable<String, Budynek> prototypeMap = new Hashtable<String, Budynek>();

    public static Budynek getBudynek(String typBudynku) {
        Budynek cachedBuilding = prototypeMap.get(typBudynku);
        return (Budynek) cachedBuilding.clone();
    }

    public static void loadCache() {
        Dom dom = new Dom();
        prototypeMap.put(dom.getTypBudynku(), dom);

        Blok blok = new Blok();
        prototypeMap.put(blok.getTypBudynku(), blok);

        WysokiBudynek wysokiBudynek = new WysokiBudynek();
        prototypeMap.put(wysokiBudynek.getTypBudynku(), wysokiBudynek);
    }
}

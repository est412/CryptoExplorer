package ru.est412.cryptoexplorer;

import ru.est412.cryptoexplorer.model.Engines;

/**
 * Created by e.tukhvatullin on 26.11.2017.
 */
public class TestEngines {

    public static void main(String[] args) throws Exception {
        Engines.load();
        System.out.println(Engines.getEntities());
    }

}

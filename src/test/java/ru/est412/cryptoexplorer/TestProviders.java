package ru.est412.cryptoexplorer;

import ru.est412.cryptoexplorer.model.Providers;

/**
 * Created by e.tukhvatullin on 26.11.2017.
 */
public class TestProviders {

    public static void main(String[] args) throws Exception {
        Providers.load();
        System.out.println(Providers.getEntities());
    }

}

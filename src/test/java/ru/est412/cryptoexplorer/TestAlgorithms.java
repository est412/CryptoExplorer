package ru.est412.cryptoexplorer;

import ru.est412.cryptoexplorer.model.Algorithms;

/**
 * Created by e.tukhvatullin on 26.11.2017.
 */
public class TestAlgorithms {

    public static void main(String[] args) throws Exception {
        Algorithms.load();
        System.out.println(Algorithms.getEntities());
    }

}

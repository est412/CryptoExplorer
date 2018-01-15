package ru.est412.cryptoexplorer;

import ru.est412.cryptoexplorer.db.Filter;
import ru.est412.cryptoexplorer.model.Engines;

/**
 * Created by e.tukhvatullin on 20.12.2017.
 */
public class TestBuildRequest {

    public static void main(String[] args) {
        Filter.setAlgorithmId(1);
        Filter.setProviderId(2);
        //System.out.println(Engines.buldRequest());
    }

}

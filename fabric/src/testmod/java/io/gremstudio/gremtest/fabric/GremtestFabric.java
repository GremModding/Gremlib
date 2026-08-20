package io.gremstudio.gremtest.fabric;

import io.gremstudio.gremlib.fabric.initializers.GremModInitializer;
import io.gremstudio.gremtest.Gremtest;

public class GremtestFabric implements GremModInitializer {
    private static Gremtest gremtest = null;

    public static Gremtest getGremtest() {
        return gremtest;
    }


    @Override
    public void onGremModInitalization() {
        gremtest = new Gremtest();
    }
}

package ru.est412.cryptoexplorer.db;

/**
 * Created by e.tukhvatullin on 20.12.2017.
 */
public class Filter {

    private static long providerId = -1;
    private static long engineId = -1;
    private static long algorithmId = -1;
    private static long serviceClassId = -1;

    public static long getProviderId() {
        return providerId;
    }

    public static void setProviderId(long providerId) {
        Filter.providerId = providerId;
    }

    public static long getEngineId() {
        return engineId;
    }

    public static void setEngineId(long engine) {
        Filter.engineId = engine;
    }

    public static long getAlgorithmId() {
        return algorithmId;
    }

    public static void setAlgorithmId(long algorithm) {
        Filter.algorithmId = algorithm;
    }

    public static long getServiceClassId() {
        return serviceClassId;
    }

    public static void setServiceClassId(long serviceClass) {
        Filter.serviceClassId = serviceClass;
    }

    public static boolean isEmpty() {
        return (providerId + engineId + algorithmId + serviceClassId) <= -4;
    }

}

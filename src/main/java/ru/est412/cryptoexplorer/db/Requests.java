package ru.est412.cryptoexplorer.db;

/**
 * Created by e.tukhvatullin on 26.11.2017.
 */
public interface Requests {

    // Parts of dynamic request:

    String FROM_ALGORITHM = "algorithm alg, alias ali";
    String FROM_PROVIDER = "provider p";
    String FROM_ENGINE = "engine e";
    String FROM_SERVICE = "service s";
    String FROM_SERVICE_CLASS = "service_class sc";

    String WHERE_ALGORITHM_REL = "alg.id = ali.algorithm_id " +
            "and ali.service_id = s.id";
    String WHERE_ALGORITHM_EQ = "alg.id = ";
    String WHERE_ENGINE_REL = "e.id = s.engine_id";
    String WHERE_ENGINE_EQ = "e.id = ";
    String WHERE_PROVIDER_REL = "p.id = s.provider_id";
    String WHERE_PROVIDER_EQ = "p.id = ";
    String WHERE_SERVICE_CLASS_REL = "sc.id = s.class_id";
    String WHERE_SERVICE_CLASS_EQ = "sc.id = ";

    String ORDER_BY_DEFAULT = "order by name";

    // Full requests

    String ALGORITHM_INSERT = "insert into algorithm (name) values (?)";
    String ALGORITHM_SELECT_ALL = "select distinct alg.id, alg.name from " + FROM_ALGORITHM;
    String ALGORITHM_DELETE_ALL = "delete from algorithm";

    String ALIAS_INSERT = "insert into alias (service_id, algorithm_id, alias_of_id) values (?, ?, ?)";
    String ALIAS_DELETE_ALL = "delete from alias";

    String PROVIDER_INSERT = "insert into provider (name, version, class_name, info) values (?, ?, ?, ?)";
    String PROVIDER_SELECT_ALL = "select distinct p.id, p.name, p.version, p.class_name, p.info from " + FROM_PROVIDER;
    String PROVIDER_SELECT_ONE = PROVIDER_SELECT_ALL + " " + WHERE_PROVIDER_EQ + " ?";
    String PROVIDER_DELETE_ALL = "delete from provider";

    String ENGINE_INSERT = "insert into engine (name) values (?)";
    String ENGINE_SELECT_ALL = "select distinct e.id, e.name from " + FROM_ENGINE;
    String ENGINE_DELETE_ALL = "delete from engine";

    String SERVICE_INSERT = "insert into service (provider_id, engine_id, class_id) values (?, ?, ?)";
    String SERVICE_DELETE_ALL = "delete from service";

    String SERVICE_CLASS_INSERT = "insert into service_class (name) values (?)";
    String SERVICE_CLASS_SELECT_ALL = "select distinct sc.id, sc.name from " + FROM_SERVICE_CLASS;
    String SERVICE_CLASS_DELETE_ALL = "delete from service_class";

}

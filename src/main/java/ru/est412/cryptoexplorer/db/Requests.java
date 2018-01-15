package ru.est412.cryptoexplorer.db;

/**
 * Created by e.tukhvatullin on 26.11.2017.
 */
public interface Requests {

    String ALGORITHM_DELETE_ALL = "delete from algorithm";
    String ALGORITHM_INSERT = "insert into algorithm (name) values (?)";
    String ALGORITHM_SELECT_ALL = "select id, name from algorithm";
    String ALGORITHM_SELECT_ONE = "select id, name from algorithm where id = ?";
    String ALGORITHM_SELECT = "select distinct alg.id, alg.name " +
            "from algorithm alg " +
            "join alias ali on alg.id = ali.algorithm_id " +
            "join engine e on e.id = s.engine_id " +
            "join provider p on p.id = s.provider_id " +
            "join service s on ali.service_id = s.id " +
            "join service_class sc on sc.id = s.class_id " +
            "where (e.id = ? or ? < 0) and (p.id = ? or ? < 0) and (sc.id = ? or ? < 0) " +
            "order by name";

    String ALIAS_DELETE_ALL = "delete from alias";
    String ALIAS_INSERT = "insert into alias (service_id, algorithm_id, alias_of_id) values (?, ?, ?)";

    String PROVIDER_DELETE_ALL = "delete from provider";
    String PROVIDER_INSERT = "insert into provider (name, version, class_name, info) values (?, ?, ?, ?)";
    String PROVIDER_SELECT_ALL = "select id, name, version, class_name, info from provider";
    String PROVIDER_SELECT_ONE = "select id, name, version, class_name, info from provider where id = ?";
    String PROVIDER_SELECT = "select distinct p.id, p.name, p.version, p.class_name, p.info " +
            "from provider p " +
            "join algorithm alg on alg.id = ali.algorithm_id " +
            "join alias ali on ali.service_id = s.id " +
            "join engine e on e.id = s.engine_id " +
            "join service s on p.id = s.provider_id " +
            "join service_class sc on sc.id = s.class_id " +
            "where (alg.id = ? or ? < 0) and (e.id = ? or ? < 0) and (sc.id = ? or ? < 0) " +
            "order by name";

    String ENGINE_DELETE_ALL = "delete from engine";
    String ENGINE_INSERT = "insert into engine (name) values (?)";
    String ENGINE_SELECT_ALL = "select id, name from engine";
    String ENGINE_SELECT_ONE = "select id, name from engine where id = ?";
    String ENGINE_SELECT = "select distinct e.id, e.name " +
            "from engine e " +
            "join alias ali on ali.service_id = s.id " +
            "join algorithm alg on alg.id = ali.algorithm_id " +
            "join provider p on p.id = s.provider_id " +
            "join service s on e.id = s.engine_id " +
            "join service_class sc on sc.id = s.class_id " +
            "where (alg.id = ? or ? < 0) and (p.id = ? or ? < 0) and (sc.id = ? or ? < 0) " +
            "order by name";

    String SERVICE_DELETE_ALL = "delete from service";
    String SERVICE_INSERT = "insert into service (provider_id, engine_id, class_id) values (?, ?, ?)";

    String SERVICE_CLASS_DELETE_ALL = "delete from service_class";
    String SERVICE_CLASS_INSERT = "insert into service_class (name) values (?)";
    String SERVICE_CLASS_SELECT_ALL = "select id, name from service_class";
    String SERVICE_CLASS_SELECT_ONE = "select id, name from service_class where id = ?";
    String SERVICE_CLASS_SELECT = "select distinct sc.id, sc.name " +
            "from service_class sc " +
            "join alias ali on ali.service_id = s.id " +
            "join algorithm alg on alg.id = ali.algorithm_id "+
            "join engine e on e.id = s.engine_id " +
            "join provider p on p.id = s.provider_id " +
            "join service s on sc.id = s.class_id " +
            "where (alg.id = ? or ? < 0) and (e.id = ? or ? < 0) and (p.id = ? or ? < 0) " +
            "order by name";

}

package org.example.configuration;

import org.example.dao.Dao;
import org.example.model.*;
import org.springframework.beans.factory.InitializingBean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageImpl implements Storage, InitializingBean {

    private Map<String, Dao> daos = new HashMap<>();
    @Override
    public void setDaos(Map<String, Dao> tables) {
        this.daos = tables;
    }

    @Override
    public Map<String, Dao> getDaos() {
        return daos;
    }

    @Override
    public Dao getDao(String tableName) {
        return daos.get(tableName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}

package org.example.configuration;

import org.example.dao.Dao;
import java.util.Map;

public interface Storage {

      void setDaos(Map<String, Dao> tables);
      Dao getDao(String tableName);

}

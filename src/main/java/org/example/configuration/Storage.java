package org.example.configuration;

import org.example.dao.Dao;
import java.util.Map;

public interface Storage {

      void setDaos(Map<String, Dao> tables);
      Map<String, Dao> getDaos();
      Dao getDao(String tableName);

}

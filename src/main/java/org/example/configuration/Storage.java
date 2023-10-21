package org.example.configuration;

import org.example.dao.Dao;
import org.example.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Storage {

      void setDaos(Map<String, Dao> tables);
      Map<String, Dao> getDaos();
      Dao getDao(String tableName);

}

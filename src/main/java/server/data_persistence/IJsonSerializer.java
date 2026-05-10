package server.data_persistence;

import java.util.HashMap;
import java.util.Map;

public interface IJsonSerializer <T>{

    public void save(String filePath, HashMap<String,T> data);

    public Map<String,T> loadSavedData(String filePath);

}

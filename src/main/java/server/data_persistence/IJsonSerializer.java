package server.data_persistence;

import java.util.HashMap;
import java.util.Map;

public interface IJsonSerializer<T> {

    void save(String filePath, Map<String, T> data);

    Map<String, T> loadSavedData(String filePath);
}

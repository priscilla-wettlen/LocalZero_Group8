package server.data_persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class JsonSerializer<T> implements IJsonSerializer<T> {

    private final Gson gson;

    private final Object lock;

    private final Class<T> typeClass;

    public JsonSerializer(Class<T> typeClass) {

        this.typeClass = typeClass;

        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        this.lock = new Object();
    }

    @Override
    public void save(String filePath,
                     Map<String, T> data) {

        synchronized (lock) {

            Path path = Path.of(filePath);

            try {

                if (path.getParent() != null) {
                    Files.createDirectories(path.getParent());
                }

                try (Writer writer =
                             Files.newBufferedWriter(path)) {

                    gson.toJson(data, writer);
                }

            } catch (IOException e) {

                throw new RuntimeException(
                        "Failed to save JSON data",
                        e
                );
            }
        }
    }

    @Override
    public Map<String, T> loadSavedData(
            String filePath) {

        synchronized (lock) {

            Path path = Path.of(filePath);

            if (!Files.exists(path)) {
                return new HashMap<>();
            }

            try (Reader reader =
                         Files.newBufferedReader(path)) {

                Type type =
                        TypeToken.getParameterized(
                                Map.class,
                                String.class,
                                typeClass
                        ).getType();

                Map<String, T> data =
                        gson.fromJson(reader, type);

                return data != null
                        ? data
                        : new HashMap<>();

            } catch (IOException e) {

                throw new RuntimeException(
                        "Failed to load JSON data",
                        e
                );
            }
        }
    }
}
package server.data_persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.security.JsonUserStore;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonSerializer implements IJsonSerializer {
    private final Path filePath;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Object lock = new Object();

    public JsonSerializer(Path filePath) {
        this.filePath = filePath;
    }
    @Override
    public void save(String filePath, HashMap data) {
        synchronized (lock) {
            List<JsonUserStore.StoredUser> users = loadUsers();
            users.removeIf(existing -> existing.email != null
                    && existing.email.equalsIgnoreCase(user.email));
            users.add(user);
            writeUsers(users);
        }
    }

    @Override
    public Map loadSavedData(String filePath) {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }
        try (InputStream in = Files.newInputStream(filePath)) {
            return objectMapper.readValue(in, new TypeReference<List<JsonUserStore.StoredUser>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static class StoredUser {
        public String id;
        public String email;
        public String name;
        public String passwordHash;
        public String neighborhood;

        public StoredUser() {
        }
    }
}

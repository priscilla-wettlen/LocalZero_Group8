package server.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonUserStore {
    private final Path filePath;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Object lock = new Object();

    public JsonUserStore(Path filePath) {
        this.filePath = filePath;
    }

    public Optional<StoredUser> findByEmail(String email) {
        if (email == null) {
            return Optional.empty();
        }
        synchronized (lock) {
            return loadUsers().stream()
                    .filter(user -> email.equalsIgnoreCase(user.email))
                    .findFirst();
        }
    }

    public boolean exists(String email) {
        return findByEmail(email).isPresent();
    }

    public void save(StoredUser user) {
        synchronized (lock) {
            List<StoredUser> users = loadUsers();
            users.removeIf(existing -> existing.email != null
                    && existing.email.equalsIgnoreCase(user.email));
            users.add(user);
            writeUsers(users);
        }
    }

    private List<StoredUser> loadUsers() {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }
        try (InputStream in = Files.newInputStream(filePath)) {
            return objectMapper.readValue(in, new TypeReference<List<StoredUser>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void writeUsers(List<StoredUser> users) {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), users);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write users file", e);
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


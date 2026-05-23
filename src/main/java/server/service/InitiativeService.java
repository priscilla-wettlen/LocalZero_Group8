package server.service;

import server.data_persistence.JsonSerializer;
import server.model.Neighborhood;
import server.model.Visibility;
import shared.Initiative;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InitiativeService implements IInitiativeService {
    private static InitiativeService initiativeServiceInstance;
    private static final String FILE_PATH = "shared/initiatives.json";
    private final JsonSerializer<Initiative> serializer = new JsonSerializer<>(Initiative.class);
    private Map<String, Initiative> initiatives = new ConcurrentHashMap<>();

    private InitiativeService() {
        // Load existing initiatives from the JSON file
        initiatives.putAll(serializer.loadSavedData(FILE_PATH));
    }

    public static InitiativeService getInitiativeServiceInstance() {
        if (initiativeServiceInstance == null) {
            initiativeServiceInstance = new InitiativeService();
        }
        return initiativeServiceInstance;
    }

    @Override
    public Initiative createInitiative(String creator, String title, String description, String initiativeType,
                                       String specificLocation, Neighborhood creatorNeighborhood,
                                       Visibility visibility, String duration, URL image) {
        // Validate input
        if (title == null || title.isEmpty() || description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Title and description cannot be null or empty.");
        }

        // Generate a unique ID for the initiative
        String id = UUID.randomUUID().toString();

        // Create a new Initiative object
        Initiative initiative = new Initiative(id, title, initiativeType, duration, visibility, description,
                specificLocation, creatorNeighborhood, image, creator);

        // Add the initiative to the map
        initiatives.put(id, initiative);

        // Save the updated initiatives map to the JSON file
        serializer.save(FILE_PATH, initiatives);

        // Return the created initiative
        return initiative;
    }

    @Override
    public Map<String, Initiative> getAllInitiatives() {
        return new ConcurrentHashMap<>(initiatives);
    }

    @Override
    public List<Initiative> getForumInitiativesForViewer(Neighborhood viewerNeighborhood) {
        List<Initiative> visible = new ArrayList<>();
        for (Initiative initiative : initiatives.values()) {
            if (isVisibleOnForum(initiative, viewerNeighborhood)) {
                visible.add(initiative);
            }
        }
        visible.sort(Comparator.comparing(Initiative::getTitle, String.CASE_INSENSITIVE_ORDER));
        return visible;
    }

    @Override
    public boolean deleteInitiative(String initiativeId) {
        if (initiativeId == null || initiativeId.isBlank()) {
            return false;
        }
        Initiative removed = initiatives.remove(initiativeId);
        if (removed == null) {
            return false;
        }
        serializer.save(FILE_PATH, initiatives);
        return true;
    }

    private boolean isVisibleOnForum(Initiative initiative, Neighborhood viewerNeighborhood) {
        if (initiative.getVisibility() == Visibility.Public) {
            return true;
        }
        if (viewerNeighborhood == null) {
            return false;
        }
        Neighborhood creatorNeighborhood = initiative.getCreatorNeighborhood();
        return creatorNeighborhood != null && viewerNeighborhood == creatorNeighborhood;
    }
}
package protocol;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import server.model.Comment;
import server.model.Neighborhood;
import server.model.User;
import server.model.Visibility;

public class Initiative implements Serializable {
    private String id;
    private String title;
    private String initiativeType;
    private String duration;
    private Visibility visibility;
    private String description;
    /** Free-text place for the initiative (e.g. "at home eating soup"). */
    private String specificLocation;
    /** Creator's registered neighborhood, used for neighborhood-only visibility. */
    private Neighborhood creatorNeighborhood;
    private URL image;
    private String creator;

    private List<String> memberIds = new ArrayList<>();

    private boolean success;
    private String message;
    private HashMap<String, Object> responseParam = new HashMap<>();

    private int likes = 0;
    private List<Comment> comments = new ArrayList<>();

    public Initiative(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Initiative(String id, String title, String initiativeType, String duration, Visibility visibility,
                      String description, String specificLocation, Neighborhood creatorNeighborhood,
                      URL image, String creator) {
        this.id = id;
        this.title = title;
        this.initiativeType = initiativeType;
        this.duration = duration;
        this.visibility = visibility;
        this.description = description;
        this.specificLocation = specificLocation;
        this.creatorNeighborhood = creatorNeighborhood;
        this.image = image;
        this.creator = creator;
        this.success = true;

    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public HashMap<String, Object> getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(HashMap<String, Object> responseParam) {
        this.responseParam = responseParam;
    }

    public void putInitiativesList(List<Initiative> initiatives) {
        responseParam.put("initiatives", initiatives);
    }

    @SuppressWarnings("unchecked")
    public List<Initiative> getInitiativesList() {
        Object list = responseParam.get("initiatives");
        if (list instanceof List<?>) {
            return (List<Initiative>) list;
        }
        return List.of();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getInitiativeType() {
        return initiativeType;
    }

    public String getDuration() {
        return duration;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public String getDescription() {
        return description;
    }

    public String getSpecificLocation() {
        return specificLocation;
    }

    public Neighborhood getCreatorNeighborhood() {
        return creatorNeighborhood;
    }

    public URL getImage() {
        return image;
    }

    public String getCreator() {
        return creator;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addLike() {
        likes++;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public List<String> getMemberIds() {

        if (memberIds == null) {
            memberIds = new ArrayList<>();
        }

        return memberIds;
    }

    public void addMember(String userId) {
        if (!memberIds.contains(userId)) {
            memberIds.add(userId);
        }
    }

    public boolean isMember(String userId) {
        for (String member : memberIds) {
            if (member.equals(userId)) {
                return true;
            }
        }

        return false;
    }


}

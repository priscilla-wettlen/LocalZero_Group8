package server.model;

import java.net.URL;
import java.time.Instant;
import java.util.Date;

public class Initiative {
    private String ID;
    private String title;
    private InitiativeType initiativeType;
    private Visibility visibility;
    private String description;
    private URL image;
    private String location;
    private int duration;
    private Date date;

    public Initiative(String ID, String title, InitiativeType initiativeType, Visibility visibility, String description, URL image, String location, int duration, Date date) {
        this.ID = ID;
        this.title = title;
        this.initiativeType = initiativeType;
        this.visibility = visibility;
        this.description = description;
        this.image = image;
        this.location = location;
        this.duration = duration;
        this.date = Date.from(Instant.now());
    }


    public String getTitle() {
        return title;
    }
    public InitiativeType getInitiativeType() {
        return initiativeType;
    }

    public Visibility getVisibility() {
        return visibility;
    }
    public String getDescription() {return description;}

    public void setID(String ID) {
        this.ID = ID;
    }
    public void setTitle(String Title) {
        this.title = Title;
    }
    public void setInitiativeType(InitiativeType initiativeType) {
        this.initiativeType = initiativeType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVisibility(String visibility) {
        this.visibility = Visibility.valueOf(visibility);
    }

}

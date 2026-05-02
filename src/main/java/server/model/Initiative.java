package main.java.server.model;

import java.time.Instant;
import java.util.Date;

public class Initiative {
    private String ID;
    private String Title;
    private InitiativeType initiativeType;
    private Visibility visibility;
    private String description;
    private Date date;

    public Initiative(String ID, String Title, InitiativeType initiativeType, Visibility visibility, String description) {
        this.date = Date.from(Instant.now());
        this.ID = ID;
        this.Title = Title;
        this.initiativeType = initiativeType;
        this.visibility = visibility;
        this.description = description;
    }


    public String getTitle() {
        return Title;
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
        this.Title = Title;
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

package server.model;

import java.time.LocalDateTime;

public class Comment {

    private String author;

    private String text;

    private String timestamp;

    // Empty constructor for Gson
    public Comment() {
    }

    public Comment(String author,
                   String text) {

        this.author = author;

        this.text = text;

        this.timestamp = LocalDateTime.now().toString();
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }
}

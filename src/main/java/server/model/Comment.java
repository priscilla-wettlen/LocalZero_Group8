package server.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Comment implements Serializable {

    private String author;

    private String text;

    private LocalDateTime timestamp;

    public Comment(String author,
                   String text) {

        this.author = author;

        this.text = text;

        this.timestamp =
                LocalDateTime.now();
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
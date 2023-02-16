package ru.netology.model;

public class Post {
    private long id;
    private String content;
    private boolean isRemove;

    public Post() {
    }

    public Post(long id, String content) {
        this.id = id;
        this.content = content;
        this.isRemove = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRemove(boolean setFlagToRemove) {
        this.isRemove = setFlagToRemove;
    }

    public boolean getIsRemove() {
        return isRemove;
    }
}

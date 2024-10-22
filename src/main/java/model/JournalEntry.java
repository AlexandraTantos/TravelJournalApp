package model;

public class JournalEntry {
    private int id;
    private String title;
    private String content;

    public JournalEntry(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "JournalEntry{" + "id=" + id + ", title='" + title + '\'' + ", content='" + content + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JournalEntry that = (JournalEntry) o;
        return id == that.id && title.equals(that.title) && content.equals(that.content);
    }
}


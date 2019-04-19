package common;

import java.util.Objects;

public class Attachment {

    private String filename;

    public Attachment() {
    }

    public Attachment(String filename) {
        this.filename = filename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return Objects.equals(filename, that.filename);
    }

    @Override
    public int hashCode() {

        return Objects.hash(filename);
    }
}

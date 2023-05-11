package tecnico.withoutnet.server.domain;

import org.springframework.lang.Nullable;

public class Node {
    private String id;
    private String commonName;
    private String readingType;

    public Node(String id, String commonName, String readingType) {
        this.setId(id);
        this.setCommonName(commonName);
        this.readingType = readingType;
    }

    public Node(String relevantNodeValue) {
        String[] relevantNodeValueComponents = relevantNodeValue.split("#");

        if(relevantNodeValueComponents.length != 3) {
            // TODO: Throw an exception
            return;
        }

        this.setId(relevantNodeValueComponents[0]);
        this.setCommonName(relevantNodeValueComponents[1]);
        this.setReadingType(relevantNodeValueComponents[2]);
    }

    public String getCommonName() {
        return this.commonName;
    }

    public void setCommonName(String commonName) {
        if(commonName == null) {
            this.commonName = "Unnamed";
        } else {
            this.commonName = commonName;
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        if(id == null) {
            this.id = "N/A";
        } else {
            this.id = id;
        }
    }

    public String getReadingType() {
        return this.readingType;
    }

    public void setReadingType(String readingType) {
        this.readingType = readingType;
    }

    @Override
    public String toString() {
        return "" + this.getId() + "#" + this.getCommonName() + "#" + this.getReadingType();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj != null && obj instanceof Node) {
            Node node = (Node) obj;
            return node.getId().equals(this.getId())
                    && node.getCommonName().equals(this.getCommonName())
                    && node.getReadingType().equals(this.getReadingType());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (this.getId()+this.getCommonName()+this.getReadingType()).hashCode();
    }
}
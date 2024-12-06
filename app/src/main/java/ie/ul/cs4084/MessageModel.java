package ie.ul.cs4084;
public class MessageModel {
    private String sender;
    private String message;
    private long timestamp;

    // Required empty constructor for Firebase
    public MessageModel() {}

    public MessageModel(String sender, String message, long timestamp) {
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
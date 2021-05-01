package project.watch_and_relax.model.view;

public class CommentViewModel {
    private String id;
    private String usersPhotoUrl;
    private String username;
    private String comment;

    public CommentViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsersPhotoUrl() {
        return usersPhotoUrl;
    }

    public void setUsersPhotoUrl(String usersPhotoUrl) {
        this.usersPhotoUrl = usersPhotoUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

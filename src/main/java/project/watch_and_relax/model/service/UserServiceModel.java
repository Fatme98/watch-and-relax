package project.watch_and_relax.model.service;

public class UserServiceModel {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private PhotoServiceModel photoServiceModel;

    public UserServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PhotoServiceModel getPhotoServiceModel() {
        return photoServiceModel;
    }

    public void setPhotoServiceModel(PhotoServiceModel photoServiceModel) {
        this.photoServiceModel = photoServiceModel;
    }
}

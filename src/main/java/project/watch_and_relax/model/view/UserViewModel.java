package project.watch_and_relax.model.view;

import java.util.List;

public class UserViewModel {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String photoUrl;
    private List<FilmViewModel> films;
    private List<RoleViewModel>roles;
    public UserViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<FilmViewModel> getFilms() {
        return films;
    }

    public void setFilms(List<FilmViewModel> films) {
        this.films = films;
    }

    public List<RoleViewModel> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleViewModel> roles) {
        this.roles = roles;
    }
}

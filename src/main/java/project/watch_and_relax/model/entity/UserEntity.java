package project.watch_and_relax.model.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="users")
@Transactional
public class UserEntity extends BaseEntity {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Photo photo;
    private List<Film>films;
    private List<RoleEntity>roles;
    private List<Film>favouriteFilms;
    public UserEntity() {
    }
@OneToMany
    public List<Film> getFavouriteFilms() {
        return favouriteFilms;
    }

    public void setFavouriteFilms(List<Film> favouriteFilms) {
        this.favouriteFilms = favouriteFilms;
    }

    @Column(name="username",nullable = false,unique=true)
@Length(min=3,message = "Username must be more than 3 symbols")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Column(name="password",nullable = false)
    @Length(min=3, message = "Password must be more than 3 symbols")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Column(name="first_name",nullable = false)
    @Length(min=3, message = "First name must be more than 3 symbols")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(name="last_name",nullable = false)
    @Length(min=3,message = "Last name must be more than 3 symbols")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
@Email(message = "Enter valid email")
@NotNull
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
@ManyToOne
    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @OneToMany(mappedBy ="user", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
@JoinColumn(name="user_id")
    public List<RoleEntity> getRoles() {
        return roles;
    }
    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }


}

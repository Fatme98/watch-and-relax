package project.watch_and_relax.model.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="actors")
@Transactional
public class Actor extends BaseEntity{
    private String fullName;
    private String nickName;
    private String roleName;
    private String gender;
    private String dateOfBirth;
    private String country;
    private String partner;
    private int children;
    private String description;
    private Photo photo;
    private Film film;

    public Actor() {
    }
    @Column(name="full_name",nullable = false)
    @Length(min=3, message = "Full name must be more than 3 symbols")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    @Column(name="nick_name",nullable = false)
    @Length(min=3, message = "Nickname must be more than 3 symbols")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    @Column(name="gender",nullable = false)
    @Length(min=3, message = "Gender must be more than 3 symbols")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name="country",nullable = false)
    @Length(min=1, message = "Country must be more than 1 symbols")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    @Column(name="partner",nullable = false)
    @Length(min=3, message = "Partner name must be more than 3 symbols")
    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }
@Min(value=0,message = "Must be positive number or 0!")
    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }
    @Column(name="description",nullable = false,columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Column(name="role_name",nullable = false)
    @Length(min=1, message = "Role name must be more than 1 symbols")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

}

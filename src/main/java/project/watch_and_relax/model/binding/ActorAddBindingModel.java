package project.watch_and_relax.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ActorAddBindingModel {
    private String fullName;
    private String nickName;
    private String roleName;
    private String gender;
    private String dateOfBirth;
    private String country;
    private String partner;
    private int children;
    private String description;

    public ActorAddBindingModel() {
    }
    @Length(min=3, message = "Full name must be more than 3 symbols")
    @NotBlank
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    @Length(min=3, message = "Nickname must be more than 3 symbols")
    @NotBlank
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    @Length(min=3, message = "Gender must be more than 3 symbols")
    @NotBlank
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

    @NotBlank
    @Length(min=1, message = "Country must be more than 1 symbols")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    @NotBlank
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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @NotBlank
    @Length(min=1, message = "Role name must be more than 1 symbols")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}

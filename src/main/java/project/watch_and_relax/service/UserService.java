package project.watch_and_relax.service;

import project.watch_and_relax.model.entity.UserEntity;
import project.watch_and_relax.model.service.FilmServiceModel;
import project.watch_and_relax.model.service.UserServiceModel;
import project.watch_and_relax.model.view.FilmViewModel;
import project.watch_and_relax.model.view.UserViewModel;

import java.util.List;

public interface UserService {
    UserViewModel findUser(String username);
    UserViewModel findUserByUsername(String username);
    boolean existsUser(String username);
    UserEntity getOrCreateUser(UserServiceModel userServiceModel);
    void createAndLoginUser(UserServiceModel userServiceModel);
    void loginUser(String username,String password);
    List<UserViewModel>getAllUsersPublicationsExceptTheUserThatIsLoggedIn(String usernameLoggedInUser);
    boolean isAdmin(String username);
    void createAdmin();



    void deleteFilmFromUser(String id,String username);
}

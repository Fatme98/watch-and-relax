package project.watch_and_relax.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.watch_and_relax.error.PasswordNotProvidedException;
import project.watch_and_relax.error.UserNotFoundException;
import project.watch_and_relax.model.entity.Film;
import project.watch_and_relax.model.entity.RoleEntity;
import project.watch_and_relax.model.entity.UserEntity;
import project.watch_and_relax.model.service.*;
import project.watch_and_relax.model.view.FilmViewModel;
import project.watch_and_relax.model.view.RoleViewModel;
import project.watch_and_relax.model.view.UserViewModel;
import project.watch_and_relax.repository.UserRepository;
import project.watch_and_relax.service.PhotoService;
import project.watch_and_relax.service.UserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PhotoService photoService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    private static final Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           PhotoService photoService, PasswordEncoder passwordEncoder, @Qualifier("userDetailsServiceImpl")
                                       UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.modelMapper=modelMapper;
        this.photoService = photoService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public UserViewModel findUser(String username) {
        UserEntity userEntity =this.userRepository.findByUsername(username).orElse(null);
        UserViewModel userViewModel=new UserViewModel();
        if(userEntity ==null){
            throw new UserNotFoundException(username);
        }else{
            userViewModel=this.modelMapper.map(userEntity,UserViewModel.class);
            userViewModel.setFilms(getFilms(userEntity.getFilms()));
            if(userEntity.getPhoto()!=null){
                userViewModel.setPhotoUrl(userEntity.getPhoto().getImageUrl());
            }

            List<RoleViewModel>roles=new ArrayList<>();
            for (RoleEntity role : userEntity.getRoles()) {
                RoleViewModel roleViewModel=this.modelMapper.map(role,RoleViewModel.class);
                roles.add(roleViewModel);
            }
            userViewModel.setRoles(roles);
        }
        return userViewModel;
    }

    @Override
    public UserViewModel findUserByUsername(String username) {
        UserEntity userEntity= this.userRepository.findByUsername(username).orElse(null);
        if(userEntity==null){
            throw new UserNotFoundException(username);
        }
        UserViewModel userViewModel=this.modelMapper.map(userEntity,UserViewModel.class);
        userViewModel.setId(userEntity.getId());
        return userViewModel;
    }

    @Override
    public boolean existsUser(String username) {
        Objects.requireNonNull(username);
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public UserEntity getOrCreateUser(UserServiceModel userServiceModel) {
        Objects.requireNonNull(userServiceModel.getPassword());
        Optional<UserEntity>userEntityOpt=userRepository.findByUsername(userServiceModel.getUsername());
        return userEntityOpt.orElseGet(()->createUser(userServiceModel));
    }

    @Override
    public void createAndLoginUser(UserServiceModel userServiceModel) {
        UserEntity newUser=createUser(userServiceModel);
        UserDetails userDetails=userDetailsService.loadUserByUsername(newUser.getUsername());
        Authentication authentication=new UsernamePasswordAuthenticationToken(userDetails,userServiceModel.getPassword(),
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void loginUser(String username,String password) {
        UserDetails userDetails=userDetailsService.loadUserByUsername(username);
        Authentication authentication=new UsernamePasswordAuthenticationToken(userDetails,password,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public List<UserViewModel> getAllUsersPublicationsExceptTheUserThatIsLoggedIn(String usernameLoggedInUser) {
        List<UserViewModel>users=new ArrayList<>();
        List<UserEntity>userEntities=this.userRepository.findAll();
        for (UserEntity userEntity : userEntities) {
            if(!userEntity.getUsername().equals(usernameLoggedInUser)&&
                    !userEntity.getUsername().equals("admin")){
                UserViewModel userViewModel=new UserViewModel();
                userViewModel=this.modelMapper.map(userEntity,UserViewModel.class);
                userViewModel.setFilms(getFilms(userEntity.getFilms()));

                userViewModel.setPhotoUrl(userEntity.getPhoto().getImageUrl());
                users.add(userViewModel);
            }
        }
        return users;
    }

    @Override
    public boolean isAdmin(String username) {
        List<String>roles=new ArrayList<>();
        UserEntity userEntity=this.userRepository.findByUsername(username).orElse(null);
        if(userEntity==null){
            throw new UserNotFoundException(username);
        }
        for (RoleEntity role : userEntity.getRoles()) {
            roles.add(role.getRole());
        }
        if(roles.contains("ADMIN")){
            return true;
        }
        return false;
    }

    @Override
    public void createAdmin() {
        if(this.userRepository.count()==0){
            UserEntity userEntity=new UserEntity();
            userEntity.setUsername("admin");
            userEntity.setPassword("admin");
            List<RoleEntity> roles = new ArrayList<>();
            RoleEntity roleEntity=new RoleEntity();
            roleEntity.setRole("ADMIN");
            roles.add(roleEntity);
            userEntity.setRoles(roles);
            userEntity.setFirstName("Admin");
            userEntity.setLastName("Admin");
            userEntity.setEmail("admin@email");
            this.userRepository.saveAndFlush(userEntity);
        }
    }




    @Override
    public void deleteFilmFromUser(String id, String username) {
        UserEntity userEntity=this.userRepository.findByUsername(username).orElse(null);
        if(userEntity!=null){
            List<Film> films = userEntity.getFilms();
            int indexForRemove=0;
            boolean doesExistFilmInList=false;
            for(int i=0;i<films.size();i++){
                if(films.get(i).getId().equals(id)){
                    doesExistFilmInList=true;
                    indexForRemove=i;
                }
            }
            if(doesExistFilmInList){
                films.remove(indexForRemove);
            }
            userEntity.setFilms(films);
            this.userRepository.save(userEntity);
        }
    }


    private UserEntity createUser(UserServiceModel userServiceModel){
        UserEntity userEntity=new UserEntity();
            LOGGER.info("Creating a new user with username [GDPR].");
            userEntity=this.createUserWithRoles(userServiceModel,"USER");
        return userRepository.save(userEntity);
    }
    private UserEntity createUserWithRoles(UserServiceModel userServiceModel,String role){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userServiceModel.getUsername());
        userEntity.setEmail(userServiceModel.getEmail());
        userEntity.setFirstName(userServiceModel.getFirstName());
        userEntity.setLastName(userServiceModel.getLastName());
        PhotoProfileServiceModel photoProfileServiceModel=this.photoService.
                addPhoto(userServiceModel.getPhotoServiceModel());
        userEntity.setPhoto(photoProfileServiceModel.getPhoto());
        if(userServiceModel.getPassword()!=null){
            userEntity.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
        }else{
            throw new PasswordNotProvidedException();
        }
        RoleEntity userRole=new RoleEntity();
        userRole.setRole(role);
        userEntity.setRoles(List.of(userRole));
        return userEntity;
    }


    private List<FilmViewModel>getFilms(List<Film>films){
        List<FilmViewModel>filmViewModels=new ArrayList<>();
        for (Film film : films) {
            FilmViewModel filmViewModel=new FilmViewModel();
            filmViewModel.setPhotoUrl(film.getPhoto().getImageUrl());
            filmViewModel.setName(film.getName());
            filmViewModel.setId(film.getId());
            filmViewModels.add(filmViewModel);
        }
        return filmViewModels;
    }


}

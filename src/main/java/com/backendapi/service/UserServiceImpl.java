//Con @Service indichiamo a Spring che la classe è un bean con logica di business.
@Service
//Con @Transactional indichiamo a Spring che tutti i metodi della classe sono in transazione.
@Transactional

//@RequiredArgsConstructor e @Slf4j sono due annotations della libreria Lombok, che ci permettono 
// di autogenerare un costruttore in base ai campi final, e creare un logger, rispettivamente.
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with username %s not found";

    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    //Il metodo save, oltre a richiamare banalmente il metodo save del repository, codifica la 
    // password prima di salvare a db. Creeremo successivamente un bean di tipo PasswordEncoder.
    public UserEntity save(UserEntity user) {
        log.info("Saving new user {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userJpaRepository.save(user);
    }

    @Override
    //Il metodo addRoleToUser permette di aggiungere un ruolo esistente ad un utente esistente. 
    // Non viene invocato il metodo save di UserJpaRepository in quanto userEntity è già una entity 
    // managed, essendo in transazione, e quindi tutte le sue modifiche dopo il findByUsername 
    // vengono salvare.
    public UserEntity addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        UserEntity userEntity = userJpaRepository.findByUsername(username);
        RoleEntity roleEntity = roleJpaRepository.findByName(roleName);
        userEntity.getRoles().add(roleEntity);
        return userEntity;
    }

}


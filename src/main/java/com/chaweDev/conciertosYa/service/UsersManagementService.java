package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.dto.ReqRes;
import com.chaweDev.conciertosYa.entity.OurUsers;
import com.chaweDev.conciertosYa.repository.UsersRepo;
import com.chaweDev.conciertosYa.service.visual.IUsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UsersManagementService implements IUsersManagementService {

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
    Instanciación de ReqRes desde un objeto de tipo DTO:
    El metodo register recibe un parámetro de tipo DTO, que podría ser cualquier objeto que implemente la interfaz DTO.
    La expresión instanceof ReqRes verifica si el objeto registrationRequest es una instancia de ReqRes,
    lo que permite que se pueda tratar específicamente como un objeto de tipo ReqRes. Si el objeto es de ese tipo,
    el código dentro del bloque if se ejecuta.

    Asignación a una variable del tipo ReqRes:
    El uso de instanceof no solo verifica el tipo del objeto,
    sino que también realiza un casting implícito del objeto registrationRequest a ReqRes.
    Esto significa que el objeto registrationRequest es tratado como un ReqRes dentro del bloque if.
    Esta técnica permite que se puedan acceder de manera directa a los métodos y propiedades específicos de ReqRes sin necesidad de hacer un cast manual,
    y es posible gracias al polimorfismo.

    Utilización de datos para crear y guardar un usuario:
    Una vez verificado y casteado el DTO como ReqRes, se procede a crear un objeto de tipo OurUsers
    y a establecer sus valores mediante los datos provenientes del DTO. Luego, el usuario se guarda en el repositorio mediante usersRepo.save().
    Si el usuario se guarda exitosamente (es decir, si el id del usuario es mayor a 0), se actualizan los valores de la respuesta y se indica que el usuario fue guardado correctamente.
    En caso contrario, se devuelve un mensaje indicando un error en el proceso de guardado.
    */
    public ReqRes register(DTO dto) {
        ReqRes resp = new ReqRes();

        try {
            // Verifica que el DTO sea de tipo ReqRes
            if (dto instanceof ReqRes registrationRequest) {
                OurUsers ourUser = new OurUsers();

                // Establece los valores del objeto OurUsers a partir del DTO
                ourUser.setEmail(registrationRequest.getEmail());
                ourUser.setCity(registrationRequest.getCity());
                ourUser.setRole(registrationRequest.getRole());
                ourUser.setName(registrationRequest.getName());
                ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

                // Guarda el usuario en la base de datos
                OurUsers ourUsersResult = usersRepo.save(ourUser);

                // Verifica si el usuario fue guardado exitosamente
                if (ourUsersResult.getId() > 0) {
                    resp.setOurUsers(ourUsersResult);
                    resp.setMessage("User Saved Successfully");
                    resp.setStatusCode(200);
                } else {
                    resp.setMessage("User not saved due to an unknown error.");
                    resp.setStatusCode(500);
                }
            } else {
                resp.setMessage("Invalid DTO type");
                resp.setStatusCode(400);
            }

        } catch (Exception e) {
            // Captura el error y lo registra en el objeto de respuesta
            resp.setStatusCode(500);
            resp.setError("Error: " + e.getMessage());
        }
        return resp;
    }

    /*
    Instanciación de ReqRes desde un objeto de tipo DTO:
    El metodo login recibe un parámetro de tipo DTO, que podría ser cualquier objeto que implemente la interfaz DTO.
    La expresión instanceof ReqRes verifica si el objeto dto es una instancia de ReqRes,
    lo que permite que se pueda tratar específicamente como un objeto de tipo ReqRes. Si el objeto es de ese tipo,
    el código dentro del bloque if se ejecuta.

    Asignación a una variable del tipo ReqRes:
    El uso de instanceof no solo verifica el tipo del objeto,
    sino que también realiza un casting implícito del objeto dto a ReqRes.
    Esto significa que el objeto dto es tratado como un ReqRes dentro del bloque if.
    Esta técnica permite que se puedan acceder de manera directa a los métodos y propiedades específicos de ReqRes sin necesidad de hacer un cast manual,
    y es posible gracias al polimorfismo.

    Proceso de autenticación y generación de tokens:
    Una vez verificado y casteado el DTO como ReqRes, se procede a autenticar al usuario utilizando el email y la contraseña proporcionados en el DTO.
    Si la autenticación es exitosa, se obtiene el usuario desde el repositorio y se genera un token JWT y un refresh token.
    Luego, se establecen los valores en la respuesta, incluyendo el ID del usuario, el rol, el token JWT, el refresh token, el tiempo de expiración y un mensaje de éxito.
    Si ocurre algún error durante este proceso, se captura la excepción y se establece un mensaje de error en la respuesta.
    */
    public ReqRes login(DTO dto) {
        ReqRes response = new ReqRes();
        try {
            if (dto instanceof ReqRes loginRequest) {
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                loginRequest.getPassword()));

                var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
                var jwt = jwtUtils.generateToken(user);
                var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

                response.setStatusCode(200);
                response.setToken(jwt);
                response.setId(user.getId());
                response.setRole(user.getRole());
                response.setRefreshToken(refreshToken);
                response.setExpirationTime("24Hrs");
                response.setMessage("Successfully Logged In");

            } else {
                response.setMessage("Invalid DTO type");
                response.setStatusCode(400);
            }

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public ReqRes refreshToken(ReqRes refreshTokenReqiest){
        ReqRes response = new ReqRes();
        try{
            String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
            OurUsers users = usersRepo.findByEmail(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenReqiest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    public ReqRes getAllUsers() {
        ReqRes reqRes = new ReqRes();

        try {
            List<OurUsers> result = usersRepo.findAll();

            if (result.isEmpty()) {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            } else {
                reqRes.setOurUsersList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Users found successfully");
            }

        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }

        return reqRes;
    }

    public ReqRes getUsersById(Integer id) {
        ReqRes reqRes = new ReqRes();
        try {
            OurUsers usersById = usersRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            reqRes.setOurUsers(usersById);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Users with id '" + id + "' found successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes deleteUser(Integer userId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(userId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes updateUser(Integer userId, OurUsers updatedUser) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                OurUsers existingUser = userOptional.get();
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setName(updatedUser.getName());
                existingUser.setCity(updatedUser.getCity());
                existingUser.setRole(updatedUser.getRole());

                // Check if password is present in the request
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    // Encode the password and update it
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                OurUsers savedUser = usersRepo.save(existingUser);
                reqRes.setOurUsers(savedUser);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }
    public ReqRes getMyInfo(String email){
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                reqRes.setOurUsers(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }
}
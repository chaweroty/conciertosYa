import com.chaweDev.conciertosYa.dto.ReqRes;
import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.entity.OurUsers;
import com.chaweDev.conciertosYa.repository.UsersRepo;
import com.chaweDev.conciertosYa.service.UsersManagementService;

import com.chaweDev.conciertosYa.service.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UsersManagementServiceTest {

    @InjectMocks
    private UsersManagementService usersManagementService;

    @Mock
    private UsersRepo usersRepo;

    @Mock
    private JWTUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserSuccessfully() {
        ReqRes reqRes = new ReqRes();
        reqRes.setEmail("test@example.com");
        reqRes.setName("Test User");
        reqRes.setPassword("password123");
        reqRes.setCity("Test City");
        reqRes.setRole("USER");

        OurUsers savedUser = new OurUsers();
        savedUser.setId(1);
        savedUser.setEmail(reqRes.getEmail());
        savedUser.setPassword(reqRes.getPassword());

        when(usersRepo.save(any(OurUsers.class))).thenReturn(savedUser);

        ReqRes response = usersManagementService.register(reqRes);

        assertEquals(200, response.getStatusCode());
        assertEquals("User Saved Successfully", response.getMessage());
        assertNotNull(response.getOurUsers());
        verify(usersRepo, times(1)).save(any(OurUsers.class));
    }

    @Test
    void testRegisterInvalidDTO() {
        DTO invalidDto = mock(DTO.class);

        ReqRes response = usersManagementService.register(invalidDto);

        assertEquals(400, response.getStatusCode());
        assertEquals("Invalid DTO type", response.getMessage());
    }

    @Test
    void testRegisterFailsToSaveUser() {
        ReqRes reqRes = new ReqRes();
        reqRes.setEmail("test@example.com");
        reqRes.setPassword("password123");

        OurUsers savedUser = new OurUsers();
        savedUser.setId(0); // Simulate failed save

        when(usersRepo.save(any(OurUsers.class))).thenReturn(savedUser);

        ReqRes response = usersManagementService.register(reqRes);

        assertEquals(500, response.getStatusCode());
        assertEquals("User not saved due to an unknown error.", response.getMessage());
    }

    @Test
    void testLoginSuccessfully() {
        ReqRes loginRequest = new ReqRes();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        OurUsers user = new OurUsers();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setRole("USER");

        when(usersRepo.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(jwtUtils.generateToken(user)).thenReturn("jwt-token");


        ReqRes response = usersManagementService.login(loginRequest);

        assertEquals(200, response.getStatusCode());
        assertEquals("Successfully Logged In", response.getMessage());
        assertEquals("jwt-token", response.getToken());
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getRole(), response.getRole());
    }

    @Test
    void testLoginInvalidDTO() {
        DTO invalidDto = mock(DTO.class);

        ReqRes response = usersManagementService.login(invalidDto);

        assertEquals(400, response.getStatusCode());
        assertEquals("Invalid DTO type", response.getMessage());
    }

    @Test
    void testLoginAuthenticationFails() {
        ReqRes loginRequest = new ReqRes();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        doThrow(new RuntimeException("Authentication failed"))
                .when(authenticationManager)
                .authenticate(any());

        ReqRes response = usersManagementService.login(loginRequest);

        assertEquals(500, response.getStatusCode());
        assertEquals("Authentication failed", response.getMessage());
    }

    @Test
    void testRefreshTokenSuccessfully() {
        ReqRes refreshRequest = new ReqRes();
        refreshRequest.setToken("valid-refresh-token");

        OurUsers user = new OurUsers();
        user.setEmail("test@example.com");

        when(jwtUtils.extractUsername("valid-refresh-token")).thenReturn("test@example.com");
        when(usersRepo.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(jwtUtils.isTokenValid("valid-refresh-token", user)).thenReturn(true);
        when(jwtUtils.generateToken(user)).thenReturn("new-jwt-token");

        ReqRes response = usersManagementService.refreshToken(refreshRequest);

        assertEquals(200, response.getStatusCode());
        assertEquals("Successfully Refreshed Token", response.getMessage());
        assertEquals("new-jwt-token", response.getToken());
    }

    @Test
    void testGetAllUsersSuccessfully() {
        List<OurUsers> usersList = new ArrayList<>();
        usersList.add(new OurUsers());

        when(usersRepo.findAll()).thenReturn(usersList);

        ReqRes response = usersManagementService.getAllUsers();

        assertEquals(200, response.getStatusCode());
        assertEquals("Users found successfully", response.getMessage());
        assertFalse(response.getOurUsersList().isEmpty());
    }

    @Test
    void testGetAllUsersEmpty() {
        when(usersRepo.findAll()).thenReturn(new ArrayList<>());

        ReqRes response = usersManagementService.getAllUsers();

        assertEquals(404, response.getStatusCode());
        assertEquals("No users found", response.getMessage());
    }

    @Test
    void testDeleteUserSuccessfully() {
        int userId = 1;
        OurUsers user = new OurUsers();
        user.setId(userId);

        when(usersRepo.findById(userId)).thenReturn(Optional.of(user));

        ReqRes response = usersManagementService.deleteUser(userId);

        assertEquals(200, response.getStatusCode());
        assertEquals("User deleted successfully", response.getMessage());
        verify(usersRepo, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUserNotFound() {
        int userId = 1;

        when(usersRepo.findById(userId)).thenReturn(Optional.empty());

        ReqRes response = usersManagementService.deleteUser(userId);

        assertEquals(404, response.getStatusCode());
        assertEquals("User not found for deletion", response.getMessage());
    }

    @Test
    void testUpdateUserSuccessfully() {
        int userId = 1;
        OurUsers user = new OurUsers();
        user.setId(userId);
        user.setEmail("old@example.com");

        OurUsers updatedUser = new OurUsers();
        updatedUser.setEmail("new@example.com");

        when(usersRepo.findById(userId)).thenReturn(Optional.of(user));
        when(usersRepo.save(any())).thenReturn(updatedUser);

        ReqRes response = usersManagementService.updateUser(userId, updatedUser);

        assertEquals(200, response.getStatusCode());
        assertEquals("User updated successfully", response.getMessage());
        assertEquals("new@example.com", response.getOurUsers().getEmail());
    }

    @Test
    void testUpdateUserNotFound() {
        int userId = 1;

        when(usersRepo.findById(userId)).thenReturn(Optional.empty());

        ReqRes response = usersManagementService.updateUser(userId, new OurUsers());

        assertEquals(404, response.getStatusCode());
        assertEquals("User not found for update", response.getMessage());
    }
}

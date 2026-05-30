package lite.vls;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import lite.vls.users.User;
import lite.vls.users.UserEntity;
import lite.vls.users.UserMapper;
import lite.vls.users.UserRepository;
import lite.vls.users.UserRole;
import lite.vls.users.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)

@SpringBootTest
class VlsApplicationTests {
	@Mock
	private UserRepository userRepository;

	@Mock
	private UserMapper userMapper;

	@InjectMocks
	private UserService userService;
	
	@Test
	void getUserTest() {

		Long userId = 1L;
		User user = new User(
			userId,
			"Иван",
			"Петров",
			"+7 (999) 123-45-67",
			"ivan.petrov@example.com",
			"password123",
			UserRole.USER,
			true
		);

		UserEntity userEntity = new UserEntity(
			userId,
			"Иван",
			"Петров",
			"+7 (999) 123-45-67",
			"ivan.petrov@example.com",
			"password123",
			UserRole.USER,
			true
		);

		when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
		when(userMapper.toDomain(userEntity)).thenReturn(user);
		

		User result = userService.getUser(userId);

		assertNotNull(result);
		assertEquals(user, result);
		assertEquals(userId, result.id());
		assertEquals("Иван", result.name());
		
		verify(userMapper, times(1)).toDomain(userEntity);
	}

	@Test
	void getAllUserTest() {

		Long userId1 = 1L;
		User user1 = new User(
			userId1,"Иван","Петров","+7 (999) 123-45-67",
			"ivan.petrov@example.com","password123",UserRole.USER,true
		);

		UserEntity userEntity1 = new UserEntity(
			userId1, "Иван", "Петров","+7 (999) 123-45-67",
			"ivan.petrov@example.com","password123",UserRole.USER,	true
		);

		Long userId2 = 2L;
		User user2 = new User(
			userId2, "Мария", "Сидорова", "+7 (999) 765-43-21",
			"maria@example.com", "password456", UserRole.USER, true
		);

		UserEntity userEntity2 = new UserEntity(
			userId2, "Мария", "Сидорова", "+7 (999) 765-43-21",
			"maria@example.com", "password456", UserRole.USER, true
		);

		List<User> users = Arrays.asList(user1, user2);
		List<UserEntity> usersEntity = Arrays.asList(userEntity1, userEntity2);

		when(userRepository.findAll()).thenReturn(usersEntity);
		when(userMapper.toDomain(userEntity1)).thenReturn(user1);
		when(userMapper.toDomain(userEntity2)).thenReturn(user2);
		

		List<User> result = userService.getAllUsers();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(users, result);
		assertEquals(user1, result.get(0));
		assertEquals("Иван", result.get(0).name());
		assertEquals(user2, result.get(1));
		assertEquals("Мария", result.get(1).name());
		
		verify(userRepository, times(1)).findAll();
		verify(userMapper, times(2)).toDomain(any(UserEntity.class));
	}

}

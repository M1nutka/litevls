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
import static org.mockito.Mockito.*;

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

}

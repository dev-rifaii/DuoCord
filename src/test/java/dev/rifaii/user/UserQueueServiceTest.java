package dev.rifaii.user;

import dev.rifaii.activity.Activity;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserQueueServiceTest {

    @Mock
    private UserQueueDao userQueueDao;

    @InjectMocks
    private UserQueueService userQueueService;

    @ParameterizedTest
    @MethodSource("enqueueUser_ThrowsValidationExceptionInput")
    void enqueueUser_ThrowsConstraintViolationException_GivenInvalidUser(User user) {
        assertThrows(ConstraintViolationException.class, () -> userQueueService.enqueueUser(user));
        verify(userQueueDao, never()).insertToQueue(user);
    }

    private static Stream<Arguments> enqueueUser_ThrowsValidationExceptionInput() {
        return Stream.of(
                Arguments.of(new User()),
                Arguments.of(new User().setAge((short) -1)),
                Arguments.of(new User().setAge((short) 121)),
                Arguments.of(new User().setAboutTheUser("         ")),
                Arguments.of(new User().setDiscordHandle("test")
                                       .setDiscordName("test")
                                       .setDiscordId(123L)
                                       .setActivity(Activity.OVERWATCH)
                                       .setAboutTheUser("test")
                                       .setAge((short) 12))
        );
    }
}
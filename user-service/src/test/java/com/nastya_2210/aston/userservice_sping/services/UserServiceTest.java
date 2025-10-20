//package com.nastya_2210.aston.userservice_sping.services;
//
//import com.nastya_2210.aston.userservice_sping.repository.UserRepository;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.kafka.core.KafkaTemplate;
//
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private KafkaTemplate<String, UserEventDTO> kafkaTemplate;
//
//    @InjectMocks
//    private UserService userService;
//
//    void shouldSendUserCreatedEventWhenUserSaved(){
//
//    }
//}
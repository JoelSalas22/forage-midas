package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseConduit {
    private final UserRepository userRepository;

//    public DatabaseConduit(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }

}

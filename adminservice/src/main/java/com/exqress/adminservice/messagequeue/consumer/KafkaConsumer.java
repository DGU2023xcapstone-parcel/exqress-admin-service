package com.exqress.adminservice.messagequeue.consumer;

import com.exqress.adminservice.entity.UserEntity;
import com.exqress.adminservice.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @PostConstruct
    public void initMapper(){
        objectMapper = new ObjectMapper();
    }


//    @KafkaListener(topics = "TrackingInfoId")
//    public void updateTrackingInfo(String kafkaMessage){
//        log.info("Kafka Message: -> " + kafkaMessage);
//
//        Map<Object, Object> map = new HashMap<>();
//        try{
//            map = modelMapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
//            /*
//            map으로 Admin에서 전송한 정보를 추출해야함
//            ------------------
//            1) userId
//            2) qrId
//            3) invoiceNo
//            4) productName
//            5) receiverName (= 본인 이름이겠지?)
//            ------------------
//            전송 받아서 연관관계 설정해주고 데이터베이스에 저장해준다
//             */
//        } catch (JsonProcessingException ex){
//            // 예외 처리 해줘야함
//            ex.printStackTrace();
//        }
//        UserEntity user = userRepository.findByUserId((String) map.get("userId"));
//        if(user != null){
//            QRdto qrDto = getQrDto(map);
//            QRinfo kafkaQRInfo = modelMapper.map(qrDto, QRinfo.class);
//
//            // 도메인 주도 설계 기법으로 구현
//            user.addQRinfoList(kafkaQRInfo);
//            kafkaQRInfo.connectUser(user);
//        }
//    }

    @KafkaListener(topics = "create_user")
    public void assignUser(String kafkaMessage){
        log.info("ComeIn User Assign to Kafka");
        Map<Object, Object> map = new HashMap<>();
        try{
            map = objectMapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException ex){
            ex.printStackTrace();
        }
        UserEntity user = getUserEntity(map);
        userRepository.save(user);
    }

    private UserEntity getUserEntity(Map<Object, Object> map){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId((String) map.get("userId"));
        userEntity.setName((String) map.get("name"));
        userEntity.setPhoneNumber((String) map.get("phoneNumber"));

        return userEntity;
    }
}
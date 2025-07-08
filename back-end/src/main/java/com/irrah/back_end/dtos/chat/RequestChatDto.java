package com.irrah.back_end.dtos.chat;

import com.irrah.back_end.entities.MessageEntity;
import com.irrah.back_end.entities.UserEntity;
import java.util.List;

public record RequestChatDto(
        UserEntity userAdminer,
        UserEntity userCommon,
        List<MessageEntity> message

) { }

package kr.ac.mju.teamcheerup.modle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class FCMMessage {
    private boolean validate_only;
    private Message message;
}

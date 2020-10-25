package kr.ac.mju.teamcheerup.modle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class Event {
    private String title;
    private String data;
    private int event;
    private LocalDateTime dateTime;
}

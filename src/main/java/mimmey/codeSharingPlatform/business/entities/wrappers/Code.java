package mimmey.codeSharingPlatform.business.entities.wrappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Code {

    private String code;
    private int time;
    private int views;
}

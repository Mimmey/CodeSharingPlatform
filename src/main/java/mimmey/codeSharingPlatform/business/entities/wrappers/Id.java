package mimmey.codeSharingPlatform.business.entities.wrappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mimmey.codeSharingPlatform.business.entities.Snippet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Id {

    private String id;

    public Id(Snippet snippet) {
        this.id = snippet.getId();
    }
}

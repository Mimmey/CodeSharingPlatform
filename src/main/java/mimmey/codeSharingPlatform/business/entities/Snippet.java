package mimmey.codeSharingPlatform.business.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import mimmey.codeSharingPlatform.business.DateTimeManipulations;
import mimmey.codeSharingPlatform.business.entities.wrappers.Code;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.UUID;
import javax.validation.constraints.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "snippets")
public class Snippet {

    @Id
    @JsonIgnore
    private String id;

    @NotBlank(message = "Please, enter your code")
    private String code;

    private String date;
    private int time;
    private int views;

    @JsonIgnore
    @Column(name = "is_time_endless")
    private boolean timeEndless;

    @JsonIgnore
    @Column(name = "are_views_endless")
    private boolean viewsEndless;

    @JsonIgnore
    private int allTime;

    {
        this.time = 0;
        this.views = 0;
        this.timeEndless = false;
        this.viewsEndless = false;
    }

    public Snippet(Code code) {
        this.id = UUID.randomUUID().toString();
        this.code = code.getCode();

        if (code.getTime() <= 0) {
            this.timeEndless = true;
            this.allTime = 0;
        } else {
            this.time = code.getTime();
            this.allTime = code.getTime();
        }

        if (code.getViews() <= 0) {
            this.viewsEndless = true;
        } else {
            this.views = code.getViews();
        }

        this.date = DateTimeManipulations.formatDate(LocalDateTime.now());
    }

    static class SortByDateDesc implements Comparator<Snippet> {
        public int compare(Snippet s1, Snippet s2) {
            if (s1.date.equals(s2.date))
                return 0;

            return DateTimeManipulations.unformatDate(s1.date).isBefore(DateTimeManipulations.unformatDate(s2.date)) ? 1 : -1;
        }
    }

    @JsonIgnore
    public static Comparator<Snippet> getComparator() {
        return new SortByDateDesc();
    }

    @JsonIgnore
    public LocalDateTime getUnformattedDate() {
        return DateTimeManipulations.unformatDate(this.date);
    }

    public void refresh() {
        this.refreshTime();
        this.refreshViews();
    }

    private void refreshTime() {
        int secondsFromCreation = DateTimeManipulations.differenceInSeconds(this.getUnformattedDate(), LocalDateTime.now());

        if (!this.timeEndless && secondsFromCreation < this.allTime) {
            this.time = this.allTime - secondsFromCreation;
        } else {
            this.time = 0;
        }
    }

    private void refreshViews() {
        if (!this.viewsEndless && this.views >= 0) {
            this.views--;
        }
    }
}
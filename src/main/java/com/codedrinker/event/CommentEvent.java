package com.codedrinker.event;

import com.codedrinker.dto.CommentDTO;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * Created by codedrinker on 2018/12/17.
 */
@Data
public class CommentEvent extends ApplicationEvent {

    private CommentDTO comment;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public CommentEvent(Object source, CommentDTO comment) {
        super(source);
        this.comment = comment;
    }
}

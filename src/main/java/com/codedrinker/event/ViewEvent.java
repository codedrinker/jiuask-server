package com.codedrinker.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * Created by codedrinker on 2018/12/17.
 */
@Data
public class ViewEvent extends ApplicationEvent {
    private Integer questionId;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ViewEvent(Object source, Integer questionId) {
        super(source);
        this.questionId = questionId;
    }
}

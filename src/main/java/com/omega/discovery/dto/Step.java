package com.omega.discovery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Step implements Comparable<Step>{

    private Integer stepNumber;

    private ElementType elementType;

    private String value;

    @Override
    public int compareTo(Step o) {
        if (this.stepNumber < o.stepNumber) {
            return -1;
        }
        if (this.stepNumber > o.stepNumber){
            return 1;
        }
        return 0;
    }
}

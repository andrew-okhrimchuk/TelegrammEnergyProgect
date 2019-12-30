package DTO;


import lombok.*;
import java.io.Serializable;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Report implements Serializable {
    private Data start, end;
    private int indicators, request;
}

package project;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TalkVO {

    private String mem_id;
    private String mem_pw;
    private String mem_nick;
    private String mem_name;
    private String gender;
    private int zipcode;
    private String mem_addr;
    private String email;
    private String Img;


}

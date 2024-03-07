package shop.mtcoding.blog.model.resume;

import lombok.Data;
import lombok.NoArgsConstructor;
import shop.mtcoding.blog.model.skill.SkillRequest;
import shop.mtcoding.blog.model.user.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ResumeRequest {



    @Data
    public static class UserViewDTO{
        private Integer id;
        private Integer userId;
        private String title;
        private String edu;
        private String area;
        private String resumeId;
        private List<SkillRequest.UserskillDTO> skillList = new ArrayList<>();
        private Integer number;
    }

}
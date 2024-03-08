package shop.mtcoding.blog.model.resume;


import lombok.Data;
import lombok.NoArgsConstructor;
import shop.mtcoding.blog.model.skill.SkillResponse;

import java.util.List;

public class ResumeResponse {


    @Data
    public static class ResumeViewDTO {
        private Integer id;
        private Integer userId;
        private String title;
        private String edu;
        private String area;
        private String career;

        public ResumeViewDTO(Integer id, Integer userId, String title, String edu, String area, String career) {
            this.id = id;
            this.userId = userId;
            this.title = title;
            this.edu = edu;
            this.area = area;
            this.career = career;
        }

        private List<SkillResponse.ResumeSkillDTO> skillList;

    }


}

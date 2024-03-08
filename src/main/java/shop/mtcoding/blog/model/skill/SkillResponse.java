package shop.mtcoding.blog.model.skill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SkillResponse {

    @Data
    public static class ResumeSkillDTO {
        private Integer id;
        private String name;

        public ResumeSkillDTO(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    }

}

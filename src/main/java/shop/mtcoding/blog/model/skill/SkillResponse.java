package shop.mtcoding.blog.model.skill;

import lombok.Data;
import lombok.NoArgsConstructor;

public class SkillResponse {

    @Data
    public class ResumeSkillDTO {
        private String name;

        public ResumeSkillDTO(String name) {
            this.name = name;
        }
    }

}

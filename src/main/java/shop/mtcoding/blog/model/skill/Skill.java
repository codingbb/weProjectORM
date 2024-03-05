package shop.mtcoding.blog.model.skill;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.blog.model.jobs.Jobs;
import shop.mtcoding.blog.model.resume.Resume;

@Table(name = "skill_tb")
@Data
@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer resumeId; // 5

    private Integer jobsId; // null

    @Column(nullable = false)
    private String name; // java

    @Column(nullable = false)
    private Integer role; // 1

}

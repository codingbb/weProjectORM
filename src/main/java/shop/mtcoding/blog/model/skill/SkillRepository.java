package shop.mtcoding.blog.model.skill;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.model.resume.Resume;
import shop.mtcoding.blog.model.resume.ResumeRequest;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class SkillRepository {
    private final EntityManager em;

    public List<Skill> findAllV2(int jobsId) {
        Query query = em.createNativeQuery("select * from skill_tb where jobs_id = ?", Skill.class);
        query.setParameter(1, jobsId);
        return query.getResultList();
    }

//    public List<Skill> findSkillName(SkillResponse.SkillDTO skillDTO) {
//        Query query = em.createNativeQuery("select * from skill_tb where resume_id = ?", Skill.class);
//        query.setParameter(1, skillDTO.getResume_id());
//        return query.getResultList();
//    }







}

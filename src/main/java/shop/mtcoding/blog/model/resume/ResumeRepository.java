package shop.mtcoding.blog.model.resume;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.model.skill.Skill;
import shop.mtcoding.blog.model.skill.SkillResponse;
import shop.mtcoding.blog.model.user.User;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ResumeRepository {

    private final EntityManager em;

    @Transactional
    public Integer saveResume(ResumeRequest.ResumeWriteDTO requestDTO ,User sessionUser) {
        String q = """
                insert into resume_tb (user_id, area, edu, career, introduce, port_link, title, is_public,created_at) 
                values (?, ?, ?, ?, ?, ?, ?, ?, now())
                """;

        Query query = em.createNativeQuery(q);
        query.setParameter(1, sessionUser.getId());
        query.setParameter(2, requestDTO.getArea());
        query.setParameter(3, requestDTO.getEdu());
        query.setParameter(4, requestDTO.getCareer());
        query.setParameter(5, requestDTO.getIntroduce());
        query.setParameter(6, requestDTO.getPortLink());
        query.setParameter(7, requestDTO.getTitle());
        query.setParameter(8, requestDTO.getIsPublic());
        query.executeUpdate();

        //결과는 max값인 5가 나온다.
        //select * from resume_tb where id = (select max(id) from resume_tb); 이렇게하면 id가 5인 것의 결과값이 다 나옴
        //딱 필요한 값인 max id값만 빼오는게 좋을 것 같음.
        String a = """
                select max(id) from resume_tb;
                """;

        Query query1 = em.createNativeQuery(a);
        Integer resumeId = (Integer) query1.getSingleResult();

        return resumeId;

    }

     public List<Resume> findAll() {
          String q = """
                  select * from resume_tb order by id desc
                  """;

          Query query = em.createNativeQuery(q, Resume.class);
          List<Resume> resumeList = query.getResultList();

          return resumeList;
      }

    public List<Resume> findByUserId(User sessionUser) {
        String q = """
                  select * from resume_tb where user_id = ? order by id desc
                  """;

        Query query = em.createNativeQuery(q, Resume.class);
        query.setParameter(1, sessionUser.getId());
        List<Resume> resumeList = query.getResultList();

        return resumeList;
    }


    public Resume findById(int id) {
        String q = """
                select * from resume_tb where id = ?
                """;
        Query query = em.createNativeQuery(q,Resume.class);
        query.setParameter(1, id);

        Resume resume = (Resume) query.getSingleResult();
        return resume;
    }



    public List<Resume> findByResumeAndUser(Integer id) {
         String q = """
                 select r.*  from resume_tb r inner join user_tb u on r.user_id = u.id where r.id = ?;
                 """;

        Query query = em.createNativeQuery(q, Resume.class);
        query.setParameter(1,id);
        List<Resume> resumeList = query.getResultList();
        return resumeList;

    }


    @Transactional
    public void deleteById(int id) {
        Query query = em.createNativeQuery("delete from resume_tb where id = ?");
        query.setParameter(1, id);
        query.executeUpdate();
    }


    //ResumeViewDTO 타입으로 받와야함. 뷰를 뿌릴거니까!
    public List<ResumeResponse.ResumeViewDTO> findAllResumeUserId(Integer id) {
        //user id가 1인
        String q = """
                select rt.id,rt.user_id ,rt.title, rt.edu, rt.career, rt.area from resume_tb rt where user_id = ?
                """;

        Query query = em.createNativeQuery(q);
        query.setParameter(1, id);

        JpaResultMapper mapper = new JpaResultMapper();
        List<ResumeResponse.ResumeViewDTO> result = mapper.list(query, ResumeResponse.ResumeViewDTO.class);

        return result;

    }

    public List<SkillResponse.ResumeSkillDTO> findAllByResumeId(Integer id) {
        String q = """
                select st.id ,st.name from skill_tb st 
                inner join resume_tb rt on st.resume_id = rt.id where rt.id =?
                """;

        Query query = em.createNativeQuery(q);
        query.setParameter(1, id);

        JpaResultMapper mapper = new JpaResultMapper(); // 이거안쓰면 DTO 타입으로 못받아온다.
        mapper.list(query, SkillResponse.ResumeSkillDTO.class);

        return mapper.list(query, SkillResponse.ResumeSkillDTO.class);

    }
}
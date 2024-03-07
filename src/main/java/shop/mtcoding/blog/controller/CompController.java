package shop.mtcoding.blog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.mtcoding.blog.dto.user.UserRequest;
import shop.mtcoding.blog.model.comp.CompRepository;
import shop.mtcoding.blog.model.comp.CompRequest;
import shop.mtcoding.blog.model.jobs.Jobs;
import shop.mtcoding.blog.model.resume.Resume;
import shop.mtcoding.blog.model.resume.ResumeRepository;
import shop.mtcoding.blog.model.scrap.Scrap;
import shop.mtcoding.blog.model.scrap.ScrapRepository;
import shop.mtcoding.blog.dto.scrap.ScrapRequest;
import shop.mtcoding.blog.model.jobs.JobsRepository;
import shop.mtcoding.blog.model.skill.SkillRequest;
import shop.mtcoding.blog.model.user.User;
import shop.mtcoding.blog.model.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CompController {
    private final UserRepository userRepository;
    private final CompRepository compRepository;
    private final JobsRepository jobsRepository;
    private final HttpSession session;

    private final ResumeRepository resumeRepository;

    @GetMapping("/comp/apply")
    public String apply() {
        return "/comp/apply";
    }

    @GetMapping("/comp/{id}/comphome")
    public String compHome(@PathVariable Integer id) {
        //id값으로 공고리스트를 가져오고  배열

        List<Object[]> jobsList = jobsRepository.findAllByUserId(id);
        List<CompRequest.JobsViewDTO> viewDTOList = new ArrayList<>();

        Integer nextNumber = 1;
        CompRequest.JobsViewDTO prevViewDTO = new CompRequest.JobsViewDTO();

        for (int i = 0; i < jobsList.size(); i++) {
            Object[] job = jobsList.get(i);
            if(prevViewDTO.getId() == job[0]){
                // 스킬 색깔 생성
                String color = "";
                if (((String)job[7]).equals("yellow")){
                    color = "badge rounded-pill text-bg-warning";
                }
                if(((String)job[7]).equals("red")){
                    color = "badge rounded-pill text-bg-danger";
                }
                if(((String)job[7]).equals("blue")){
                    color = "badge rounded-pill text-bg-info";
                }
                if(((String)job[7]).equals("green")){
                    color = "badge rounded-pill text-bg-success";
                }
                if(((String)job[7]).equals("green")){
                    color = "badge rounded-pill text-bg-success";
                }
                if(((String)job[7]).equals("green")){
                    color = "badge rounded-pill text-bg-success";
                }

                SkillRequest.CompskillDTO skillDTO = SkillRequest.CompskillDTO.builder().name((String) job[6]).color((String) job[7]).build();

                // 이전에 있던 viewDOT.skillList에 add
                prevViewDTO.getSkillList().add(skillDTO);
                }else{
                // 스킬 리스트 생성
                List<SkillRequest.CompskillDTO> skillList = new ArrayList<>();

                String color = "";
                if (((String)job[7]).equals("yellow")){
                    color = "badge rounded-pill text-bg-warning";
                }
                if(((String)job[7]).equals("red")){
                    color = "badge rounded-pill text-bg-danger";
                }
                if(((String)job[7]).equals("blud")){
                    color = "badge rounded-pill text-bg-info";
                }
                if(((String)job[7]).equals("green")){
                    color = "badge rounded-pill text-bg-success";
                }
                if(((String)job[7]).equals("purple")){
                    color = "badge rounded-pill text-bg-success";
                }
                if(((String)job[7]).equals("green")){
                    color = "badge rounded-pill text-bg-success";
                }

                // 스킬 이름 set
                skillList.add(SkillRequest.CompskillDTO.builder().name((String) job[6]).color(color).build());

                // 새로운 DTO 생성
                prevViewDTO = new CompRequest.JobsViewDTO();
                prevViewDTO.setId((Integer) job[0]);
                prevViewDTO.setUserId((Integer) job[1]);
                prevViewDTO.setCompName((String) job[2]);
                prevViewDTO.setTitle((String) job[3]);
                prevViewDTO.setTask((String) job[4]);
                prevViewDTO.setCareer((String) job[5]);
                prevViewDTO.setNumber(nextNumber++);
                prevViewDTO.setSkillList(skillList);

                viewDTOList.add(prevViewDTO);
            }

        }

        System.out.println(viewDTOList);

        session.setAttribute("jobList", viewDTOList);

        //리스트를 담는 ArrayList 생성
        //세션에 저장
        //머스테치에 뿌림

        return "/comp/comphome";
    }

    @GetMapping("/comp/joinForm")
    public String compJoinForm() {

        return "/comp/joinForm";
    }

    @PostMapping("/comp/join/{role}")
    public String compJoin(@PathVariable int role, UserRequest.UserAllDTO requestDTO){
        requestDTO.setRole(role);
        userRepository.save(requestDTO);
        List<User> userList = userRepository.findAll();
        System.out.println(userList);
        return "redirect:/";
    }



    @GetMapping("/comp/profileUpdateForm")
    public String profileUpdateForm() {
        return "/comp/profileUpdateForm";
    }

//    @GetMapping("/comp/readResume")
//    public String readResume(HttpServletRequest request){
//        List<Resume> resumeList = resumeRepository.findAll();
//
//        request.setAttribute("resumeList2", resumeList);
//
//        return "/comp/readResume";
//    }


    @GetMapping("/comp/scrap")
    public String scrap() {
        return "/comp/scrap";
    }

    @GetMapping("/comp/talent")
    public String talent() {
        return "/comp/talent";
    }

    @GetMapping("/comp/updateForm")
    public String updateForm() {
        return "/comp/updateForm";
    }

    @GetMapping("/comp/jobsInfo")
    public String jobsInfo() {

        return "/comp/jobsInfo";
    }

    @GetMapping("/comp/compResumeDetail")
    public String compResumeDetail() {
        return "/comp/compResumeDetail";
    }
}

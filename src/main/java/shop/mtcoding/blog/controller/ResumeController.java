package shop.mtcoding.blog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.dto.scrap.ScrapResponse;
import shop.mtcoding.blog.model.resume.Resume;
import shop.mtcoding.blog.model.resume.ResumeRepository;
import shop.mtcoding.blog.model.resume.ResumeRequest;
import shop.mtcoding.blog.model.resume.ResumeResponse;
import shop.mtcoding.blog.model.scrap.ScrapRepository;
import shop.mtcoding.blog.model.skill.Skill;
import shop.mtcoding.blog.model.skill.SkillRepository;
import shop.mtcoding.blog.model.skill.SkillResponse;
import shop.mtcoding.blog.model.user.User;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ResumeController {

    private final HttpSession session;
    private final ResumeRepository resumeRepository;
    private final ScrapRepository scrapRepository;
    private final SkillRepository skillRepository;

    //save를 resume과 skill에 2번 해준다
    @PostMapping("/resume/save")
    public String save(ResumeRequest.ResumeWriteDTO requestDTO) {
        System.out.println(requestDTO);

        //return용 id로 뿌릴려고 가져와봄
        User sessionUser = (User) session.getAttribute("sessionUser");

        //resume이력서 저장하고, max값을 받아옴.
        // 이 이력서 max값과 skill 값을 매칭시켜서 같이 저장해야 하기 때문! (1:N)
        // 어떤 이력서에 skill을 저장해야 할 지 알려줘야 하기 때문에 max값(=최신 이력서)을 뽑는다.
        // for문도 여기서 돌리는게 아니다.
        Integer resumeId = resumeRepository.saveResume(requestDTO);

        // 스킬에 저장! requestDTO 중에서 저장이 안 된 것은 List<Strong>이니까 getSkills를 해준다!
        skillRepository.saveSkill(requestDTO.getSkills(), resumeId);


        //이거 터지기 때문에 추후 resumeDTO에서 받아와서 getUserid로 바꿔주던가
        //return "redirect:/resume/"+ sessionUser.getId() +"/manageResume"; 로 해주면 됨
        return "redirect:/resume/"+ sessionUser.getId() +"/manageResume";
    }


    @GetMapping("/resume/{id}/manageResume")
    public String manageResume(@PathVariable Integer id, HttpServletRequest request) {

        //스킬을 넣지 않았을 때의 resume을 (=resumeDTO를) 가져온다.
        //유저 1명이 여러개의 이력서를 작성했을 수도 있기에 List<>로 가져옴
        List<ResumeResponse.ResumeViewDTO> resumeList = resumeRepository.findAllResumeUserId(id);
        System.out.println(resumeList);

        //우리가 아까만든 생성자에 resumeList 값들이 들어간다
        for (int i = 0; i < resumeList.size(); i++) {
            ResumeResponse.ResumeViewDTO dto = resumeList.get(i);
            dto.setSkillList(resumeRepository.findAllByResumeId(dto.getId()));
        }

        request.setAttribute("resumeList", resumeList);
        System.out.println(request);    // 이건 스킬추카하고 나서 리스트

        return "/resume/manageResume";
    }



    @GetMapping("/resume/resumeDetail/{id}")
    public String resumeDetail (@PathVariable Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionComp");

        Resume resumeDTO = resumeRepository.findById(id);
        request.setAttribute("resume", resumeDTO);

        if(sessionUser == null) {
            ScrapResponse.DetailDTO scrapDetailDTO = scrapRepository.findScrap(id);
            request.setAttribute("scrap", scrapDetailDTO);
        } else {
            ScrapResponse.DetailDTO scrapDetailDTO = scrapRepository.findScrap(id, sessionUser.getId());
            request.setAttribute("scrap", scrapDetailDTO);
        }
        return "/resume/resumeDetail";
    }

    @GetMapping("/resume/updateResumeForm")
    public String updateResumeForm () {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if(sessionUser == null){ // 401
            return "redirect:/loginForm";
        }

        return "/resume/updateResumeForm";
    }


    @GetMapping("/resume/writeResumeForm")
    public String writeResumeForm() {

        return "/resume/writeResumeForm";
    }





    @PostMapping("/resume/{id}/delete")
    public String delete(@PathVariable int id, HttpServletRequest request){
        User sessionUser = (User) session.getAttribute("sessionComp");
        if(sessionUser == null){ // 401
            return "redirect:/loginForm";
        }
        Resume resumeDTO = resumeRepository.findById(id);
        resumeRepository.deleteById(id);

        request.setAttribute("resume", resumeDTO);

        return "redirect:/resume/manageResume";

    }

}
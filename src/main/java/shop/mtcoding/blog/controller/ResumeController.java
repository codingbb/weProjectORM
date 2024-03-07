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
    public String save() {

        //이거 터지기 때문에 추후 resumeDTO에서 받아와서 getUserid로 바꿔주던가
        //return "redirect:/resume/"+ sessionUser.getId() +"/manageResume"; 로 해주면 됨
        return "redirect:/resume/manageResume";
    }


    @GetMapping("/resume/{id}/manageResume")
    public String manageResume(@PathVariable Integer id, HttpServletRequest request) {


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
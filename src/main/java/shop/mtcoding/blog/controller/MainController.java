package shop.mtcoding.blog.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import shop.mtcoding.blog.model.jobs.JobResponse;
import shop.mtcoding.blog.model.jobs.Jobs;
import shop.mtcoding.blog.model.jobs.JobsRepository;
import shop.mtcoding.blog.model.page.Page;
import shop.mtcoding.blog.model.page.Paging;
import shop.mtcoding.blog.model.skill.Skill;
import shop.mtcoding.blog.model.skill.SkillRepository;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Controller
public class MainController {
    private final Paging paging;
    private final JobsRepository jobsRepository;
    private final SkillRepository skillRepository;


    @GetMapping("/")
    public String index(HttpServletRequest request, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") String page) {
        int currentPage = Integer.parseInt(page);
        boolean lastPage = paging.lastPage(currentPage);
        boolean firstPage = paging.firstPage(currentPage);
        int totalPages = paging.totalPages();

        List<Page> pageActive = new ArrayList<>();

        // 페이지 네이션에 현재페이지 active기능 넣기
        for (int i = 1; i <= totalPages; i++) {
            Page page1 = new Page();
            page1.setNumber(i);
            page1.setActive(currentPage == i);
            pageActive.add(page1);
        }

        // 한 페이지에 5개씩 출력 - 10개중 현재페이지가 1이면 10부터 6까지, 현재 페이지가 2면 5부터 1까지



        if (keyword.isBlank()) { //isBlank면 검색 안함
            List<JobResponse.DTO> pageList = paging.showPagesV2(currentPage);

            pageList.forEach(dto -> {
                List<Skill> skillList = skillRepository.findAllV2(dto.getId());
                dto.setSkillList(skillList);
            });
            request.setAttribute("keyword", keyword);
            request.setAttribute("pages", pageActive);
            request.setAttribute("firstPage", firstPage);
            request.setAttribute("lastPage", lastPage);
            request.setAttribute("pageList", pageList);
            request.setAttribute("prevPage", Math.max(1, currentPage - 1));
            request.setAttribute("nextPage", Math.min(totalPages, currentPage + 1));
            return "index";

        } else {    //검색하면 키워드를 던져줌
            List<JobResponse.DTO> pageList = paging.showPagesV2(currentPage, keyword);

            pageList.forEach(dto -> {
                List<Skill> skillList = skillRepository.findAllV2(dto.getId());
                dto.setSkillList(skillList);
            });
            request.setAttribute("keyword", keyword);
            request.setAttribute("pages", pageActive);
            request.setAttribute("firstPage", firstPage);
            request.setAttribute("lastPage", lastPage);
            request.setAttribute("pageList", pageList);
            request.setAttribute("prevPage", Math.max(1, currentPage - 1));
            request.setAttribute("nextPage", Math.min(totalPages, currentPage + 1));
            return "index";
        }
    }

}

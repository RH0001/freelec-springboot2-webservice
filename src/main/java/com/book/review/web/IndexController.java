package com.book.review.web;

import com.book.review.config.auth.dto.SessionUser;
import com.book.review.service.PostsService;
import com.book.review.web.dto.PostsResponseDto;
import com.book.review.web.dto.PostsUpdateRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;
    private SessionUser user;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());

        user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("myUserName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String save(Model model) {
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("posts", dto);
        return "posts-update";
    }


}

package com.javarush.alimin.controller;

import com.javarush.alimin.domain.Task;
import com.javarush.alimin.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String tasks(Model model
            , @RequestParam(value = "page", required = false, defaultValue = "1") int page
            , @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {

        List<Task> tasks = taskService.getAll((page - 1) * limit, limit);
        model.addAttribute("tasks", tasks);
        model.addAttribute("current_page", page);
        int pagesCount = (int) Math.ceil(1.0 * taskService.getAllCount() / limit);
        if (pagesCount > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pagesCount).boxed().toList();
            model.addAttribute("page_numbers", pageNumbers);
        }
        return "tasks";
    }

    @PostMapping("/{id}")
    public String edit(Model model
            , @PathVariable Integer id
            , @RequestBody TaskDTO dto) {

        if (isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id");
        }

        taskService.edit(id, dto.getDescription(), dto.getStatus());

        return tasks(model, 1, 10);
    }

    @PostMapping("/")
    public String add(Model model
            , @RequestBody TaskDTO dto) {

        taskService.create(dto.getDescription(), dto.getStatus());
        return tasks(model, 1, 10);
    }

    @DeleteMapping("/{id}")
    public String delete(Model model
            , @PathVariable Integer id) {

        if (isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id");
        }

        taskService.delete(id);
        return tasks(model, 1, 10);
    }

}

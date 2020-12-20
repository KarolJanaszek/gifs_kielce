package pl.akademiakodu.gifs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.akademiakodu.gifs.model.Category;
import pl.akademiakodu.gifs.model.Gif;
import pl.akademiakodu.gifs.service.CategoryService;
import pl.akademiakodu.gifs.service.GifService;


import java.util.List;

//toDo Obsługa wyszukiwania w samych 'favorites' -> jeden GetMapping z jedną metodą w GifService
//toDo Zakładka: 'Categories' -> W kontrolerze, nowy dla Category, który obsługuje '/categoie' i '/categorie/{id}'.

@Controller
public class GifController {
    private GifService gifService;
    private CategoryService categoryService;


    //@Autowired tworzy nowy obiekt zamista słow new GifService().
    @Autowired
    public GifController(GifService gifService, CategoryService categoryService) {
        this.gifService = gifService;
        this.categoryService = categoryService;

    }

    @GetMapping("/")
    public String home(Model model, @RequestParam(required = false) String q) {

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        if (q == null) {
            model.addAttribute("gifs", gifService.getGifs());
        } else {
            model.addAttribute("gifs", gifService.findGif(q));
        }
        return "home";
    }

    @PostMapping("/")
    public String addGif(@RequestParam("file") MultipartFile file, @RequestParam(value = "categoryId", defaultValue = "1") Long categoryId,
                         RedirectAttributes redirectAttributes) {
        if (file.getContentType().equals("image/gif")) {
            gifService.addGif(file, categoryId);
            redirectAttributes.addFlashAttribute("message",
                    "You have successfully uploaded " + file.getOriginalFilename() + "!");
            redirectAttributes.addFlashAttribute("success",true);
        } else {
            redirectAttributes.addFlashAttribute("message",
                    "Wrong file's type. Please upload only '.gif' file.");
            redirectAttributes.addFlashAttribute("success",false);
        }

        return "redirect:/";
    }

    @GetMapping("/gif/{name}")
    public String getGif(Model model, @PathVariable String name) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("editedGif", gifService.findGifByName(name));
        return "gif-details";
    }

    @PostMapping("/gif/{name}")
    public String changeTag(@ModelAttribute Gif editedGif) {
        gifService.changeTag(editedGif);
        return "redirect:/gif/{name}";
    }

    @GetMapping("/gif/{name}/delete")
    public String delete(@PathVariable String name, RedirectAttributes redirectAttributes) {

        Long id = gifService.findGifByName(name).getId();
        gifService.deleteGif(id);

        redirectAttributes.addFlashAttribute("message",
                "You have successfully deleted gif: " + name + ".");
        redirectAttributes.addFlashAttribute("success",true);
        return "redirect:/";
    }

    @PostMapping("/gif/{name}/updateCategory")
    public String changeCategory(@ModelAttribute Gif editedGif) {
        gifService.changeCategory(editedGif);
        return "redirect:/gif/{name}";
    }

    @GetMapping("/gif/{name}/favorite")
    public String toggleFav(@PathVariable String name, @RequestParam(required = false, defaultValue = "") String r) {
        Gif gif = gifService.findGifByName(name);
        gifService.toggleFavorite(gif);
        if (r.equals("details")) {
            return "redirect:/gif/{name}";
        } else if (r.equals("favorites")) {
            return "redirect:/favorites";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/favorites")
    public String getFavorites(Model model) {
        model.addAttribute("favoritesGifs", gifService.findFavorites());
        return "favorites";
    }

}

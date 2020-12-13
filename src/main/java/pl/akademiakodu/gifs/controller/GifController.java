package pl.akademiakodu.gifs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.akademiakodu.gifs.model.Category;
import pl.akademiakodu.gifs.model.Gif;
import pl.akademiakodu.gifs.service.CategoryService;
import pl.akademiakodu.gifs.service.GifService;
import pl.akademiakodu.gifs.service.StorageService;

import java.util.List;

//toDo Obsługa wyszukiwania w samych 'favorites' -> jeden GetMapping z jedną metodą w GifService
//toDo Zakładka: 'Categories' -> W kontrolerze, nowy dla Category, który obsługuje '/categoie' i '/categorie/{id}'.

@Controller
public class GifController {
    private GifService gifService;
    private CategoryService categoryService;
    private StorageService storageService;

    //@Autowired tworzy nowy obiekt zamista słow new GifService().
    @Autowired
    public GifController(GifService gifService, CategoryService categoryService, StorageService storageService) {
        this.gifService = gifService;
        this.categoryService = categoryService;
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String home(Model model, @RequestParam(required = false) String q) {
        if (q == null) {
            model.addAttribute("gifs", gifService.getGifs());
        } else {
            model.addAttribute("gifs", gifService.findGif(q));
        }
        return "home";
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

    @PostMapping("/gif/{name}/updateCategory")
    public String changeCategory(@ModelAttribute Gif editedGif) {
        gifService.changeCategory(editedGif);

        //gdyby operować na {id} a nie na {name}, to wyglądało by to tak:
        //gifService.changeCategory(editedGif);
        //co byłoby lepsze.
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

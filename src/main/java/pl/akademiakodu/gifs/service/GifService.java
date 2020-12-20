package pl.akademiakodu.gifs.service;

import org.springframework.web.multipart.MultipartFile;
import pl.akademiakodu.gifs.model.Gif;

import java.util.List;

public interface GifService {
    List<Gif> getGifs();
    List<Gif> findGif(String name);
    Gif findGifByName(String name);
    void changeTag(Gif gif);
    void toggleFavorite(Gif gif);
    List<Gif> findFavorites();
    void addGif(MultipartFile file, Long categoryId);
    void deleteGif(Long id);

    void changeCategory(Gif editedGif);
}

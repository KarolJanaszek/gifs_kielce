package pl.akademiakodu.gifs.repository;

import pl.akademiakodu.gifs.model.Gif;

import java.util.List;

//CRUD
public interface GifDao {
    void createGif(Gif gif);
    List<Gif> findAllGifs();
    void updateGif(Gif gif);
    void deleteGif(Long id);


    Gif findGifByNameSql(String name);
}

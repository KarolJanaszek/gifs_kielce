package pl.akademiakodu.gifs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.akademiakodu.gifs.model.Gif;
import pl.akademiakodu.gifs.repository.GifDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GifServiceImpl implements GifService {

    private GifDao gifDao;
    private StorageService storageService;

    @Autowired
    public GifServiceImpl(GifDao gifDao, StorageService storageService) {
        this.gifDao = gifDao;
        this.storageService = storageService;
    }

    @Override
    public List<Gif> getGifs() {
        return gifDao.findAllGifs();
    }

    @Override
    public List<Gif> findGif(String name) {
        return getGifs().stream().filter(gif ->
                gif.getTag() != null ? gif.getTag().equals(name) : gif.getName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public Gif findGifByName(String name) {
        return gifDao.findGifByNameSql(name);
    }

    @Override
    public void changeTag(Gif gif) {
        gif.setFavorite(null);
        gif.setCategoryId(null);

        gifDao.updateGif(gif);
    }

    @Override
    public void toggleFavorite(Gif gif) {
        gif.setFavorite(!gif.isFavorite());

        gif.setCategoryId(null);
        gif.setTag(null);

        gifDao.updateGif(gif);
    }

    @Override
    public List<Gif> findFavorites() {
        return getGifs().stream().filter(Gif::isFavorite).collect(Collectors.toList());
    }

    @Override
    public void addGif(MultipartFile file, Long categoryId) {
        String name = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf('.'));
        Gif newGif = new Gif(name, null, false, categoryId);
        storageService.uploadFile(file);
        gifDao.createGif(newGif);
    }

    @Override
    public void deleteGif(Long id) {
        gifDao.deleteGif(id);
    }

    @Override
    public void changeCategory(Gif gif) {
        gif.setFavorite(null);
        gif.setTag(null);

        gifDao.updateGif(gif);
    }

}

package com.none.no_name.domain.playList.controller.convert;


import com.none.no_name.domain.playList.service.sort.PlayListSort;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PlayListMusicConverter implements Converter<String, PlayListSort> {

    @Override
    public PlayListSort convert(String source) {
        return PlayListSort.fromUrl(source);
    }
}

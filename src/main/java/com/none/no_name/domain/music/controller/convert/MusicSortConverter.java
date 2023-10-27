package com.none.no_name.domain.music.controller.convert;


import com.none.no_name.domain.music.dto.MusicSort;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MusicSortConverter implements Converter<String, MusicSort> {

    @Override
    public MusicSort convert(String source) {
        return MusicSort.fromUrl(source);
    }
}

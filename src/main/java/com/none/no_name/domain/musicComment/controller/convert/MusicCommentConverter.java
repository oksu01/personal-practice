package com.none.no_name.domain.musicComment.controller.convert;

import com.none.no_name.domain.musicComment.dto.CommentSort;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class MusicCommentConverter implements Converter<String, CommentSort>

{


    @Override
    public CommentSort convert(String source) {
        return CommentSort.fromUrl(source);
    }
}

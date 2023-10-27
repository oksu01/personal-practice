package com.none.no_name.domain.playListComment.controller.convert;


import com.none.no_name.domain.playListComment.service.sort.PlayListCommentSort;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PlayListCommentsConvert implements Converter<String, PlayListCommentSort> {

    @Override
    public PlayListCommentSort convert(String source) {
        return PlayListCommentSort.fromUrl(source);
    }
}

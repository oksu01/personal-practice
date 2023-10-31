package com.none.no_name.domain.playList.service;


import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.music.repository.MusicRepository;
import com.none.no_name.domain.playList.dto.PlayListCreateApi;
import com.none.no_name.domain.playList.dto.PlayListPatchApi;
import com.none.no_name.domain.playList.dto.PlayListInfo;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playList.repository.PlayListRepository;
import com.none.no_name.domain.playList.service.sort.PlayListSort;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.music.MusicNotFoundException;
import com.none.no_name.global.exception.business.playList.PlayListNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayListService {

    private final PlayListRepository playListRepository;
    private final MusicRepository musicRepository;
    private final MemberRepository memberRepository;

    public Long createPlayList(Long musicId, Long loginMemberId, PlayListCreateApi request) {

        Member member = verifiedMember(loginMemberId);
        Music music = verifiedMusic(musicId);

        PlayList playList = PlayList.createPlayList(music, member, request);

        playListRepository.save(playList);

        return playList.getPlayListId();
    }

    public PlayListInfo getPlayList(Long playListId, Long loginMemberId, PlayListInfo request) {

        verifiedPlayList(playListId);

        verifiedMember(loginMemberId);

        playListRepository.findById(playListId).orElseThrow(PlayListNotFoundException::new);

        return PlayListInfo.builder()
                .playListId(request.getPlayListId())
                .memberId(request.getMemberId())
                .title(request.getTitle())
                .coverImg(request.getCoverImg())
                .tags(request.getTags())
                .likes(request.getLikes())
                .createdDate(request.getCreatedDate())
                .modifiedDate(request.getModifiedDate())
                .build();
    }

    public Page<PlayListInfo> getPlayLists(Long playListId, Long loginMemberId, int page, int size, PlayListSort sort) {

        Sort sorting = (sort == PlayListSort.Likes)
                ? Sort.by(Sort.Direction.DESC, "like", "createdDate")
                : Sort.by(Sort.Direction.DESC, "createdDate");

        PageRequest pageRequest = PageRequest.of(page, size, sorting);

        Page<PlayList> playLists = playListRepository.findAllByPlayListId(playListId, pageRequest);

        Page<PlayListInfo> playListInfoPage = playLists.map(playList ->
                PlayListInfo.builder()
                        .playListId(playListId)
                        .memberId(loginMemberId)
                        .title(playList.getTitle())
                        .coverImg(playList.getCoverImg())
                        .tags(playList.getTags())
                        .likes(playList.getLikes())
                        .createdDate(playList.getCreatedDate())
                        .modifiedDate(playList.getModifiedDate())
                        .build());

        return playListInfoPage;
    }

    public void updatePlayList(Long playListId, Long loginMemberId, PlayListPatchApi request) {

        verifiedMember(loginMemberId);
        verifiedPlayList(playListId);

        PlayList.updatePlayList(request.getTitle(), request.getCoverImg(), request.getContent());
    }

    public void deletePlayList(Long playListId, Long loginMemberId) {

        verifiedMember(loginMemberId);

        playListRepository.deleteById(playListId);
    }

    public void deleteMusicInPlayList(Long musicId, Long loginMemberId) {

        verifiedMember(loginMemberId);

        musicRepository.deleteById(musicId);
    }

    public void addMusic(Long musicId, Long playListId, Long loginMemberId, MusicInfo musicInfo) {

        verifiedMember(loginMemberId);
//        verifiedPlayList(playListId);

        Music music = Music.addMusic(musicId, playListId, musicInfo);

        musicRepository.save(music);
    }

    public Member verifiedMember(Long loginMemberId) {

        return memberRepository.findById(loginMemberId).orElseThrow(MemberAccessDeniedException::new);
    }

    public Music verifiedMusic(Long musicId) {

        return musicRepository.findById(musicId).orElseThrow(MusicNotFoundException::new);
    }

    public void verifiedPlayList(Long playListId) {

        playListRepository.findById(playListId).orElseThrow(PlayListNotFoundException::new);
    }




}

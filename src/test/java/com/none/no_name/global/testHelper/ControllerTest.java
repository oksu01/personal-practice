package com.none.no_name.global.testHelper;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.none.no_name.domain.memberMusic.controller.MemberMusicController;
import com.none.no_name.domain.memberMusic.service.MemberMusicService;
import com.none.no_name.domain.music.controller.MusicController;
import com.none.no_name.domain.music.service.MusicService;
import com.none.no_name.domain.musicComment.controller.MusicCommentController;
import com.none.no_name.domain.musicComment.service.MusicCommentService;
import com.none.no_name.domain.musicLike.controller.MusicLikeController;
import com.none.no_name.domain.musicLike.service.MusicLikeService;
import com.none.no_name.domain.musicTag.controller.MusicTagController;
import com.none.no_name.domain.musicTag.service.MusicTagService;
import com.none.no_name.domain.playList.controller.PlayListController;
import com.none.no_name.domain.playList.service.PlayListService;
import com.none.no_name.domain.playListComment.controller.PlayListCommentController;
import com.none.no_name.domain.playListComment.service.PlayListCommentService;
import com.none.no_name.domain.playListLike.controller.PlayListLikeController;
import com.none.no_name.domain.playListLike.service.PlayListLikeService;
import com.none.no_name.domain.playListMusic.entity.PlayListMusic;
import com.none.no_name.domain.playListMusic.service.PlayListMusicService;
import com.none.no_name.domain.playListTag.controller.PlayListTagController;
import com.none.no_name.domain.playListTag.service.PlayListTagService;
import com.none.no_name.domain.search.controller.SearchController;
import com.none.no_name.domain.search.service.SearchService;
import com.none.no_name.domain.tag.controller.TagController;
import com.none.no_name.domain.tag.service.TagService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.metadata.BeanDescriptor;
import jakarta.validation.metadata.ConstraintDescriptor;
import jakarta.validation.metadata.PropertyDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest({
        MemberMusicController.class,
        MusicController.class,
        MusicCommentController.class,
        MusicLikeController.class,
        MusicTagController.class,
        PlayListController.class,
        PlayListCommentController.class,
        PlayListLikeController.class,
        PlayListMusic.class,
        PlayListTagController.class,
        SearchController.class,
        TagController.class
})
@ExtendWith({RestDocumentationExtension.class})
//@ActiveProfiles("local")
public class ControllerTest {
    @MockBean
    protected MemberMusicService memberMusicService;
    @MockBean
    protected MusicService musicService;
    @MockBean
    protected MusicCommentService musicCommentService;
    @MockBean
    protected MusicLikeService musicLikeService;
    @MockBean
    protected MusicTagService musicTagService;
    @MockBean
    protected PlayListService playListService;
    @MockBean
    protected PlayListCommentService playListCommentService;
    @MockBean
    protected PlayListLikeService playListLikeService;
    @MockBean
    protected PlayListMusicService playListMusicService;
    @MockBean
    protected PlayListTagService playListTagService;
    @MockBean
    protected SearchService searchService;
    @MockBean
    protected TagService tagService;

    // 컨트롤러 테스트에 필요한 것들
    @Autowired
    protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper; // json으로 변환
    @Autowired private MessageSource messageSource; //

    // 문서 생성 및 제어 기능을 제공하는 클래스
    protected RestDocumentationResultHandler documentHandler;
    // BeanDescriptor에 할당하기 위한 Validator 객체를 생성
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator(); // 제약 조건 정보를 가진 객체
    // 제약 조건을 포함하고 있는 객체
    private BeanDescriptor beanDescriptor;

    protected final String AUTHORIZATION = "Authorization";              

    // 공통 전처리
    @BeforeEach
    void setUp(WebApplicationContext context,
               final RestDocumentationContextProvider restDocumentation,
               TestInfo testInfo) {


        // 현재 테스트 클래스명을 가져와서 ControllerTest 부분을 지우고 실제 클래스명을 얻음
        String className = testInfo.getTestClass().orElseThrow().getSimpleName()
                .replace("ControllerTest", "").toLowerCase();
        String methodName = testInfo.getTestMethod().orElseThrow().getName().toLowerCase();

        documentHandler = document(
                // 클래스명으로 디렉터리를 생성하고 메서드명.adoc 파일 생성
                className + "/" + methodName,
                // 문서 포맷을 이쁘게 설정
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        );

        DefaultMockMvcBuilder mockMvcBuilder = webAppContextSetup(context) // mockMvc 초기화
                .apply(documentationConfiguration(restDocumentation)) // 문서화 설정 적용
                .addFilters(new CharacterEncodingFilter("UTF-8", true)); // 인코딩


        //validation 은 문서화하지 않음
        if(!methodName.contains("validation")){
            mockMvcBuilder.alwaysDo(documentHandler);
        }

        mockMvc = mockMvcBuilder.build();
    }

    // beanDescriptor에 제약 조건을 가진 validator 객체를 할당
    // 이 메서드를 사용해야 특정 클래스의 제약 조건을 가져올 수 있음 (중요!)
    protected void setConstraintClass(Class<?> clazz){
        this.beanDescriptor = (BeanDescriptor) validator.getConstraintsForClass(clazz);
    }

    // 제약 조건을 가져오는 메서드
    protected Attributes.Attribute getConstraint(String value){
        // 제약 조건을 가지고 있는 beanDescriptor를 사용
        assert(beanDescriptor != null) : "constraint 설정이 되어있지 않습니다. setConstraintClass() 를 통해 설정해주세요 ";

        // 주어진 속성(value)의 제약 조건 검색
        PropertyDescriptor propertyDescriptor = beanDescriptor.getConstraintsForProperty(value);

        StringBuilder sb = new StringBuilder();

        if(propertyDescriptor == null){
            return new Attributes.Attribute("constraints", sb.toString());
        }

        // PropertyDescriptor에 저장한 제약 조건들을 Set<ConstraintDescriptor<?>>에 저장
        Set<ConstraintDescriptor<?>> constraintDescriptors = propertyDescriptor.getConstraintDescriptors();

        // Set을 순회하며 제약 조건의 정보를 추출
        for (ConstraintDescriptor<?> constraintDescriptor : constraintDescriptors) {

            // 현재 속성의 NotNull 같은 어노테이션의 타입을 가져옴
            String type = constraintDescriptor.getAnnotation().annotationType().getSimpleName();

            // 현재 속성의 message, min, max 속성을 가져옴
            String message = (String) constraintDescriptor.getAttributes().get("message");
            Integer min = (Integer) constraintDescriptor.getAttributes().get("min");
            Integer max = (Integer) constraintDescriptor.getAttributes().get("max");

            // 실제 메시지를 가져옴
            String actualMessage = getActualMessage(message, min, max);

            sb.append(" [");
            sb.append(type);
            sb.append(" : ");
            sb.append(actualMessage);
            sb.append("] ");
        }

        return new Attributes.Attribute("constraints", sb.toString());
    }

    // 제약 조건의 실제 메시지를 가져오는 메서드
    protected String getActualMessage(String messageKey, Integer min, Integer max) {
        // messageSource를 사용해 실제 메시지를 가져오기 전에 중괄호 제거
        String actualMessageKey = messageKey.replace("{", "").replace("}", "");

        // 현재 언어에 맞는 실제 메시지를 가져옴
        String message = messageSource.getMessage(actualMessageKey, null, Locale.getDefault());

        if(min == null || max == null){
            return message;
        }

        return message.replace("{min}", min.toString()).replace("{max}", max.toString());
    }

    protected <T> Page<T> createPage(List<T> contents, int page, int size, int totalElements) {
        return new PageImpl<>(contents, PageRequest.of(page, size), totalElements);
    }

}

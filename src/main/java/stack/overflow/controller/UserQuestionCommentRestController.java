package stack.overflow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import stack.overflow.model.api.Data;
import stack.overflow.model.dto.request.QuestionCommentRequestDto;
import stack.overflow.model.dto.response.QuestionCommentResponseDto;
import stack.overflow.model.entity.QuestionComment;
import stack.overflow.model.enumeration.SortType;
import stack.overflow.model.mapper.QuestionCommentMapper;
import stack.overflow.model.pagination.Page;
import stack.overflow.model.pagination.PaginationParameters;
import stack.overflow.service.dto.QuestionCommentResponseDtoService;
import stack.overflow.service.entity.QuestionCommentService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.net.URI;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/question-comments")
public class UserQuestionCommentRestController {
    private final QuestionCommentService service;
    private final QuestionCommentResponseDtoService dtoService;
    private final QuestionCommentMapper questionCommentMapper;
    private static final String BASE_URI = "/api/v1/user/question-comments";
    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody @Valid @NotNull QuestionCommentRequestDto dto,
                                              @NotNull Authentication authentication) {
        QuestionComment questionComment = questionCommentMapper.dtoAndAuthenticationToQuestionComment(dto, authentication);
        QuestionComment savedQuestionComment = service.create(questionComment);
        Long generatedId = savedQuestionComment.getId();
        URI location = URI.create(BASE_URI + "/" + generatedId);
        return ResponseEntity
                .created(location)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable @Positive @NotNull Long id,
                                              @NotNull Authentication authentication) {
        service.deleteByIdIfOwner(id, authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Data<QuestionCommentResponseDto>> getComment(@PathVariable @Positive @NotNull
                                                                           @NotNull Long id) {
        QuestionCommentResponseDto responseDto = dtoService.getById(id);
        Data<QuestionCommentResponseDto> responseData = Data.build(responseDto);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<Data<Page<QuestionCommentResponseDto>>> getPage(@PathVariable @Positive @NotNull Integer pageNumber,
                                                                          @RequestParam(defaultValue = "20") @NotNull Integer pageSize,
                                                                          @RequestParam(defaultValue = "ID_ASC") @NotNull SortType sortType,
                                                                          @RequestParam(required = false) String text) {
        PaginationParameters params = PaginationParameters.builder(pageNumber)
                .withPageSize(pageSize)
                .withSortType(sortType)
                .withText(text)
                .build();
        Page<QuestionCommentResponseDto> page = dtoService.getPage(params);
        Data<Page<QuestionCommentResponseDto>> responseData = Data.build(page);
        return ResponseEntity.ok(responseData);
    }
}

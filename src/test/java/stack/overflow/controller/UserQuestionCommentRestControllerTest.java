package stack.overflow.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import stack.overflow.IntegrationTestContext;
import stack.overflow.controller.expectationTesters.ExpectationTester;
import stack.overflow.controller.expectationTesters.GetCommentExpectationTester;
import stack.overflow.controller.expectationTesters.GetCommentPageExpectationTester;
import stack.overflow.model.dto.request.QuestionCommentRequestDto;
import stack.overflow.util.TestUtil;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserQuestionCommentRestControllerTest extends IntegrationTestContext {
    private static final String testUsername = "mickey_m";
    private static final String testPassword = "mickey";
    private final String token;
    private static final String BASE_URI = "/api/v1/user/question-comments/";
    private static final String BASE_SCRIPT_PATH = "/sql/UserQuestionCommentRestControllerTest/";
    private ExpectationTester expectationTester;

    @Autowired
    public UserQuestionCommentRestControllerTest(TestUtil testUtil) throws Exception {
        token = testUtil.getToken(testUsername, testPassword);
    }

    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "CreateCommentTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "CreateCommentTest/after.sql")
    @Test
    public void createCommentTest() throws Exception {
        QuestionCommentRequestDto dto =
                new QuestionCommentRequestDto("text", 1L);
        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(qc.id) = 1
                                FROM QuestionComment qc
                                JOIN qc.owner ow
                                JOIN qc.question q
                                WHERE qc.text = 'text'
                                AND q.createdDate IS NOT NULL
                                AND q.modifiedDate IS NOT NULL
                                AND ow.id = 1
                                """, Boolean.class)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "DeleteCommentTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "DeleteCommentTest/after.sql")
    public void deleteCommentTest() throws Exception {
        mockMvc.perform(delete(BASE_URI + 1)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk());
        assertTrue(entityManager.createQuery("""
                        SELECT COUNT(qc.id) = 0
                        FROM QuestionComment qc
                        """, Boolean.class)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetCommentTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetCommentTest/after.sql")
    public void getCommentTest() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + 1)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentExpectationTester.Builder(response)
                .setExpectedOwnerUsername("mickey")
                .build();

        expectationTester.test();
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/after.sql")
    public void getPageOneDefaultTest() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + "page/" + 1)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentPageExpectationTester.Builder(response)
                .setExpectedPageCount(10)
                .setExpectedPageDtoListSize(10)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }
    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/after.sql")
    public void getPageOneSizeFiveTest() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + "page/" + 1)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("pageSize", "5"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentPageExpectationTester.Builder(response)
                .setExpectedPageCount(10)
                .setExpectedPageDtoListSize(5)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }
    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/before.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, value = BASE_SCRIPT_PATH + "GetPageTest/after.sql")
    public void getPageTwoSizeFiveTest() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URI + "page/" + 2)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("pageSize", "5"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        expectationTester = new GetCommentPageExpectationTester.Builder(response)
                .setExpectedPageCount(10)
                .setExpectedPageDtoListSize(5)
                .setExpectedFirstCommentId(6)
                .setExpectedOwnerUsername("mickey_m")
                .build();

        expectationTester.test();
    }
}
